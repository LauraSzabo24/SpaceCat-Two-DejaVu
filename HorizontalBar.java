
public class HorizontalBar extends ProgressBar
{
    public CatDate[] dates;
    public catLocation[] dateSymbols;
   public HorizontalBar(DoubleLinkedList w)
   {
       super(w);
       setImage("img/ProgressBar/horiBar.png");
       super.mainChar = new catLocation("dot", 7);
       dates = world.dateList();
   }
    public void updateColors()
    {
        colors = world.colorList();
        int r = world.right;
        int l = world.left;
        int x = 0;
        for(int i=0; i<15; i++)
        {
            symbols[i]= new BarSymbol("blank");
        }
        for(int i=(7-l); i<(7+r+1); i++)
        {
            symbols[i]= new BarSymbol(colors[x]);
            x++;
        }
        
    }
    public void updateDates()
    {
        dates = world.dateList();
        String[] dateText = new String[15];
        dateSymbols = new catLocation[15];
        int r = world.right;
        int l = world.left;
        int x = 0;
        for(int i=(7-l); i<(7+r+1); i++)
        {
            if(dates[x]==null)
            {
                dateText[i]=null;
            }
            else if(dates[x].isDated())
            {
                dateText[i]="colorH";
            }
            else
            {
                dateText[i]="emptyH";
            }
            x++;
        }
        for(int i=0; i<15; i++)
        {
            if(dateText!=null)
            {
                dateSymbols[i] = new catLocation(dateText[i], i);
            }
        }
    }
    public catLocation[] getDateSymbols()
    {
        return dateSymbols;
    }
}
