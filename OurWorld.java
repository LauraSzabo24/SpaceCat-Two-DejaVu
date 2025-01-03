import mayflower.*;
import java.util.*;

public class OurWorld extends World {
    private Cat cat;
    private String[][] tiles;
    public boolean generateLevel;
    private ArrayList<Actor> objects; 
    public int catLastY;
    public int catLastX;
    public Play play;
    private boolean started;
    
    //planets
    private ArrayList<String> planetList;
    private String currWorld = null;
    private int currCount = 0;
    private int worldCount = 0;//n
    private WorldMap currWrldMap =null;
    
    //world generation
    private PlatformWorld startWorld;
    private DoubleLinkedList<WorldMap> universe;
    private ProgressBar horiB;
    private ProgressBar veriB;
    private int currLocation;
    private int leftestLoc;
    private int rightestLoc;
    private boolean shouldAct;
    
    //eras
    private DoubleLinkedList<Era> timeZones;
    private int currEra;
    
    //button press
    public boolean sPressed;
    public boolean wPressed;
    private Select currButton;
    public EndWorldButton endBut;//n
    private int counterFinal = 0;
    
    
    //dates
    private CatDate date;
    private String text = "this thing 1";
    
    public OurWorld() 
    {
        started = false;
        shouldAct = true;
        exposition();
        universe = new DoubleLinkedList<WorldMap>();
        timeZones = new DoubleLinkedList<Era>();
        
        for(int i=0; i<5; i++)
        {
            timeZones.addToEnd(new Era(i, universe));
        }
        currEra = 0;
        
        planetList = new ArrayList<String>();
        planetList.add("purple");
        planetList.add("pink");
        planetList.add("ice");
        planetList.add("forest");
        planetList.add("desert");
        
        currWorld = "purple";
        currCount++;
        
        currLocation = 7;
        leftestLoc = 7;
        rightestLoc = 7;
        
        sPressed = false;
        wPressed = false;
    }
    public void exposition()
    {
        setBackground("img/Tiles/SPACECAT.png");
        play = new Play();
        addObject(play, 300, 100);
        play.scale(200,200);
    }
     public void startGame()
     {
        removeObject(play);
        tiles = new String[8][11];
        objects = new ArrayList<Actor>();
        
        //cat
        cat = new Cat();
        catLastY = 0;
        catLastX = 0;
        
        //world generation and first date
        startWorld = new PlatformWorld(1, 1, 0, 0, 0, 0, "purple");
        startWorld.createDate();
        date = ((PlatformWorld)startWorld).getCatDate();
        addObject(date,100, 153);
        date.scale(100, 87);
        currButton = startWorld.getButton();
        currWrldMap = startWorld;
        universe.addToEnd(startWorld);
        createWorld(1,1, startWorld);
        text = "After exploring the galaxy, Space cat has decided to search for a partner...";
        addMainCharacter(cat);
        
        //progress bars
        horiB = new HorizontalBar(universe);
        horiB.updateColors();
        ((HorizontalBar)horiB).updateDates();
        addObject(horiB,136,7);
        veriB = new VerticalBar(universe);
        veriB.updateColors();
        addObject(veriB,750,200);
        updateProgressHori();
        updateProgressVeri();
        
        //end
        endBut = new EndWorldButton();
        addObject(endBut,640,520);
        
        showText(text, 20, 30, 530, Color.WHITE);
    }
    public void act()
    {
        if(shouldAct)
        {
            if(endBut != null && endBut.getAction().equalsIgnoreCase("END_WORLD")){
                destroyWorld();
                buildEndWorld();
                endBut.setAction("PAUSE");
            }
            else if(currWrldMap!=null && currWrldMap.getCatDate()!=null && currWrldMap.getCatDate().isDejaVu()==true)
            {
                if(counterFinal==100)
                {
                    destroyWorld();
                    buildAlternateEnd();
                    endBut.setAction("PAUSE");
                }
                else
                {
                    counterFinal++;
                }
            }
            else
            {
                if (play.getNewAction().equals("PlayGame")){
                    startGame();
                    play.setNewAction("Pause");
                    started = true;
                }
                if(started)
                {
                    addObject(endBut,640,520);
                    timeTravel();
                    expandWorld();
                    updateEra();
                    updateDate();
                    text = date.getTrait();
                    showText(text, 20, 30, 530, Color.WHITE);
                }
            }
        }
    }
    
