package Appli;

public class Jeu {
    public static void main(String[] args) {
        //Creation d'un plateau de jeu
        Plateau plateau = new Plateau();

        //Creation des joueurs Rouge, Bleu et de la piece Neutre
        Joueurs joueurR = new Joueurs('R');
        Joueurs joueurB = new Joueurs('B');
        Joueurs pieceW = new Joueurs('W');

        //Initialiser le plateau avec les pieces des joueurs et de la piece neutre
        plateau.initPlateau();
        plateau.placerPiece(1, 0, 2, 0, joueurR.getCouleurPiece());
        plateau.placerPiece(1, 1, 2, 1, pieceW.getCouleurPiece());
        plateau.placerPiece(1, 2, 2, 2, joueurB.getCouleurPiece());

        boolean joueurRJoue = true;

        //Affichage du plateau initial
        plateau.affichagePlateau();

        while (true) {
            //Determiner le joueur actuel
            Joueurs joueurActuel = joueurRJoue ? joueurR : joueurB;
            System.out.println("\nC'est au tour du joueur " + joueurActuel.getCouleurPiece());

            //Affichage des destinations possibles pour le joueur actuel
            plateau.destPossibles(joueurActuel);
            plateau.affichagePlateau();

            //Saisie du numero de la destination
            plateau.deplacerPiece(joueurActuel);
            plateau.affichagePlateau();

            //Verifie si le joueur actuel a gagne
            if (joueurActuel.getPoints() >= 12) break;


            //Le tour de la piece neutre
            System.out.println("\nJouer la piece neutre");

            //Affichage des destinations possibles pour la piece neutre
            plateau.destPossibles(pieceW);
            plateau.affichagePlateau();

            //Saisie du numero de la destination
            plateau.deplacerPiece(pieceW);
            plateau.affichagePlateau();

            //Affichage des points des joueurs
            System.out.println("Point du joueur ROUGE : " + joueurR.getPoints());
            System.out.println("Point du joueur BLEU : " + joueurB.getPoints());

            //Changement de joueur
            joueurRJoue = !joueurRJoue;
        }

        //Determiner le joueur gagnant et le joueur perdant
        Joueurs joueurGagnant = joueurR.getPoints() >= 12 ? joueurR : joueurB;
        Joueurs joueurPerdant = joueurR.getPoints() < 12 ? joueurR : joueurB;

        //Si le joueur perdant a moins de 6 points, il devient le gagnant
        if (joueurPerdant.getPoints() < 6)
            joueurGagnant = joueurPerdant;

        //Affichage du résultat de la partie
        System.out.println(joueurGagnant);
    }
}