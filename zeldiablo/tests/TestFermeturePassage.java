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
        assertEquals(false, laby.passageSecret.etreOuvert());
        assertEquals(3, laby.passageSecret.getPos().getX());
        assertEquals(2, laby.passageSecret.getPos().getY());

        assertEquals(4, laby.boutonOuverture.getPos().getX());
        assertEquals(5, laby.boutonOuverture.getPos().getY());

        assertEquals(4, laby.boutonFermeture.getPos().getX());
        assertEquals(4, laby.boutonFermeture.getPos().getY());
    }

    @Test
    public void testOuverturePassage() throws IOException {
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyFermePassage.txt");
        assertEquals(false, laby.passageSecret.etreOuvert());
        laby.deplacerPerso("Gauche");
        assertEquals(true, laby.passageSecret.etreOuvert());
        laby.deplacerPerso("Haut");
        assertEquals(false, laby.passageSecret.etreOuvert());
    }
}
