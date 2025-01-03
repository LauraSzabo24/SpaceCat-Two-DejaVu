import java.awt.*;
import mayflower.*;

public class EndWorldButton  extends Actor
{
    private String action = "PAUSE";
    
    public EndWorldButton()
    {
        setImage("img/Tiles/CHOOSE.png");
    }
    
    public String getAction(){
        return action;
    }
    
    public void setAction(String act){
         action = act;
    }
    
    public void act()
    {
        if (Mayflower.mouseClicked(this)){
            action = "END_WORLD";
        }
       
    }
    
}
