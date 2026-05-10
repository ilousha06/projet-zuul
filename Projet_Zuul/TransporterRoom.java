import java.util.Random;

/**
 * La classe TransporterRoom represente une salle speciale de teleportation.
 * Elle herite de Room et ajoute la logique de teleportation aleatoire.
 *
 * Quand le joueur tente de quitter cette salle, il est teleporte dans
 * une salle choisie aleatoirement parmi les salles cibles definies
 * via setTargetRooms().
 *
 * @author Ilyas
 * @version 2.0
 */
public class TransporterRoom extends Room
{
    /** Tableau des salles vers lesquelles le joueur peut etre teleporte */
    private Room[] aTargetRooms;

    /**
     * Constructeur de la salle de teleportation.
     *
     * @param pDescription description de la salle
     * @param pImage       nom du fichier image associe
     */
    public TransporterRoom(final String pDescription, final String pImage)
    {
        super(pDescription, pImage);
    }

    /**
     * Definit les salles de destination possibles pour la teleportation.
     *
     * @param pRooms tableau de salles vers lesquelles le joueur peut etre envoye
     */
    public void setTargetRooms(final Room[] pRooms)
    {
        this.aTargetRooms = pRooms;
    }

    /**
     * Retourne une salle choisie aleatoirement parmi les salles cibles.
     * Utilise Random.nextInt() pour obtenir un index valide dans le tableau.
     *
     * @return une salle aleatoire parmi aTargetRooms
     */
    public Room getRandomRoom()
    {
        Random vRandom = new Random();
        return aTargetRooms[vRandom.nextInt(this.aTargetRooms.length)];
    }
}
