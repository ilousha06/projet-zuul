/**
 * La classe CommandWords contient l'ensemble des commandes valides
 * reconnues par le jeu Zuul.
 * Elle est responsable de la validation des commandes et de la
 * production de la liste des commandes disponibles.
 */
public class CommandWords
{
    /** Tableau contenant les commandes valides du jeu */
    private final String[] aValidCommands = {
            "go", "help", "quit", "look", "eat"
    };

    /**
     * Vérifie si une chaîne correspond à une commande valide.
     *
     * @param pString la chaîne à tester
     * @return true si la commande est valide, false sinon
     */
    public boolean isCommand(final String pString)
    {
        for (String vCommand : aValidCommands) {
            if (vCommand.equals(pString)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Construit et retourne la liste des commandes valides.
     * Cette méthode ne fait qu'assembler l'information et
     * ne réalise aucun affichage.
     *
     * @return une chaîne contenant toutes les commandes valides
     */
    public String getCommandList()
    {
        StringBuilder vCommands = new StringBuilder();

        for (String vCommand : aValidCommands) {
            vCommands.append(vCommand).append(" ");
        }

        return vCommands.toString();
    }
}
