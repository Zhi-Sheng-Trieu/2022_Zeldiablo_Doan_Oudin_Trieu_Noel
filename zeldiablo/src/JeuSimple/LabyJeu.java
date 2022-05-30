package JeuSimple;

import gameLaby.laby.BoutonPassage;
import gameLaby.laby.Labyrinthe;
import gameLaby.laby.PassageSecret;
import gameLaby.laby.Monstre;
import gameLaby.laby.Perso;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Classe pour representer le jeu
 */
public class LabyJeu implements Jeu {

    /**
     * Perso representant le personnage
     */
    private Perso personnage;
    /**
     * Perso representant le monstre
     */
    private ArrayList<Monstre> monstre;
    /**
     * Liste des murs du laby
     */
    private boolean[][] murs;

    private PassageSecret passageSecret;
    private BoutonPassage boutonPassage;
    /**
     * Labyrinthe du jeu
     */
    private Labyrinthe lab;

    /**
     * Compteur de deplacement
     */
    private int compteur;

    /**
     * Limite de monstre
     */
    private static int limite = 5;




    /**
     * Constructeur a partir du nom d'un labyrinthe
     *
     * @param nom le nom du labyrinthe a charger
     */
    public LabyJeu(String nom) throws IOException {
        // On cree un nouveau labyrinthe a partir du nom donné en param
        Labyrinthe laby = new Labyrinthe(nom);
        this.lab = laby;
        this.murs = lab.murs;
        this.personnage = lab.pj;
        this.monstre = lab.monstre;
        this.passageSecret = lab.passageSecret;
        this.boutonPassage = lab.boutonPassage;
        this.compteur = 0;
    }

    public void ajouterMonstre(){
        int x = (int)Math.round(Math.random()* (this.getLength()-1));
        int y = (int)Math.round(Math.random()* (this.getLengthY()-1));
        boolean arret = false;
        while (!arret){
            for(int i = 0; i < monstre.size(); i ++){
                if(!this.murs[x][y] && (this.monstre.get(i).getX() != x || this.monstre.get(i).getY() != y) && (this.personnage.getX() != x || this.personnage.getY() != y)){
                    this.monstre.add(new Monstre(x,y));
                    arret = true;
                }
            }
            x = (int)Math.round(Math.random()* (this.getLength()-1));
            y = (int)Math.round(Math.random()* (this.getLengthY()-1));
        }
    }


    /**
     * Met a jour le jeu
     *
     * @param secondes temps ecoule depuis la derniere mise a jour
     * @param clavier  objet contenant l'état du clavier'
     */
    @Override
    public void update(double secondes, Clavier clavier) {
        this.compteur+=1;
        // On déplace le personnage selon les touches du perso
        if (clavier.droite){
            this.lab.deplacerPerso("Droite");
            genererMonstre();
        }

        if (clavier.gauche){
            this.lab.deplacerPerso("Gauche");
            genererMonstre();
        }

        if (clavier.haut){
            this.lab.deplacerPerso("Haut");
            genererMonstre();
        }

        if (clavier.bas){
            this.lab.deplacerPerso("Bas");
            genererMonstre();
        }

        // On met a jour les position du perso
        this.personnage = this.lab.pj;
    }


    public void genererMonstre(){
        if(this.compteur == 20 && this.monstre.size() <= this.limite){
            this.ajouterMonstre();
            this.compteur = 0;
        }
    }

    /**
     * On initialise le laby
     */
    @Override
    public void init() {
        // On initialise le labyrinthe
    }


    /**
     * Le jeu ne finit pas
     *
     * @return
     */
    @Override
    public boolean etreFini() {
        return false;
    }

    /**
     * return taille selon Y
     *
     * @return
     */
    public int getLengthY() {
        return murs[0].length;
    }

    /**
     * return taille selon X
     *
     * @return
     */
    public int getLength() {
        return murs.length;
    }

    /**
     * Permet de recuperer le mur
     */
    public boolean[][] getMurs() {
        return this.murs;
    }

    /**
     * permet de recuperer le personnage
     *
     * @return
     */
    public Perso getPj() {
        return this.personnage;
    }

    /**
     * Permet de recup le monstre
     * @return
     */
    public ArrayList<Monstre> getMonstre(){
        return this.monstre;
    }

    public PassageSecret getPassageSecret() {
        return passageSecret;
    }

    public BoutonPassage getBoutonPassage() {
        return boutonPassage;
    }
}
