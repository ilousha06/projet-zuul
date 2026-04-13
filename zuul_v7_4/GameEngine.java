/**
 * La classe GameEngine est le contrôleur principal du jeu Zuul en version GUI.
 * Elle gère :
 * - les déplacements du joueur
 * - l’interprétation des commandes
 * - la communication avec l’interface graphique (UserInterface)
 *
 * Cette version remplace la classe Game (mode terminal).
 */
public class GameEngine
{
    /** Pièce dans laquelle se trouve actuellement le joueur */
    private Room aCurrentRoom;

    /** Analyseur des commandes saisies par le joueur */
    private final Parser aParser;

    /** Interface graphique du jeu */
    private UserInterface gui;

    /**
     * Construit le moteur du jeu.
     * Initialise les pièces et le parser.
     */
    public GameEngine()
    {
        this.createRooms();
        this.aParser = new Parser();
    }

    /**
     * Définit l'interface graphique et lance le message de bienvenue.
     *
     * @param pUserInterface l'interface graphique associée au jeu
     */
    public void setGUI(UserInterface pUserInterface)
    {
        this.gui = pUserInterface;
        this.printWelcome();
    }

    /**
     * Crée toutes les pièces du jeu et leurs connexions.
     * Chaque pièce est associée à une image.
     */
    private void createRooms()
    {
        Room vDortoirEast        = new Room("dans le dortoir east reserve aux soeurs", "dortoir.jpg");
        Room vDortoirOuest       = new Room("dans le dortoir ouest plus silencieux", "dortoir2.jpg");
        Room vRefectoire         = new Room("dans le refectoire commun", "refectoire.jpg");
        Room vCuisine            = new Room("dans la cuisine ou les repas sont prepares", "cuisine.jpg");
        Room vInfirmerie         = new Room("dans l infirmerie aux lumieres froides", "infirmerie.jpg");
        Room vHallCeremonies     = new Room("dans le hall des ceremonies", "hall.jpg");
        Room vCourIntern         = new Room("dans la cour interieure entouree de murs", "cour.jpg");
        Room vJardinCentral      = new Room("dans le jardin central sombre", "jardin.jpg");
        Room vPuitsAncien        = new Room("pres du puits ancien en pierre", "puits.jpg");
        Room vSalleCachee        = new Room("dans une salle cachee sous le puits", "cachee.jpg");
        Room vSerreCultivee      = new Room("dans la serre cultivee abandonnee", "serre.jpg");
        Room vLabyrintheVeg      = new Room("dans le labyrinthe vegetal inquietant", "labyrinthe.jpg");
        Room vAutelExtern        = new Room("pres de l'autel exterieur en pierre", "autel.jpg");
        Room vCabaneJardin       = new Room("dans la cabane du jardinier", "cabane.jpg");
        Room vChapPrinci         = new Room("dans la chapelle principale silencieuse", "chapelle.jpg");
        Room vBiblioSacree       = new Room("dans la bibliotheque sacree", "biblio.jpg");
        Room vArchiInterd        = new Room("dans les archives interdites", "archives.jpg");
        Room vCloitreIntern      = new Room("dans le cloitre interieur", "cloitre.jpg");
        Room vSalleSerments      = new Room("dans la salle des serments", "serments.jpg");
        Room vBureauMatriarche   = new Room("dans le bureau de la matriarche", "bureau.jpg");
        Room vAntichambreSacree  = new Room("dans l antichambre sacree", "antichambre.jpg");
        Room vSalleRituels       = new Room("dans la salle des rituels", "rituels.jpg");
        Room vSanctuaireIntern   = new Room("dans le sanctuaire interieur", "sanctuaire.jpg");
        Room vCrypteAncienne     = new Room("dans la crypte ancienne", "crypte.jpg");
        Room vChambreReliques    = new Room("dans la chambre des reliques", "reliques.jpg");
        Room vEscalierCave       = new Room("dans l escalier menant a la cave", "escalier.jpg");
        Room vCavePrincipale     = new Room("dans la cave principale", "cave.jpg");
        Room vPorteScellee       = new Room("devant la porte scellee", "porte.jpg");
        Room vSortieExtern       = new Room("devant la sortie exterieure", "sortie.jpg");

        // Connexions (identiques à ton code)
        vCourIntern.setExit("north", vDortoirOuest);
        vCourIntern.setExit("south", vJardinCentral);
        vCourIntern.setExit("east",  vHallCeremonies);
        vCourIntern.setExit("west",  vRefectoire);

        vDortoirEast.setExit("west", vDortoirOuest);
        vDortoirOuest.setExit("south", vCourIntern);
        vDortoirOuest.setExit("west", vInfirmerie);
        vDortoirOuest.setExit("east", vDortoirEast);

        vInfirmerie.setExit("east", vDortoirOuest);

        vRefectoire.setExit("east", vCourIntern);
        vRefectoire.setExit("north", vCuisine);
        vCuisine.setExit("west", vRefectoire);

        vJardinCentral.setExit("north", vCourIntern);
        vJardinCentral.setExit("south", vPuitsAncien);
        vJardinCentral.setExit("east", vAutelExtern);
        vJardinCentral.setExit("west", vLabyrintheVeg);

        vPuitsAncien.setExit("north", vJardinCentral);
        vPuitsAncien.setExit("down", vSalleCachee);
        vSalleCachee.setExit("up", vPuitsAncien);

        vLabyrintheVeg.setExit("east", vJardinCentral);
        vLabyrintheVeg.setExit("west", vCabaneJardin);

        vCabaneJardin.setExit("east", vLabyrintheVeg);
        vAutelExtern.setExit("west", vJardinCentral);
        vAutelExtern.setExit("south", vSerreCultivee);
        vSerreCultivee.setExit("north", vAutelExtern);

        vHallCeremonies.setExit("west", vCourIntern);
        vHallCeremonies.setExit("north", vSalleSerments);
        vHallCeremonies.setExit("south", vChapPrinci);

        vSalleSerments.setExit("south", vHallCeremonies);
        vSalleSerments.setExit("east", vAntichambreSacree);

        vAntichambreSacree.setExit("south", vSalleSerments);
        vAntichambreSacree.setExit("north", vBureauMatriarche);

        vBureauMatriarche.setExit("south", vAntichambreSacree);

        vChapPrinci.setExit("north", vHallCeremonies);
        vChapPrinci.setExit("south", vSanctuaireIntern);
        vChapPrinci.setExit("east", vBiblioSacree);
        vChapPrinci.setExit("west", vCloitreIntern);

        vCloitreIntern.setExit("east", vChapPrinci);
        vBiblioSacree.setExit("west", vChapPrinci);
        vBiblioSacree.setExit("north", vArchiInterd);
        vArchiInterd.setExit("south", vBiblioSacree);

        vSanctuaireIntern.setExit("north", vChapPrinci);
        vSanctuaireIntern.setExit("south", vCrypteAncienne);
        vSanctuaireIntern.setExit("east", vSalleRituels);

        vSalleRituels.setExit("west", vSanctuaireIntern);

        vCrypteAncienne.setExit("north", vSanctuaireIntern);
        vCrypteAncienne.setExit("east", vChambreReliques);

        vChambreReliques.setExit("west", vCrypteAncienne);

        vCrypteAncienne.setExit("east", vEscalierCave);
        vEscalierCave.setExit("north", vCrypteAncienne);

        vEscalierCave.setExit("down", vCavePrincipale);
        vCavePrincipale.setExit("up", vEscalierCave);

        vCavePrincipale.setExit("south", vPorteScellee);
        vPorteScellee.setExit("north", vCavePrincipale);

        vPorteScellee.setExit("south", vSortieExtern);
        vSortieExtern.setExit("north", vPorteScellee);

        this.aCurrentRoom = vDortoirEast;
    }

