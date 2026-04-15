import java.util.HashMap;
import java.util.ArrayList;

/**
 * La classe Room représente une pièce du jeu Zuul.
 * Une pièce possède :
 * - une description
 * - une image associée
 * - un ensemble de sorties vers d'autres pièces
 * - une liste d'items (plusieurs objets possibles)
 */
public class Room
{
    /** Description de la pièce */
    private final String aDescription;

    /** Collection des sorties */
    private final HashMap<String, Room> aExits;

    /** Nom du fichier image */
    private final String aImageName;

    /** Liste des items présents dans la pièce */
    private final ArrayList<Item> aItems;

    /**
     * Constructeur de la classe Room
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        this.aImageName = pImage;
        this.aExits = new HashMap<>();
        this.aItems = new ArrayList<>(); // initialisation de la liste des items
    }

    /**
     * Ajoute une sortie à la pièce
     */
    public void setExit(final String pDirection, final Room pNeighbor)
    {
        this.aExits.put(pDirection, pNeighbor);
    }

    /**
     * Retourne la pièce voisine dans une direction donnée
     */
    public Room getExit(final String pDirection)
    {
        return this.aExits.get(pDirection);
    }

    /**
     * Retourne la description de la pièce
     */
    public String getDescription()
    {
        return this.aDescription;
    }

    /**
     * Retourne les sorties disponibles
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
     * description + sorties + items
     */
    public String getLongDescription()
    {
        return "Vous êtes " + this.aDescription + ".\n"
                + this.getExitString() + "\n"
                + this.getItemString();
    }

    /**
     * Retourne le nom de l’image associée
     */
    public String getImageName()
    {
        return this.aImageName;
    }


    /**
     * Ajoute un item dans la pièce
     */
    public void addItem(Item item)
    {
        this.aItems.add(item); // ajoute un objet dans la salle
    }

    /**
     * Retourne la liste des items présents dans la pièce
     */
    public String getItemString()
    {
        if(aItems.isEmpty()) {
            return "No item here.";
        }

        StringBuilder result = new StringBuilder("Items:");

        for(Item item : aItems) {
            result.append(" ").append(item.getName());
        }

        return result.toString();
    }

    /**
     * Recherche un item par son nom dans la pièce
     */
    public Item getItem(String name)
    {
        for(Item item : aItems) {
            if(item.getName().equalsIgnoreCase(name)) return item;
        }
        return null;
    }
}