package gameLaby.laby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * classe labyrinthe. represente un labyrinthe avec
 * <ul> des murs </ul>
 * <ul> un personnage (x,y) </ul>
 */
public class Labyrinthe {

    /**
     * Constantes char
     */
    public static final char MUR = 'X';
    public static final char PJ = 'P';
    public static final char VIDE = '.';
    public static final char MONSTRE = 'M';
    public static final char PASSAGE = '#';
    public static final char BOUTON = 'o';
    public static final char BOUTONFERME = 'f';

    /**
     * constantes actions possibles
     */
    public static final String HAUT = "Haut";
    public static final String BAS = "Bas";
    public static final String GAUCHE = "Gauche";
    public static final String DROITE = "Droite";

    /**
     * attribut du personnage
     */
    public Perso pj;
    /**
     * attribut du monstre
     */
    public ArrayList<Monstre> monstre;

    /**
     * attribut du passage secret
     */
    public PassageSecret passageSecret;
    public BoutonOuverture boutonOuverture;
    public BoutonFermeture boutonFermeture;

    /**
     * les murs du labyrinthe
     */
    public boolean[][] murs;

    /**
     * retourne la case suivante selon une actions
     *
     * @param x      case depart
     * @param y      case depart
     * @param action action effectuee
     * @return case suivante
     */
    static int[] getSuivant(int x, int y, String action) {
        switch (action) {
            case HAUT:
                // on monte une ligne
                y--;
                break;
            case BAS:
                // on descend une ligne
                y++;
                break;
            case DROITE:
                // on augmente colonne
                x++;
                break;
            case GAUCHE:
                // on augmente colonne
                x--;
                break;
            default:
                throw new Error("action inconnue");
        }
        int[] res = {x, y};
        return res;
    }

    /**
     * charge le labyrinthe
     *
     * @param nom nom du fichier de labyrinthe
     * @return labyrinthe cree
     * @throws IOException probleme a la lecture / ouverture
     */
    public Labyrinthe(String nom) throws IOException {
        // ouvrir fichier
        FileReader fichier = new FileReader(nom);
        BufferedReader bfRead = new BufferedReader(fichier);

        int nbLignes, nbColonnes;
        // lecture nblignes
        nbLignes = Integer.parseInt(bfRead.readLine());
        // lecture nbcolonnes
        nbColonnes = Integer.parseInt(bfRead.readLine());

        // creation labyrinthe vide
        this.murs = new boolean[nbColonnes][nbLignes];
        this.pj = null;
        this.monstre = new ArrayList<Monstre>();
        this.passageSecret = null;
        this.boutonOuverture = null;


        // lecture des cases
        String ligne = bfRead.readLine();

        // stocke les indices courants
        int numeroLigne = 0;

        Position pos_passage = null;
        Position pos_bouton = null;
        Position pos_bouton_ferme = null;
        // parcours le fichier
        while (ligne != null) {

            // parcours de la ligne
            for (int colonne = 0; colonne < ligne.length(); colonne++) {
                char c = ligne.charAt(colonne);
                switch (c) {
                    case MUR:
                        this.murs[colonne][numeroLigne] = true;
                        break;
                    case VIDE:
                        this.murs[colonne][numeroLigne] = false;
                        break;
                    case PJ:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute PJ
                        this.pj = new Perso(colonne, numeroLigne);
                        break;
                    case MONSTRE:
                        this.murs[colonne][numeroLigne] = false;
                        // On ajoute le monstre si il y en a un
                        this.monstre.add(new Monstre(colonne, numeroLigne));
                        break;
                    case PASSAGE:
                        this.murs[colonne][numeroLigne] = false;
                        pos_passage = new Position(colonne, numeroLigne);
                        break;
                    case BOUTON:
                        this.murs[colonne][numeroLigne] = false;
                        pos_bouton = new Position(colonne, numeroLigne);
                        break;
                    case BOUTONFERME:
                        this.murs[colonne][numeroLigne] = false;
                        pos_bouton_ferme = new Position(colonne, numeroLigne);
                        break;
                    default:
                        throw new Error("caractere inconnu " + c);
                }
            }


            // lecture
            ligne = bfRead.readLine();
            numeroLigne++;
        }
        //si on a trouvé un passage secret dans le fichier texte
        if (pos_passage != null){
            this.passageSecret = new PassageSecret(pos_passage);
        }
        if (pos_bouton != null) {
            this.boutonOuverture = new BoutonOuverture(pos_bouton, passageSecret);
        }
        if (pos_bouton_ferme != null) {
            this.boutonFermeture = new BoutonFermeture(pos_bouton_ferme, passageSecret);
        }

        // ferme fichier
        bfRead.close();
    }


