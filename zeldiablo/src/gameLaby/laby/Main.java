package gameLaby.laby;

import java.io.IOException;
import java.util.ArrayList;

/**
 * charge et affiche un labyrinthe
 */
public class Main {
    public static void main(String[] args) throws IOException {

        // charge le labyrinthe
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyMonstre.txt");

        System.out.println(laby);
        ArrayList<Position> positions = laby.recherche(laby.monstre.get(0).getPos());
        System.out.println(positions);
    }
}
