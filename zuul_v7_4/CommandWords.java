public class CommandWords
{
    private final String[] aValidCommands = {"go", "help", "quit", "look", "eat"};

    public boolean isCommand(final String pString)
    {
        for(String vCommand : aValidCommands) {
            if(vCommand.equals(pString)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Construit et retourne la liste des commandes valides.
     * Les commandes sont concaténées dans une chaîne de caractères
     * afin de pouvoir être affichées dans l'aide du jeu.
     *
     * @return une chaîne contenant toutes les commandes valides
     */
    public String getCommandList()
    {
        StringBuilder vCommands = new StringBuilder();

        for(String vCommand : aValidCommands) {
            vCommands.append(vCommand).append(" ");
        }

        return vCommands.toString();
    }

}

