package pt.iscte.poo.game;

import java.util.ArrayList;
import java.util.List;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Skeleton extends GameElement implements Movable{

    private int life;

    public Skeleton(Point2D position) {
        super(position);
        life = 5;
    }
    public int life() {
        return life;
    }

    public void getsAtk(int x) {
        life = life-x;
    }
    @Override
    public String getName() {
        return "Skeleton";
    }

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
    @Override
    public boolean isEmpty() {
        return false;
    }

    public Point2D chaseY() {
        Point2D result = new Point2D (0,0);
        Point2D hero = GameEngine.getInstance().heroPosition();
        if (hero.getY() > this.position.getY()) {
            result = this.position.plus(new Vector2D (0,1));
            return result;
        }
        if (hero.getY() < this.position.getY()) {
            return result = this.position.plus(new Vector2D (0,-1));
        }
        return position;
    }

    public Point2D chaseX() {
        Point2D result = new Point2D (0,0);
        Point2D hero = GameEngine.getInstance().heroPosition();
        if (hero.getX() > this.position.getX()) {
            result=this.position.plus(new Vector2D (1,0));
            return result;
        }
        if (hero.getX() < this.position.getX()) {
            result= this.position.plus(new Vector2D (-1,0));
            return result;
        }
        return position;
    }

    public boolean canMove(Point2D position) {
        List<GameElement> objs = GameEngine.getInstance().listObjs();
        Point2D hero = GameEngine.getInstance().heroPosition();
        boolean something = true;

        for (GameElement g : objs) {
            if (g.getPosition().equals(position)) {
                something = false;
                break;
            }
        }
        if(hero.equals(position))
            something =false;
        return something;
    }
    public void move(){
        Point2D positionY = chaseY();
        Point2D positionX = chaseX();
        int turns = GameEngine.getInstance().getTurn();
        if (!(turns % 2 == 0)) {
            if(canMove(positionY)) {
                if (positionY.getY() > this.position.getY()) {
                    moveDOWN();
                    return;
                }
                if (positionY.getY() < this.position.getY()) {
                    moveUP();
                    return;
                }
            }
            if(canMove(positionX)) {
                if (positionX.getX() > this.position.getX()) {
                    moveLEFT();
                    return;
                }
                moveRIGHT();
            }
        }
    }

    public void atk() {
        Hero hero = GameEngine.getInstance().getHero();
        int turns = GameEngine.getInstance().getTurn();
        if (!(turns % 2 == 0)) {
            if(heroClose()){
                hero.getsAtk(1);
            }
        }
    }

    public boolean heroClose() {
        Point2D result = position;
        result = result.plus(new Vector2D (0,-1));
        Point2D hero = GameEngine.getInstance().heroPosition();
        if(result.equals(hero))
            return true;
        result = position;
        result = result.plus(new Vector2D (0,1));
        if(result.equals(hero))
            return true;
        result = position;
        result = result.plus(new Vector2D (1,0));
        if(result.equals(hero))
            return true;
        result = position;
        result = result.plus(new Vector2D (-1,0));
        return result.equals(hero);
    }
    @Override
    public boolean isAttackable() {
        return true;
    }
    @Override
    public void dies() {
        ImageMatrixGUI grid = GameEngine.getInstance().image();
        List<GameElement> objs = GameEngine.getInstance().listObjs();
        List<GameElement> aux = new ArrayList<GameElement>();
        for (GameElement g : objs) {
            if(g instanceof Movable) {
                if(((Movable) g).life() <= 0) {
                    grid.removeImage(g);
                    aux.add(g);
                }
            }
        }
        objs.removeAll(aux);
    }
    @Override
    public boolean isCatchable() {
        return false;
    }

}