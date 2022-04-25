package za.co.wethinkcode.robotworlds.client.SwingUI.Projectiles;

import za.co.wethinkcode.robotworlds.client.SwingUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingUI.TankWorld;
import za.co.wethinkcode.robotworlds.client.SwingUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingUI.Tanks.Enemy;
import za.co.wethinkcode.robotworlds.client.SwingUI.Tanks.Tank;
import za.co.wethinkcode.robotworlds.client.SwingUI.WorldObject;

import java.awt.*;
import java.util.ArrayList;

public abstract class Projectile implements ProjectileI{
    private int range;
    String sprite;
    int x;
    int y;
    int tankX;
    int tankY;
    int size;
    Direction direction;

    //setters
    public void setDirection(Direction direction){this.direction=direction;}
    //Getters
    public Direction getDirection(){return this.direction;}

    public String getSpriteName() {return this.sprite;}
    public int getSize(){return this.size;}


    public void strike() {

    }

    public boolean isHitting(WorldObject object) {
        return TankWorld.intersects(this, object);
    }

    public abstract boolean reachedRange(Direction direction);
}
