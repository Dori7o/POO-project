package pt.iscte.poo.game;


import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;


public class Door extends GameElement {

    final String key;
    Point2D hero;
    String destiny;
    boolean lock;

    public Door(Point2D position, String key, Point2D hero, String destino) {
        super(position);
        this.key=key;
        this.hero=hero;
        this.destiny =destino;
        lock = false;
    }

    @Override
    public String getName() {
        if (lock)
            return "DoorOpen";
        return "DoorClosed";
    }
    public String getUnlock() {
        return key;
    }
    public void open() {
        this.lock=true;
    }

    public boolean lock() {
        return lock;
    }

    @Override
    public int getLayer() {
        return 0;
    }
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean isCatchable() {
        return false;
    }

    public void nextLvl() {
        ImageMatrixGUI grid = GameEngine.getInstance().image();
        List<GameElement> objs = GameEngine.getInstance().listObjs();
        List<GameElement> aux = new ArrayList<GameElement>();
        Hero hero = GameEngine.getInstance().getHero();
        for (GameElement g : objs) {
            if((g.equals(hero.getItem1())) || g.equals(hero.getItem2()) ||g.equals(hero.getItem3())) {
            }
            else {
                grid.removeImage(g);
                aux.add(g);
            }
        }
        objs.removeAll(aux);
        GameEngine.getInstance().readLevel(destiny);
        hero.setPosition(this.hero);
    }
}