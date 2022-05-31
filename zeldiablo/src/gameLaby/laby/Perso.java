package gameLaby.laby;


/**
 * gere un personnage situe en x,y
 */
public class Perso extends Entitee{

    /**
     * constructeur
     *
     * @param dx position selon x
     * @param dy position selon y
     */
    public Perso(int dx, int dy,int pv) {
        super(dx,dy,pv);
    }

    @Override
    public boolean attaquer(Entitee e) {
        e.perdrePv(Entitee.DEGAT);
        return e.etreMort();
    }
}
