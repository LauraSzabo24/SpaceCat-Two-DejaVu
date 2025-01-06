 

import mayflower.*;

public class SpaceCatRunner 
{
    public static MyMayflower m;
    public static void main(String[] args) 
    {
        m = new MyMayflower();
    }
    public static void setCosmoCurrX(int x)
    {
        m.setCosmoCurrX(x);
    }
    public static void setCosmoCurrY(int y)
    {
        m.setCosmoCurrY(y);
    }
}