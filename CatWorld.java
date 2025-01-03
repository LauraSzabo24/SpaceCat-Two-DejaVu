import mayflower.*;
import java.util.Queue;
import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.HashSet;
import java.util.*;
import java.util.Map.*;


public class CatWorld extends Actor
{
   
   private String[] cats = { "sage1","romantic1", "creator1", "ethereal1", "jester1",
    "supporter1", "go-getter1", "caregiver1", "innocent1",  "natural1" };
    
     private int traitNumber = 0;
     private String selCat;
     private int selCatIndex = 0;
    //Set<String> charac = new HashSet<String>();
        
    public CatWorld()
    {
        
        selCat = cats[selCatIndex];
        setImage("img/Cats/"+selCat+".png");
        selCatIndex++;
    }
    
    public CatWorld(String cat)
    {
        
        selCat = cat;
        selCatIndex= findIndex(selCat);
        setImage("img/Cats/"+selCat+".png");
        selCatIndex++;
    }
    
    public CatWorld(int indx)
    {
        
        selCat = cats[indx];
        setImage("img/Cats/"+selCat+".png");
        selCatIndex = indx;
    }
    
    public String[]  getCats()
    {
        return cats;
    }
    
    public void setSelCat(String sCat){
        selCat = sCat;
        selCatIndex = findIndex(sCat);
    }
    
    public String getSelCat()
    {        
        return selCat;
    }
    
    public void nextTrait()
    {
        System.out.println("Mouse Clicked: " + selCat);
    }
   
    public int findIndex(String currCat)
    {
        int foundIndex = 0;
         for (int i = 0; i < cats.length; i++) {
            
            if (cats[i].equals(currCat)) {
                foundIndex = i;
                break;
            }
        }
        return foundIndex;
    }
    
    public void act()
    {
        //"sage1","romantic1", "creator1", "ethereal1", "jester1",
    //"supporter1", "go-getter1", "caregiver1", "innocent1",  "natural1"
       if (Mayflower.mouseClicked(this)){
            EmptyWorld emWor;
            System.out.println("Mouse Clicked: " + selCat);
            if (selCat.equalsIgnoreCase("jester1")){
                emWor = new EmptyWorld("img/Tiles/jesterX.png");
            }
            else if(selCat.equalsIgnoreCase("sage1")){
                emWor = new EmptyWorld("img/Tiles/sageX.png");
            }
            else if(selCat.equalsIgnoreCase("romantic1")){
                emWor = new EmptyWorld("img/Tiles/romanticX.png");
            }
            else if(selCat.equalsIgnoreCase("creator1")){
                emWor = new EmptyWorld("img/Tiles/creatorX.png");
            }
            else if(selCat.equalsIgnoreCase("ethereal1")){
                emWor = new EmptyWorld("img/Tiles/etherealX.png");
            }
            else if(selCat.equalsIgnoreCase("supporter1")){
                emWor = new EmptyWorld("img/Tiles/supporterX.png");
            }
            else if(selCat.equalsIgnoreCase("go-getter1")){
                emWor = new EmptyWorld("img/Tiles/go-getterX.png");
            }
            else if(selCat.equalsIgnoreCase("caregiver1")){
                emWor = new EmptyWorld("img/Tiles/caregiverX.png");
            }else if(selCat.equalsIgnoreCase("innocent1")){
                emWor = new EmptyWorld("img/Tiles/innocentX.png");
            }
            else if(selCat.equalsIgnoreCase("natural1")){
                emWor = new EmptyWorld("img/Tiles/naturalX.png");
            }else{
                emWor = new EmptyWorld();
            }
        
            //emWor.addObject(new CatWorld(selCatIndex), 90, 180);
            Mayflower.setWorld(emWor);
        
        }
    }
}