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
        assertEquals(3, laby.passageSecret.getPos().getX());
        assertEquals(2, laby.passageSecret.getPos().getY());
        assertEquals(4, laby.boutonOuverture.getPos().getX());
        assertEquals(5, laby.boutonOuverture.getPos().getY());
    }

    @Test
    public void testOuverturePassage() throws IOException {
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyPassageSecret.txt");
        assertEquals(false, laby.passageSecret.etreOuvert());
        laby.deplacerPerso("Gauche");
        assertEquals(4,laby.pj.getPos().getX());
        assertEquals(5,laby.pj.getPos().getX());
        assertEquals(true, laby.passageSecret.etreOuvert());
    }

    /**
     * Test de la méthode déplacer perso si le passage est fermé
     */
    @Test
    public void testPassageFerme_deplacement() throws IOException {
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyPassageSecret1.txt");
        laby.deplacerPerso("Gauche");
        assertEquals(5, laby.pj.getPos().getX());
        assertEquals(5, laby.pj.getPos().getX());
    }
}