    //UPDATES
    public void updateDate()
    {   
        if(currWrldMap instanceof PlatformWorld)
        {
            if(currWrldMap.getCatDate().isConvoOver() && currButton!=null && currButton.getStatus()==false)
            {
                addObject(currButton,120, 100);
                currButton.scale(80, 80);
            }
        }
    }
    public void updateProgressHori()
    {
        addObject(horiB,136,7);
        BarSymbol[] bars = horiB.getSymbols();
        catLocation[] hearts = ((HorizontalBar)horiB).getDateSymbols();
        int x = 0;
        int y = 0;
        for(BarSymbol bar : bars)
        {
            x+=34;
            addObject(bar,113+x,12);
        }
        for(catLocation heart : hearts)
        {
            y+=34;
            addObject(heart,113+y,12);
        }
        addObject(horiB.getMainChar(), 147 + (horiB.getMainChar().getIndex()*34), 12);
    }
    public void updateProgressVeri()
    {
        addObject(veriB,750,200);
        BarSymbol[] bars = veriB.getSymbols();
        int x = 0;
        for(BarSymbol bar : bars)
        {
            x+=34;
            addObject(bar, 758,176+x);
        }
        addObject(veriB.getMainChar(), 752 ,214 + (veriB.getMainChar().getIndex()*34));
    }
    public void updateTrait(CatDate date)
    {
        text= date.getTrait();
        showText(text, 20, 30, 530, Color.WHITE);
    }
    
    //ERAS
    public void timeTravel()
    {
        if(Mayflower.isKeyDown(Keyboard.KEY_S) && currEra<4 && timeZones.get(currEra).getStatus()==true)
        {
            sPressed = true;
        }
        else if(Mayflower.isKeyDown(Keyboard.KEY_W) && currEra>0)
        {
            wPressed = true;
        }
        if(sPressed && !Mayflower.isKeyDown(Keyboard.KEY_S))
        {
            sPressed = false;
            currEra++;
            veriB.getMainChar().setIndex(veriB.getMainChar().getIndex()+1);
            updateProgressVeri();
            resetEra();
            universe = timeZones.get(currEra).getWorlds();
        }
        else if(wPressed && !Mayflower.isKeyDown(Keyboard.KEY_W))
        {
            wPressed = false;
            currEra--;
            veriB.getMainChar().setIndex(veriB.getMainChar().getIndex()-1);
            updateProgressVeri();
            resetEra();
            universe = timeZones.get(currEra).getWorlds();
        }
    }
    public void updateEra()
    {
        if(currButton.getStatus()==true &&  timeZones.get(currEra).getStatus()==false)
        {
            timeZones.get(currEra).setStatus(true);
            currButton.setStatus(false);
            ((VerticalBar)veriB).dateColor(currEra);
            updateProgressVeri();
            currWrldMap.getCatDate().dateCat();
        }
        if(timeZones.get(currEra).getStatus()==true)
        {
            if(currWrldMap.getCatDate()!=null)
            {
                currWrldMap.getCatDate().noHabla(false);
                currWrldMap.getCatDate().setTransparency(50);
            }
        }
    }
    public void resetEra()
    {
        timeZones.get(currEra).setStatus(false);
        timeZones.get(currEra).resetWorlds();
    }
    
