import gameLaby.laby.Labyrinthe;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsPassageSecret {

    @Test
    public void testPassageSecret() throws IOException {
        Labyrinthe laby = new Labyrinthe("zeldiablo/labySimple/labyPassageSecret.txt");
        laby.deplacerPerso("gauche");
        assertEquals(laby.pj.getX(), 3);
        assertEquals(laby.pj.getY(), 4);
    }


}
