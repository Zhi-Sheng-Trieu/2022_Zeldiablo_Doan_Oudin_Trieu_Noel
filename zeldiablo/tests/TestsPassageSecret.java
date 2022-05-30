import gameLaby.laby.Labyrinthe;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsPassageSecret {

    @Test
    /**
     * On teste si on arrive a lire le fichier de labyrinthe et que la porte et son bouton sont bien placés
     */
    public void testPassageSecret() throws IOException {
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyPassageSecret.txt");
        assertEquals(false, laby.passageSecret.etreOuvert());
        assertEquals(laby.passageSecret.getPos().getX(), 2);
        assertEquals(laby.passageSecret.getPos().getY(), 1);
        assertEquals(laby.boutonPassage.getPos().getX(), 3);
        assertEquals(laby.boutonPassage.getPos().getY(), 4);
    }

    @Test
    public void testOuverturePassage() throws IOException {
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyPassageSecret.txt");
        assertEquals(false, laby.passageSecret.etreOuvert());
        laby.deplacerPerso("gauche");
        assertEquals(laby.pj.getX(), 3);
        assertEquals(laby.pj.getY(), 4);
        assertEquals(true, laby.passageSecret.etreOuvert());
    }




}
