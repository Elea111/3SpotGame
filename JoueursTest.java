package Tests;
import static org.junit.Assert.*;

import Appli.Joueurs;
import org.junit.Test;

public class JoueursTest {
    @Test
    public void ajtPointsTest() {
        Joueurs joueurA = new Joueurs('A');

        //On ajoute 1 point au joueur A
        joueurA.ajtPoints();

        //Verifier que le point a ete ajouter
        assertEquals(1, joueurA.getPoints());
    }
}
