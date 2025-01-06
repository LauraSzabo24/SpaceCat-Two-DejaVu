 


import mayflower.*;
import java.util.*;
public class Era
{
    private DoubleLinkedList<WorldMap> worlds;
    private boolean dated;
    private Ghost homeGhost;
    public Era(int a, DoubleLinkedList<WorldMap> ws)
    {
        dated = false;
        worlds = ws;
        homeGhost = new Ghost();
    }
    public void updateHomeGhostMemory(int x, int y, String anim)
    {
        homeGhost.memorize(x,y,anim);
        if(homeGhost.getMemoLength()>100)
        {
            homeGhost.forget();
        }
    }
    public boolean isDualExistence()
    {
        if(homeGhost.getDuality())
        {
            return true;
        }
        return false;
    }
    public Ghost getHomeGhost()
    {
        return homeGhost;
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
