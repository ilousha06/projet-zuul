/**
 * Représente un objet dans le jeu
 */
public class Item
{
    private final String description;
    private final int weight;

    public Item(final String description, final int weight)
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
}
