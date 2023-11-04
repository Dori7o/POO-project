package pt.iscte.poo.game;

import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Hero extends GameElement implements Movable{

    private int life;
    private final Items[] items = new Items[3];

    public Hero(Point2D position) {
        super(position);
        life = 10;
    }

    public int life() {
        return life;
    }
    @Override
    public void getsAtk(int x) {
        int dmg = x;
        for (int g =0; g < items.length ; g++) {
            if(items[g] != null)
                dmg= dmg-items[g].defense(x);
        }
        life= life-dmg;
    }
    public void heal() {
        ImageMatrixGUI grid = GameEngine.getInstance().image();
        for (int x= 0; x < items.length; x++) {
            if(items[x] != null)
                if(items[x].use() >= 1) {
                    for( int i = 0; i<= items[x].use()-1; i++ ) {
                        if(life<10) {
                            life++;
                        }
                    }
                    grid.removeImage((GameElement)items[x]);
                    items[x]= null;
                }
        }
    }

    @Override
    public String getName() {
        return "Hero";
    }

    public void move() {
        position = position.plus(new Vector2D(0,1));
    }


  //  public void move(Vector2D vector) {
    //    position = position.plus(vector);
   // }

    public void moveUP() {
        position = position.plus(new Vector2D (0,-1));
    }

    public void moveDOWN() {
        position = position.plus(new Vector2D (0,1));
    }

    public void moveLEFT() {
        position = position.plus(new Vector2D (1,0));
    }

    public void moveRIGHT() {
        position = position.plus(new Vector2D (-1,0));
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public GameElement getItem1() {
        return (GameElement) items[0];
    }


    public GameElement getItem2() {
        return (GameElement) items[1];
    }

    public GameElement getItem3() {
        return (GameElement) items[2];
    }

    public Items[] getbag() {
        return items;
    }


    @Override
    public Point2D chaseX() {
        return null;
    }


    public void move(Point2D position) {
        List<GameElement> objs = GameEngine.getInstance().listObjs();
        boolean occupied = true;
        for (GameElement g : objs) {
            if (g.getPosition().equals(position)) {
                if(!g.isEmpty()) {
                    occupied = false;
                    if(g.isAttackable()) {
                        ((Movable) g).getsAtk(atks());
                        if(((Movable) g).life() <= 0) {
                            ((Movable) g).dies();
                            return;
                        }
                    }
                }
                if(g.isCatchable()) {
                    if(slotOpenInt() == -1) {
                        System.out.println("Bag is full");
                        return;
                    }
                    this.position= position;
                    ((Items) g).catchItem();
                    return;
                }
                if(g.getClass() == Door.class) {
                    if(!((Door) g).lock())
                        for (int x =0; x < items.length ; x++) {
                            if(items[x] != null)
                                if(items[x].getClass()== Key.class)
                                    if(((Key) items[x]).getUnlock().equals(((Door) g).getUnlock()))
                                        ((Door)g).open();
                        }
                    else {
                        ((Door) g).nextLvl();
                        return;
                    }
                }
                if(g.getClass() == Treasure.class) {
                    ((Treasure) g).win();

                }
            }
        }
        if(occupied) {
            this.position= position;
        }
    }


    public void pickItem(Items item ) {
        slotOpen(item);
    }
    private void slotOpen(Items item) {
        if(getItem1() == null) {
            items[0] = item;
            return;
        }
        if(getItem2() == null) {
            items[1] = item;
            return;
        }
        if(getItem3() == null) {
            items[2] = item;
        }
    }

    public int slotOpenInt() {
        if(getItem1() == null) {
            return 0;
        }
        if(getItem2() == null) {
            return 1;
        }
        if(getItem3() == null) {
            return 2;
        }
        else
            return -1;
    }


    public boolean isEmpty() {
        return false;
    }

    public void dropItem(int x) {
        if(items[x] != null) {
            items[x].drop(x);
        }
    }

    @Override
    public Point2D chaseY() {
        return null;
    }


    public int atks() {
        int dmg = 1;
        for (int g =0 ; g < items.length ; g++) {
            if(items[g] != null)
                dmg= dmg+items[g].extradmg(dmg);
        }
        return dmg;
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public void dies() {
    }

    @Override
    public boolean isCatchable() {
        return false;
    }
    public void updateHP() {
        Hp[] hpBar = GameEngine.getInstance().bar();
        Hero hero = GameEngine.getInstance().getHero();
        if(hero.life() >= 0 )
            for(int x = 9; x >= hero.life(); x--) {
                hpBar[x].setRed();
            }
        if(hero.life() >= 0 )
            for(int x = 0; x < hero.life(); x++) {
                hpBar[x].setGreen();
            }
    }

    public void setPosition(Point2D hero) {
        this.position=hero;
    }
    @Override
    public void atk() {
    }
}
