package za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles;

import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Tank;
import za.co.wethinkcode.robotworlds.client.SwingGUI.WorldObject;
import java.awt.*;

public class Shell extends Projectile{
    //TODO: CHECK WHICH FUNCTIONS CAN BE MOVED TO PARENT CLASS
    String sprite="Shell";
    int tankX;
    int tankY;
    int x;
    int y;
    Direction direction;
    private int range;
    Tank tank;
    int size = 10;

    public String getSprite() {
        return sprite;
    }

    @Override
    public int getTankX() {
        return tankX;
    }

    @Override
    public int getTankY() {
        return tankY;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getRange() {
        return range;
    }

    public Tank getTank() {
        return tank;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Direction getDirection(){return this.direction;}


    //ADD MISSILE TO WORLD
    public void discharge(Tank tank) {
        this.x = tank.getX();
        this.tankX = tank.getX();
        this.y = tank.getY();
        this.tankY = tank.getY();
        this.tank = tank;
        this.direction = tank.getDirection();
        this.range = tank.getRange();
        TankWorld.addProjectile(this);
    }

    //DISPLAY MISSILE ON GUI
    @Override
    public void draw(Graphics g) {
        g.drawImage(tank.getDirection().getImage("missile"), x+14, y+14, null);
    }

    //ANIMATE MISSILE ON WORLD
    @Override
    public void project(Direction direction){
        switch (direction){
            case Up:
                this.y-=5;
                break;
            case Down:
                this.y+=5;
                break;
            case Left:
                this.x-=5;
                break;
            case Right:
                this.x+=5;
        }
    }

    //CHECK IF MISSILE IS HITTING A WORLD OBJECT
    @Override
    public boolean isHitting(WorldObject object) {
        return TankWorld.intersects(this, object);
    }

    //CHECK IF A MISSILE HAS HIT A WORLD OBJECT
    public boolean reachedRange(Direction direction){
//        this.x-(range-1), this.y-(range-1), range*2, range*2
        Rectangle playerRect = new Rectangle(this.x,this.y,this.size,this.size);
        Rectangle tankRect = new Rectangle(tankX-(range-1),tankY-(range-1),
                range*2, range*2);
        return !playerRect.intersects(tankRect);
    }
}
