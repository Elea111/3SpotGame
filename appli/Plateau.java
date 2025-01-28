package Appli.appli;

import Appli.appli.Joueurs;

import java.util.Scanner;

public class Plateau {
    private final char[][] carre;
    private final char[][] dest;
    private final int[][] numDesti;
    private final int largeur = 26, hauteur = 13, tailleCarre = 3, nbDestPossiMax = 10, nbCoordonnees = 4;
    private int numDest, destMax;

    public Plateau() {
        this.carre = new char[largeur][hauteur];
        this.dest = new char[largeur][hauteur];
        this.numDesti = new int[nbDestPossiMax][nbCoordonnees];
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    //Initialisation du plateau
    public void initPlateau() {
        int nbLignes = 4;
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                carre[x][y] = ' ';
                dest[x][y] = ' ';
            }
            for (int x = 0; x < largeur; x++) {
                if (x % (nbLignes * 2) == 0 || y % nbLignes == 0) {
                    carre[x][y] = '*';
                    x++;
                }
            }
        }
    }

    //Affichage du plateau
    public void affichagePlateau() {
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++)
                System.out.print(getCarre(x, y));
            System.out.println();
        }
    }

    /**
     * Placer la piece sur le plateau
     *
     * @param x      :la ligne de la 1ere case du piece
     * @param y      :la colonne de la 1ere case du piece
     * @param dx     :la ligne de la 2e case du piece
     * @param dy     :la colonne de la 2e case du piece
     * @param lettre : couleur de la piece
     */
    public void placerPiece(int x, int y, int dx, int dy, char lettre) {
        carre[4 + x * 8][2 + y * 4] = lettre;
        carre[4 + dx * 8][2 + dy * 4] = lettre;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Verifier si le deplacement de la piece vers une nouvelle position est possible
     *
     * @param x      :la ligne de la 1ere case de la piece
     * @param y      :la colonne de la 1ere case de la piece
     * @param dx     :la ligne de la 2e case de la piece
     * @param dy     :la colonne de la 2e case de la piece
     * @param piece: La couleur de la piece a deplacer
     * @return true si le deplacement est possible, sinon false
     */
    public boolean verifierPossibilite(int x, int y, int dx, int dy, char piece) {
        char emplXY = getEmplPiece(x, y);
        char empldXdY = getEmplPiece(dx, dy);

        //Verifier si la piece peut se deplacer a la nouvelle position
        if (emplXY == piece) {
            if ((empldXdY == ' ' || empldXdY == 'O') && empldXdY != piece) {
                return true;
            } else if (empldXdY == piece)
                return false;
        }
        if (emplXY == ' ' || emplXY == 'O') {
            return empldXdY == ' ' || empldXdY == 'O' || empldXdY == piece || (empldXdY >= '0' && empldXdY <= '9');
        }
        return false;
    }

    /**
     * Placer une destination sur le plateau
     *
     * @param i : le numero de la destination
     * @param x : la ligne de la destination
     * @param y : la colonne de la destination
     */
    public void placerDest(int i, int x, int y) {
        //Convertir le numero de la destination en caractere
        char num = (char) (i + '0');
        if (getDest(x, y) == ' ' || getDest(x, y) == 'O') {
            setDest(x, y, num);
        } else {//Sinon cette place a deja une destination et on l’ajoute une 2e destination
            dest[4 + x * 8 - 2][2 + y * 4] = getDest(x, y);
            setDest(x, y, '-');
            dest[4 + x * 8 + 2][2 + y * 4] = num;
        }
    }

    /**
     * Enregistrer les destinations et la position de la piece
     *
     * @param num : numero de destination
     * @param x   :la ligne de la 1ere case de la piece
     * @param y   :la colonne de la 1ere case de la piece
     * @param dx  :la ligne de la 2e case de la piece
     * @param dy  :la colonne de la 2e case de la piece
     */
    public void enregistrerDestPiece(int num, int x, int y, int dx, int dy) {
        numDesti[num][0] = x;
        numDesti[num][1] = y;
        numDesti[num][2] = dx;
        numDesti[num][3] = dy;
        ++destMax;
        ++numDest;
    }

    /**
     * Modifier la lettre qui joue actuellement
     *
     * @param x      : ligne de la lettre
     * @param y      : colone de la lettre
     * @param lettre : la lettre qu'on veut modifier
     */
    public void modifierLettre(int x, int y, char lettre) {
        carre[4 + x * 8][2 + y * 4] = lettre;
    }

    /**
     * Rechercher et enregistrer les destinations possibles pour la piece
     * Les destinations possible sont enregistrees dans le tableau numDesti
     *
     * @param pieces : la piece qu'il faut jouer
     */
    public void destPossibles(Joueurs pieces) {
        numDest = 1;
        destMax = 0;
        //Parcourt le plateau pour trouver les destinations possibles
        for (int y = 0; y < tailleCarre; y++) {
            for (int x = 0; x < tailleCarre; x++) {
                //Si on peut placer la piece verticalement alors on place la destination et on l’enregistre
                if (x < tailleCarre - 1 && verifierPossibilite(x, y, x + 1, y, pieces.getCouleurPiece())) {
                    placerDest(numDest, x, y);
                    enregistrerDestPiece(numDest, x, y, x + 1, y);
                }
                //Si on peut placer la piece horizontalement alors on place la destination et on l'enregistre
                if (y == tailleCarre - 1 && verifierPossibilite(x, y, x, y - 1, pieces.getCouleurPiece())) {
                    placerDest(numDest, x, y);
                    enregistrerDestPiece(numDest, x, y, x, y - 1);
                }
                //Si on peut placer la piece horizontalement quand on est a la 3e ligne alors on place la destination et on l'enregistre
                if (y < tailleCarre - 1 && verifierPossibilite(x, y, x, y + 1, pieces.getCouleurPiece())) {
                    placerDest(numDest, x, y);
                    enregistrerDestPiece(numDest, x, y, x, y + 1);
                }
            }
        }

        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if (x < tailleCarre && y < tailleCarre) {
                    if (getEmplPiece(x, y) == pieces.getCouleurPiece())
                        modifierLettre(x, y, ' '); //Enleve la piece du plateau
                    if (getEmplPiece(2, y) == ' ')
                        modifierLettre(2, y, 'O'); //S’il n’y a pas de piece sur le spot alors on met O
                }
                if (dest[x][y] != ' ')
                    carre[x][y] = dest[x][y];  //Placer les destinations dans la matrice carre
            }
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Placer la lettre du joueur sur les positions enregistrees
     * Ajoute des points au joueur s'il atterrit sur une case donnant des points.
     *
     * @param piece  : Le joueur pour lequel nous devons placer la lettre et ajouter des points
     * @param lettre :  La lettre a placer sur les positions enregistrees
     * @param num    : Le numero de destination
     */
    public void placerLettreNumDest(Joueurs piece, char lettre, int num) {
        int x = numDesti[num][0];
        int y = numDesti[num][1];
        int dx = numDesti[num][2];
        int dy = numDesti[num][3];
        if (getEmplPiece(x, y) == getEmplPiece(2, 0) || getEmplPiece(x, y) == getEmplPiece(2, 1) || getEmplPiece(x, y) == getEmplPiece(2, 2)) {
            piece.ajtPoints();
        }
        if (getEmplPiece(dx, dy) == getEmplPiece(2, 0) || getEmplPiece(dx, dy) == getEmplPiece(2, 1) || getEmplPiece(dx, dy) == getEmplPiece(2, 2)) {
            piece.ajtPoints();
        }
        placerPiece(x, y, dx, dy, lettre);
    }

    /**
     * Deplacer la piece du joueur a la destination indiquee
     *
     * @param piece : La piece a deplacer
     */
    public void deplacerPiece(Joueurs piece) {
        //Initialisation du scanner pour la saisie des joueurs
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez saisir le numéro de déplacement que vous voulez faire ");
        int numero = scanner.nextInt();

        //Verifier que le numero saisie est possible
        while (numero > destMax) {
            System.out.println("Déplacement pas valide, veuillez réessayer");
            numero = scanner.nextInt();
        }

        //Deplacer la piece
        int num = (char) (numero + '0');
        for (int y = 0; y < tailleCarre; y++) {
            for (int x = 0; x < tailleCarre; x++) {
                if (num == getEmplPiece(x, y)) {//S’il y a qu'une seule destination possible dans la case
                    placerLettreNumDest(piece, piece.getCouleurPiece(), numero); //Placer la piece a la position qui etait enregistree
                } else if (num == carre[4 + x * 8 - 2][2 + y * 4]) { //S’il y a 2 destinations possibles, on prend la premiere
                    placerLettreNumDest(piece, piece.getCouleurPiece(), ++numero); //Placer verticalement la piece
                } else if (num == carre[4 + x * 8 + 2][2 + y * 4]) { //S’il y a 2 destinations possibles, on prend la deuxieme
                    placerLettreNumDest(piece, piece.getCouleurPiece(), --numero); //Placer horizontalement la piece
                }
            }
        }
        //Efface toutes les destinations
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if (dest[x][y] != ' ' || dest[x][y] != '*')
                    dest[x][y] = ' ';
                if (getCarre(x, y) >= '0' && getCarre(x, y) <= '9' || getCarre(x, y) == '-')
                    carre[x][y] = ' ';
            }
        }
    }

    /**
     * @param x : ligne de la matrice
     * @param y : colonne de la matrice
     * @return : le contenu dans la matrice aux coordonnees(x, y)
     */
    public char getCarre(int x, int y) {
        return carre[x][y];
    }

    /**
     * Renvoyer la piece contenue dans l'emplacement actuel
     *
     * @param x : ligne de la case
     * @param y : colonne de la case
     * @return la lettre contenue dans la case
     */
    public char getEmplPiece(int x, int y) {
        return carre[4 + x * 8][2 + y * 4];
    }

    /**
     * Renvoyer le numero de la destination
     *
     * @param x : ligne de la case
     * @param y : colonne de la case
     * @return le contenu dans la case
     */
    public char getDest(int x, int y) {
        return dest[4 + x * 8][2 + y * 4];
    }

    /**
     * Ajouter un numero dans la matrice dest
     *
     * @param x      : ligne de la case
     * @param y      : colonne de la case
     * @param numero : que l'on veut placer dans la case
     */
    public void setDest(int x, int y, char numero) {
        dest[4 + x * 8][2 + y * 4] = numero;
    }

    /**
     * Meme methode que deplacerPiece mais sans scanner
     *
     * @param piece  : La piece a deplacer
     * @param numero : Le numero de la destination vers laquelle deplacer la piece
     */
    public void deplacerPiece2(Joueurs piece, int numero) {
        int num = (char) (numero + '0');
        for (int y = 0; y < tailleCarre; y++) {
            for (int x = 0; x < tailleCarre; x++) {
                if (num == getEmplPiece(x, y)) {//S’il y a qu'une seule destination possible dans la case
                    placerLettreNumDest(piece, piece.getCouleurPiece(), numero); //Placer la piece a la position qui etait enregistree
                } else if (num == carre[4 + x * 8 - 2][2 + y * 4]) { //S’il y a 2 destinations possibles, on prend la premiere
                    placerLettreNumDest(piece, piece.getCouleurPiece(), ++numero); //Placer verticalement la piece
                } else if (num == carre[4 + x * 8 + 2][2 + y * 4]) { //S’il y a 2 destinations possibles, on prend la deuxieme
                    placerLettreNumDest(piece, piece.getCouleurPiece(), --numero); //Placer horizontalement la piece
                }
            }
        }
    }
}