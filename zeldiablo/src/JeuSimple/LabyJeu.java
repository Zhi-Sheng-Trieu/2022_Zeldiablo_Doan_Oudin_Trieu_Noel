package JeuSimple;

import gameLaby.laby.*;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Classe pour representer le jeu
 */
public class LabyJeu implements Jeu {

    /**
     * Labyrinthe du jeu
     */
    private Labyrinthe lab;

    /**
     * Compteur de deplacement
     */
    private int compteur;

    /**
     * Limite de monstre
     */
    private final static int limite = 2;


    /**
     * Constructeur a partir du nom d'un labyrinthe
     *
     * @param nom le nom du labyrinthe a charger
     */
    public LabyJeu(String nom) throws IOException {
        // On cree un nouveau labyrinthe a partir du nom donné en param
        Labyrinthe laby = new Labyrinthe(nom);
        this.lab = laby;
        this.compteur = 0;
    }

    public void ajouterMonstre() {
        int x = 0;
        int y = 0;
        boolean arret = false;
        while (!arret) {
            x = (int) Math.round(Math.random() * (this.getLength() - 1));
            y = (int) Math.round(Math.random() * (this.getLengthY() - 1));
            if (lab.getMonstre().isEmpty()) {
                if (!this.lab.getMurs()[x][y] && (! this.lab.getPj().getPos().posEquals(x, y))) {
                    arret = true;
                    if (lab.getPassageSecret() != null){
                        if ((lab.getPassageSecret().getPos().posEquals(x, y))) {
                            arret = false;
                        }
                    }
                }
            } else {
                for (Monstre value : lab.getMonstre()) {
                    if (!this.lab.getMurs()[x][y] && (!value.getPos().posEquals(x, y)) && (! this.lab.getPj().getPos().posEquals(x, y))) {
                        arret = true;
                        if (lab.getPassageSecret() != null){
                            if ((lab.getPassageSecret().getPos().posEquals(x, y))) {
                                arret = false;
                            }
                        }
                    }
                }
            }
        }
        this.lab.getMonstre().add(new Monstre(x, y));
    }


    /**
     * Met a jour le jeu
     *
     * @param secondes temps ecoule depuis la derniere mise a jour
     * @param clavier  objet contenant l'état du clavier'
     */
    @Override
    public void update(double secondes, Clavier clavier) {
        // On déplace le personnage selon les touches du perso
        if (clavier.droite) {
            this.lab.deplacerPerso("Droite");
            genererMonstre();
        }

        if (clavier.gauche) {
            this.lab.deplacerPerso("Gauche");
            genererMonstre();
        }

        if (clavier.haut) {
            this.lab.deplacerPerso("Haut");
            genererMonstre();
        }

        if (clavier.bas) {
            this.lab.deplacerPerso("Bas");
            genererMonstre();
        }

        if(clavier.space){
            for (int i = 0; i < lab.getMonstre().size() ; i++) {
                if((lab.getPj().getPos().getX()+1 == lab.getMonstre().get(i).getPos().getX() && lab.getPj().getPos().getY() == lab.getMonstre().get(i).getPos().getY())
                        || (lab.getPj().getPos().getX() == lab.getMonstre().get(i).getPos().getX() && lab.getPj().getPos().getY()+1 == lab.getMonstre().get(i).getPos().getY())
                        || (lab.getPj().getPos().getX()-1 == lab.getMonstre().get(i).getPos().getX() && lab.getPj().getPos().getY() == lab.getMonstre().get(i).getPos().getY())
                        || (lab.getPj().getPos().getX() == lab.getMonstre().get(i).getPos().getX() && lab.getPj().getPos().getY()-1 == lab.getMonstre().get(i).getPos().getY())){
                    if(lab.getPj().attaquer(lab.getMonstre().get(i))){
                        lab.getMonstre().remove(i);
                    }
                    else{
                        this.lab.deplacerMonstre(lab.getMonstre().get(i));
                    }
                }else{
                    this.lab.deplacerMonstre(lab.getMonstre().get(i));
                }
            }
            genererMonstre();
        }
    }


    public void genererMonstre() {
        this.compteur += 1;
        if (this.compteur >= 20 && this.lab.getMonstre().size() < limite) {
            this.ajouterMonstre();
            this.compteur = 0;
        }
    }

    /**
     * On initialise le laby
     */
    @Override
    public void init() {
        // On initialise le labyrinthe
    }


    /**
     * Le jeu ne finit pas
     *
     * @return
     */
    @Override
    public boolean etreFini() {
        return false;
    }

    /**
     * return taille selon Y
     *
     * @return
     */
    public int getLengthY() {
        return lab.getMurs()[0].length;
    }

    /**
     * return taille selon X
     *
     * @return
     */
    public int getLength() {
        return lab.getMurs().length;
    }

    /**
     * Permet de recuperer le mur
     */
    public boolean[][] getMurs() {
        return lab.getMurs();
    }

    /**
     * permet de recuperer le personnage
     *
     * @return
     */
    public Perso getPj() {
        return this.lab.getPj();
    }

    /**
     * Permet de recup le monstre
     *
     * @return
     */
    public ArrayList<Monstre> getMonstre() {
        return this.lab.getMonstre();
    }

    public PassageSecret getPassageSecret() {
        return lab.getPassageSecret();
    }

    public BoutonOuverture getBoutonOuverture() {
        return lab.getBoutonOuverture();
    }

    public BoutonFermeture getBoutonFermeture() {
        return lab.getBoutonFermeture();
    }
}
