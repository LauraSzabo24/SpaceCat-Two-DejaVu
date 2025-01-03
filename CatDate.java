
import mayflower.*;
import java.util.Queue;
import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class CatDate extends Actor
{
    private String current = "sage";
    String[][] chaos = {
    {"sage", "My only goal is to understand the world", "Can you go away? I'm trying to finish my book", ""},
    {"romantic", "It must be so nice traveling the galaxy", "All those different stars and planets","No rules...", "No destination...", "Only the journey", "Could I leave with you?", ""},
    {"creator", "Such unique features..","Could I paint you?", "Stand still now..", "this will only take a few hours",""},
    {"ethereal", "I've been expecting you...", "This is all by fates design..", ""},
    {"jester", "Did you hear about the restaurant on the moon?" ,"I heard the food was good but it had no atmosphere!" ,"Haha!", "Hahahahahaha!", "Hahahahahahahahahahahahahahahahah!!" , "Hahahahahahahahahahahahahahahahahhahahahahahahahahahahahaahahahahahahaha!!",""},
    {"supporter", "Where are you from?", "Wow, and you can fly too?", "Amazing!", ""},
    {"go-getter", "So many things to do...", "water the fish..", "feed the plants...", "knit the friend for my sweater..", "would you like to help?", ""},
    {"caregiver", "Do you need a glass of water?", "Want some cat-nip?", "Are you tired?", ""},
    {"innocent", "...ummmm...", "...hi...", "...have you been here before?...", ""},
    {"natural", "You new?" ,"I'll give a tour", "This is my planet", "These are the blocks", "These are the moons", "These are the rocks", ""}
     };
     
     String DejaVuScript = "I feel like this has happened before...";
     private ArrayList<Integer> chosen;
     private int traitNumber = 0;
     private boolean dated;
     
     private int convoCnt;
     private int catID;
     private boolean eligible;
     
     private boolean dejaVu;
    //Set<String> charac = new HashSet<String>();
        
    public CatDate()
    {
      catID=0;
      dejaVu = false;
      eligible = true;
      chosen = new ArrayList<Integer>();
      current = generate();
      setImage("img/Cats/"+current+".png");
      dated = false;
      convoCnt = chaos[findIndex()].length;
    }
    public void setTransparency(int i)
    {
        MayflowerImage pic = new MayflowerImage("img/Cats/"+current+".png");
        pic.setTransparency(i);
        setImage(pic);        
    }
    public String generate()
    {
        
      //String[] character = {"sage", "romantic", "creator", "ethereal", "jester", "supporter", "go-getter", "caregiver", "innocent", "natural"};
      Queue<String> queueCats = new LinkedList<>();
      
      int random = (int)(Math.random() * 10);
      while(chosen.contains(random))
      {
          random = (int)(Math.random() * 10);
      }
      chosen.add(random);
        while(queueCats.size()!=5)
        {
            String thing1 = chaos[random][0];
            queueCats.add(thing1);
       }
     
      return getDate(chaos, queueCats);
    }
    
    public String getDate(String[][] chaos, Queue<String> queueCats)
    {
        //int random = (int)(Math.random() * queueCats.size());
        traitNumber = 0;
        return queueCats.poll();
    }
    
    public void nextTrait()
    {
        int index = findIndex();
        traitNumber++;
    }
   
    public String getTrait()
    {
        if(!dated)
        {
            int index = findIndex();
            if(traitNumber==0){
                return chaos[index][traitNumber] + "cat";
            }
            return chaos[index][traitNumber];
        }
        else if(eligible)
        {
            dejaVu = true;
            return DejaVuScript;
        }
        return "";
    }
    
    public int findIndex()
    {
        int foundIndex = 0;
         for (int i = 0; i < chaos.length; i++) {
            String[] row = chaos[i];
            if (row.length > 0 && row[0].equals(current)) {
                foundIndex = i;
                break;
            }
        }
        
        return foundIndex;
    }
    public void act()
    {
        if(eligible)
        {
            if(!dated)
            {
               if (Mayflower.mouseClicked(this) && convoCnt>1){
                    nextTrait();
                    convoCnt--;
                }
            }
            else
            {
                nextTrait();
            }
        }
    }
    public void dateCat()
    {
        noHabla(false);
        dated = true;
    }
    public boolean isDated()
    {
        return dated;
    }
    public boolean isDejaVu()
    {
        return dejaVu;
    }
    public void noHabla(boolean f)
    {
        eligible = f;
    }
    public boolean isConvoOver()
    {
        if(convoCnt<=1)
        {
            return true;
        }
        return false;
    }
}