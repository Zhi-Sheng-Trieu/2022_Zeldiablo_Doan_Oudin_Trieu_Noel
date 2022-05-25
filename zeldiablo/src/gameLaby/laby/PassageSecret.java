package gameLaby.laby;


public class PassageSecret {

    //attributs
    private Position pos;
    private boolean ouvert;

    //constructeur
    public PassageSecret(Position p){
        this.pos = p;
        this.ouvert = false;
    }

    //méthodes
    public void ouvrirPassage(){
        this.ouvert = true;
    }

    public boolean etreOuvert(){
        return this.ouvert;
    }

    public Position getPos() {
        return pos;
    }
}
