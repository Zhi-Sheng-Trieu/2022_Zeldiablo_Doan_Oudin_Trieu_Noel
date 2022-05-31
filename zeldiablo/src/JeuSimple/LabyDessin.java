package JeuSimple;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

public class LabyDessin implements DessinJeu {


    public final static int TAILLE = 40;

    /**
     * affiche l'etat du jeu dans le canvas passe en parametre
     *
     * @param jeu    jeu a afficher
     * @param canvas canvas dans lequel dessiner l'etat du jeu
     */
    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        LabyJeu ljeu = (LabyJeu) jeu;

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < ljeu.getMurs().length; i++) {
            for (int j = 0; j < ljeu.getMurs()[i].length; j++) {
                if (ljeu.getMurs()[i][j]) {
                    creerRectangle(gc, i * TAILLE, j * TAILLE);
                }
            }
        }
        if (ljeu.getPassageSecret() != null){
            if (ljeu.getPassageSecret().etreOuvert()){
                gc.setFill(Color.GRAY);
            }else {
                gc.setFill(Color.BLACK);
            }
            gc.fillRect(ljeu.getPassageSecret().getPos().getX()*TAILLE, ljeu.getPassageSecret().getPos().getY()*TAILLE, TAILLE, TAILLE);
            gc.setFill(Color.ORANGE);
            gc.fillOval(ljeu.getBoutonPassage().getPos().getX()*TAILLE, ljeu.getBoutonPassage().getPos().getY()*TAILLE, TAILLE, TAILLE);
        }

        gc.setFill(Color.RED);
        gc.fillOval(ljeu.getPj().getX() * TAILLE, ljeu.getPj().getY() * TAILLE, TAILLE, TAILLE);

        if (ljeu.getMonstre() != null) {
            for (int i = 0; i < ljeu.getMonstre().size(); i++) {
                gc.setFill(Color.VIOLET);
                gc.fillOval(ljeu.getMonstre().get(i).getX() * TAILLE, ljeu.getMonstre().get(i).getY() * TAILLE, TAILLE, TAILLE);
            }
        }


    }

    public void creerRectangle(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, TAILLE, TAILLE);
    }
}
