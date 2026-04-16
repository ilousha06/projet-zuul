import java.util.Stack;

/**
 * La classe Player représente le joueur du jeu.
 * Elle gère :
 * - la position actuelle du joueur (salle courante)
 * - l’historique des déplacements (pour la commande back)
 */
public class Player
{
    /** Salle actuelle du joueur */
    private Room aCurrentRoom;

    /** Historique des salles visitées */
    private final Stack<Room> aHistory; //important

    private Item aItem;

    /**
     * Constructeur du joueur.
     * Initialise l’historique des déplacements.
     */
    public Player()
    {
        this.aHistory = new Stack<>();
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
            this.aHistory.push(this.aCurrentRoom); // sauvegarde de la salle
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
            return true;    // succès
        }
        return false;       // rien à faire
    }

    /**
     * Permet de prendre un item
     */
    public void takeItem(Item item)
    {
        this.aItem = item;
    }

    /**
     * Permet de déposer un item
     */
    public Item dropItem()
    {
        Item temp = this.aItem;
        this.aItem = null;
        return temp;
    }

    /**
     * Vérifie si le joueur porte un item
     */
    public boolean hasItem()
    {
        return this.aItem != null;
    }
}