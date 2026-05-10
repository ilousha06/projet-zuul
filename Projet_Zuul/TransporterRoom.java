import java.util.Random;

/**
 * La classe TransporterRoom represente une salle speciale de teleportation.
 * Elle herite de Room et ajoute la logique de teleportation aleatoire.
 *
 * En mode test, la commande "alea <nom>" permet de forcer la destination
 * en memorisant un nom de salle dans aleaString.
 * La commande "alea" sans argument remet le comportement aleatoire.
 *
 * @author Ilyas
 * @version 3.0
 */
public class TransporterRoom extends Room
{
    /** Tableau des salles vers lesquelles le joueur peut etre teleporte */
    private Room[] aTargetRooms;

    /**
     * Nom de la salle forcee en mode test.
     * Si null, la teleportation est vraiment aleatoire.
     */
    private String aleaString;

    /**
     * Constructeur de la salle de teleportation.
     *
     * @param pDescription description de la salle
     * @param pImage       nom du fichier image associe
     */
    public TransporterRoom(final String pDescription, final String pImage)
    {
        super(pDescription, pImage);
        this.aleaString = null;
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
     * Memorise le nom de la salle a utiliser pour le prochain tirage.
     * Utilise uniquement en mode test (commande alea).
     * Passer null pour revenir au comportement aleatoire.
     *
     * @param pAleaString le nom de la salle forcee, ou null
     */
    public void setAleaString(final String pAleaString)
    {
        this.aleaString = pAleaString;
    }

    /**
     * Redefinie getExit() par polymorphisme.
     * La direction est ignoree : le joueur est toujours teleporte
     * vers une salle choisie par getRandomRoom().
     *
     * C est ici que le polymorphisme s applique : GameModel appelle
     * getExit() sur un objet Room, mais c est cette version qui s execute
     * quand la salle courante est une TransporterRoom.
     *
     * @param pDirection la direction tentee (ignoree)
     * @return une salle de destination aleatoire ou forcee
     */
    @Override
    public Room getExit(String pDirection)
    {
        return getRandomRoom();
    }

    /**
     * Retourne une salle de destination.
     * Si aleaString est defini, retourne la salle dont l image correspond.
     * Sinon, retourne une salle choisie aleatoirement via Random.nextInt().
     *
     * @return la salle de destination
     */
    public Room getRandomRoom()
    {
        // Mode test : on cherche la salle dont l image correspond a aleaString
        if (aleaString != null) {
            for (Room room : aTargetRooms) {
                String nomImage = room.getImageName().toLowerCase().replace(".png", "");
                if (nomImage.equals(aleaString.toLowerCase())) {
                    return room;
                }
            }
        }

        // Mode normal : tirage aleatoire
        Random vRandom = new Random();
        return aTargetRooms[vRandom.nextInt(this.aTargetRooms.length)];
    }
}
