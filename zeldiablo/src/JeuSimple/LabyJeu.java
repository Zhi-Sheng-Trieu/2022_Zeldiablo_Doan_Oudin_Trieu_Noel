package JeuSimple;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

public class LabyJeu implements Jeu {

    private Circle Personnage;
    private Rectangle[] Murs;


    public LabyJeu(Circle p, Rectangle[] m){
        this.Murs = m;
        this.Personnage = p;
    }


    @Override
    public void update(double secondes, Clavier clavier) {
        // On déplace le personnage selon les touches du clavier
        if (clavier.droite){

        }

        if (clavier.gauche){

        }

        if (clavier.haut){

        }

        if (clavier.bas){

        }
    }


    @Override
    public void init() {
        // On initialise le labyrinthe
    }


    @Override
    public boolean etreFini() {
        return false;
    }
}
