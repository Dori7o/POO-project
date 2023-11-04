package pt.iscte.poo.game;

import pt.iscte.poo.utils.Point2D;

public interface Items {
    void catchItem();
    int use();
    void drop(int x);
    int defense(int x);
    void setPosition(Point2D position);
    int extradmg(int dmg);
    String getName();
}
