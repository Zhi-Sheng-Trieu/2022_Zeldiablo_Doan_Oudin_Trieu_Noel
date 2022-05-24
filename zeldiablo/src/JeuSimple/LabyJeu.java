package JeuSimple;

import gameLaby.laby.Labyrinthe;
import gameLaby.laby.Perso;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Classe pour representer le jeu
 */
public class LabyJeu implements Jeu {

    /**
     * Perso representant le personnage
     */
    private Perso personnage;
    /**
     * Perso representant le monstre
     */
    private Perso monstre;
    /**
     * Liste des murs du laby
     */
    private boolean[][] murs;
    /**
     * Labyrinthe du jeu
     */
    private Labyrinthe lab;


    /**
     * Constructeur a partir du nom d'un labyrinthe
     *
     * @param nom le nom du labyrinthe a charger
     */
    public LabyJeu(String nom) throws IOException {
        // On cree un nouveau labyrinthe a partir du nom donné en param
        Labyrinthe laby = new Labyrinthe(nom);
        this.lab = laby;
        this.murs = lab.murs;
        this.personnage = lab.pj;
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
        if (clavier.droite){
            this.lab.deplacerPerso("Droite");
        }

        if (clavier.gauche){
            this.lab.deplacerPerso("Gauche");
        }

        if (clavier.haut){
            this.lab.deplacerPerso("Haut");
        }

        if (clavier.bas){
            this.lab.deplacerPerso("Bas");
        }

        // On met a jour les position du perso
        this.personnage = this.lab.pj;
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
     * Permet de recuperer le mur
     */
    public boolean[][] getMurs() {
        return this.murs;
    }

    /**
     * permet de recuperer le personnage
     *
     * @return
     */
    public Perso getPj() {
        return this.personnage;
    }

    /**
     * Permet de recup le monstre
     * @return
     */
    public Perso getMonstre(){
        return this.monstre;
    }
}
