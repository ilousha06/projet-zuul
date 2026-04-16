import java.util.HashMap;

/**
 * La classe ItemList permet de gérer une collection d'items.
 *
 * Elle encapsule une structure de données (HashMap) afin de :
 * - stocker les items
 * - accéder rapidement à un item via son nom
 * - ajouter et retirer des items
 */
public class ItemList
{
    /** Collection des items (clé = nom de l'item) */
    private final HashMap<String, Item> aItems;

    /**
     * Constructeur de la classe ItemList.
     * Initialise la collection d'items.
     */
    public ItemList()
    {
        this.aItems = new HashMap<>();
    }

    /**
     * Ajoute un item à la collection.
     *
     * @param pItem l'item à ajouter
     */
    public void addItem(final Item pItem)
    {
        this.aItems.put(pItem.getName(), pItem);
    }

    /**
     * Retire un item de la collection à partir de son nom.
     *
     * @param pName le nom de l'item à retirer
     * @return l'item retiré, ou null s'il n'existe pas
     */
    public Item removeItem(final String pName)
    {
        return this.aItems.remove(pName);
    }

    /**
     * Retourne un item à partir de son nom.
     *
     * @param pName le nom de l'item
     * @return l'item correspondant, ou null s'il n'existe pas
     */
    public Item getItem(final String pName)
    {
        return this.aItems.get(pName);
    }

    /**
     * Vérifie si un item est présent dans la collection.
     *
     * @param pName le nom de l'item
     * @return true si l'item existe, false sinon
     */
    public boolean hasItem(final String pName)
    {
        return this.aItems.containsKey(pName);
    }

    /**
     * Vérifie si la collection est vide.
     *
     * @return true si aucun item n'est présent, false sinon
     */
    public boolean isEmpty()
    {
        return this.aItems.isEmpty();
    }

    /**
     * Retourne une description des items présents.
     *
     * @return une chaîne contenant la liste des items
     */
    public String getItemString()
    {
        if(this.aItems.isEmpty()) {
            return "Items: none";
        }

        StringBuilder vResult = new StringBuilder("Items:");

        for(Item vItem : this.aItems.values()) {
            vResult.append("\n - ").append(vItem.getName());
        }

        return vResult.toString();
    }
}