import java.util.HashMap;

/**
 * La classe Room represente une piece du jeu.
 * Une piece possede :
 * - une description
 * - une image associee
 * - un ensemble de sorties vers d autres pieces
 * - une liste d items
 * - un ensemble de portes verrouillees avec leurs conditions
 */
public class Room
{
    /** Description de la piece */
    private final String aDescription;

    /** Collection des sorties */
    private final HashMap<String, Room> aExits;

    /** Nom du fichier image */
    private final String aImageName;

    /** Liste des items presents dans la piece */
    private final ItemList aItems;

    /**
     * Constructeur de la classe Room.
     *
     * @param pDescription description de la piece
     * @param pImage       nom du fichier image
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        this.aImageName   = pImage;
        this.aExits       = new HashMap<>();
        this.aItems       = new ItemList();
    }

    /**
     * Ajoute une sortie a la piece.
     *
     * @param pDirection la direction
     * @param pNeighbor  la piece voisine
     */
    public void setExit(final String pDirection, final Room pNeighbor)
    {
        this.aExits.put(pDirection, pNeighbor);
    }

    /**
     * Retourne la piece voisine dans une direction donnee.
     *
     * @param pDirection la direction
     * @return la piece voisine ou null
     */
    public Room getExit(final String pDirection)
    {
        return this.aExits.get(pDirection);
    }

    /**
     * Retourne la description de la piece.
     */
    public String getDescription()
    {
        return this.aDescription;
    }

    /**
     * Retourne les sorties disponibles sous forme de texte.
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
     * Retourne une description complete de la piece.
     */
    public String getLongDescription()
    {
        return "Vous etes " + this.aDescription + ".\n"
                + this.getExitString() + "\n"
                + this.getItemString();
    }

    /**
     * Retourne le nom de l image associee.
     */
    public String getImageName()
    {
        return this.aImageName;
    }

    /**
     * Ajoute un item dans la piece.
     *
     * @param item l item a ajouter
     */
    public void addItem(Item item)
    {
        this.aItems.addItem(item);
    }

    /**
     * Retourne la liste des items presents dans la piece.
     */
    public String getItemString()
    {
        return this.aItems.getItemString();
    }

    /**
     * Recherche un item par son nom dans la piece.
     *
     * @param name le nom de l item
     * @return l item ou null
     */
    public Item getItem(String name)
    {
        return this.aItems.getItem(name);
    }

    /**
     * Supprime un item de la piece.
     *
     * @param item l item a supprimer
     */
    public void removeItem(Item item)
    {
        this.aItems.removeItem(item.getName());
    }

    /**
     * Verifie si une salle fait partie des sorties.
     *
     * @param pRoom la salle a verifier
     * @return true si la salle est une sortie
     */
    public boolean isExit(Room pRoom)
    {
        return this.aExits.containsValue(pRoom);
    }
}