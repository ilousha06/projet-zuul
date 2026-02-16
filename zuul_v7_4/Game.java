/**
 * La classe Game est le contrôleur principal du jeu Zuul.
 * Elle gère la boucle de jeu, l'interprétation des commandes
 * et les déplacements du joueur entre les pièces.
 */
public class Game
{
    /** Pièce dans laquelle se trouve actuellement le joueur */
    private Room aCurrentRoom;

    /** Analyseur des commandes saisies par le joueur */
    private final Parser aParser;

    /**
     * Construit le jeu, initialise les pièces et démarre la partie.
     */
    public Game()
    {
        this.createRooms();
        this.aParser = new Parser();
        this.play();
    }

    /**
     * Crée toutes les pièces du jeu et leurs connexions.
     */
    private void createRooms()
    {
        Room vDortoirEst        = new Room("dans le dortoir est reserve aux soeurs");
        Room vDortoirOuest      = new Room("dans le dortoir ouest plus silencieux");
        Room vRefectoire        = new Room("dans le refectoire commun");
        Room vCuisine           = new Room("dans la cuisine ou les repas sont prepares");
        Room vInfirmerie        = new Room("dans l infirmerie aux lumieres froides");
        Room vHallCeremonies    = new Room("dans le hall des ceremonies");
        Room vCourIntern        = new Room("dans la cour interieure entouree de murs");
        Room vJardinCentral     = new Room("dans le jardin central sombre");
        Room vPuitsAncien       = new Room("pres du puits ancien en pierre");
        Room vSalleCachee       = new Room("dans une salle cachee sous le puits");
        Room vSerreCultivee     = new Room("dans la serre cultivee abandonnee");
        Room vLabyrintheVeg     = new Room("dans le labyrinthe vegetal inquietant");
        Room vAutelExtern       = new Room("pres de l'autel exterieur en pierre");
        Room vCabaneJardin      = new Room("dans la cabane du jardinier");
        Room vChapPrinci        = new Room("dans la chapelle principale silencieuse");
        Room vBiblioSacree      = new Room("dans la bibliotheque sacree");
        Room vArchiInterd       = new Room("dans les archives interdites");
        Room vCloitreIntern     = new Room("dans le cloitre interieur");
        Room vSalleSerments     = new Room("dans la salle des serments");
        Room vBureauMatriarche  = new Room("dans le bureau de la matriarche");
        Room vAntichambreSacree = new Room("dans l antichambre sacree");
        Room vSalleRituels      = new Room("dans la salle des rituels");
        Room vSanctuaireIntern  = new Room("dans le sanctuaire interieur");
        Room vCrypteAncienne    = new Room("dans la crypte ancienne");
        Room vChambreReliques   = new Room("dans la chambre des reliques");
        Room vEscalierCave      = new Room("dans l escalier menant a la cave");
        Room vCavePrincipale    = new Room("dans la cave principale");
        Room vPorteScellee      = new Room("devant la porte scellee");
        Room vSortieExtern      = new Room("devant la sortie exterieure");

        // HUB CENTRAL
        vCourIntern.setExit("north", vDortoirOuest);
        vCourIntern.setExit("south", vJardinCentral);
        vCourIntern.setExit("east",  vHallCeremonies);
        vCourIntern.setExit("west",  vRefectoire);

        vDortoirEst.setExit("west",  vDortoirOuest);
        vDortoirOuest.setExit("south", vCourIntern);
        vDortoirOuest.setExit("west", vInfirmerie);
        vDortoirOuest.setExit("east", vDortoirEst);

        vInfirmerie.setExit("east", vDortoirOuest);

        vRefectoire.setExit("east", vCourIntern);
        vRefectoire.setExit("north", vCuisine);
        vCuisine.setExit("west", vRefectoire);

        // ZONE JARDIN
        vJardinCentral.setExit("north", vCourIntern);
        vJardinCentral.setExit("south", vPuitsAncien);
        vJardinCentral.setExit("east",  vAutelExtern);
        vJardinCentral.setExit("west",  vLabyrintheVeg);

        vPuitsAncien.setExit("north", vJardinCentral);

        vPuitsAncien.setExit("down", vSalleCachee); // Verticalité du puits
        vSalleCachee.setExit("up", vPuitsAncien); // Verticalité du puits

        vLabyrintheVeg.setExit("east", vJardinCentral);
        vLabyrintheVeg.setExit("west", vCabaneJardin);

        vCabaneJardin.setExit("east", vLabyrintheVeg);
        vAutelExtern.setExit("west", vJardinCentral);
        vAutelExtern.setExit("south", vSerreCultivee);
        vSerreCultivee.setExit("north", vAutelExtern);

        // ZONE RELIGIEUSE
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

        // ZONE OCCULTE
        vSanctuaireIntern.setExit("north", vChapPrinci);
        vSanctuaireIntern.setExit("south", vCrypteAncienne);
        vSanctuaireIntern.setExit("east", vSalleRituels);

        vSalleRituels.setExit("west", vSanctuaireIntern);

        vCrypteAncienne.setExit("north", vSanctuaireIntern);
        vCrypteAncienne.setExit("east", vChambreReliques);

        vChambreReliques.setExit("west", vCrypteAncienne);

        // ZONE CAVE
        vCrypteAncienne.setExit("down", vEscalierCave);
        vEscalierCave.setExit("up", vCrypteAncienne);

        vEscalierCave.setExit("south", vCavePrincipale);
        vCavePrincipale.setExit("north", vEscalierCave);

        vCavePrincipale.setExit("south", vPorteScellee);
        vPorteScellee.setExit("north", vCavePrincipale);

        vPorteScellee.setExit("south", vSortieExtern);
        vSortieExtern.setExit("north", vPorteScellee);

        this.aCurrentRoom = vDortoirEst;
    }

