package JeuSimple;

import JeuSimple.LabyJeu;
import com.sun.jdi.VMOutOfMemoryException;
import moteurJeu.MoteurJeu;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainLaby {

    public static void main(String[] args) throws IOException {

        LabyJeu labyJeu = new LabyJeu("zeldiablo/labySimple/laby1.txt");
        LabyDessin labyDessin = new LabyDessin();

        int width = labyJeu.getLength()*LabyDessin.TAILLE;
        int height = labyJeu.getLengthY()*LabyDessin.TAILLE;

        int fps = 100;

        MoteurJeu.setTaille(width, height);
        MoteurJeu.setFPS(fps);

        MoteurJeu.launch(labyJeu, labyDessin);

    }
}
