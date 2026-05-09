import java.util.Scanner;
import java.io.InputStream;

/**
 * La classe GameEngine represente le controleur du jeu (architecture MVC).
 * Elle gere :
 * - l interpretation des commandes
 * - la communication entre le modele et la vue
 * - les portes verrouillees et la progression de l histoire
 */
public class GameEngine
{
    /** Analyseur des commandes */
    private final Parser aParser;

    /** Interface graphique */
    private UserInterface gui;

    /** Modele du jeu */
    private final GameModel model;

    /** Niveau de suspicion de la communaute */
    private int suspicion;

    /**
     * Constructeur du moteur du jeu.
     */
    public GameEngine()
    {
        this.model     = new GameModel();
        this.aParser   = new Parser();
        this.suspicion = 0;
    }

    /**
     * Associe l interface graphique et affiche le message de bienvenue.
     *
     * @param pUserInterface l interface graphique
     */
    public void setGUI(UserInterface pUserInterface)
    {
        this.gui = pUserInterface;
        this.printWelcome();
        gui.setSuspicion(suspicion);
    }

    /**
     * Interprete une commande saisie par l utilisateur.
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

        switch (pCommand.getCommandWord()) {
            case "help"       -> this.printHelp();
            case "go"         -> this.goRoom(pCommand);
            case "look"       -> this.look(pCommand);
            case "quit"       -> this.endGame();
            case "back"       -> this.back(pCommand);
            case "test"       -> this.test(pCommand);
            case "take"       -> this.take(pCommand);
            case "drop"       -> this.drop(pCommand);
            case "inventaire" -> this.inventaire();
            case "charge"     -> this.charge();
            case "fire"       -> this.fire();
            case "use"        -> this.use(pCommand);
            default           -> gui.println("Command not implemented.");
        }
    }

    /**
     * Affiche le message de bienvenue du jeu.
     */
    private void printWelcome()
    {
        gui.println("=============================");
        gui.println("      LA COMMUNAUTE          ");
        gui.println("=============================");
        gui.println("");
        gui.println("Vous etes une journaliste infiltree dans une communaute");
        gui.println("religieuse secrete. Des femmes disparaissent depuis des annees.");
        gui.println("Votre but : trouver les preuves et sortir vivante.");
        gui.println("");
        gui.println("Tapez 'help' pour voir les commandes.");
        gui.println("");
        printLocationInfo();
    }

    /**
     * Affiche l aide avec toutes les commandes.
     */
    private void printHelp()
    {
        gui.clear();
        gui.println("");
        gui.println("+---------------------------------+");
        gui.println("|           AIDE DU JEU           |");
        gui.println("+---------------------------------+");
        gui.println("");
        gui.println("  [ DEPLACEMENT ]");
        gui.println("    go north / south / east / west");
        gui.println("    go up / go down");
        gui.println("    back  ->  salle precedente");
        gui.println("");
        gui.println("  [ EXPLORATION ]");
        gui.println("    look           ->  observer la salle");
        gui.println("    look <objet>   ->  examiner un objet");
        gui.println("");
        gui.println("  [ INVENTAIRE ]");
        gui.println("    take <objet>   ->  ramasser");
        gui.println("    drop <objet>   ->  deposer");
        gui.println("    inventaire     ->  voir vos objets");
        gui.println("    use  <objet>   ->  utiliser ou consommer");
        gui.println("");
        gui.println("  [ BEAMER ]");
        gui.println("    charge         ->  memoriser la salle");
        gui.println("    fire           ->  se teleporter");
        gui.println("");
        gui.println("  [ SYSTEME ]");
        gui.println("    help           ->  afficher l aide");
        gui.println("    quit           ->  quitter le jeu");
        gui.println("");
        gui.println("+---------------------------------+");
        gui.println("");
    }

