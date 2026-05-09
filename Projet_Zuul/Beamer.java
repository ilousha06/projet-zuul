/**
 * La classe Beamer represente la relique occulte du jeu.
 * Elle permet de memoriser une salle et de teleporter
 * le joueur exactement dans cette salle plus tard.
 * Elle ne peut etre utilisee qu'une seule fois.
 */
public class Beamer extends Item
{
    /** La salle memorisee lors du chargement */
    private Room chargedRoom;

    /** Indique si la relique a deja ete utilisee */
    private boolean used;

    /**
     * Constructeur du beamer.
     * Initialise la relique comme non chargee et non utilisee.
     */
    public Beamer()
    {
        super("beamer", "Une relique occulte qui pulse d'une energie etrange.", 1);
        this.chargedRoom = null;
        this.used = false;
    }

    /**
     * Charge la relique en memorisant la salle donnee.
     *
     * @param room la salle a memoriser
     */
    public void charge(Room room)
    {
        if (used) return;
        this.chargedRoom = room;
    }

    /**
     * Retourne true si la relique est chargee.
     *
     * @return true si chargee
     */
    public boolean isCharged()
    {
        return this.chargedRoom != null;
    }

    /**
     * Retourne true si la relique a deja ete utilisee.
     *
     * @return true si utilisee
     */
    public boolean isUsed()
    {
        return this.used;
    }

    /**
     * Utilise la relique pour teleporter le joueur
     * dans la salle qui a ete memorisee lors du chargement.
     * La relique devient inutilisable apres utilisation.
     *
     * @return la salle memorisee, ou null si non chargee
     */
    public Room fire()
    {
        if (chargedRoom == null || used) return null;
        this.used = true;
        Room destination = this.chargedRoom;
        this.chargedRoom = null;
        return destination;
    }

    /**
     * Recharge le beamer pour pouvoir le reutiliser.
     * Reinitialise l etat utilise et vide la salle memorisee.
     * Necessite un cristal de recharge pour etre appele.
     */
    public void recharge()
    {
        this.used = false;
        this.chargedRoom = null;
    }
}