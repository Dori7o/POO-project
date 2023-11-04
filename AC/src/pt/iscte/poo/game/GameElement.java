package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {

    public Point2D position;

    public GameElement(Point2D position ) {
        this.position = position;
    }
    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 0;
    }

    public static GameElement create( String code, Point2D position, String[] line) {
        switch(code) {
            case "Skeleton": return new Skeleton (position);
            case "Bat":  return new Bat (position);
            case "Sword": return new Sword (position);
            case "Key":  return new Key (position, line[3]);
            case "Door": return new Door (position,line[6],new Point2D (Integer. parseInt(line[4]),Integer. parseInt(line[5])), line[3]);
            case "HealingPotion": return new HealingPotion (position);
            case "Treasure": return new Treasure (position);
            case "Thug": return new Thug (position);
            case "Armor": return new Armor (position);
            case "Thief": return new Thief (position);
            case "": return new Floor (position);
            case "#": return new Wall (position);
        }
        return null;
    }
    public abstract boolean isEmpty();
    public abstract boolean isAttackable();
    public abstract boolean isCatchable();
    public abstract String getName();

}
