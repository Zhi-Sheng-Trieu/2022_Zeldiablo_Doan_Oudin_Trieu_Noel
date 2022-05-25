import JeuSimple.LabyJeu;
import gameLaby.laby.Labyrinthe;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMonstre {
    @Test
    public void test1_monstre_immobile() throws IOException {

        LabyJeu labyJeu = new LabyJeu("zeldiablo/labySimple/labyMonstre2.txt");

        // On regarde si le monstre positionné par défaut sur la map est présent dans la liste
        assertEquals(1, labyJeu.getMonstre().size(), "Le monstre n est pas present dans la liste");
        // On regarde si les coordonnées du monstre sont les bonnes
    }
}
