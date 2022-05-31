package gameLaby.laby;

import java.io.IOException;

/**
 * charge et affiche un labyrinthe
 */
public class Main {
    public static void main(String[] args) throws IOException {

        // charge le labyrinthe
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyPassageSecret1.txt");

        System.out.println(laby);
        laby.deplacerPerso("Gauche");
        System.out.println(laby);
        laby.deplacerPerso("Haut");
        System.out.println(laby);
        laby.deplacerPerso("Gauche");
        laby.deplacerPerso("Bas");
        System.out.println(laby);
    }
}
