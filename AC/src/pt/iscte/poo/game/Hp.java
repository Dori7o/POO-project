package pt.iscte.poo.game;


import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Hp implements ImageTile{

    private final Point2D position;
    boolean green;

    public Hp(Point2D position) {
        this.position=position;
        this.green= true;
    }

    @Override
    public String getName() {
        if(green)
            return "Green";
        else
            return "Red";
    }
    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 0;
    }
    public void setRed() {
        green=false;
    }
    public void setGreen() {
        green=true;
    }

}
