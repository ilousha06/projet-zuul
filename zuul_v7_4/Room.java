public class Room
{
    private final String aDescription;
    public Room aNorthExit;
    public Room aEastExit;
    public Room aSouthExit;
    public Room aWestExit;

    public Room(final String pDescripion)
    {
        this.aDescription = pDescripion;
    }

    public void setExits(final Room pNorth, final Room pSouth, final Room pEast, final Room pWest)
    {
        this.aNorthExit = pNorth;
        this.aEastExit = pEast;
        this.aSouthExit = pSouth;
        this.aWestExit = pWest;
    }

    public Room getExit(String direction)
    {
        return switch (direction) {
            case "north" -> this.aNorthExit;
            case "south" -> this.aSouthExit;
            case "east" -> this.aEastExit;
            case "west" -> this.aWestExit;
            default -> null;
        };
    }

    public String getDescription()
    {
        return this.aDescription;
    }

    public String getExitString()
    {
        StringBuilder vExits = new StringBuilder();

        if (this.aNorthExit != null) vExits.append("north ");
        if (this.aEastExit  != null) vExits.append("east ");
        if (this.aSouthExit != null) vExits.append("south ");
        if (this.aWestExit  != null) vExits.append("west ");

        return vExits.toString();
    }


} // Room
