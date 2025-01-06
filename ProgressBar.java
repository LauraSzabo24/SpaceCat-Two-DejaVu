 

import java.awt.*;
import mayflower.*;
public class ProgressBar extends Actor
{
    public DoubleLinkedList world;
    public BarSymbol[] symbols;
    public String[] colors;
    public catLocation mainChar;
    public ProgressBar(DoubleLinkedList w)
    {
        world = w;
        colors = world.colorList();
        symbols = new BarSymbol[15];
    }
    public catLocation getMainChar()
    {
        return mainChar;
    }
    public void updateColors()
    {
        
    }
    public BarSymbol[] getSymbols()
    {
        return symbols;
    }
    public void act()
    {
       
    }
}
