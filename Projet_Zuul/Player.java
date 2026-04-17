import java.util.Stack;

/**
 * La classe Player représente le joueur du jeu.
 * Elle gère :
 * - la position actuelle du joueur
 * - l’historique des déplacements
 * - les items portés
 * - le système de poids (limite + affichage)
 */
public class Player
{
    /** Salle actuelle du joueur */
    private Room aCurrentRoom;

    /** Historique des salles visitées */
    private final Stack<Room> aHistory;

    /** Inventaire du joueur */
    private final ItemList aItems;

    /** Poids maximum que le joueur peut porter */
    private int aMaxWeight;

    /** Poids actuel transporté */
    private int aCurrentWeight;

    /**
     * Constructeur du joueur.
     * Initialise l’historique et l’inventaire.
     */
    public Player()
    {
        this.aMaxWeight = 5;
        this.aCurrentWeight = 0;
        this.aHistory = new Stack<>();
        this.aItems = new ItemList();
    }

    /**
     * Retourne la salle actuelle.
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }

    /**
     * Définit la salle actuelle.
     */
    public void setCurrentRoom(final Room pRoom)
    {
        this.aCurrentRoom = pRoom;
    }

    /**
     * Déplace le joueur vers une nouvelle salle.
     */
    public void goRoom(final Room pNextRoom)
    {
        if(pNextRoom != null) {
            this.aHistory.push(this.aCurrentRoom);
            this.aCurrentRoom = pNextRoom;
        }
    }

    /**
     * Retourne à la salle précédente.
     */
    public boolean goBack()
    {
        if(!this.aHistory.isEmpty()) {
            this.aCurrentRoom = this.aHistory.pop();
            return true;
        }
        return false;
    }

    /**
     * Ajoute un item si le poids le permet.
     */
    public boolean takeItem(Item item)
    {
        int weight = item.getWeight();

        if(this.aCurrentWeight + weight > this.aMaxWeight) {
            return false;
        }

        this.aItems.addItem(item);
        this.aCurrentWeight += weight;
        return true;
    }

    /**
     * Retire un item de l’inventaire.
     */
    public Item dropItem(String name)
    {
        Item item = this.aItems.removeItem(name);

        if(item != null) {
            this.aCurrentWeight -= item.getWeight();
        }

        return item;
    }

    /**
     * Vérifie si inventaire vide.
     */
    public boolean hasItem()
    {
        return !this.aItems.isEmpty();
    }

    /**
     * Vérifie si un item existe.
     */
    public boolean hasItem(String name)
    {
        return this.aItems.hasItem(name);
    }

    /**
     * Retourne l'inventaire sous forme de texte.
     */
    public String getInventoryString()
    {
        return this.aItems.getInventoryString();
    }

    /**
     * Retourne le poids actuel.
     */
    public int getTotalWeight()
    {
        return this.aCurrentWeight;
    }

    /**
     * Retourne le poids maximum.
     */
    public int getMaxWeight()
    {
        return this.aMaxWeight;
    }

    /**
     * Double la capacité maximale.
     */
    public void increaseMaxWeight()
    {
        this.aMaxWeight *= 2;
    }

    /**
     * Ajoute au poids max.
     */
    public void addMaxWeight(int value)
    {
        this.aMaxWeight += value;
    }

    /**
     * Réduit le poids max.
     */
    public void reduceMaxWeight(int value)
    {
        this.aMaxWeight -= value;
    }

    /**
     * Génère une barre visuelle du poids transporté.
     * Exemple : [████░░░░░░]
     *
     * @return barre de progression du poids
     */
    public String getWeightBar()
    {
        int maxBars = 10;

        // éviter division par 0
        if(aMaxWeight == 0) return "[----------]";

        int filled = (int)((double)aCurrentWeight / aMaxWeight * maxBars);

        StringBuilder bar = new StringBuilder("[");

        for(int i = 0; i < maxBars; i++) {
            if(i < filled) {
                bar.append("█");
            } else {
                bar.append("=");
            }
        }

        bar.append("]");
        return bar.toString();
    }

    /**
     * Retourne une version complète du poids (texte + barre).
     *
     * @return ex : Poids : 3/5 [████======]
     */
    public String getWeightInfo()
    {
        return "Poids : " + aCurrentWeight + "/" + aMaxWeight + " " + getWeightBar();
    }
}