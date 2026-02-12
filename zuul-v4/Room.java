
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

    public void setExits(final Room pNorth,final Room pEast,final Room pSouth,final Room pWest){
        this.aNorthExit = pNorth;
        this.aEastExit  =  pEast;
        this.aSouthExit = pSouth;
        this.aWestExit  =  pWest;
    }
} // Room
