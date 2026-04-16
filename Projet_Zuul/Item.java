/**
 * Représente un objet du jeu
 */
public class Item
{
    private final String aDescription;
    private final int aWeight;
    private final String aName;

    public Item(final String name, final String description, final int weight)
    {
        this.aName = name;
        this.aDescription = description;
        this.aWeight = weight;
    }

    public String getDescription()
    {
        return aDescription;
    }

    public int getWeight()
    {
        return aWeight;
    }

    public String getName()
    {
        return aName;
    }

    /**
     * description complète de l’item
     */
    public String getLongDescription()
    {
        return "Item: " + aName + " : " + aDescription + " (" + aWeight + " kg)";
    }
}