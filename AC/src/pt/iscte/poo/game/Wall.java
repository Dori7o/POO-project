package pt.iscte.poo.game;

import pt.iscte.poo.utils.Point2D;

public class Wall extends GameElement {

    public Wall(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Wall";
    }

    @Override
    public int getLayer() {
        return 0;
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
        return false;
    }
}