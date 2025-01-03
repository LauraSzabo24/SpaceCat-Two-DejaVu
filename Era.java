
import mayflower.*;
import java.util.*;
public class Era
{
    private DoubleLinkedList<WorldMap> worlds;
    private boolean dated;
    public Era(int a, DoubleLinkedList<WorldMap> ws)
    {
        dated = false;
        worlds = ws;
    }
    public void resetWorlds()
    {
        worlds.cleanWorlds();
    }
    public DoubleLinkedList<WorldMap> getWorlds()
    {
        return worlds;
    }
    public boolean getStatus()
    {
        return dated;
    }
    public void setStatus(boolean f)
    {
        dated = f;
    }
}
