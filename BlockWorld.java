import mayflower.*;
import java.util.*;

public class BlockWorld extends WorldMap
{
    public BlockWorld(int catX, int catY, int xdir, int ydir, int dir, int age, String color)
    {
        super(catX, catY, xdir, ydir, dir, age);
        buildWall();
        buildLowBorder();
        makePath(catX,catY, xdir, ydir, dir);
        destroyLowBorder();
        buildWorld(catX, catY);
        super.setType("BlockWorld");
        super.setColor(color);
    }
    @Override 
    public void buildWorld(int catX, int catY)
    {
        for(int r=0; r<tiles.length; r++)
        {
            for(int c=0; c<tiles[r].length; c++)
            {
                int random=(int)(Math.random()*6);
                if(tiles[r][c]==null && random<3)
                {
                    tiles[r][c] = "block";
                }
            }
        }
    }
    public void makePath(int startX, int startY, int xdir, int ydir, int dir)
    {
        int[] coor = new int[]{startY,startX};
        tiles[coor[0]+xdir][coor[1]+ydir] = "actor";
        //tiles[coor[0]+xdir+1][coor[1]+ydir] = "block"; 
        tiles[coor[0]+xdir][coor[1]+ydir] = "path";
        if(dir==0)
        {
            while(coor[0]<10)
            {
                coor = getNeighbor(coor[0], coor[1]);
                if(!(coor[0]==-1 || coor[1]==-1))
                {
                    tiles[coor[0]][coor[1]] = "path";
                }
                else{
                    break;
                }
            }
        }
        else
        {
            tiles[coor[0]][coor[1]] = "path";
            while(coor[0]<10)
            {
                coor = getNeighborBackwards(coor[0], coor[1]);
                if(!(coor[0]==-1 || coor[1]==-1))
                {
                    tiles[coor[0]][coor[1]] = "path";
                }
                else{
                    break;
                }
            }
        }
        //System.out.println("path complete");
    }
    public int[] getNeighbor(int r, int c)
    {
        if(c>=9)
        {
             return new int[]{-1,-1};
        }
         ArrayList possibleNeighbors = new ArrayList<int[]>();
         int[][] neighbors = new int[][]{{r+1,c},{r-1,c},{r,c+1}}; //{r,c-1}
         for(int row=0; row<neighbors.length; row++)
         {
            if(tiles[neighbors[row][0]][neighbors[row][1]]==null)
            {
                possibleNeighbors.add(neighbors[row]);
            }
            else if(tiles[neighbors[row][0]][neighbors[row][1]].equals("path") && (neighbors[row][1]>9))
            {
                return new int[]{-1,-1};
            }
         }
         int[] chosenOne = new int[2];
         if(possibleNeighbors.size()>0)
         {
             int random=(int)(Math.random()*possibleNeighbors.size());
             chosenOne = (int[])possibleNeighbors.get(random);
         }
         else{
             if(c<9)
             {
                 chosenOne = new int[]{r,c+1};
             }
             else{
                 return new int[]{-1,-1};
             }
         }
         return chosenOne;
    }
    public int[] getNeighborBackwards(int r, int c)
    {
        if(c<=1)
        {
             return new int[]{-1,-1};
        }
         ArrayList possibleNeighbors = new ArrayList<int[]>();
         int[][] neighbors = new int[][]{{r+1,c},{r-1,c},{r,c-1}}; 
         for(int row=0; row<neighbors.length; row++)
         {
            if(tiles[neighbors[row][0]][neighbors[row][1]]==null)
            {
                possibleNeighbors.add(neighbors[row]);
            }
            else if(tiles[neighbors[row][0]][neighbors[row][1]].equals("path") && (neighbors[row][1]<1))
            {
                return new int[]{-1,-1};
            }
         }
         int[] chosenOne = new int[2];
         if(possibleNeighbors.size()>0)
         {
             int random=(int)(Math.random()*possibleNeighbors.size());
             chosenOne = (int[])possibleNeighbors.get(random);
         }
         else{
             if(c>1)
             {
                 chosenOne = new int[]{r,c-1};
             }
             else{
                 return new int[]{-1,-1};
             }
         }
         return chosenOne;
    }
    public void buildLowBorder()
    {
        for(int b=0; b<11; b++)
        {
            tiles[6][b] = "tempWall";
        }
    }
    public void destroyLowBorder()
    {
        for(int b=0; b<11; b++)
        {
            tiles[6][b] = null;
        }
    }
}
