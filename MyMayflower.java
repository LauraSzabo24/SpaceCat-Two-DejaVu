import mayflower.*;
public class MyMayflower extends Mayflower
{
    public OurWorld w;
    public MyMayflower()
    {
        super("window", 800, 600);
    }

    public void init()
    {
        Mayflower.setFullScreen(false);
        w =  new OurWorld();
        Mayflower.setWorld(w);
    }
    
    public void setCosmoCurrX(int x)
    {
        w.setCosmoCurrX(x);
    }
    public void setCosmoCurrY(int y)
    {
        w.setCosmoCurrY(y);
    }
}
// 
