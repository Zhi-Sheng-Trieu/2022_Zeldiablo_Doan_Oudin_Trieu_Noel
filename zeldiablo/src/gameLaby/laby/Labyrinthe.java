package gameLaby.laby;

import javafx.geometry.Pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import static JeuSimple.LabyDessin.TAILLE;

import java.util.Stack;

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
    private Perso pj;
    /**
     * attribut du monstre
     */
    private ArrayList<Monstre> monstre;

    /**
     * attribut du passage secret
     */
    private PassageSecret passageSecret;
    private BoutonOuverture boutonOuverture;
    private BoutonFermeture boutonFermeture;

    /**
     * les murs du labyrinthe
     */
    private boolean[][] murs;

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
                        this.pj = new Perso(colonne, numeroLigne, 10);
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
        if (pos_passage != null) {
            this.passageSecret = new PassageSecret(pos_passage);

            if (pos_bouton_ferme != null) {
                this.boutonFermeture = new BoutonFermeture(pos_bouton_ferme, passageSecret);
                passageSecret.ouvrirPassage();
            }
            if (pos_bouton != null) {
                this.boutonOuverture = new BoutonOuverture(pos_bouton, passageSecret);
                passageSecret.fermerPassage();
            }
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
    public void deplacerMonstre(Monstre m) {
        //on recupere le meilleur chemin vers le perso
        //ce chin inclu la position du monstre et du perso
        ArrayList<Position> chemin = this.recherche(m.getPos());
        ArrayList<Position> chemin2;
        for (int i = 0; i < 100; i++) {
            chemin2 = this.recherche(m.getPos());
            if (chemin2.size()<chemin.size()){
                chemin = chemin2;
            }
        }
        //si il n'y a aucun chemin vers le perssonage on fait un mouvement aleatoire
        switch (chemin.size()) {
            case 0:
            case 1:
                // On choisi un nombre au hasard
                Random random = new Random();
                int aleaNb = random.nextInt(4);

                String action = "";

                // Selon le nombre aleatoire, le monstre va se deplacer dans une direction
                switch (aleaNb) {
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

                // calcule case suivante
                int[] suivante = getSuivant(m.getPos().getX(), m.getPos().getY(), action);
                // si c'est pas un mur ou un monstre, on effectue le deplacement

                if (this.deplacementPossible(suivante)) {
                    // on met a jour le monstre
                    m.getPos().setX(suivante[0]);
                    m.getPos().setY(suivante[1]);
                }
                break;
            case 2:
                //si la longueur du chemin est de 2, cela veut dire que la prochaine case est le perso
                //si la prochaine case est le perso on ne bouge pas pour pas empierter
                break;
            default:
                //si ce n'est pas un cas particulier, on bouge le monstre vers le perso
                m.getPos().setX(chemin.get(1).getX());
                m.getPos().setY(chemin.get(1).getY());


        }


    }


    /**
     * Methode permettant de savoir si la case suivante est dispo
     *
     * @param suivant la case a selectionner
     * @return Si le deplacement est possible sur la case selectionnee
     */
    public boolean deplacementPossible(int[] suivant) {
        boolean valide = true;
        //on vérifie si on ne se déplace pas sur un mur
        if (!this.murs[suivant[0]][suivant[1]]) {
            // on vérifie si la position n'est pas la même que le personnage
            if (this.pj.getPos().posEquals(suivant[0], suivant[1])) {
                valide = false;
            } else {
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
        } else {
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
            if (this.passageSecret != null) {
                //sinon si la case contient la sortie
                if (this.passageSecret.getPos().posEquals(x, y)) {
                    valeurCase = PASSAGE;
                } else {
                    valeurCase = VIDE;
                }
            } else {
                if (this.passageSecret != null) {
                    if (this.boutonOuverture.getPos().posEquals(x, y)) {
                        valeurCase = BOUTON;
                    } else {
                        valeurCase = VIDE;
                    }
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

        res += murs.length + "\n";
        res += murs[0].length + "\n";
        for (int i = 0; i < murs[0].length; i++) {
            for (int j = 0; j < murs.length; j++) {
                res += getChar(j, i);
            }
            res += "\n";
        }
        return res;
    }

    public ArrayList<Position> recherche(Position pos) {
        //on a une liste des positions visitees
        boolean[][] visites = new boolean[this.getLength()][this.getLengthY()];
        //on garde le chemin qu'on prend grace a une pile
        Stack<Position> pile = new Stack<>();
        pile.push(pos);
        //tant qu'on ne trouve pas la position du personnage
        while ((!pile.isEmpty()) && (!pile.peek().posEquals(this.pj.getPos()))) {
            Position p = pile.peek();
            visites[p.getX()][p.getY()] = true;
            //on recupere les positions adjacentes
            ArrayList<Position> voisins = this.voisins(p);

            //on cherche si une position n'a pas ete visitee et si c'est un personnage
            boolean valide = false;
            while (voisins.size()>0 && !valide) {
                int i = (int) Math.round(Math.random() * (voisins.size() - 1));
                Position voisin = voisins.get(i);
                voisins.remove(i);

                if (voisin.posEquals(this.pj.getPos()) || !visites[voisin.getX()][voisin.getY()]) {
                    pile.push(voisin);
                    valide = true;
                }
                i++;
            }
            //si aucune position n'est ni valide ni non-visitee
            //cela veut dire qu'on doit revenir en arriere
            if (!valide) {
                pile.pop();
            }
        }
        return new ArrayList<>(pile);
    }

    /**
     * Le resultat sera soit les voisins valides soit juste le personnage si on le trouve
     *
     * @param p
     * @return
     */
    public ArrayList<Position> voisins(Position p) {

        ArrayList<Position> res = new ArrayList<>();
        int x = p.getX();
        int y = p.getY();

        //on recupere les positions des voisins
        ArrayList<int[]> liste = new ArrayList<>();
        liste.add(getSuivant(x, y, GAUCHE));
        liste.add(getSuivant(x, y, DROITE));
        liste.add(getSuivant(x, y, HAUT));
        liste.add(getSuivant(x, y, BAS));

        // on cherche le personnage parmi les voisins
        boolean trouve = false;
        int i = 0;
        while (i < liste.size() && !trouve) {
            int[] pos = liste.get(i);
            Position p2 = new Position(pos[0], pos[1]);
            if (deplacementPossible(pos)) {
                //si la position est valide elle fera partie des voisins valides
                res.add(p2);
            } else if (this.pj.getPos().posEquals(pos[0], pos[1])) {
                //si on trouve le personnage
                //la liste ne contiendra que le personnage
                res.clear();
                res.add(p2);
                trouve = true;
            }
            i++;
        }

        return res;
    }

    public Perso getPj() {
        return pj;
    }

    public ArrayList<Monstre> getMonstre() {
        return monstre;
    }

    public boolean[][] getMurs() {
        return murs;
    }

    public BoutonFermeture getBoutonFermeture() {
        return boutonFermeture;
    }

    public PassageSecret getPassageSecret() {
        return passageSecret;
    }

    public BoutonOuverture getBoutonOuverture() {
        return boutonOuverture;
    }
}
