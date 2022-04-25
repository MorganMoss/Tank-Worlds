package za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles;

import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.client.SwingGUI.WorldObject;

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
