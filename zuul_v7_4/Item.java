/**
 * Représente un objet du jeu
 */
public class Item
{
    private String description;
    private int weight;

    public Item(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
    }

    public String getDescription()
    {
        return description;
    }

    public int getWeight()
    {
        return weight;
    }

    /**
     * description complète de l’item
     */
    public String getLongDescription()
    {
        return description + " (" + weight + " kg)";
    }
}