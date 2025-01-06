 

  
import mayflower.*;

public class MayAnimatedObject extends Actor
{
    private MayAnimation animation;
    private Timer animationTimer;
    public MayAnimatedObject()
    {
        animationTimer = new Timer(1000);
    }
    public void setAnimation(MayAnimation a)
    {
        animation = a;
    }
    public void act()
    {
        if(animation!=null)
        {
            if(animationTimer.isDone())
            {
                animationTimer.reset();
                MayflowerImage next = animation.getNextFrame();
                setImage(next);
            }
        }
        
    }
}
