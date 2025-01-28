package Appli;

public class Joueurs extends Plateau {
    private final char couleurPiece;
    private int points;

    public Joueurs(char couleur) {
        this.couleurPiece = couleur;
    }

    //Ajouter les points au joueur
    public void ajtPoints() {
        this.points++;
    }

    //Retourne la couleur de la piece
    public char getCouleurPiece() {
        return this.couleurPiece;
    }

    //Retourne le nombre de point
    public int getPoints() {
        return this.points;
    }

    @Override
    public String toString() {
        String couleur;
        if (this.couleurPiece == 'B') {
            couleur = "BLEU";
        } else {
            couleur = "ROUGE";
        }
        return "La partie est terminée ! \nLe joueur " + couleur + " a gagné avec " + this.points + " points !";
    }
}