import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La classe RoomRandomizer gere une liste globale de salles
 * et permet d en choisir une aleatoirement.
 *
 * Elle est utilisee par TransporterRoom pour determiner
 * la destination de teleportation du joueur.
 *
 * @author Ilyas
 * @version 1.0
 */
public class RoomRandomizer
{
    /** Liste de toutes les salles enregistrees */
    private static final List<Room> rooms = new ArrayList<>();

    /** Generateur de nombres aleatoires */
    private static final Random random = new Random();

    /**
     * Enregistre une salle dans la liste des destinations possibles.
     *
     * @param room la salle a ajouter
     */
    public static void addRoom(Room room)
    {
        rooms.add(room);
    }

    /**
     * Retourne une salle choisie aleatoirement parmi toutes les salles enregistrees.
     *
     * @return une salle aleatoire, ou null si aucune salle n est enregistree
     */
    public static Room getRandomRoom()
    {
        if (rooms.isEmpty()) return null;
        // nextInt(n) retourne un entier entre 0 (inclus) et n (exclu)
        return rooms.get(random.nextInt(rooms.size()));
    }
}
