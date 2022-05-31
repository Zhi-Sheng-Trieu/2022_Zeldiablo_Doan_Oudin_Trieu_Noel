package gameLaby.laby;


/**
 * gere un personnage situe en x,y
 */
public class Perso {

    /**
     * position du personnage
     */
    private Position pos;

    /**
     * constructeur
     *
     * @param dx position selon x
     * @param dy position selon y
     */
    public Perso(int dx, int dy) {
        this.pos = new Position(dx, dy);
    }

    /**
     * permet de savoir si le personnage est en x,y
     *
     * @param dx position testee
     * @param dy position testee
     * @return true si le personnage est bien en (dx,dy)
     */
    public boolean etrePresent(int dx, int dy) {

        return (this.pos.getX() == dx && this.pos.getY() == dy);
    }

    // ############################################
    // GETTER
    // ############################################

    /**
     * @return position du personnage
     */
    public Position getPos() {
        return pos;
    }
}
