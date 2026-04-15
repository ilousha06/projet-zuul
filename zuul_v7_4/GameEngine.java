/**
 * La classe GameEngine représente le contrôleur du jeu (architecture MVC).
 * Elle gère :
 * - l’interprétation des commandes
 * - la communication entre le modèle (GameModel) et la vue (UserInterface)
 *
 * Elle ne contient pas la logique du jeu directement.
 */
public class GameEngine
{
    /** Analyseur des commandes */
    private final Parser aParser;

    /** Interface graphique */
    private UserInterface gui;

    /** Modèle du jeu (logique) */
    private final GameModel model;

    /**
     * Constructeur du moteur du jeu.
     * Initialise le modèle et le parser.
     */
    public GameEngine()
    {
        this.model = new GameModel();
        this.aParser = new Parser();
    }

    /**
     * Associe l'interface graphique au moteur du jeu
     * et affiche le message de bienvenue.
     *
     * @param pUserInterface l'interface graphique
     */
    public void setGUI(UserInterface pUserInterface)
    {
        this.gui = pUserInterface;
        this.printWelcome();
    }

    /**
     * Interprète une commande saisie par l'utilisateur.
     *
     * @param pCommandLine la commande sous forme de texte
     */
    public void interpretCommand(String pCommandLine)
    {
        Command pCommand = this.aParser.getCommand(pCommandLine);

        if (pCommand.isUnknown()) {
            gui.println("I don't know what you mean...");
            return;
        }

        String vCommandWord = pCommand.getCommandWord();

        switch (vCommandWord) {
            case "help" -> this.printHelp();
            case "go" -> this.goRoom(pCommand);
            case "look" -> this.look(pCommand);
            case "eat" -> this.eat();
            case "quit" -> this.endGame();
            default -> gui.println("Command not implemented.");
        }
    }

    /**
     * Affiche le message de bienvenue du jeu.
     */
    private void printWelcome()
    {
        gui.println("=================================");
        gui.println("        LA COMMUNAUTÉ             ");
        gui.println("=================================");
        gui.println("Type 'help' if you need help.");
        gui.println("");
        printLocationInfo();
    }

    /**
     * Affiche l'aide et la liste des commandes disponibles.
     */
    private void printHelp()
    {
        gui.println("Your command words are:");
        gui.println(this.aParser.getCommandList());
    }

    /**
     * Affiche la description de la pièce actuelle
     * ainsi que son image associée.
     */
    private void printLocationInfo()
    {
        gui.println(model.getCurrentRoom().getLongDescription());
        gui.showImage(model.getCurrentRoom().getImageName());
    }

    /**
     * Gère le déplacement du joueur.
     *
     * @param pCommand la commande contenant la direction
     */
    private void goRoom(Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            gui.println("Go where ?");
            return;
        }

        model.goRoom(pCommand.getSecondWord());
        printLocationInfo();
    }

    /**
     * Commande look :
     * - sans argument → affiche la salle
     * - avec argument → affiche un item
     */
    private void look(Command pCommand)
    {
        // look seul
        if (!pCommand.hasSecondWord()) {
            // on affiche la description complète de la salle
            gui.println(model.getCurrentRoom().getLongDescription());
            return;
        }

        // look objet
        String itemName = pCommand.getSecondWord();

        // on cherche l'objet dans la salle actuelle
        Item item = model.getCurrentRoom().getItem(itemName);

        // si l'objet n'exi pas
        if(item == null) {
            gui.println("There is no such item here.");
        }
        else {
            // on affiche les détails de l'objet
            gui.println(item.getLongDescription());
        }
    }

    /**
     * Commande eat : affiche un message.
     */
    private void eat()
    {
        gui.println("You have eaten now and you are not hungry any more.");
    }

    /**
     * Termine le jeu.
     */
    private void endGame()
    {
        gui.println("Thank you for playing. Good bye.");
        gui.enable(false);
    }
}