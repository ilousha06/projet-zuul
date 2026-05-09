import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * La classe Player represente le joueur du jeu.
 * Elle gere :
 * - la position actuelle du joueur
 * - l'historique des deplacements
 * - les items portes
 * - le systeme de poids (limite + affichage)
 * - la confirmation de la trap door
 * - la liste des salles visitees (pour le beamer)
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

    /** Liste de toutes les salles deja visitees (pour le beamer) */
    private final List<Room> visitedRooms;

    /**
     * Constructeur du joueur.
     * Initialise l'historique, l'inventaire et la liste des salles visitees.
     */
    public Player()
    {
        this.aMaxWeight = 5;
        this.aCurrentWeight = 0;
        this.aHistory = new Stack<>();
        this.aItems = new ItemList();
        this.trapDoorConfirmed = false;
        this.visitedRooms = new ArrayList<>();
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
     * Retourne la liste des salles deja visitees.
     * Utilisee par le beamer pour choisir une destination.
     *
     * @return liste des salles visitees
     */
    public List<Room> getVisitedRooms()
    {
        return this.visitedRooms;
    }

    /**
     * Retourne la salle actuelle du joueur.
     *
     * @return la salle dans laquelle se trouve le joueur
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }

    /**
     * Definit la salle de depart du joueur.
     *
     * @param pRoom la salle a definir comme position actuelle
     */
    public void setCurrentRoom(final Room pRoom)
    {
        this.aCurrentRoom = pRoom;
    }

    /**
     * Deplace le joueur vers une nouvelle salle.
     * Enregistre la salle actuelle dans l historique et dans les salles visitees.
     *
     * @param pNextRoom la salle vers laquelle le joueur se deplace
     */
    public void goRoom(final Room pNextRoom)
    {
        if (pNextRoom != null) {
            this.aHistory.push(this.aCurrentRoom);
            this.aCurrentRoom = pNextRoom;

            // Enregistre la salle si elle n'a pas encore ete visitee
            if (!visitedRooms.contains(pNextRoom)) {
                visitedRooms.add(pNextRoom);
            }
        }
    }

    /**
     * Retourne a la salle precedente via l historique.
     *
     * @return true si le retour a reussi, false si l historique est vide
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
     * Tente d ajouter un item a l inventaire si le poids maximum n est pas depasse.
     *
     * @param item l item a ramasser
     * @return true si l item a ete pris, false si trop lourd
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
     * Retire un item de l inventaire et met a jour le poids courant.
     *
     * @param name le nom de l item a deposer
     * @return l item depose, ou null s il n est pas dans l inventaire
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
     * Verifie si l inventaire contient au moins un item.
     *
     * @return true si l inventaire est non vide, false sinon
     */
    public boolean hasItem()
    {
        return !this.aItems.isEmpty();
    }

    /**
     * Verifie si un item specifique est dans l inventaire.
     *
     * @param name le nom de l item a rechercher
     * @return true si l item est present, false sinon
     */
    public boolean hasItem(String name)
    {
        return this.aItems.hasItem(name);
    }

    /**
     * Retourne l inventaire sous forme de texte lisible.
     *
     * @return une chaine listant les items et leur poids
     */
    public String getInventoryString()
    {
        return this.aItems.getInventoryString();
    }

    /**
     * Retourne le poids total actuellement porte par le joueur.
     *
     * @return le poids courant en unites de charge
     */
    public int getTotalWeight()
    {
        return this.aCurrentWeight;
    }

    /**
     * Retourne la capacite maximale de port du joueur.
     *
     * @return le poids maximum en unites de charge
     */
    public int getMaxWeight()
    {
        return this.aMaxWeight;
    }

    /**
     * Double la capacite maximale du joueur.
     * Utilise par l effet de l hostie sacree.
     */
    public void increaseMaxWeight()
    {
        this.aMaxWeight *= 2;
    }

    /**
     * Augmente la capacite maximale du joueur d une valeur donnee.
     *
     * @param value le nombre d unites a ajouter au poids max
     */
    public void addMaxWeight(int value)
    {
        this.aMaxWeight += value;
    }

    /**
     * Reduit la capacite maximale du joueur d une valeur donnee.
     *
     * @param value le nombre d unites a retirer du poids max
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