    /**
     * Affiche la description de la piece actuelle et son image.
     */
    private void printLocationInfo()
    {
        gui.println(model.getCurrentRoom().getLongDescription());
        gui.showImage(model.getCurrentRoom().getImageName());

        // Verifie si le joueur a gagne
        if (model.getCurrentRoom().getImageName().equals("sortie.png")
                && model.getPlayer().hasItem("document")) {
            gui.println("");
            gui.println("Vous sortez dans la nuit froide.");
            gui.println("Les preuves sont dans vos mains.");
            gui.println("La verite sera connue de tous.");
            gui.println("");
            gui.println("FELICITATIONS - Vous avez termine le jeu !");
            gui.enable(false);
        }
    }

    /**
     * Gere le deplacement du joueur.
     * Verifie les portes verrouillees avant chaque deplacement.
     *
     * @param pCommand la commande contenant la direction
     */
    private void goRoom(Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            gui.println("Go where ?");
            return;
        }

        String direction     = pCommand.getSecondWord();
        String imageActuelle = model.getCurrentRoom().getImageName();

        // Trap door du puits : avertissement avant descente
        if (direction.equals("down") && imageActuelle.equals("Puits.png")) {
            if (!model.getPlayer().isTrapDoorConfirmed()) {
                gui.println("");
                gui.println("Une inscription gravee dit : Ce qui descend ne remonte jamais.");
                gui.println("Tapez 'go down' une deuxieme fois pour confirmer.");
                model.getPlayer().setTrapDoorConfirmed(true);
                return;
            }
        }
        model.getPlayer().setTrapDoorConfirmed(false);

        // Porte 1 : Cloitre -> Sanctuaire (necessite clematri)
        if (direction.equals("south") && imageActuelle.equals("cloitre.png")) {
            if (!model.getPlayer().hasItem("clematri")) {
                gui.println("La porte est fermee a cle.");
                gui.println("Il vous faut la cle de la Matriarche.");
                return;
            }
            // Ouvre le passage definitivement
            model.ouvrirPassageSanctuaire();
            gui.println("La cle de la Matriarche tourne dans la serrure...");
            gui.println("Le passage vers le sanctuaire est ouvert.");
        }

        // Porte 2 : Bibliotheque -> Archives (necessite livre + clearchive)
        if (direction.equals("north") && imageActuelle.equals("Bibliotheque.png")) {
            if (!model.getPlayer().hasItem("livre")) {
                gui.println("Une inscription dit : Seul celui qui sait peut entrer.");
                gui.println("Il vous faut un livre pour avoir l autorisation.");
                return;
            }
            if (!model.getPlayer().hasItem("clearchive")) {
                gui.println("La porte est verrouillee.");
                gui.println("Il vous faut la cle des archives.");
                return;
            }
            // Ouvre le passage definitivement
            model.ouvrirPassageArchives();
            gui.println("Le livre et la cle ouvrent les archives interdites...");
        }

        // Porte 3 : Escalier -> Cave (necessite code + clerouge + encens)
        if (direction.equals("down") && imageActuelle.equals("escalier.png")) {
            if (!verifierCodeCave()) return;
            // Ouvre le passage definitivement
            model.ouvrirPassageCave();
        }

        // Porte 4 : Porte scellee -> Sortie (necessite clefinale)
        if (direction.equals("south") && imageActuelle.equals("porte.png")) {
            if (!model.getPlayer().hasItem("clefinale")) {
                gui.println("La porte scellee resiste.");
                gui.println("Il vous faut la cle finale.");
                return;
            }
            // Ouvre le passage definitivement
            model.ouvrirPorteScellee();
            gui.println("La cle finale tourne... la porte s ouvre.");
            gui.println("La liberte est a portee de main !");
        }

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
        if (suspicion >= 90)                   gui.println("La matriarche sait quelque chose...");

