import java.util.Stack;

/**
 * La classe Player represente le joueur du jeu.
 * Elle gere :
 * - la position actuelle du joueur
 * - l'historique des deplacements
 * - les items portes
 * - le systeme de poids (limite + affichage)
 * - la confirmation de la trap door
 */
public class Player
{
    /** Salle actuelle du joueur */
    private Room aCurrentRoom;

    /** Historique des salles visitees */
    private final Stack<Room> aHistory;

    /** Inventaire du joueur */
    private final ItemList aItems;

    /** Poids maximum que le joueur peut porter */
    private int aMaxWeight;

    /** Poids actuel transporte */
    private int aCurrentWeight;

    /** Indique si le joueur a deja vu l'avertissement de la trap door */
    private boolean trapDoorConfirmed;

    /**
     * Constructeur du joueur.
     * Initialise l'historique et l'inventaire.
     */
    public Player()
    {
        this.aMaxWeight       = 5;
        this.aCurrentWeight   = 0;
        this.aHistory         = new Stack<>();
        this.aItems           = new ItemList();
        this.trapDoorConfirmed = false;
    }

    /**
     * Retourne true si le joueur a deja vu l'avertissement de la trap door.
     *
     * @return true si la confirmation est en attente
     */
    public boolean isTrapDoorConfirmed()
    {
        return trapDoorConfirmed;
    }

    /**
     * Definit si le joueur a confirme vouloir passer la trap door.
     *
     * @param confirmed true si l'avertissement a ete affiche
     */
    public void setTrapDoorConfirmed(boolean confirmed)
    {
        trapDoorConfirmed = confirmed;
    }

    /**
     * Retourne la salle actuelle.
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }

    /**
     * Definit la salle actuelle.
     */
    public void setCurrentRoom(final Room pRoom)
    {
        this.aCurrentRoom = pRoom;
    }

    /**
     * Deplace le joueur vers une nouvelle salle.
     */
    public void goRoom(final Room pNextRoom)
    {
        if (pNextRoom != null) {
            this.aHistory.push(this.aCurrentRoom);
            this.aCurrentRoom = pNextRoom;
        }
    }

    /**
     * Retourne a la salle precedente.
     */
    public boolean goBack()
    {
        if (!this.aHistory.isEmpty()) {
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
        if (this.aCurrentWeight + weight > this.aMaxWeight) {
            return false;
        }
        this.aItems.addItem(item);
        this.aCurrentWeight += weight;
        return true;
    }

    /**
     * Retire un item de l'inventaire.
     */
    public Item dropItem(String name)
    {
        Item item = this.aItems.removeItem(name);
        if (item != null) {
            this.aCurrentWeight -= item.getWeight();
        }
        return item;
    }

    /**
     * Verifie si l'inventaire est vide.
     */
    public boolean hasItem()
    {
        return !this.aItems.isEmpty();
    }

    /**
     * Verifie si un item existe dans l'inventaire.
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
     * Double la capacite maximale.
     */
    public void increaseMaxWeight()
    {
        this.aMaxWeight *= 2;
    }

    /**
     * Ajoute de la capacite au poids max.
     */
    public void addMaxWeight(int value)
    {
        this.aMaxWeight += value;
    }

    /**
     * Reduit le poids max.
     */
    public void reduceMaxWeight(int value)
    {
        this.aMaxWeight -= value;
    }

    /**
     * Genere une barre visuelle du poids transporte.
     * Exemple : [████======]
     *
     * @return barre de progression du poids
     */
    public String getWeightBar()
    {
        int maxBars = 10;
        if (aMaxWeight == 0) return "[----------]";
        int filled = (int)((double) aCurrentWeight / aMaxWeight * maxBars);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < maxBars; i++) {
            bar.append(i < filled ? "█" : "=");
        }
        bar.append("]");
        return bar.toString();
    }

    /**
     * Retourne une version complete du poids (texte + barre).
     * Exemple : Poids : 3/5 [████======]
     *
     * @return texte complet du poids
     */
    public String getWeightInfo()
    {
        return "Poids : " + aCurrentWeight + "/" + aMaxWeight + " " + getWeightBar();
    }

    /**
     * Vide l'historique des salles.
     * Utilise apres une trap door.
     */
    public void clearHistory()
    {
        this.aHistory.clear();
    }

    /**
     * Retourne la salle precedente sans la supprimer.
     *
     * @return salle precedente ou null si aucune
     */
    public Room getPreviousRoom()
    {
        if (this.aHistory.isEmpty()) {
            return null;
        }
        return this.aHistory.peek();
    }
}