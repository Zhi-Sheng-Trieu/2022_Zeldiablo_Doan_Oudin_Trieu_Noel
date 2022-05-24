package JeuSimple;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

public class LabyJeu implements Jeu {

    private Circle personnage;
    private Rectangle[] murs;


    public LabyJeu(){
        this.personnage = new Circle();
    }


    @Override
    public void update(double secondes, Clavier clavier) {
        // On déplace le personnage selon les touches du clavier
        if (clavier.droite){
            this.personnage.setCenterX(this.personnage.getCenterX() + 1);
        }

        if (clavier.gauche){
            this.personnage.setCenterX(this.personnage.getCenterX() - 1);
        }

        if (clavier.haut){
            this.personnage.setCenterY(this.personnage.getCenterY() + 1);
        }

        if (clavier.bas){
            this.personnage.setCenterY(this.personnage.getCenterY() - 1);
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
