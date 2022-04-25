package za.co.wethinkcode.robotworlds.client.SwingUI.Tanks;

import za.co.wethinkcode.robotworlds.client.SwingUI.WorldObject;

import java.awt.*;

public interface TankI extends WorldObject {

    //setters
    void setTankHealth(int health);
    Direction getDirection();
    int getRange();
    void setTankDirection(Direction direction);
    void takeHit();
    void repair();
    void moveForward();
    void moveBack();
    void setName(String clientName);
    int getAmmo();
    void reload();

    //getters
    int getX();
    int getY();
    String getTankName();
    String getSpriteName();
    String getDeadImage();
    int getTankHealth();
    int getSize();

    //display
    void fire();
    void draw(Graphics g);
    void showState(Graphics g);

    void setAmmo(int ammo);

    void setRange(int range);

    void setSprite(String sprite);

    void setKills(int kills);
    void addKill();
    void addDeaths();

    void setDeaths(int deaths);
}
