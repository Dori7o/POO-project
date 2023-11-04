package pt.iscte.poo.game;

import java.util.List;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameElement implements Items {

    public Sword(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
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
        return 0;
    }

    @Override
    public int extradmg(int x) {
        return 2*x;
    }
}