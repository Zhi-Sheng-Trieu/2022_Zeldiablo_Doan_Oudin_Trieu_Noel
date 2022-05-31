import JeuSimple.LabyJeu;
import gameLaby.laby.Labyrinthe;
import moteurJeu.Clavier;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMonstre {
    @Test
    public void test1_monstre_immobile() throws IOException {

        LabyJeu labyJeu = new LabyJeu("zeldiablo/labySimple/labyMonstre2.txt");

        // On regarde si le monstre positionné par défaut sur la map est présent dans la liste
        assertEquals(1, labyJeu.getMonstre().size(), "Le monstre n est pas present dans la liste");
        // On regarde si les coordonnées du monstre sont les bonnes
        assertEquals(labyJeu.getMonstre().get(0).getX(), 4, "Les coordonnees ne sont pas les bonnes en X");
        assertEquals(labyJeu.getMonstre().get(0).getY(), 3, "Les coordonnees ne sont pas les bonnes en Y");
    }


    @Test
    public void test2_bougerMonstre() throws IOException {
        LabyJeu labyJeu = new LabyJeu("zeldiablo/labySimple/labyMonstre2.txt");
        Clavier clavier = new Clavier();
        clavier.setDroite(true);
        // On regarde si le monstre positionné par défaut sur la map est présent dans la liste
        assertEquals(1, labyJeu.getMonstre().size(), "Le monstre n est pas present dans la liste");
        // On regarde si les coordonnées du monstre sont les bonnes
        assertEquals(labyJeu.getMonstre().get(0).getX(), 4, "Les coordonnees ne sont pas les bonnes en X");
        assertEquals(labyJeu.getMonstre().get(0).getY(), 3, "Les coordonnees ne sont pas les bonnes en Y");

        // On garde en memoire les coordonnees du monstre
        int x = labyJeu.getMonstre().get(0).getX();
        int y = labyJeu.getMonstre().get(0).getY();

        // On fait bouger le perso pour faire bouger le monstre
        labyJeu.update(15, clavier);
        assertEquals(labyJeu.getMonstre().get(0).etrePresent(x, y), false, "Le monstre aurait du changer d'emplacement");
    }
}
