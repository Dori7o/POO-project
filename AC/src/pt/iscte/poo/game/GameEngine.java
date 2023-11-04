package pt.iscte.poo.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;


public class GameEngine implements Observer {

    public static final int GRID_HEIGHT = 10;
    public static final int GRID_WIDTH = 10;

    private static GameEngine INSTANCE = null;
    private final ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    private final List<GameElement> objs = new ArrayList<GameElement>();
    private Hero hero;
    private int turns;
    private boolean win;
    private final Hp[] bar = new Hp[10];

    public static GameEngine getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GameEngine();
        return INSTANCE;
    }

    private GameEngine() {
        gui.registerObserver(this);
        gui.setSize(GRID_WIDTH, GRID_HEIGHT+2);
        gui.go();
    }

    public void start() {
        addFloor();
        readLevel("room0");
        hero = new Hero(new Point2D(1,1));
        gui.addImage(hero);
        for(int x = 0; x < 10; x++) {
            bar[x]= new Hp (new Point2D (x,10));
            gui.addImage(bar[x]);
        }
        gui.setStatusMessage("ROGUE  - Turns: " + turns);
        gui.update();


    }

    public void readLevel( String lvl) {
        File file = new File ("rooms/"+lvl+".txt");
        try {
            Scanner scanner = new Scanner (file);
            scanner.useDelimiter("\r\n");

            for (int y=0; y!=GRID_WIDTH; y++) {
                String line = scanner.next();
                for (int x=0; x!=GRID_HEIGHT; x++) {
                    char obj = line.charAt(x);
                    if ( obj == '#') {
                        GameElement add = GameElement.create( ""+obj, new Point2D(x,y), null );
                        gui.addImage(add);
                        objs.add(add);
                    }
                    else {
                    }

                }
            }
            scanner.nextLine();
            scanner.nextLine();
            while(scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                GameElement add = GameElement.create( line[0], new Point2D (Integer. parseInt(line[1]),Integer. parseInt(line[2])), line);
                gui.addImage(add);
                objs.add(add);
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("Level não encontrado");
        }
    }

    private void addFloor() {
        List<ImageTile> tileList = new ArrayList<>();
        for (int x=0; x!=GRID_WIDTH; x++)
            for (int y=0; y!=GRID_HEIGHT; y++)
                tileList.add(new Floor(new Point2D(x,y)));
        gui.addImages(tileList);
    }
    @Override
    public void update(Observed source) {

        if (ImageMatrixGUI.getInstance().wasWindowClosed())
            System.out.println("Close");

        if(win) {
            gui.setStatusMessage("You win");
            return;
        }
        if(hero.life() >= 0) {
            int key = ((ImageMatrixGUI) source).keyPressed();
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP ) {
                hero.move(trymove(new Vector2D (0,-1)));
                gameTurn();
                turns++;
                hero.updateHP();
            }
            if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN ) {
                hero.move(trymove(new Vector2D (0,1)));
                gameTurn();
                turns++;
                hero.updateHP();
            }
            if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT ) {
                hero.move(trymove(new Vector2D (1,0)));
                gameTurn();
                turns++;
                hero.updateHP();
            }
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT ) {
                hero.move(trymove(new Vector2D (-1,0)));
                gameTurn();
                turns++;
                hero.updateHP();
            }
            if (key == KeyEvent.VK_1) {
                hero.dropItem(0);
            }
            if (key == KeyEvent.VK_2) {
                hero.dropItem(1);
            }
            if (key == KeyEvent.VK_3) {
                hero.dropItem(2);
            }
            if (key == KeyEvent.VK_E) {
                hero.heal();
                hero.updateHP();
            }
            gui.setStatusMessage("ROGUE- Turns: " + turns);
            gui.update();
        }
        else {
            gui.setStatusMessage("Game Over");
        }
    }

    private Point2D trymove( Vector2D add) {
        Point2D d = hero.getPosition().plus(add);
        return d;
    }

    public List<GameElement> listObjs() {
        return objs;
    }
    public Point2D heroPosition() {
        return hero.getPosition();
    }
    public ImageMatrixGUI image() {
        return gui;
    }
    public Hp[] bar() {
        return bar;
    }
    public int getTurn() {
        return turns;
    }
    public Hero getHero() {
        return hero;
    }

    private void gameTurn() {
        for (GameElement g : objs) {
            if(g instanceof Movable) {
                ((Movable) g).atk();
                ((Movable) g).move();
            }
        }
    }

    public void setWin(boolean win) {
        this.win = win;
    }

}
