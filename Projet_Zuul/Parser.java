import java.util.Scanner;

/**
 * Analyse les commandes saisies par l'utilisateur.
 */
public class Parser
{
    /** Liste des commandes valides */
    private final CommandWords aCommandWords;

    /**
     * Constructeur du parser
     */
    public Parser()
    {
        this.aCommandWords = new CommandWords();
    }

    /**
     * Analyse une ligne de commande saisie par l'utilisateur
     *
     * @param pCommandLine la commande entrée sous forme de texte
     * @return un objet Command correspondant
     */
    public Command getCommand(String pCommandLine)
    {
        String vWord1 = null;
        String vWord2 = null;

        Scanner vTokenizer = new Scanner(pCommandLine);

        if (vTokenizer.hasNext()) {
            vWord1 = vTokenizer.next();
            if (vTokenizer.hasNext()) {
                vWord2 = vTokenizer.next();
            }
        }

        if (this.aCommandWords.isCommand(vWord1)) {
            return new Command(vWord1, vWord2);
        }
        else {
            return new Command(null, null);
        }
    }

    /**
     * Retourne la liste des commandes valides
     */
    public String getCommandList()
    {
        return this.aCommandWords.getCommandList();
    }
}