    //WORLD GENERATION "jelly"
    public void expandWorld()
    {
        if(cat.location[0]>=780)
        {
            removeObject(date);
            removeObject(currButton);
            destroyWorld();
            cat.setLocation(1, (((int)catLastY/100))*100);
            if(currLocation>=14)
            {
                universe.setLoc(0);
                currLocation = leftestLoc;
                WorldMap nW = universe.getCurrent();
                currWrldMap = nW;
                createWorld(1,((int)catLastY/100)+1,nW);
                checkBlock("left");
                horiB.updateColors();
                ((HorizontalBar)horiB).updateDates();
                horiB.getMainChar().setIndex(leftestLoc);
                if(nW.getHasCat())
                {
                    date = nW.getCatDate();
                    addObject(date,100, 153);
                    date.scale(100, 87);
                    
                    currButton = nW.getButton();
                }
            }
            else{
                if(universe.getNext()==null)
                {
                    WorldMap newWorld = new BlockWorld(1, ((int)catLastY/100)+1, 0, 0, 0,0, currWorld);
                    if(currCount==1 && !currWorld.equals("purple"))
                    {
                        newWorld = new PlatformWorld(1, ((int)catLastY/100)+1, 0, 0, 0,0, currWorld);
                        ((PlatformWorld)newWorld).createDate();
                        date = ((PlatformWorld)newWorld).getCatDate();
                        addObject(date,100, 153);
                        date.scale(100, 87);
                        
                        currButton = ((PlatformWorld)newWorld).getButton();
                    }
                    universe.addToEnd(newWorld);
                    currWrldMap = newWorld;
                    createWorld(1,((int)catLastY/100)+1, newWorld);
                    currCount++;
                    rightestLoc++;
                }
                else{
                    WorldMap nW = universe.getNext();
                    currWrldMap = nW;
                    createWorld(1,((int)catLastY/100)+1,nW);
                    checkBlock("left");
                    System.out.println(nW.getHasCat());
                    if(nW.getHasCat())
                    {
                        date = nW.getCatDate();
                        addObject(date,100, 153);
                        date.scale(100, 87);
                        
                        currButton = nW.getButton();
                    }
                } 
                universe.incLoc();
                horiB.updateColors();
                ((HorizontalBar)horiB).updateDates();
                horiB.getMainChar().setIndex(horiB.getMainChar().getIndex()+1);
                currLocation++;
            }            
            updateProgressHori();
            updateProgressVeri();
        }
        else if(cat.location[0]<0)
        {
            removeObject(date);
            removeObject(currButton);
            text="";
            destroyWorld();
            cat.setLocation(750, (((int)catLastY/100))*100);            
            if(currLocation<=0)
            {
                universe.setLoc(rightestLoc);
                currLocation = rightestLoc;
                WorldMap nW = universe.getCurrent();
                currWrldMap = nW;
                createWorld(1,((int)catLastY/100)+1,nW);
                checkBlock("right");
                horiB.updateColors();
                ((HorizontalBar)horiB).updateDates();
                horiB.getMainChar().setIndex(rightestLoc);
                if(nW.getHasCat())
                {
                    date = ((PlatformWorld)nW).getCatDate();
                    addObject(date,100, 153);
                    date.scale(100, 87);
                    
                    currButton = ((PlatformWorld)nW).getButton();                 
                }
            }
            else
            {
                if(universe.getPrevious()==null)
                {
                    WorldMap newWorld = new BlockWorld(8, ((int)catLastY/100)+1, 0, 1,1, 0, currWorld);
                    if(currCount==1 && !currWorld.equals("purple"))
                    {
                        newWorld = new PlatformWorld(8, ((int)catLastY/100)+1, 0, 1,1, 0, currWorld);
                        ((PlatformWorld)newWorld).createDate();
                        date = ((PlatformWorld)newWorld).getCatDate();
                        addObject(date,100, 153);
                        date.scale(100, 87);
                        
                        currButton = ((PlatformWorld)newWorld).getButton();
                    }
                    universe.addToStart(newWorld);
                    currWrldMap = newWorld;
                    createWorld(8,((int)catLastY/100)+1, newWorld);
                    leftestLoc--;
                    currCount++;
                }
                else
                {
                    WorldMap nW = universe.getPrevious();
                    currWrldMap = nW;
                    createWorld(1,((int)catLastY/100)+1,nW);
                    universe.redLoc();
                    checkBlock("right");
                    
                    if(nW.getHasCat())
                    {
                        date = ((PlatformWorld)nW).getCatDate();
                        addObject(date,100, 153);
                        date.scale(100, 87);
                        
                        currButton = ((PlatformWorld)nW).getButton();                      
                    }
                }
                horiB.updateColors();
                ((HorizontalBar)horiB).updateDates();
                horiB.getMainChar().setIndex(horiB.getMainChar().getIndex()-1);
                currLocation--;
            }
            updateProgressHori();
            updateProgressVeri();
        }
        if(currCount==3)
        {
            currCount = 0;
            currWorld = randomPlanet();
        }
    }
    public void checkBlock(String dir)
    {
        if(cat.isBlocked())
        {
            int yCor = 0;
            if(dir.equals("right"))
            {
                for(int i=0; i<tiles.length; i++)
                {
                    if(tiles[i][tiles[1].length-2].equals("path"))
                    {
                        break;
                    }
                    yCor+=93;
                }
                cat.setLocation(730, yCor-93);
            }
            else{
                for(int i=0; i<tiles.length; i++)
                {
                    if(tiles[i][1].equals("path"))
                    {
                        break;
                    }
                    yCor+=93;
                }
                cat.setLocation(1, yCor);
            }
        }
    }
    public void createWorld(int catX, int catY, WorldMap sWorld)
    { 
        tiles = sWorld.getTiles();
        buildRandomWorld((catX-1)*100, (catY-1)*100, sWorld.getColor());
    }
    public String randomPlanet()
    {
        planetList.remove(currWorld);
        String planet = "purple";
        for(int i = 0; i < planetList.size(); i++)  
        { 
            int index = (int)(Math.random() * planetList.size()); 
            planet = planetList.get(index);           
        }
        return planet;
    }
    public void destroyWorld()
    {
        catLastY = cat.location[1];
        catLastX = cat.location[0];
        for(Actor object: objects)
        {
            removeObject(object);
        }
        tiles = new String[8][11];
    }
    public void buildRandomWorld(int catX, int catY, String planet)
    {        
        if (planet.equalsIgnoreCase("ice")){
            buildWorld(catX, catY, "iceplanet");
        }else if (planet.equalsIgnoreCase("forest")){
            buildWorld(catX, catY, "forestplanet");
        }else if (planet.equalsIgnoreCase("desert")){
            buildWorld(catX, catY, "desertplanet");
        }else if (planet.equalsIgnoreCase("pink")){
            buildWorld(catX, catY, "pinkplanet");
        }else if (planet.equalsIgnoreCase("purple")){
            buildWorld(catX, catY, "purpleplanet");
        }
    } 
    public void buildWorld(int catX, int catY, String bg)
    {
        setBackground("img/Tiles/" + bg + ".jpg");
        for(int r=0; r<tiles.length; r++)
        {
            for(int c=0; c<tiles[r].length; c++)
            {
                if(!(tiles[r][c]==null) && tiles[r][c].equals("block"))
                {
                    Block b = new Block();
                    if(bg.equals("pinkplanet"))
                    {
                        b = new PinkBlock();
                    }
                    else if(bg.equals("iceplanet"))
                    {
                        b = new IceBlock();
                    }
                    else if(bg.equals("forestplanet"))
                    {
                        b = new ForestBlock();
                    }
                    else if(bg.equals("desertplanet"))
                    {
                        b = new SandBlock();
                    }
                    else
                    {
                        b = new Block();
                    }
                    addObject(b, (c-1)*(90), ((r)*100)-90);
                    objects.add(b);
                }
            }
        }
    }
    public void addMainCharacter(Actor actor)
    {
        boolean added = false;
        int row = (int)(Math.random()*tiles.length);
        int col = (int)(Math.random()*tiles[0].length);
        while(added==false)
        {
            if(tiles[1][1]==null || tiles[1][1].equals("path"))
            {
                addObject(actor,1, 1);
                tiles[1][1] = "actor";
                added = true;
            }
        }
    }
    public void cleanupcrew()
    {
        removeObject(cat);
        removeObject(date);
        removeObject(currButton);
        removeObject(horiB);
        removeObject(veriB);
        removeObject(endBut);
        BarSymbol[] bars = veriB.getSymbols();
        for(BarSymbol bar : bars)
        {
            removeObject(bar);
        }    
        BarSymbol[] barss = horiB.getSymbols();
        catLocation[] hearts = ((HorizontalBar)horiB).getDateSymbols();
        for(BarSymbol bar : barss)
        {
            removeObject(bar);
        }
        for(catLocation heart : hearts)
        {
            removeObject(heart);
        }
        removeObject(veriB.getMainChar());
        removeObject(horiB.getMainChar());
    }
    public void buildAlternateEnd()
    {
        shouldAct = false;
        cleanupcrew();
        setBackground("Ends/dejaVu.png");
    }
    public void buildEndWorld(){
        shouldAct = false;
        cleanupcrew();
        setBackground("img/Tiles/end screen.png");
        
        CatWorld catD = new CatWorld("Sage1");
        addObject(catD, 120, 150);
        catD = new CatWorld("romantic1");
        addObject(catD, 250, 150);
        catD = new CatWorld("creator1");
        addObject(catD, 380 , 150);
        catD = new CatWorld("ethereal1");
        addObject(catD, 510, 150);
        catD = new CatWorld("jester1");
        addObject(catD, 640, 150);
        catD = new CatWorld("supporter1");
        addObject(catD, 120, 300);
        catD = new CatWorld("go-getter1");
        addObject(catD, 250, 300);
        catD = new CatWorld("caregiver1");
        addObject(catD, 380, 300);
        catD = new CatWorld("innocent1");
        addObject(catD, 510, 300);
        catD = new CatWorld("natural1");
        addObject(catD, 640, 300);
    }
}       