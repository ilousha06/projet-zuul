import java.util.Scanner;
import java.io.InputStream;

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
            case "back" -> this.back(pCommand);
            case "test" -> this.test(pCommand);
            case "take" -> this.take(pCommand);
            case "drop" -> this.drop(pCommand);
            default -> gui.println("Command not implemented.");
        }
    }

    /**
     * Affiche le message de bienvenue du jeu.
     */
    private void printWelcome()
    {
        gui.println("=============================");
        gui.println("              LA COMMUNAUTÉ             ");
        gui.println("=============================");
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
        gui.clear();

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

    /**
     * Commande back :
     * permet de revenir à la salle précédente
     * et gère les erreurs (second mot)
     */
    private void back(Command pCommand)
    {
        // refuse un second mot
        if(pCommand.hasSecondWord()) {
            gui.println("The back command doesn't accept a second word.");
            return;
        }

        // pas d'historique
        if(!model.goBack()) {
            gui.println("No previous room.");
            return;
        }
        // retour normal
        printLocationInfo();
    }

    /**
     * Commande test :
     * permet d'exécuter des commandes depuis un fichier texte
     */
    private void test(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()) {
            gui.println("Need a file name");
            return;
        }

        String fileName = pCommand.getSecondWord() + ".txt";

        InputStream input = this.getClass().getClassLoader().getResourceAsStream(fileName);

        if(input == null) {
            gui.println("File not found.");
            return;
        }

        Scanner scanner = new Scanner(input);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // évite boucle infinie
            if(line.startsWith("test")) {
                gui.println("Test command inside test file is not allowed.");
                continue;
            }

            gui.println("> " + line);
            interpretCommand(line);
        }

        scanner.close();
    }

    /**
     * Commande take :
     * Permet au joueur de prendre un item dans la salle.
     * Suppression de la limite à un seul item
     */
    private void take(Command pCommand)
    {
        if(!pCommand.hasSecondWord()) {
            gui.println("Take what?");
            return;
        }

        String itemName = pCommand.getSecondWord();

        Item item = model.getCurrentRoom().getItem(itemName);

        if(item == null) {
            gui.println("Item not found.");
            return;
        }

        // plus de limite à 1 item
        model.getPlayer().takeItem(item);
        model.getCurrentRoom().removeItem(item);

        gui.println("Item taken.");
    }

    /**
     * Commande drop :
     * Permet au joueur de déposer un item dans la salle.
     *
     *  @param pCommand nom d'item
     */
    private void drop(Command pCommand)
    {
        if(!pCommand.hasSecondWord()) {
            gui.println("Drop what?");
            return;
        }

        String name = pCommand.getSecondWord();

        Item item = model.getPlayer().dropItem(name);

        if(item == null) {
            gui.println("You don't have this item.");
            return;
        }

        model.getCurrentRoom().addItem(item);
        gui.println("Item dropped.");
    }

}