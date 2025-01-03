//import java.awt.*;
import mayflower.*;
public class Select extends Actor
{
    //private DoubleLinkedList world;
    //private String[] colors;
    private boolean status;
    public Select()
    {
        status =false;
        setImage("img/Object/select.png");
    }
    public void act()
    {
       if (Mayflower.mouseClicked(this))
       {
            status = true;
       }
    }
    public boolean getStatus()
    {
        return status;
    }
    public void setStatus(boolean s)
    {
        status = s;
    }
}