        if (suspicion >= 100) {
            gui.println("");
            gui.println("La communaute vous a demasquee.");
            gui.println("Vous avez ete capturee.");
            endGame();
        }
    }

    /**
     * Verifie si le joueur a tous les elements pour acceder a la cave.
     * Il faut les 4 morceaux du code, la cle rouge et l encens.
     *
     * @return true si toutes les conditions sont remplies
     */
    private boolean verifierCodeCave()
    {
        Player player = model.getPlayer();

        if (!player.hasItem("morceau1") || !player.hasItem("morceau2")
                || !player.hasItem("morceau3") || !player.hasItem("morceau4")) {
            gui.println("Un cadenas a 4 chiffres bloque le passage.");
            gui.println("Il vous faut les 4 morceaux du code.");
            return false;
        }
        if (!player.hasItem("clerouge")) {
            gui.println("Une serrure rouge bloque le passage.");
            gui.println("Il vous faut la cle rouge.");
            return false;
        }
        if (!player.hasItem("encens")) {
            gui.println("Un mecanisme etrange ne repond pas.");
            gui.println("Il vous faut un objet rituel pour l activer.");
            return false;
        }

        gui.println("Vous entrez le code 3-7-1-9...");
        gui.println("La cle rouge tourne dans la serrure...");
        gui.println("L encens se consume et active le mecanisme...");
        gui.println("Le passage s ouvre dans un grondement sourd.");
        return true;
    }

    /**
     * Commande look :
     * - sans argument = affiche la salle
     * - avec argument = examine un item
     */
    private void look(Command pCommand)
    {
        gui.clear();
        if (!pCommand.hasSecondWord()) {
            gui.println(model.getCurrentRoom().getLongDescription());
            return;
        }
        Item item = model.getCurrentRoom().getItem(pCommand.getSecondWord());
        if (item == null) {
            gui.println("There is no such item here.");
        } else {
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
     * Commande back : revient a la salle precedente.
     * Bloquee apres une trap door.
     */
    private void back(Command pCommand)
    {
        if (pCommand.hasSecondWord()) {
            gui.println("The back command doesn't accept a second word.");
            return;
        }

        Room previousRoom = model.getPlayer().getPreviousRoom();

        if (previousRoom == null) {
            gui.println("No previous room.");
            return;
        }

        if (!model.getCurrentRoom().isExit(previousRoom)) {
            gui.println("Impossible de revenir en arriere.");
            model.getPlayer().clearHistory();
            return;
        }

        model.goBack();
        gui.clear();
        printLocationInfo();
    }

    /**
     * Commande test : execute des commandes depuis un fichier texte.
     */
    private void test(final Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            gui.println("Need a file name");
            return;
        }
        String fileName = pCommand.getSecondWord() + ".txt";
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (input == null) {
            gui.println("File not found.");
            return;
        }
        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("test")) {
                gui.println("Test command inside test file is not allowed.");
                continue;
            }
            gui.println("> " + line);
            interpretCommand(line);
        }
        scanner.close();
    }

    /**
     * Commande take : ramasse un item dans la salle.
     */
    private void take(Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            gui.println("Take what?");
            return;
        }
        String itemName = pCommand.getSecondWord();
        Item item = model.getCurrentRoom().getItem(itemName);
        if (item == null) {
            gui.println("Item not found.");
            return;
        }
        if (!model.getPlayer().takeItem(item)) {
            gui.println("Too heavy.");
            return;
        }
        model.getCurrentRoom().removeItem(item);
        gui.println(item.getName() + " taken.");
    }

    /**
     * Commande drop : depose un item dans la salle.
     */
    private void drop(Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            gui.println("Drop what?");
            return;
        }
        Item item = model.getPlayer().dropItem(pCommand.getSecondWord());
        if (item == null) {
            gui.println("You don't have this item.");
            return;
        }
        model.getCurrentRoom().addItem(item);
        gui.println("Item dropped.");
    }

    /**
     * Commande inventaire : affiche les items portes et le poids.
     */
    private void inventaire()
    {
        gui.clear();
        Player player = model.getPlayer();
        gui.println("----- INVENTAIRE -----");
        if (player.hasItem()) {
            gui.println(player.getInventoryString());
        } else {
            gui.println("Inventory is empty.");
        }
        gui.println(player.getWeightInfo());
    }

    /**
     * Commande use : utilise ou consomme un item.
     * Gere tous les effets des items speciaux.
     */
    private void use(Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            gui.println("Utiliser quoi ?");
            return;
        }

        String name = pCommand.getSecondWord();

        if (!model.getPlayer().hasItem(name)) {
            gui.println("You don't have this item.");
            return;
        }

        Item item = model.getPlayer().dropItem(name);

        switch (name) {

            // Items consommables avec effets
            case "hostie"   -> { model.getPlayer().increaseMaxWeight(); gui.println("Vous consommez l hostie sacree... votre force double."); }
            case "elixir"   -> { model.getPlayer().addMaxWeight(5);     gui.println("L elixir noir augmente votre capacite."); }
            case "herbes"   ->   gui.println("Les herbes apaisent votre esprit.");
            case "painbeni" ->   gui.println("Le pain beni vous apporte du reconfort.");
            case "encens"   ->   gui.println("Une vision etrange traverse votre esprit... vous voyez des visages.");
            case "poison"   -> { model.getPlayer().reduceMaxWeight(3);  gui.println("Le poison vous affaiblit..."); }
            case "relique"  -> { model.getPlayer().addMaxWeight(10);    gui.println("La relique vous donne une force immense !"); }

            // Pioche : casse le mur dans la salle cachee
            case "pioche" -> {
                if (!model.getCurrentRoom().getImageName().equals("font_puit.png")) {
                    gui.println("Il n y a pas de mur a casser ici.");
                    model.getPlayer().takeItem(item);
                    return;
                }
                model.ouvrirPassageCrypte();
                gui.println("Vous cassez le mur avec la pioche...");
                gui.println("Un passage s ouvre vers la crypte !");
                gui.println("Vous sentez un courant d air froid.");
            }

            // Recharge : recharge le beamer
            case "recharge" -> {
                if (!model.getPlayer().hasItem("beamer")) {
                    gui.println("Vous ne portez pas le beamer.");
                    model.getPlayer().takeItem(item);
                    return;
                }
                if (!model.getBeamer().isUsed()) {
                    gui.println("Le beamer n a pas encore ete utilise.");
                    model.getPlayer().takeItem(item);
                    return;
                }
                model.getBeamer().recharge();
                gui.println("Le cristal se dissout dans le beamer...");
                gui.println("Le beamer est recharge et pret a etre utilise a nouveau.");
            }

            default -> {
                gui.println("Cet objet ne peut pas etre utilise.");
                model.getPlayer().takeItem(item);
            }
        }
    }

    /**
     * Commande charge : memorise la salle actuelle dans le beamer.
     */
    private void charge()
    {
        if (!model.getPlayer().hasItem("beamer")) {
            gui.println("Vous ne portez pas le beamer.");
            return;
        }
        if (model.getBeamer().isUsed()) {
            gui.println("Le beamer est epuise. Utilisez une recharge.");
            return;
        }
        model.getBeamer().charge(model.getCurrentRoom());
        gui.println("Le beamer pulse... il a memorise cet endroit.");
    }

    /**
     * Commande fire : teleporte le joueur dans la salle memorisee.
     */
    private void fire()
    {
        if (!model.getPlayer().hasItem("beamer")) {
            gui.println("Vous ne portez pas le beamer.");
            return;
        }
        if (!model.getBeamer().isCharged()) {
            gui.println("Le beamer n est pas charge. Utilisez 'charge' d abord.");
            return;
        }
        Room destination = model.getBeamer().fire();
        model.getPlayer().goRoom(destination);
        gui.println("Le beamer explose en un flash aveuglant...");
        gui.println("Vous vous retrouvez la ou vous etiez avant.");
        gui.println("");
        gui.clear();
        printLocationInfo();
        gui.println("Le beamer tombe en poussiere. Il ne peut plus etre utilise.");
    }
}