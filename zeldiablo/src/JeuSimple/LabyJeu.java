package JeuSimple;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

public class LabyJeu implements Jeu {

    private Circle Personnage;
    private Rectangle[] Murs;


    public LabyJeu(){
        this.Personnage = new Circle();
    }


    @Override
    public void update(double secondes, Clavier clavier) {
        // On déplace le personnage selon les touches du clavier
        if (clavier.droite){
            this.Personnage.setCenterX(this.Personnage.getCenterX() + 1);
        }

        if (clavier.gauche){
            this.Personnage.setCenterX(this.Personnage.getCenterX() - 1);
        }

        if (clavier.haut){
            this.Personnage.setCenterY(this.Personnage.getCenterY() + 1);
        }

        if (clavier.bas){
            this.Personnage.setCenterY(this.Personnage.getCenterY() - 1);
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
