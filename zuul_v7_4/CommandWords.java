public class CommandWords
{
    private final String[] aValidCommands = {"go", "help", "quit", "look"};

    public boolean isCommand(final String pString)
    {
        for(String vCommand : aValidCommands) {
            if(vCommand.equals(pString)) {
                return true;
            }
        }
        return false;
    }
}

