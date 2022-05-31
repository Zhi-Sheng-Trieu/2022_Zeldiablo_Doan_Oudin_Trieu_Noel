import JeuSimple.LabyJeu;
import gameLaby.laby.Labyrinthe;
import moteurJeu.Clavier;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMonstre {
    /**
     * Test permettant de verifier si un monstre est present ou non dans le labyrinthe
     * Et qui permet de voir si il est immobile
     * @throws IOException
     */
    @Test
    public void test1_monstre_immobile() throws IOException {

        LabyJeu labyJeu = new LabyJeu("zeldiablo/labySimple/labyMonstre2.txt");

        // On regarde si le monstre positionné par défaut sur la map est présent dans la liste
        assertEquals(1, labyJeu.getMonstre().size(), "Le monstre n est pas present dans la liste");
        // On regarde si les coordonnées du monstre sont les bonnes
        assertEquals(labyJeu.getMonstre().get(0).getX(), 4, "Les coordonnees ne sont pas les bonnes en X");
        assertEquals(labyJeu.getMonstre().get(0).getY(), 3, "Les coordonnees ne sont pas les bonnes en Y");
    }

    /**
     * Test permettant de voir si le monstre bouge dans une position aleatoire lorsque le joueur bouge
     * @throws IOException
     */
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

    /**
     * Test permettant de verifier la collision fonctionne
     * @throws IOException
     */
    @Test
    public void test3_ColisionMonstre() throws IOException {
        LabyJeu labyJeu = new LabyJeu("zeldiablo/labySimple/labyMonstreProcheJoueur.txt");
        Clavier clavier = new Clavier();
        clavier.setDroite(true);
        // On regarde si le monstre positionné par défaut sur la map est présent dans la liste
        assertEquals(1, labyJeu.getMonstre().size(), "Le monstre n est pas present dans la liste");
        // On regarde si les coordonnées du monstre sont les bonnes
        assertEquals(6, labyJeu.getMonstre().get(0).getX(), "Les coordonnees ne sont pas les bonnes en X");
        assertEquals(5, labyJeu.getMonstre().get(0).getY(), "Les coordonnees ne sont pas les bonnes en Y");

        // On stocke les coordonnees du joueur
        int x = labyJeu.getPj().getX();
        int y = labyJeu.getPj().getY();


        // On fait bouger le perso pour faire bouger le monstre
        labyJeu.update(15, clavier);
        // On verifie si le monstre n a pas bougé
        assertEquals(6, labyJeu.getMonstre().get(0).getX(), "Le monstre n aurait pas du bouger");
        assertEquals(5, labyJeu.getMonstre().get(0).getY(), "Le monstre n aurait pas du bouger");
        // On verifie si le joueur n a pas bougé
        assertEquals(labyJeu.getPj().getX(), x, "Le joueur n aurait pas du bouger");
        assertEquals(labyJeu.getPj().getY(), y, "Le joueur n aurait pas du bouger");

    }
}
