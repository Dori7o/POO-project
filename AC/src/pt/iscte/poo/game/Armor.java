package pt.iscte.poo.game;

import java.util.List;
import pt.iscte.poo.utils.Point2D;

/*
An object that the hero can pick catch and add to his iventory giving the hero
a chance to avoid taking damage
*/
public class Armor extends GameElement implements Items {

    public Armor(Point2D position) {
        super(position);
    }
    @Override
    public Point2D getPosition() {
        return position;
    }
    @Override
    public int getLayer() {
        return 0;
    }
    @Override
    public String getName() {
        return "Armor";
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
    @Override
    public boolean isAttackable() {
        return false;
    }
    @Override
    public boolean isCatchable() {
        return true;
    }
    @Override
    public void setPosition(Point2D point) {
        this.position = point;
    }

    @Override
    public void catchItem() {
        List<GameElement> objs = GameEngine.getInstance().listObjs();
        Hero hero = GameEngine.getInstance().getHero();
        for (GameElement g : objs) {
            if(g instanceof Items) {
                if(g.getPosition().equals(hero.getPosition())) {
                    ((Items) g).setPosition(new Point2D (hero.slotOpenInt(),11));
                    hero.pickItem((Items) g);
                }
            }
        }
    }
    @Override
    public int use() {
        return 0;

    }
    @Override
    public void drop (int x) {
        Hero hero = GameEngine.getInstance().getHero();
        Items drop = hero.getbag()[x];
        drop.setPosition(hero.getPosition());
        hero.getbag()[x] = null;
    }
    @Override
    public int defense(int x) {
        if(Math.random() < 0.5) {
            return x;
        }
        return 0;

    }
    @Override
    public int extradmg(int dmg) {
        return 0;
    }
}
