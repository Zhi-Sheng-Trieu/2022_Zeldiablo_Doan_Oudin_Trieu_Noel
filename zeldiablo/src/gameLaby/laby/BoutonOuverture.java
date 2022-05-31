package gameLaby.laby;

public class BoutonOuverture {

    private Position pos;
    private PassageSecret passageSecret;

    public BoutonOuverture(Position p, PassageSecret ps){
        this.pos = p;
        this.passageSecret = ps;
    }

    public void activerPassage(){
        this.passageSecret.ouvrirPassage();
    }

    public Position getPos() {
        return pos;
    }
}
