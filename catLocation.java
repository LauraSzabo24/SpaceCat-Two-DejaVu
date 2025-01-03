import mayflower.*;
public class catLocation extends Actor
{
    private int index;
    public catLocation(String type, int in)
    {
       index = in;
       setImage("img/ProgressBar/" + type + ".png");
    }
    public void setIndex(int in)
    {
        index = in;
    }
    public int getIndex()
    {
        return index;
    }
    public void act()
    {
       
    }
}