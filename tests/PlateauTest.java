package Appli.tests;

import static org.junit.Assert.*;

import Appli.appli.Joueurs;
import Appli.appli.Plateau;
import org.junit.Test;

public class PlateauTest {
    @Test
    public void testInitPlateau() {
        Plateau plateau = new Plateau();
        plateau.initPlateau();

        // Vérifier que le plateau est correctement initialisé
        assertEquals('*', plateau.getCarre(0, 0));
        assertEquals(' ', plateau.getCarre(10, 10));
        assertEquals(' ', plateau.getCarre(1, 1));
    }

    @Test
    public void testPlacerLettre() {
        Plateau plateau = new Plateau();
        plateau.placerPiece(2, 2, 1, 2, 'R');

        //Verifier que la lettre a ete placee correctement
        assertEquals('R', plateau.getEmplPiece(2, 2));
        assertEquals('R', plateau.getEmplPiece(1, 2));
    }

    @Test
    public void testPlacerDest() {
        Plateau plateau = new Plateau();
        plateau.initPlateau();

        //Placer une destination sur le plateau
        plateau.placerDest(4, 1, 1);

        //Verifier que la destination a ete correctement placee
        assertEquals('4', plateau.getDest(1, 1));
        assertEquals(' ', plateau.getDest(2, 1));
    }

    @Test
    public void testVerifiePossibilite() {
        Plateau plateau = new Plateau();
        plateau.initPlateau();
        plateau.placerPiece(0, 0, 0, 1, 'R');

        // Vérifier la possibilité de déplacement
        assertTrue(plateau.verifierPossibilite(0, 0, 1, 0, 'R'));
        assertFalse(plateau.verifierPossibilite(0, 0, 0, 1, 'R'));//quand la piece est a sa place initial
    }

    @Test
    public void testDestPossibles() {
        Plateau plateau = new Plateau();
        Joueurs joueurA = new Joueurs('A');
        Joueurs joueurB = new Joueurs('B');
        Joueurs joueurC = new Joueurs('C');

        plateau.initPlateau();
        plateau.placerPiece(1, 0, 2, 0, joueurA.getCouleurPiece());
        plateau.placerPiece(1, 1, 2, 1, joueurB.getCouleurPiece());
        plateau.placerPiece(1, 2, 2, 2, joueurC.getCouleurPiece());

        plateau.destPossibles(joueurA); //Trouver les destinations possible du joueur A

        assertEquals('3', plateau.getDest(0, 1));
        assertEquals('4', plateau.getDest(0, 2));
    }

    @Test
    public void testDeplacerPiece() {
        Plateau plateau = new Plateau();
        Joueurs joueurA = new Joueurs('A');
        Joueurs joueurB = new Joueurs('B');
        plateau.initPlateau();

        // On initialise le plateau avec quelques pieces
        plateau.placerPiece(1, 0, 2, 0, joueurA.getCouleurPiece());
        plateau.placerPiece(1, 1, 2, 1, joueurB.getCouleurPiece());

        // On deplace la piece 'A' vers le numero 1
        plateau.destPossibles(joueurA);
        plateau.deplacerPiece2(joueurA, 1);

        //Verifier que la piece 'A' est deplacee vers la destination 1
        assertEquals('A', plateau.getEmplPiece(0, 0));
        assertEquals('A', plateau.getEmplPiece(0, 1));

        // On deplace la piece 'B' vers le numero 3
        plateau.destPossibles(joueurB);
        plateau.deplacerPiece2(joueurB, 3);

        //Verifier que la piece 'B' est deplacee vers la destination 3
        assertEquals('B', plateau.getEmplPiece(2, 0));
        assertEquals('B', plateau.getEmplPiece(2, 1));
    }
}