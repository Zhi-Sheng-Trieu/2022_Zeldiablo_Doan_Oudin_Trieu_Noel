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
public class LabyJeu implements Jeu  {

    /**
     * Perso representant le personnage
     */
    private Perso personnage;
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
     * @param secondes temps ecoule depuis la derniere mise a jour
     * @param clavier objet contenant l'état du clavier'
     */
    @Override
    public void update(double secondes, Clavier clavier) {
        // On déplace le personnage selon les touches du clavier
        if (clavier.droite){
            this.lab.deplacerPerso("droite");
        }

        if (clavier.gauche){
            this.lab.deplacerPerso("gauche");
        }

        if (clavier.haut){
            this.lab.deplacerPerso("haut");
        }

        if (clavier.bas){
            this.lab.deplacerPerso("bas");
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
     * @return
     */
    @Override
    public boolean etreFini() {
        return false;
    }

    /**
     * getter
     */
    public boolean[][] getMurs(){
        return this.murs;
    }

    public Perso getPj(){
        return this.personnage
    }
}
