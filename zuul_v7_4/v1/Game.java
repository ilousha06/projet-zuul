package v1;

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
        Room vTheatre = new Room("in a lecture theatre");
        Room vPub     = new Room("in the campus pub");
        Room vLab     = new Room("in a computing lab");
        Room vOffice  = new Room("in the computing admin office");
        Room vOutside = new Room("outside the main entrance of the university");
        
        vOutside.setExits(null, vTheatre, vLab, null);
        vTheatre.setExits(null, null, vOutside, null);
        vLab.setExits(vOutside, vOffice, null, null);
        vOffice.setExits(null, null, null, vLab);
        vPub.setExits(null, null, null, null);

        this.aCurrentRoom = vOutside;
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
