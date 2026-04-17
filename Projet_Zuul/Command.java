
/**
 * Classe Command - une commande du jeu d'aventure Zuul.
 *
 * @author Damiri Ilyas
 */
public class Command
{
    private final String commandWord;
    private final String secondWord;

    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    public String getCommandWord()
    {
        return commandWord;
    }

    public String getSecondWord()
    {
        return secondWord;
    }

    public boolean hasSecondWord()
    {
        return secondWord != null;
    }

    public boolean isUnknown()
    {
        return (commandWord == null);
    }
} // Command
