import mayflower.*;
public class BarSymbol extends Actor
{
    private String colors;
    public BarSymbol(String color)
    {
        colors = color;
        setImage("img/ProgressBar/" + color + ".png");
    }
    public String getC()
    {
        return colors;
    }
    public void act()
    {
        
    }
}