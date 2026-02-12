public class CommandWords
{
    private final String[] aValidCommands = { "go", "help", "quit" };

    public boolean isCommand(final String pString)
    {
        for (String aValidCommand : this.aValidCommands) {
            if (aValidCommand.equals(pString))
                return true;
        }
        return false;
    }
}
