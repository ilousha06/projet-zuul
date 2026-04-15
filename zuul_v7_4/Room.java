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

    /** Collection des sorties de la pièce (direction = Room) */
    private final HashMap<String, Room> aExits;

    /** Nom du fichier image associé à la pièce */
    private final String aImageName;

    /** Item présent dans la pièce (peut être null si aucun objet) */
    private Item aItem; // contient l’objet de la salle
    /**
     * Construit une nouvelle pièce avec une description et une image.
     *
     * @param pDescription la description de la pièce
     * @param pImage le nom du fichier image associé à la pièce
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        this.aImageName = pImage;
        this.aExits = new HashMap<>();
    }

    /**
     * Ajoute une sortie à la pièce dans une direction donnée.
     *
     * @param pDirection la direction de la sortie (north, south, etc.)
     * @param pNeighbor la pièce voisine
     */
    public void setExit(final String pDirection, final Room pNeighbor)
    {
        this.aExits.put(pDirection, pNeighbor);
    }

    /**
     * Retourne la pièce voisine dans une direction donnée.
     */
    public Room getExit(final String pDirection)
    {
        return this.aExits.get(pDirection);
    }

    /**
     * Retourne la description de la pièce.
     */
    public String getDescription()
    {
        return this.aDescription;
    }

    /**
     * Retourne les sorties disponibles.
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
     * Retourne une description complète :
     * description + sorties + item
     */
    public String getLongDescription()
    {
        return "Vous êtes " + this.aDescription + ".\n"
                + this.getExitString() + "\n"
                + this.getItemString(); //affichage item
    }

    /**
     * Retourne le nom de l’image.
     */
    public String getImageName()
    {
        return this.aImageName;
    }

    /**
     * Associe un item à la pièce.
     */
    public void setItem(Item item)
    {
        this.aItem = item; //on place un objet dans la salle
    }

    /**
     * Retourne une description de l’item présent.
     */
    public String getItemString()
    {
        if(aItem == null) {
            return "No item here.";
        }
        return "Item: " + aItem.getLongDescription(); // MVC
    }

    /**
     * Retourne l'item présent dans la pièce correspondant au nom donné.
     *
     * @param name le nom de l'objet recherché
     * @return l'item s'il existe dans la pièce, sinon null
     */
    public Item getItem(String name)
    {
        // on vérifie d'abord qu'il y a bien un item dans la salle
        // sinon ça évite une erreur
        if(aItem != null && aItem.getName().equalsIgnoreCase(name)) {
            // equalsIgnoreCase = ignore majuscule/minuscule
            return aItem; // on retourne l'objet trouvé
        }

        //si aucun item ou mauvais nom → on retourne null
        return null;
    }
}