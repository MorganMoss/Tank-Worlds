package za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles;

import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Player;
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
    Player tank;
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
    @Override
    public Player getTank() {
        return tank;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Direction getDirection(){return this.direction;}


    //ADD MISSILE TO WORLD
    public void discharge(Player tank) {
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
        Image shell = tank.getDirection().getImage("missile");
        g.drawImage(shell, x+tank.getSize()/2-6, y+tank.getSize()/2-6, null);
        g.drawImage(shell, x+tank.getSize()/2-6, y+tank.getSize()/2-6, null);
    }

    //ANIMATE MISSILE ON WORLD
    @Override
    public void project(Direction direction){
        switch (direction){
            case Up:
                this.y-=TankWorld.getX_scale()/2;
                break;
            case Down:
                this.y+=TankWorld.getX_scale()/2;
                break;
            case Left:
                this.x-=TankWorld.getX_scale()/2;
                break;
            case Right:
                this.x+=TankWorld.getX_scale()/2;
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
