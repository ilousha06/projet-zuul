
public class Room
{
    private final String aDescription;
    public Room aNorthExit;
    public Room aEastExit;
    public Room aSouthExit;
    public Room aWestExit;

    public Room(final String pDescripion){
        this.aDescription = pDescripion;
    }


    public String getDescription()
    {
        return this.aDescription;
    }

    public void setExits(final Room pNorth, final Room pSouth, final Room pEast, final Room pWest){
        this.aNorthExit = pNorth;
        this.aEastExit  =  pEast;
        this.aSouthExit = pSouth;
        this.aWestExit  =  pWest;
    }

    public Room getExit(String direction)
    {
        if (direction.equals("north")) return this.aNorthExit;
        if (direction.equals("south")) return this.aSouthExit;
        if (direction.equals("east"))  return this.aEastExit;
        if (direction.equals("west"))  return this.aWestExit;

        return null;
    }

} // Room
