import mayflower.*;
import java.util.*;

public class WorldMap extends World
{
    public String[][] tiles;
    private int age;
    public boolean hasCat;
    public CatDate date;
    private String color;
    public String type;
     private Select button;
    public WorldMap(int catX, int catY, int xdir, int ydir, int dir, int era)
    {
        tiles = new String[8][11];
        age = era;
        color = "purple";
        type = "PlatformWorld";
        button = new Select();
        hasCat = false;
    }
    public void act()
    {
        
    }
    public void experienceAge()
    {
        
    }
    public void buildWorld(int catX, int catY)
    {
        
    }
    public void printTiles()
    {
        System.out.println();
        for(int r=0; r<tiles.length; r++)
        {
            for(int c=0; c<tiles[r].length; c++)
            {
                System.out.print(tiles[r][c]);
            }
            System.out.println();
        }
    }
    public void buildWall()
    {
        for(int t=0; t<11; t++)
        {
            tiles[0][t] = "wall";
        }
        for(int b=0; b<11; b++)
        {
            tiles[7][b] = "wall";
        }
        for(int l=0; l<8; l++)
        {
            tiles[l][0] = "wall";
        }
        for(int r=0; r<8; r++)
        {
            tiles[r][10] = "wall";
        }
    }
    public String[][] getTiles()
    {
        return tiles;
    }
    public Select getButton()
    {
       return button;
    }
    public boolean getHasCat()
    {
        return hasCat;
    }
    public void setCatDate(CatDate d)
    {
        date = d;
        hasCat = true;
    }
    public CatDate getCatDate()
    {
       return date;
    }
    public int getAge()
    {
        return age;
    }
    public String getColor()
    {
        return color;
    }
    public void setColor(String c)
    {
        color = c;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String t)
    {
        type = t;
    }
    public void setHasCat(boolean c)
    {
        hasCat = c;
    }
    public void setAge(int a)
    {
        age = a;
    }
}
