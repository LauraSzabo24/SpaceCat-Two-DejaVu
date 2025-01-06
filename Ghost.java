import java.util.*;

import java.io.*;
public class Ghost extends MayAnimatedActor
{
    private LinkedList<int[]> moves;
    private LinkedList<String> anims;
    private boolean duality;
    private MayAnimation walkRight, walkLeft, idle, idleLeft,fallLeft, fall;
    private int posCount;
    public Ghost()
    {
        walkRight = makeAnimation(walkRight,10,"Walk", 100, 87, 50, 0);
        walkLeft = makeAnimation(walkRight,10,"Walk", 100, 87, 50, 1);
        idle = makeAnimation(idle,10,"Idle", 100, 87, 50, 0);
        idleLeft = makeAnimation(idle,10,"Idle", 100, 87, 50, 1);
        fall = makeAnimation(idle,8,"Fall", 100, 87, 50, 0);
        fallLeft = makeAnimation(idle,8,"Fall", 100, 87, 50, 1);
        setAnimation(idle);
        
        duality = false;
        moves = new LinkedList<int[]>();
        anims = new LinkedList<String>();
        posCount = -1;
    }
    public void memorize(int x, int y, String currAnim)
    {
        moves.add(new int[]{x,y});
        anims.add(currAnim);
        /*if(moves.size()==0)
        {
            moves.add(new int[]{x,y});
            anims.add(currAnim);
        }
        else{
            if((!(moves.get(moves.size()-1)[0]==x) || !(moves.get(moves.size()-1)[1]==y)))
            {
                moves.add(new int[]{x,y});
                anims.add(currAnim);
            }
        }*/
    }
    public void forget()
    {
        moves.remove(0);
        anims.remove(0);
    }
    public int[] getCurrPos()
    {
        posCount++;
        if(posCount>=moves.size())
        {
            posCount = 0;
        }
        return moves.get(posCount);
    }
    public void setDuality(boolean d){
        duality = d;
    }
    public boolean getDuality()
    {
        return duality;
    }
    public int getMemoLength()
    {
        return moves.size();
    }
    public void act()
    {
        super.setAnimation(idle);
        if(isTouching(Cat.class))
        {
            duality = true;
        }
        /*String currAnim = anims.get(posCount);
        switch(currAnim){
            case"wr":
                super.setAnimation(walkRight);
            case "wl":
                super.setAnimation(walkLeft);
            case "i":
                super.setAnimation(idle);
            case "il":
                super.setAnimation(idleLeft);
            case "f":
                super.setAnimation(fall);
            case "fl":
                super.setAnimation(fallLeft);
        }*/
        super.act();
    }
    public MayAnimation makeAnimation(MayAnimation anim, int l, String name, int w, int h, int t, int m)
    {
        String[] frames = new String[l];
        for(int i=1; i<=frames.length; i++)
        {
            frames[i-1]= "img/cat/" + name + " ("+i+").png";
        }
        anim = new MayAnimation(50, frames);
        anim.scale(w,h);
        anim.setTransparency(t);
        anim.mirrorHorizontally(m);
        return anim;
    }
}