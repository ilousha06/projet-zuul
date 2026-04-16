import java.util.HashMap;
import java.util.Stack;

/**
 * La classe Player représente le joueur du jeu.
 * Elle gère :
 * - la position actuelle du joueur
 * - l’historique des déplacements
 * - les items portés par le joueur
 */
public class Player
{
    /** Salle actuelle du joueur */
    private Room aCurrentRoom;

    /** Historique des salles visitées */
    private final Stack<Room> aHistory;

    /**
     * Collection des items portés par le joueur
     * (clé = nom de l'item, valeur = objet Item)
     * ENGROS permet de prendre plusieurs items
     */
    private final ItemList aItems;

    /** Poids maximum que le joueur peut porter */
    private int aMaxWeight;

    /** Poids actuel transporté */
    private int aCurrentWeight;

    /**
     * Constructeur du joueur.
     * Initialise l’historique et la collection d’items.
     */
    public Player()
    {
        this.aMaxWeight = 5;
        this.aCurrentWeight = 0;
        this.aHistory = new Stack<>();
        this.aItems = new ItemList();
    }

    /**
     * Retourne la salle actuelle du joueur.
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }

    /**
     * Définit la salle actuelle du joueur (position initiale).
     */
    public void setCurrentRoom(final Room pRoom)
    {
        this.aCurrentRoom = pRoom;
    }

    /**
     * Déplace le joueur vers une nouvelle salle.
     * La salle actuelle est sauvegardée dans l’historique.
     */
    public void goRoom(final Room pNextRoom)
    {
        if(pNextRoom != null) {
            this.aHistory.push(this.aCurrentRoom);
            this.aCurrentRoom = pNextRoom;
        }
    }

    /**
     * Permet de revenir à la salle précédente.
     *
     * @return true si le retour est possible, false sinon
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
     * Ajoute un item à l'inventaire du joueur avec limite de slot.
     *
     * @param item nom de l'item
     * @return l'item ajouté ou trop lourd
     */
    public boolean takeItem(Item item)
    {
        int weight = item.getWeight();

        if(this.aCurrentWeight + weight > this.aMaxWeight) {
            return false; // trop lourd
        }
        else {
            this.aItems.addItem(item);
            this.aCurrentWeight += weight;
            return true;
        }

    }

    /**
     * Retire un item de l'inventaire du joueur.
     *
     * @param name nom de l'item
     * @return l'item retiré ou null s'il n'existe pas
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
     * Vérifie si le joueur possède au moins un item.
     */
    public boolean hasItem()
    {
        return !this.aItems.isEmpty();
    }

    /**
     * Vérifie si le joueur possède un item spécifique.
     */
    public boolean hasItem(String name)
    {
        return this.aItems.hasItem(name);
    }

    /**
     * Retourne la description de l'inventaire du joueur.
     * Délègue l'affichage à la classe ItemList.
     *
     * @return une chaîne décrivant les items portés
     */
    public String getInventoryString()
    {
        return this.aItems.getInventoryString();
    }

    /**
     * Retourne le poids total des items portés par le joueur.
     *
     * @return le poids total
     */
    public int getTotalWeight()
    {
        return this.aCurrentWeight;
    }


    /**
     * Double la capacité de port du joueur
     */
    public void increaseMaxWeight()
    {
        this.aMaxWeight *= 2;
    }

    /**
     * Ajoute une valeur au poids maximum
     */
    public void addMaxWeight(int value)
    {
        this.aMaxWeight += value;
    }

    /**
     * Réduit la capacité du joueur
     */
    public void reduceMaxWeight(int value)
    {
        this.aMaxWeight -= value;
    }

    /**
     * Retourne le poids max
     */
    public int getMaxWeight()
    {
        return this.aMaxWeight;
    }
}