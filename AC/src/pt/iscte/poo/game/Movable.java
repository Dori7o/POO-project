package pt.iscte.poo.game;

import pt.iscte.poo.utils.Point2D;

public interface Movable {

    void moveUP();
    void moveDOWN();
    void moveLEFT();
    void moveRIGHT();
    void move();
    Point2D chaseY();
    Point2D chaseX();
    void atk();
    void getsAtk(int atk);
    void dies();
    int life();
}
