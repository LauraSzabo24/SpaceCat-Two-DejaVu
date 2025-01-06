
import mayflower.*;
import java.util.*;
public class OurWorld extends World {
    private Cat cosmo;
    private String[][] tiles;
    public boolean generateLevel;
    private ArrayList<Actor> objects; 
    public int cosmoLastY;
    public int cosmoLastX;
    public int cosmoCurrY;
    public int cosmoCurrX;
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
    private int updateCycle;
    private int lastUpdateCycle;
    
    //button press
    public boolean sPressed;
    public boolean wPressed;
    private Select currButton;
    public EndWorldButton endBut;//n
    private int counterFinal = 0;
    
    //ghosts
    private Ghost eraZero, eraOne, eraTwo, eraThree;    
    
    //dates
    private CatDate date;
    private String text = "this thing 1";
    
    public OurWorld() 
    {
        updateCycle = 0;
        lastUpdateCycle = 0;
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
        
        //cosmo
        cosmo = new Cat();
        cosmoLastY = 0;
        cosmoLastX = 0;
        cosmoCurrY = 0;
        cosmoCurrX = 0;
        
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
        addMainCharacter(cosmo);
        addGhosts();
        
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
                    endBut.setAction("PAUSE");
                    buildDejaVuEnd();
                }
                else
                {
                    counterFinal++;
                }
            }
            else if(isDualExistence())
            {
                if(counterFinal==100)
                {
                    destroyWorld();
                    endBut.setAction("PAUSE");
                    buildDualEnd();
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
                    updateGhosts();
                    text = date.getTrait();
                    showText(text, 20, 30, 530, Color.WHITE);
                }
            }
        }
    }
    
    //UPDATES
    public void updateGhosts()
    {
        updateCycle++;
        Era currEraZone = timeZones.get(currEra);
        currEraZone.updateHomeGhostMemory(cosmoCurrX, cosmoCurrY, "i");
        double erax = 0;
        double eray = 0;
        if(updateCycle==100000)
        {
            updateCycle = 0;
        }
        if(lastUpdateCycle==100000)
        {
            lastUpdateCycle = 0;
        }
        switch(currEra){
            case 0:
                eraZero = currEraZone.getHomeGhost();
            case 1:
                if(updateCycle-lastUpdateCycle==7)
                {
                    eraOne = currEraZone.getHomeGhost();
                    erax = eraZero.getCurrPos()[0];
                    eray = eraZero.getCurrPos()[1];
                    eraZero.setLocation(erax,eray);
                    lastUpdateCycle = updateCycle;
                }    
            case 2:
                if(updateCycle-lastUpdateCycle==20)
                {
                    eraTwo = currEraZone.getHomeGhost();
                    erax = eraZero.getCurrPos()[0];
                    eray = eraZero.getCurrPos()[1];
                    eraZero.setLocation(erax,eray);
                    
                    erax = eraOne.getCurrPos()[0];
                    eray = eraOne.getCurrPos()[1];
                    eraOne.setLocation(erax,eray);
                }
            case 3:
                if(updateCycle-lastUpdateCycle==20)
                {
                    eraThree = currEraZone.getHomeGhost();
                    erax = eraZero.getCurrPos()[0];
                    eray = eraZero.getCurrPos()[1];
                    eraZero.setLocation(erax,eray);
                    
                    erax = eraOne.getCurrPos()[0];
                    eray = eraOne.getCurrPos()[1];
                    eraOne.setLocation(erax,eray);
                    
                    erax = eraTwo.getCurrPos()[0];
                    eray = eraTwo.getCurrPos()[1];
                    eraTwo.setLocation(erax,eray);
                }
            case 4:
                if(updateCycle-lastUpdateCycle==20)
                {
                    erax = eraZero.getCurrPos()[0];
                    eray = eraZero.getCurrPos()[1];
                    eraZero.setLocation(erax,eray);
                    
                    erax = eraOne.getCurrPos()[0];
                    eray = eraOne.getCurrPos()[1];
                    eraOne.setLocation(erax,eray);
                    
                    erax = eraTwo.getCurrPos()[0];
                    eray = eraTwo.getCurrPos()[1];
                    eraTwo.setLocation(erax,eray);
                    
                    erax = eraThree.getCurrPos()[0];
                    eray = eraThree.getCurrPos()[1];
                    eraThree.setLocation(erax,eray);
                }
        }
    }
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
            addGhosts();
        }
        else if(wPressed && !Mayflower.isKeyDown(Keyboard.KEY_W))
        {
            wPressed = false;
            currEra--;
            veriB.getMainChar().setIndex(veriB.getMainChar().getIndex()-1);
            updateProgressVeri();
            resetEra();
            universe = timeZones.get(currEra).getWorlds();
            removeGhosts();
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
    public boolean isDualExistence()
    {
        for(int i=0; i< timeZones.size(); i++)
        {
            if(timeZones.get(i)!=null && timeZones.get(i).isDualExistence()==true)
            {
                return true;
            }
        }
        return false;
    }
    
    //WORLD GENERATION
    public void addGhosts()
    {
        switch(currEra){
            case 0:
                break;
            case 1:
                addObject(eraZero,1,1);
            case 2:
                addObject(eraOne,1,1);
            case 3:
                addObject(eraTwo,1,1);
            case 4:
                addObject(eraThree,1,1);
        }
    }
    public void removeGhosts()
    {
        switch(currEra){
            case 0:
                System.out.println("removeeraZero" + eraZero);
                removeObject(eraZero);
            case 1:
                removeObject(eraZero);
            case 2:
                removeObject(eraZero);
            case 3:
                removeObject(eraZero);
            case 4:
                break;
        }
    }
    public void expandWorld()
    {
        if(cosmo.location[0]>=780)
        {
            removeObject(date);
            removeObject(currButton);
            destroyWorld();
            cosmoCurrX = 1;
            cosmoCurrY = (((int)cosmoLastY/100))*100;
            cosmo.setLocation(cosmoCurrX, cosmoCurrY);
            if(currLocation>=14)
            {
                universe.setLoc(0);
                currLocation = leftestLoc;
                WorldMap nW = universe.getCurrent();
                currWrldMap = nW;
                createWorld(1,((int)cosmoLastY/100)+1,nW);
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
                    WorldMap newWorld = new BlockWorld(1, ((int)cosmoLastY/100)+1, 0, 0, 0,0, currWorld);
                    if(currCount==1 && !currWorld.equals("purple"))
                    {
                        newWorld = new PlatformWorld(1, ((int)cosmoLastY/100)+1, 0, 0, 0,0, currWorld);
                        ((PlatformWorld)newWorld).createDate();
                        date = ((PlatformWorld)newWorld).getCatDate();
                        addObject(date,100, 153);
                        date.scale(100, 87);
                        
                        currButton = ((PlatformWorld)newWorld).getButton();
                    }
                    universe.addToEnd(newWorld);
                    currWrldMap = newWorld;
                    createWorld(1,((int)cosmoLastY/100)+1, newWorld);
                    currCount++;
                    rightestLoc++;
                }
                else{
                    WorldMap nW = universe.getNext();
                    currWrldMap = nW;
                    createWorld(1,((int)cosmoLastY/100)+1,nW);
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
        else if(cosmo.location[0]<0)
        {
            removeObject(date);
            removeObject(currButton);
            text="";
            destroyWorld();
            cosmoCurrX = 750;
            cosmoCurrY = (((int)cosmoLastY/100))*100;
            cosmo.setLocation(cosmoCurrX, cosmoCurrY);            
            if(currLocation<=0)
            {
                universe.setLoc(rightestLoc);
                currLocation = rightestLoc;
                WorldMap nW = universe.getCurrent();
                currWrldMap = nW;
                createWorld(1,((int)cosmoLastY/100)+1,nW);
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
                    WorldMap newWorld = new BlockWorld(8, ((int)cosmoLastY/100)+1, 0, 1,1, 0, currWorld);
                    if(currCount==1 && !currWorld.equals("purple"))
                    {
                        newWorld = new PlatformWorld(8, ((int)cosmoLastY/100)+1, 0, 1,1, 0, currWorld);
                        ((PlatformWorld)newWorld).createDate();
                        date = ((PlatformWorld)newWorld).getCatDate();
                        addObject(date,100, 153);
                        date.scale(100, 87);
                        
                        currButton = ((PlatformWorld)newWorld).getButton();
                    }
                    universe.addToStart(newWorld);
                    currWrldMap = newWorld;
                    createWorld(8,((int)cosmoLastY/100)+1, newWorld);
                    leftestLoc--;
                    currCount++;
                }
                else
                {
                    WorldMap nW = universe.getPrevious();
                    currWrldMap = nW;
                    createWorld(1,((int)cosmoLastY/100)+1,nW);
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
        if(cosmo.isBlocked())
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
                cosmoCurrX = 730;
                cosmoCurrY = yCor-93;
                cosmo.setLocation(cosmoCurrX, cosmoCurrY);
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
                cosmoCurrX = 1;
                cosmoCurrY = yCor;
                cosmo.setLocation(cosmoCurrX, cosmoCurrY);
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
        cosmoLastY = cosmo.location[1];
        cosmoLastX = cosmo.location[0];
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
        removeObject(cosmo);
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
    public void buildDejaVuEnd()
    {
        shouldAct = false;
        cleanupcrew();
        setBackground("Ends/dejaVu.png");
    }
    public void buildDualEnd()
    {
        shouldAct = false;
        cleanupcrew();
        setBackground("Ends/dualexistence.png");
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
    public void setCosmoCurrX(int x)
    {
        cosmoCurrX = x;
    }
    public void setCosmoCurrY(int y)
    {
        cosmoCurrY = y;
    }
}       
