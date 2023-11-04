package pt.iscte.poo.game;

import java.util.List;
import pt.iscte.poo.utils.Point2D;

public class Key extends GameElement implements Items {

    final String unlock;

    public Key(Point2D position, String line) {
        super(position);
        this.unlock= line;
    }

    public String getUnlock() {
        return unlock;
    }

    @Override
    public String getName() {
        return "Key";
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
    public void setPosition(Point2D point) {
        this.position = point;
    }
    @Override
    public int extradmg(int dmg) {
        return 0;
    }
}