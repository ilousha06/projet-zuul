

public class Game
{
    private Room aCurrentRoom;
    private Parser aParser;

    public Game()
    {
        this.createRooms();
        this.aParser = new Parser();
        this.play();
    }

    private void createRooms()
    {
        Room vDortoirEst        = new Room("dans le dortoir est reserve aux soeurs");  // SPAWN
        Room vDortoirOuest      = new Room("dans le dortoir ouest plus silencieux");
        Room vRefectoire        = new Room("dans le refectoire commun");
        Room vCuisine           = new Room("dans la cuisine ou les repas sont prepares");
        Room vInfirmerie        = new Room("dans l infirmerie aux lumieres froides");
        Room vCourIntern        = new Room("dans la cour interieure entouree de murs");
        Room vJardinCentral     = new Room("dans le jardin central sombre");
        Room vPuitsAncien       = new Room("pres du puits ancien en pierre");
        Room vSerreCultivee     = new Room("dans la serre cultivee abandonnee envahie par les plantes");
        Room vLabyrintheVeg     = new Room("dans le labyrinthe vegetal inquietant");
        Room vAutelExtern       = new Room("pres de l'autel exterieur en pierre");
        Room vCabaneJardin      = new Room("dans la cabane du jardinier");
        Room vChapPrinci        = new Room("dans la chapelle principale silencieuse");
        Room vBiblioSacree      = new Room("dans la bibliotheque sacree remplie d ouvrages");
        Room vArchiInterd       = new Room("dans les archives interdites sous surveillance");
        Room vCloitreIntern     = new Room("dans le cloitre interieur calme");
        Room vHallCeremonies    = new Room("dans le hall des ceremonies imposant");
        Room vSalleSerments     = new Room("dans la salle des serments solemnels");
        Room vBureauMatriarche  = new Room("dans le bureau prive de la matriarche");
        Room vAntichambreSacree = new Room("dans l'antichambre sacree gardee");
        Room vSalleRituels      = new Room("dans la salle des rituels obscurs");
        Room vSanctuaireIntern  = new Room("dans le sanctuaire interieur interdit");
        Room vCrypteAncienne    = new Room("dans la crypte ancienne souterraine");
        Room vChambreReliques   = new Room("dans la chambre des reliques anciennes");
        Room vEscalierCave      = new Room("dans l'escalier menant a la cave");
        Room vCavePrincipale    = new Room("dans la cave principale humide");
        Room vPorteScellee      = new Room("devant la porte scellee par des cadenas");
        Room vSortieExtern      = new Room("devant la sortie vers l'exterieur");        // Exit

        // HUB CENTRAL
        vCourIntern.setExits(vDortoirEst, vJardinCentral, vHallCeremonies, vRefectoire);
        vDortoirEst.setExits(null, vCourIntern, vDortoirOuest, vInfirmerie);
        vDortoirOuest.setExits(null, null, null, vDortoirEst);
        vInfirmerie.setExits(null, null, vDortoirEst, null);
        vRefectoire.setExits(null, null, vCourIntern, vCuisine);
        vCuisine.setExits(null, null, vRefectoire, null);

        // ZONE JARDIN
        vJardinCentral.setExits(vCourIntern, vAutelExtern, vPuitsAncien, vLabyrintheVeg);
        vPuitsAncien.setExits(null, null, null, vJardinCentral);
        vLabyrintheVeg.setExits(null, null, vJardinCentral, vCabaneJardin);
        vCabaneJardin.setExits(null, null, vLabyrintheVeg, null);
        vAutelExtern.setExits(vJardinCentral, vSerreCultivee, null, null);
        vSerreCultivee.setExits(vAutelExtern, null, null, null);

        // ZONE RELIGIEUX
        vHallCeremonies.setExits(vSalleSerments, null, vChapPrinci, vCourIntern);
        vSalleSerments.setExits(vAntichambreSacree, vHallCeremonies, null, null);
        vAntichambreSacree.setExits(vBureauMatriarche, vSalleSerments, null, null);
        vBureauMatriarche.setExits(null, vAntichambreSacree, null, null);
        vChapPrinci.setExits(vBiblioSacree, vSanctuaireIntern, vCloitreIntern, vHallCeremonies);
        vCloitreIntern.setExits(null, null, null, vChapPrinci);
        vBiblioSacree.setExits(vArchiInterd, vChapPrinci, null, null);
        vArchiInterd.setExits(null, vBiblioSacree, null, null);

        // ZONE OCCULT
        vSanctuaireIntern.setExits(vChapPrinci, vSalleRituels, vCrypteAncienne, null);
        vSalleRituels.setExits(vSanctuaireIntern, null, null, null);
        vCrypteAncienne.setExits(null, vEscalierCave, vChambreReliques, vSanctuaireIntern);
        vChambreReliques.setExits(null, null, null, vCrypteAncienne);

        // ZONE CAVE

        vEscalierCave.setExits(vCrypteAncienne, vCavePrincipale, null, null);
        vCavePrincipale.setExits(vEscalierCave, vPorteScellee, null, null);
        vPorteScellee.setExits(vCavePrincipale, vSortieExtern, null, null);
        vSortieExtern.setExits(vPorteScellee, null, null, null);

        this.aCurrentRoom =vDortoirEst;
    }

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

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the world of Zuul!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(this.aCurrentRoom.getDescription());
        System.out.print("Exits: ");
        this.printExits();
    }

    private boolean processCommand(Command pCommand)
    {
        if (pCommand.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String vCommandWord = pCommand.getCommandWord();

        if (vCommandWord.equals("help")) {
            this.printHelp();
            return false;
        }
        else if (vCommandWord.equals("go")) {
            this.goRoom(pCommand);
            return false;
        }
        else if (vCommandWord.equals("quit")) {
            return this.quit(pCommand);
        }

        return false;
    }

    private void printHelp()
    {
        System.out.println("You are lost. You are alone.");
        System.out.println("You wander around at the university.");
        System.out.println("Your command words are: go help quit");
    }

    private boolean quit(Command pCommand)
    {
        if (pCommand.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        return true;
    }

    private void goRoom(Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            System.out.println("Go where ?");
            return;
        }

        String vDirection = pCommand.getSecondWord();
        Room vNextRoom = null;

        if (vDirection.equals("north")) {
            vNextRoom = this.aCurrentRoom.aNorthExit;
        }
        else if (vDirection.equals("east")) {
            vNextRoom = this.aCurrentRoom.aEastExit;
        }
        else if (vDirection.equals("south")) {
            vNextRoom = this.aCurrentRoom.aSouthExit;
        }
        else if (vDirection.equals("west")) {
            vNextRoom = this.aCurrentRoom.aWestExit;
        }
        else {
            System.out.println("Unknown direction !");
            return;
        }

        if (vNextRoom == null) {
            System.out.println("There is no door !");
            return;
        }

        this.aCurrentRoom = vNextRoom;
        System.out.println(this.aCurrentRoom.getDescription());
        System.out.print("Exits: ");
        this.printExits();
    }

    private void printExits()
    {
        if (this.aCurrentRoom.aNorthExit != null) System.out.print("north ");
        if (this.aCurrentRoom.aEastExit  != null) System.out.print("east ");
        if (this.aCurrentRoom.aSouthExit != null) System.out.print("south ");
        if (this.aCurrentRoom.aWestExit  != null) System.out.print("west ");
        System.out.println();
    }
}
