package gameLaby.laby;

import java.io.IOException;
import java.util.ArrayList;

/**
 * charge et affiche un labyrinthe
 */
public class Main {
    public static void main(String[] args) throws IOException {

        // charge le labyrinthe
<<<<<<< HEAD
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyMonstre.txt");

        System.out.println(laby);
        ArrayList<Position> positions = laby.recherche(laby.monstre.get(0).getPos());
        System.out.println(positions);
=======
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/grandLaby.txt");

        System.out.println(laby);
        laby.deplacerPerso("Gauche");
        System.out.println(laby);
        laby.deplacerPerso("Haut");
        System.out.println(laby);
        laby.deplacerPerso("Gauche");
        laby.deplacerPerso("Bas");
        System.out.println(laby);

        ArrayList<Position> chemin = laby.recherche(new Position(5, 9));
        for (Position p : chemin) {
            System.out.println(p.getX() + " " + p.getY());
        }
>>>>>>> 09a44b222d347e2a0e2137a1d9620a90421cbcd6
    }
}
