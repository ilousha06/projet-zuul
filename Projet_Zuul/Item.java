/**
 * La classe Item represente un objet du jeu.
 * Chaque item possede un nom, une description et un poids.
 * Les items peuvent etre ramasses, deposes et utilises par le joueur.
 *
 * @author Ilyas
 * @version 2.0
 */
public class Item
{
    /** Description detaillee de l item */
    private final String aDescription;

    /** Poids de l item en unites de charge */
    private final int aWeight;

    /** Nom unique identifiant l item */
    private final String aName;

    /**
     * Constructeur de la classe Item.
     *
     * @param name        le nom unique de l item
     * @param description la description detaillee de l item
     * @param weight      le poids de l item en unites de charge
     */
    public Item(final String name, final String description, final int weight)
    {
        this.aName = name;
        this.aDescription = description;
        this.aWeight = weight;
    }

    /**
     * Retourne la description de l item.
     *
     * @return la description de l item
     */
    public String getDescription()
    {
        return aDescription;
    }

    /**
     * Retourne le poids de l item.
     *
     * @return le poids en unites de charge
     */
    public int getWeight()
    {
        return aWeight;
    }

    /**
     * Retourne le nom de l item.
     *
     * @return le nom de l item
     */
    public String getName()
    {
        return aName;
    }

    /**
     * Retourne une description complete de l item avec son nom, sa description et son poids.
     *
     * @return une chaine contenant toutes les informations de l item
     */
    public String getLongDescription()
    {
        return "Item: " + aName + " - " + aDescription + " (" + aWeight + " kg)";
    }
}
