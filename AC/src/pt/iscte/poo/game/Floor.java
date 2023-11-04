package pt.iscte.poo.game;


import pt.iscte.poo.utils.Point2D;


public class Floor extends GameElement {


    public Floor(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Floor";
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
        return false;
    }
}