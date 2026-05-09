import java.util.Scanner;
import java.io.InputStream;
import java.util.Objects;

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

    /**Limiteur de déplacement */
    private int suspicion ;

    /**
     * Constructeur du moteur du jeu.
     * Initialise le modèle et le parser.
     */
    public GameEngine()
    {
        this.model = new GameModel();
        this.aParser = new Parser();

        // Pour la barre de déplacement
        this.suspicion = 0;
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
        gui.setSuspicion(suspicion);
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
            case "eat" -> this.eat(pCommand);
            case "quit" -> this.endGame();
            case "back" -> this.back(pCommand);
            case "test" -> this.test(pCommand);
            case "take" -> this.take(pCommand);
            case "drop" -> this.drop(pCommand);
            case "inventaire" -> this.inventaire();
            default -> gui.println("Command not implemented.");
        }
    }

    /**
     * Affiche le message de bienvenue du jeu.
     */
    private void printWelcome()
    {
        gui.println("=============================");
        gui.println("        LA COMMUNAUTÉ        ");
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
     * Gere le deplacement du joueur.
     * Si le joueur descend dans le puits, un message d'avertissement
     * s'affiche avant le deplacement sans effacer l'ecran.
     *
     * @param pCommand la commande contenant la direction
     */
    private void goRoom(Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            gui.println("Go where ?");
            return;
        }

        String direction = pCommand.getSecondWord();

        // Detecte la trap door AVANT le deplacement
        boolean trapDoor = direction.equals("down")
                && model.getCurrentRoom().getImageName().equals("Puits.png");

        // Si c'est la trap door, on affiche le message et on s'arrete
        // Le joueur devra cliquer une 2eme fois pour confirmer
        if (trapDoor && !model.getPlayer().isTrapDoorConfirmed()) {
            gui.println("");
            gui.println("Une fois descendue, vous ne pourrez peut-etre plus revenir.");
            gui.println("Tapez 'down' une deuxieme fois pour confirmer.");
            model.getPlayer().setTrapDoorConfirmed(true);
            return;
        }

        // Remet la confirmation a false pour la prochaine fois
        model.getPlayer().setTrapDoorConfirmed(false);

        // Deplacement normal
        Room oldRoom = model.getCurrentRoom();
        model.goRoom(direction);

        if (oldRoom != model.getCurrentRoom()) {
            suspicion += 5;
            if (suspicion > 100) suspicion = 100;
            gui.setSuspicion(suspicion);
        }

        gui.clear();
        printLocationInfo();

        // Messages de suspicion
        if (suspicion >= 30 && suspicion < 60) gui.println("Vous sentez des regards sur vous...");
        if (suspicion >= 60 && suspicion < 90) gui.println("Les soeurs commencent a se mefier.");
        if (suspicion >= 90) gui.println("La matriarche sait quelque chose...");
        if (suspicion >= 100) {
            gui.println("");
            gui.println("La communaute vous a demasquee.");
            gui.println("Vous avez ete capturee.");
            endGame();
        }
    }

    /**
     * Commande look :
     * - sans argument = affiche la salle
     * - avec argument = affiche un item
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
     * Termine le jeu.
     */
    private void endGame()
    {
        gui.println("Thank you for playing. Good bye.");
        gui.enable(false);
    }

    /**
     * Commande back :
     * permet de revenir à la salle précédente.
     * Certaines salles possèdent des trap doors
     * empêchant le retour en arrière.
     */
    private void back(Command pCommand)
    {
        // refuse un second mot
        if(pCommand.hasSecondWord()) {
            gui.println("The back command doesn't accept a second word.");
            return;
        }

        // récupère la salle précédente
        Room previousRoom = model.getPlayer().getPreviousRoom();

        // aucune salle précédente
        if(previousRoom == null) {
            gui.println("No previous room.");
            return;
        }

        // vérifie si retour possible
        if(!model.getCurrentRoom().isExit(previousRoom)) {
            gui.println("Impossible de revenir en arrière.");
            model.getPlayer().clearHistory();
            return;
        }

        // retour normal
        model.goBack();

        gui.clear();
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

        // UNE SEULE FOIS
        if(!model.getPlayer().takeItem(item)) {
            gui.println("Too heavy.");
            return;
        }

        model.getCurrentRoom().removeItem(item);

        gui.println(item.getName() + " taken.");
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

    /**
     * Commande inventaire :
     * Affiche :
     * - les items portés par le joueur
     * - le poids total
     * - une barre visuelle de charge
     */
    private void inventaire()
    {
        Player player = model.getPlayer();

        gui.println("----- INVENTAIRE -----");

        if(player.hasItem()) {
            gui.println(player.getInventoryString());
        } else {
            gui.println("Inventory is empty.");
        }

        gui.println(player.getWeightInfo());

    }

    /**
     * Commande eat :
     * Permet de manger un item porté par le joueur.
     * Chaque item possède un effet différent.
     */
    private void eat(Command pCommand)
    {
        if(!pCommand.hasSecondWord()) {
            gui.println("Eat what?");
            return;
        }

        String name = pCommand.getSecondWord();

        if(!model.getPlayer().hasItem(name)) {
            gui.println("You don't have this item.");
            return;
        }

        Item item = model.getPlayer().dropItem(name);

        // EFFETS

        // DOUBLE CAPACITE
        switch (name) {
            case "hostie" -> {
                model.getPlayer().increaseMaxWeight();
                gui.println("Vous consommez l'hostie sacrée... votre force augmente.");
            }

            // BONUS +5
            case "elixir" -> {
                model.getPlayer().addMaxWeight(5);
                gui.println("L'élixir vous rend plus puissant.");
            }

            // SOIN (RP)
            case "herbes" -> gui.println("Les herbes apaisent votre esprit.");


            // RP
            case "painbeni" -> gui.println("Le pain béni vous apporte du réconfort.");


            // RP mystique
            case "encens" -> gui.println("Une vision étrange traverse votre esprit...");


            // MALUS
            case "poison" -> {
                model.getPlayer().reduceMaxWeight(3);
                gui.println("Le poison vous affaiblit...");
            }

            // GROS BONUS
            case "relique" -> {
                model.getPlayer().addMaxWeight(10);
                gui.println("La relique vous donne une force immense !");
            }

            // AUTRES
            default -> {
                gui.println("Cet objet ne peut pas être mangé.");
                model.getPlayer().takeItem(item);
            }
        }
    }

}