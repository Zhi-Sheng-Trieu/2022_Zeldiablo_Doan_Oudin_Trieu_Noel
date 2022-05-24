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

    }

    @Override
    public void init() {

    }

    @Override
    public boolean etreFini() {
        return false;
    }
}
