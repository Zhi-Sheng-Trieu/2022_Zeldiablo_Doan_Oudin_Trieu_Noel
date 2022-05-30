package gameLaby.laby;

public class BoutonFermeture {
    private Position pos;
    private PassageSecret passageSecret;

    public BoutonFermeture(Position p, PassageSecret ps){
        this.pos = p;
        this.passageSecret = ps;
    }

    public void fermer(){
        this.passageSecret.fermerPassage();
    }

    public Position getPos() {
        return pos;
    }
}
