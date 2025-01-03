public class VerticalBar extends ProgressBar
{
   public VerticalBar(DoubleLinkedList w)
   {
       super(w);
       setImage("img/ProgressBar/VeriB.png");
       super.mainChar = new catLocation("dot", 0);
   }
   public void updateColors()
    {
        for(int i=0; i<5; i++)
        {
            symbols[i]= new BarSymbol("blankEra");
        }
    }
    public void dateColor(int in)
    {
        symbols[in]=new BarSymbol("dateEra");
    }
}