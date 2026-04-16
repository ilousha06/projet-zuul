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

    /**
     * Constructeur du joueur.
     * Initialise l’historique et la collection d’items.
     */
    public Player()
    {
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
     * Ajoute un item à l'inventaire du joueur.
     */
    public void takeItem(Item item)
    {
        this.aItems.addItem(item);
    }

    /**
     * Retire un item de l'inventaire du joueur.
     *
     * @param name nom de l'item
     * @return l'item retiré ou null s'il n'existe pas
     */
    public Item dropItem(String name)
    {
        return this.aItems.removeItem(name);
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
}