    /**
     * Lance la boucle principale du jeu.
     */
    private void play()
    {
        this.printWelcome();
        boolean vFinished = false;

        while (!vFinished) {
            Command vCommand = this.aParser.getCommand();
            vFinished = this.processCommand(vCommand);
        }

        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Affiche le message de bienvenue et les informations initiales.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the world of Zuul!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Affiche l'aide du jeu.
     * Cette méthode affiche un message d'aide ainsi que la liste
     * des commandes disponibles, obtenue dynamiquement via le Parser.
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone.");
        System.out.println("You wander around at the university.");
        System.out.println("Your command words are:");
        System.out.println(this.aParser.getCommandWords().getCommandList());
    }


    /**
     * Affiche les informations de la pièce courante.
     */
    private void printLocationInfo()
    {
        System.out.println(aCurrentRoom.getLongDescription());
    }

    /**
     * Déplace le joueur vers une autre pièce selon la commande donnée.
     *
     * @param pCommand la commande contenant la direction
     */
    private void goRoom(Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            System.out.println("Go where ?");
            return;
        }

        String vDirection = pCommand.getSecondWord();
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);

        if (vNextRoom == null) {
            System.out.println("There is no door !");
            return;
        }

        this.aCurrentRoom = vNextRoom;
        printLocationInfo();
    }

    /**
     * Commande look (avec optionnel : second mot interdit).
     */
    private void look (final Command pCommand)
    {
        if (pCommand.hasSecondWord())
        {
            System.out.println("I don't know how to look at something in particular yet.");
            return;
        }
        System.out.println(this.aCurrentRoom.getLongDescription());
    }

    /**
     * Exécute la commande eat.
     * Affiche un message indiquant que le joueur a mangé
     * et n’a plus faim.
     */
    private void eat()
    {
        System.out.println("You have eaten now and you are not hungry any more.");
    }

    /**
     * Traite une commande saisie par le joueur.
     *
     * @param pCommand la commande à traiter
     * @return true si le jeu doit se terminer, false sinon
     */
    private boolean processCommand(Command pCommand)
    {
        if (pCommand.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String vCommandWord = pCommand.getCommandWord();

        switch (vCommandWord) {
            case "help" -> {
                this.printHelp();
                return false;
            }
            case "go" -> {
                this.goRoom(pCommand);
                return false;
            }
            case "look" -> {
                this.look(pCommand);
                return false;
            }
            case "eat" -> {
                this.eat();
                return false;
            }
            case "quit" -> {
                return this.quit(pCommand);
            }
        }
        return false;
    }

    /**
     * Termine le jeu si la commande est valide.
     *
     * @param pCommand la commande quit
     * @return true si le jeu doit s'arrêter
     */
    private boolean quit(Command pCommand)
    {
        if (pCommand.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        return true;
    }

} // Game
