import java.util.HashMap;

public class Room
{
    private final String aDescription;
    private final HashMap<String, Room> aExits;



    public Room(final String pDescripion)
    {
        this.aDescription = pDescripion;
        this.aExits = new HashMap<>();

    }

    public void setExit(String direction, Room neighbor)
    {
        this.aExits.put(direction, neighbor);
    }

    public Room getExit(final String direction)
    {
        return this.aExits.get(direction);
    }

    public String getDescription()
    {
        return this.aDescription;
    }

    public String getExitString()
    {
        StringBuilder vExits = new StringBuilder();

        for (String direction : this.aExits.keySet()) {
            vExits.append(direction).append(" ");
        }

        return vExits.toString();
    }

} // Room
