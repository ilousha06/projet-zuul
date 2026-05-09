/**
 * La classe Command represente une commande saisie par le joueur.
 * Elle est composee d un mot principal (le verbe) et d un mot optionnel
 * (l argument, par exemple la direction ou le nom d un objet).
 *
 * @author Damiri Ilyas
 * @version 2.0
 */
public class Command
{
    /** Le premier mot de la commande (le verbe) */
    private final String commandWord;

    /** Le second mot de la commande (l argument), peut etre null */
    private final String secondWord;

    /**
     * Constructeur de la classe Command.
     *
     * @param firstWord  le verbe de la commande, null si commande inconnue
     * @param secondWord l argument de la commande, null si absent
     */
    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * Retourne le premier mot (le verbe) de la commande.
     *
     * @return le mot de commande
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * Retourne le second mot (l argument) de la commande.
     *
     * @return le second mot, ou null s il est absent
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * Verifie si la commande possede un second mot.
     *
     * @return true si un second mot est present, false sinon
     */
    public boolean hasSecondWord()
    {
        return secondWord != null;
    }

    /**
     * Verifie si la commande est inconnue.
     * Une commande est inconnue si son premier mot est null.
     *
     * @return true si la commande est inconnue, false sinon
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }
}