    /**
     * deplace le personnage en fonction de l'action.
     * gere la collision avec les murs
     *
     * @param action une des actions possibles
     */
    public void deplacerPerso(String action) {
        // case courante
        int[] courante = {this.pj.getPos().getX(), this.pj.getPos().getY()};

        // calcule case suivante
        int[] suivante = getSuivant(courante[0], courante[1], action);
        // si c'est pas un mur ou un monstre, on effectue le deplacement
        if (this.deplacementPossible(suivante)) {
            // on met a jour personnage
            this.pj.getPos().setX(suivante[0]);
            this.pj.getPos().setY(suivante[1]);
            // on vérifie si le perso est sur un bouton
            if (boutonOuverture != null) {
                if (this.pj.getPos().posEquals(this.boutonOuverture.getPos())) {
                    this.boutonOuverture.activerPassage();
                }
            }
            if (boutonFermeture != null) {
                if (this.pj.getPos().posEquals(this.boutonFermeture.getPos())) {
                    this.boutonFermeture.fermer();
                }
            }
            // deplace tous les monstres si le perso bouge
            for (Monstre value : monstre) {
                deplacerMonstre(value);
            }
        }

    }

    /**
     * Methode permettant de deplacer le monstre selon les deplacements du joueur
     */
    public void deplacerMonstre(Monstre m){
        // On choisi un nombre au hasard
        Random random = new Random();
        int aleaNb = random.nextInt(4);

        String action = "";

        // Selon le nombre aleatoire, le monstre va se deplacer dans une direction
        switch (aleaNb){
            case 0:
                action = "Haut";
                break;
            case 1:
                action = "Bas";
                break;
            case 2:
                action = "Droite";
                break;
            case 3:
                action = "Gauche";
                break;
        }

        // case courante
        int[] courante = {m.getPos().getX(), m.getPos().getY()};

        // calcule case suivante
        int[] suivante = getSuivant(courante[0], courante[1], action);

        // si c'est pas un mur ou un monstre, on effectue le deplacement

        if (this.deplacementPossible(suivante)) {
            // on met a jour le monstre
            m.getPos().setX(suivante[0]);
            m.getPos().setY(suivante[1]);
        }

    }


    /**
     * Methode permettant de savoir si la case suivante est dispo
     * @param suivant la case a selectionner
     * @return Si le deplacement est possible sur la case selectionnee
     */
    public boolean deplacementPossible(int[] suivant) {
        boolean valide = true;
        //on vérifie si on ne se déplace pas sur un mur
        if (!this.murs[suivant[0]][suivant[1]]) {
            // on vérifie si la position n'est pas la même que le personnage
            if (this.pj.getPos().posEquals(suivant[0], suivant[1])){
                valide = false;
            }
            else {
                //s'il existe un passage secret, on vérifie si le passage secret est ouvert ou non
                if (this.passageSecret != null) {
                    if (this.passageSecret.getPos().posEquals(suivant[0], suivant[1])) {
                        valide = this.passageSecret.etreOuvert();
                    }
                }
                //s'il y a des monstres, on vérifie que le déplcement n'est pas sur un monstre
                if (this.monstre != null) {
                    //parcours tous les monstres
                    for (Monstre value : this.monstre) {
                        //test si la place est libre
                        if (value.getPos().posEquals(suivant[0], suivant[1])) {
                            valide = false;
                            break;
                        }
                    }
                }
            }
        }
        else{
            //s'il y a un mur
            valide = false;
        }
        return valide;
    }



    /**
     * jamais fini
     *
     * @return fin du jeu
     */
    public boolean etreFini() {
        return false;
    }

    // ##################################
    // GETTER
    // ##################################

    /**
     * return taille selon Y
     *
     * @return
     */
    public int getLengthY() {
        return murs[0].length;
    }

    /**
     * return taille selon X
     *
     * @return
     */
    public int getLength() {
        return murs.length;
    }

    /**
     * return mur en (i,j)
     *
     * @param x
     * @param y
     * @return
     */
    public boolean getMur(int x, int y) {
        // utilise le tableau de boolean
        return this.murs[x][y];
    }

    public char getChar(int x, int y) {
        char valeurCase;
        //si la case contient le personnage
        if (this.pj.getPos().posEquals(x, y)) {
            valeurCase = PJ;
        } else {
            //sinon si la case contient la sortie
            if (this.passageSecret.getPos().posEquals(x, y)) {
                valeurCase = PASSAGE;
            } else {
                if (this.boutonOuverture.getPos().posEquals(x, y)) {
                    valeurCase = BOUTON;
                } else {
                    //si la case est vide
                    if (!this.murs[x][y]) {
                        valeurCase = VIDE;
                    }
                    //sinon la case est un mur
                    else {
                        valeurCase = MUR;
                    }
                }
            }
        }
        return valeurCase;
    }

    public String toString() {

        String res = "";

        res += murs.length+"\n";
        res += murs[0].length+"\n";
        for (int i = 0; i < murs[0].length; i++) {
            for (int j = 0; j < murs.length; j++) {
                res += getChar(j, i);
            }
            res += "\n";
        }
        return res;
    }
}
