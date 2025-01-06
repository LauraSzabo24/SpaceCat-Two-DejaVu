 

import mayflower.*;
public class MayMovableAnimatedActor extends MayAnimatedActor
{
   
     private MayAnimation walkRight, idle, idleLeft, leftFalling, rightFalling, walkLeft;
     private String currentAction;
     private String direction;
     
     private boolean jumping;
     private boolean passLimit;
     private int oldY;
     
     public int[] location;
     
     public MayMovableAnimatedActor()
     {
         direction = "right";
         jumping = false;
         passLimit = false;
         oldY = getY();
         location = new int[]{getX(), getY()};
     }
     public void setJumping(boolean state)
     {
         jumping = state;
     }
    public void act()
    {
       
        super.act();

        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();
        location = new int[]{x, y};
        String newAction = null;
        
        if(y<0)
        {
            passLimit = true;
        }
        else{
            passLimit = false;
        }
        
        if(currentAction==null)
        {
            newAction= "idle";
        }
       
        if(jumping && !passLimit)
        {
             if(getY()>oldY-20 && !isBlocked())
             {
                 setLocation(x, y-13);
                 SpaceCatRunner.setCosmoCurrX(x);
                 SpaceCatRunner.setCosmoCurrY(y-13);
             }
             else
             {
                 jumping = false;
                 setLocation(x, y+12);
                 SpaceCatRunner.setCosmoCurrX(x);
                 SpaceCatRunner.setCosmoCurrY(y+12);
             }
         }
         if(jumping && passLimit)
        {
             oldY = getY();
             setLocation(x, oldY+610);
             SpaceCatRunner.setCosmoCurrX(x);
             SpaceCatRunner.setCosmoCurrY(oldY+610);
             if(getY()>oldY-20 && !isBlocked())
             {
                 setLocation(x, getY()-13);
                 SpaceCatRunner.setCosmoCurrX(x);
                 SpaceCatRunner.setCosmoCurrY(getY()-13);
             }
             else
             {
                 jumping = false;
                 setLocation(x, y+12);
                 SpaceCatRunner.setCosmoCurrX(x);
                 SpaceCatRunner.setCosmoCurrY(y+12);
             }
         }
         
        //MovingForward Section
        if(Mayflower.isKeyDown(Keyboard.KEY_UP))//&& y-5>0 
        {
            jumping = true;
            int oldY = y;
            if(isBlocked())
            {
                setLocation(x, y+1);
                SpaceCatRunner.setCosmoCurrX(x);
                SpaceCatRunner.setCosmoCurrY(y+1);
            }
        }
        if(Mayflower.isKeyDown(Keyboard.KEY_RIGHT) )//x+5+w<800
        {
            if(!Mayflower.isKeyDown(Keyboard.KEY_UP))
            {
                jumping = false;
            }
            setLocation(x+10, y);
            SpaceCatRunner.setCosmoCurrX(x+10);
            SpaceCatRunner.setCosmoCurrY(y);
            if(!isFalling())
            {
                newAction = "walkRight";
            }
            direction = "right";
            if(isBlocked())
            {
                setLocation(x-1, y);
                SpaceCatRunner.setCosmoCurrX(x-1);
                SpaceCatRunner.setCosmoCurrY(y);
            }
        }
         else if(Mayflower.isKeyDown(Keyboard.KEY_LEFT))// && x-5>0
        {
            if(!Mayflower.isKeyDown(Keyboard.KEY_UP))
            {
                jumping = false;
            }
            setLocation(x-10, y);
            SpaceCatRunner.setCosmoCurrX(x-10);
            SpaceCatRunner.setCosmoCurrY(y);
            ////
            if(!isFalling())
            {
                newAction = "walkLeft";
            }
            direction = "left";
            if(isBlocked())
            {
                setLocation(x+1, y);
                SpaceCatRunner.setCosmoCurrX(x+1);
                SpaceCatRunner.setCosmoCurrY(y);
            }
        }
        else if(!isFalling())
        {
            newAction = "idle";
            if(direction!=null && direction.equals("left"))
            {
                newAction = "idleLeft";
            }
        }
        if(isFalling())
        {
            if(direction!=null && direction.equals("left"))
            {
                newAction = "fallLeft";
            }
            if(direction!=null && direction.equals("right"))
            {
                 newAction = "fallRight";
            }
        }
       
        if((!(newAction==null)) && !(newAction.equals(currentAction)))
        {
         
        if(newAction.equals("walkRight"))
         {
            setAnimation(walkRight);
         }
         if(newAction.equals("idle"))
         {
             setAnimation(idle);
         }
         if(newAction.equals("idleLeft"))
         {
             setAnimation(idleLeft);
         }
         if(newAction.equals("walkLeft"))
         {
             setAnimation(walkLeft);
         }
         if(newAction.equals("fallLeft"))
         {
             setAnimation(leftFalling);
         }
         if(newAction.equals("fallRight"))
         {
             setAnimation(rightFalling);
         }
         
         currentAction = newAction;
        }
       
            }
    //might need to remove that
    public void setAnimation(MayAnimation a)
    {
        super.setAnimation(a);
    }
   
    public void setWalkRightAnimation(MayAnimation ani)
    {
        walkRight = ani;
    }
   
    public void setIdleAnimation(MayAnimation ani)
    {
        idle = ani;
    }
   
    public void setWalkLeftAnimation(MayAnimation ani)
    {
        walkLeft = ani;
    }
   
    public void setIdleLeftAnimation(MayAnimation ani)
    {
        idleLeft = ani;
    }
   
    public void setLeftFallingAnimation(MayAnimation ani)
    {
        leftFalling = ani;
    }
   
    public void setRightFallingAnimation(MayAnimation ani)
    {
        rightFalling = ani;
    }
   
}