    /**
     * Interprète une commande saisie par l'utilisateur.
     *
     * @param pCommandLine la commande entrée sous forme de texte
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
     * Affiche le message de bienvenue.
     */
    private void printWelcome()
    {
        gui.println("=================================");
        gui.println("        LA COMMUNAUTÉ             ");
        gui.println("=================================");
        gui.println("Une communauté religieuse fermée.");
        gui.println("Des secrets. Des rituels. Une seule issue.");
        gui.println("Type 'help' if you need help.");
        gui.println("");
        printLocationInfo();
    }

    /**
     * Affiche l'aide et la liste des commandes.
     */
    private void printHelp()
    {
        gui.println("Your command words are:");
        gui.println(this.aParser.getCommandList());
    }

    /**
     * Affiche les informations de la pièce actuelle + son image.
     */
    private void printLocationInfo()
    {
        gui.println(aCurrentRoom.getLongDescription());

        if (aCurrentRoom.getImageName() != null) { // security if image == null
            gui.showImage(aCurrentRoom.getImageName());
        }
    }

    /**
     * Déplace le joueur vers une autre pièce.
     *
     * @param pCommand la commande contenant la direction
     */
    private void goRoom(Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            gui.println("Go where ?");
            return;
        }

        String vDirection = pCommand.getSecondWord();
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);

        if (vNextRoom == null) {
            gui.println("There is no door !");
            return;
        }

        this.aCurrentRoom = vNextRoom;
        printLocationInfo();
    }

    /**
     * Commande look : affiche la description de la pièce.
     *
     * @param pCommand la commande look
     */
    private void look(Command pCommand)
    {
        if (pCommand.hasSecondWord()) {
            gui.println("I don't know how to look at something in particular yet.");
            return;
        }
        gui.println(this.aCurrentRoom.getLongDescription());
    }

    /**
     * Commande eat.
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