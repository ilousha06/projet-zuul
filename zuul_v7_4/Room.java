import java.util.HashMap;

/**
 * La classe Room représente une pièce du jeu Zuul.
 * Une pièce possède une description et un ensemble de sorties
 * permettant de se déplacer vers d'autres pièces.
 */
public class Room
{
    /** Description de la pièce */
    private final String aDescription;

    /** Collection des sorties de la pièce (direction → Room) */
    private final HashMap<String, Room> aExits;

    /**
     * Construire une nouvelle pièce avec une description donnée.
     * 
     * @param pDescripion la description de la pièce
     */
    public Room(final String pDescripion)
    {
        this.aDescription = pDescripion;
        this.aExits = new HashMap<>();
    }

    /**
     * Ajoute une sortie à la pièce dans une direction donnée.
     * 
     * @param direction la direction de la sortie (north, south, up, down, etc.)
     * @param neighbor la pièce voisine associée à cette direction
     */
    public void setExit(String direction, Room neighbor)
    {
        this.aExits.put(direction, neighbor);
    }

    /**
     * Retourne la pièce voisine dans la direction donnée.
     * 
     * @param direction la direction à emprunter
     * @return la pièce correspondante ou null si aucune sortie n'existe
     */
    public Room getExit(final String direction)
    {
        return this.aExits.get(direction);
    }

    /**
     * Retourne la description de la pièce.
     * 
     * @return la description de la pièce
     */
    public String getDescription()
    {
        return this.aDescription;
    }

    /**
     * Construit et retourne une chaîne de caractères décrivant
     * toutes les sorties disponibles depuis cette pièce.
     * 
     * @return une chaîne contenant les directions possibles
     */
    public String getExitString()
    {
        StringBuilder vExits = new StringBuilder();

        for (String direction : this.aExits.keySet()) {
            vExits.append(direction).append(" ");
        }

        return vExits.toString();
    }
} // Room
