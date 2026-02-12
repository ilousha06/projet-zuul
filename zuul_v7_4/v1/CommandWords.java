package v1;

public class CommandWords
{
    private final String[] aValidCommands = { "go", "help", "quit" };

    public boolean isCommand(final String pString)
    {
        for (int vI = 0; vI < this.aValidCommands.length; vI++) {
            if (this.aValidCommands[vI].equals(pString))
                return true;
        }
        return false;
    }
}
