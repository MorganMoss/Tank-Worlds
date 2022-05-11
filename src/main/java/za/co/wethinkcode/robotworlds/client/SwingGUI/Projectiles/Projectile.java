package za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles;

import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Player;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Tank;
import za.co.wethinkcode.robotworlds.client.SwingGUI.WorldObject;

public abstract class Projectile implements WorldObject {
    private int range;
    String sprite;
    int x;
    int y;
    int tankX;
    int tankY;
    int size;
    Direction direction;
    private Player tank;


    //setters
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    //Getters

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getTankX() {return tankX;}
    public int getTankY() {return tankY;}

    public Direction getDirection(){return this.direction;}

    public int getRange() {
        return this.range;
    }

    public String getSpriteName() {return this.sprite;}

    public int getSize() {
        return this.size;
    }




    public void strike() {
    }

    public boolean isHitting(WorldObject object) {
        return TankWorld.intersects(this, object);
    }

    public abstract boolean reachedRange(Direction direction);

    public abstract void project(Direction direction);

    public Player getTank(){
        return this.tank;
    }
}
