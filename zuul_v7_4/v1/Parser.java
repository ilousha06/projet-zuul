package v1;

import java.util.Scanner;

public class Parser
{
    private CommandWords aCommandWords;
    private Scanner aScanner;

    public Parser()
    {
        this.aCommandWords = new CommandWords();
        this.aScanner = new Scanner(System.in);
    }

    public Command getCommand()
    {
        String vInputLine;
        String vWord1 = null;
        String vWord2 = null;

        vInputLine = this.aScanner.nextLine();

        Scanner vTokenizer = new Scanner(vInputLine);

        if (vTokenizer.hasNext()) {
            vWord1 = vTokenizer.next();
            if (vTokenizer.hasNext()) {
                vWord2 = vTokenizer.next();
            }
        }

        if (this.aCommandWords.isCommand(vWord1)) {
            return new Command(vWord1, vWord2);
        }
        else {
            return new Command(null, null);
        }
    }
}
