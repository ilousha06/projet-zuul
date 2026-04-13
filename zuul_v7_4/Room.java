import java.util.HashMap;

/**
 * La classe Room représente une pièce du jeu Zuul.
 * Une pièce possède :
 * - une description
 * - une image associée
 * - un ensemble de sorties vers d'autres pièces
 */
public class Room
{
    /** Description de la pièce */
    private final String aDescription;

    /** Collection des sorties de la pièce (direction → Room) */
    private final HashMap<String, Room> aExits;

    /** Nom du fichier image associé à la pièce */
    private final String imageName;

    /**
     * Construit une nouvelle pièce avec une description et une image.
     *
     * @param pDescription la description de la pièce
     * @param pImage le nom du fichier image associé à la pièce
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        this.imageName = pImage;
        this.aExits = new HashMap<>();
    }

    /**
     * Ajoute une sortie à la pièce dans une direction donnée.
     *
     * @param pDirection la direction de la sortie (north, south, up, down, etc.)
     * @param pNeighbor la pièce voisine associée à cette direction
     */
    public void setExit(final String pDirection, final Room pNeighbor)
    {
        this.aExits.put(pDirection, pNeighbor);
    }

    /**
     * Retourne la pièce voisine dans la direction donnée.
     *
     * @param pDirection la direction à emprunter
     * @return la pièce correspondante ou null si aucune sortie n'existe
     */
    public Room getExit(final String pDirection)
    {
        return this.aExits.get(pDirection);
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
     * Construit une chaîne contenant toutes les sorties disponibles.
     *
     * @return une chaîne contenant les directions possibles
     */
    public String getExitString()
    {
        StringBuilder vExit = new StringBuilder("Exits:");

        for (String direction : this.aExits.keySet()) {
            vExit.append(" ").append(direction);
        }

        return vExit.toString();
    }

    /**
     * Retourne une description complète de la pièce,
     * incluant la description et les sorties.
     *
     * @return la description complète de la pièce
     */
    public String getLongDescription()
    {
        return "Vous êtes " + this.aDescription + ".\n" + this.getExitString();
    }

    /**
     * Retourne le nom du fichier image associé à la pièce.
     *
     * @return le nom de l'image
     */
    public String getImageName()
    {
        return this.imageName;
    }
}