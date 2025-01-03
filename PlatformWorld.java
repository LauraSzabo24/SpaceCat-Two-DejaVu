import mayflower.*;
import java.util.*;

public class PlatformWorld extends WorldMap
{
    public PlatformWorld(int catX, int catY, int xdir, int ydir, int dir, int age, String color)
    {
        super(catX, catY, xdir, ydir, dir, age);
        buildWall();
        buildWorld(catX, catY);
        super.setType("PlatformWorld");
        super.setColor(color);
    }
    @Override
    public void buildWorld(int catX, int catY)
    {
        for(int r=tiles.length-4; r<tiles.length-2; r++)
        {
            for(int c=3; c<tiles[r].length-3; c++)
            {
                tiles[r][c] = "block";
            }
        }
    }
    public void createDate()
    {
        super.date = new CatDate();
        super.hasCat = true;
    }
}
