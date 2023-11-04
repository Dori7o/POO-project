package pt.iscte.poo.game;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Thug extends GameElement implements Movable{

    private int life;

    public Thug(Point2D position) {
        super(position);
        life = 10;
    }
    public int life() {
        return life;
    }

    public void getsAtk(int x) {
        life = life-x;
    }
    @Override
    public String getName() {
        return "Thug";
    }

    public void move(Point2D position) {

        if (position.getY() > this.position.getY()) {
            moveDOWN();
            return;
        }
        if (position.getY() < this.position.getY()) {
            moveUP();
            return;
        }
        if (position.getX() > this.position.getX()) {
            moveLEFT();
            return;
        }
        moveRIGHT();

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
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 1;
    }
    public void move(){
        Point2D positionY = chaseY();
        Point2D positionX = chaseX();
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

    @Override
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

    @Override
    public Point2D chaseX() {
        Point2D result = new Point2D (0,0);
        Point2D hero = GameEngine.getInstance().heroPosition();
        if (hero.getX() > this.position.getX()) {
            result =  this.position.plus(new Vector2D (1,0));
            return result;
        }
        if (hero.getX() < this.position.getX()) {
            result= this.position.plus(new Vector2D (-1,0));
            return result;
        }
        return position;
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
    public void atk() {
        Hero hero = GameEngine.getInstance().getHero();
        if (Math.random() < 0.3) {
            if(heroClose()){
                hero.getsAtk(3);
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