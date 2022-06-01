import gameLaby.laby.Labyrinthe;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFermeturePassage {

    @Test
    /**
     * On teste si on arrive a lire le fichier de labyrinthe et que la porte
     * et ses boutons d'ouverture et de fermeture sont bien placés
     */
    public void testPassageSecret() throws IOException {
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyFermePassage.txt");
        assertEquals(false, laby.getPassageSecret().etreOuvert());
        assertEquals(3, laby.getPassageSecret().getPos().getX());
        assertEquals(2, laby.getPassageSecret().getPos().getY());

        assertEquals(4, laby.getBoutonOuverture().getPos().getX());
        assertEquals(5, laby.getBoutonOuverture().getPos().getY());

        assertEquals(4, laby.getBoutonFermeture().getPos().getX());
        assertEquals(4, laby.getBoutonFermeture().getPos().getY());
    }

    @Test
    public void testOuverturePassage() throws IOException {
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyFermePassage.txt");
        assertEquals(false, laby.getPassageSecret().etreOuvert());
        laby.deplacerPerso("Gauche");
        assertEquals(true, laby.getPassageSecret().etreOuvert());
        laby.deplacerPerso("Haut");
        assertEquals(false, laby.getPassageSecret().etreOuvert());
    }
}
