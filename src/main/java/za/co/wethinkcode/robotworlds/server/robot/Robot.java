package za.co.wethinkcode.robotworlds.server.robot;

import java.util.Timer;
import java.util.TimerTask;

import za.co.wethinkcode.robotworlds.server.Direction;
import za.co.wethinkcode.robotworlds.server.Position;

public abstract class Robot {
    private final String name;
    private final String visibilityPattern;
    private final int maxShield;
    private final int maxAmmo;
    private final int reloadTime;
    private final String firingPattern;
    private Position position;
    private Direction direction;
    private int currentShield;
    private int currentAmmo;

    public Robot(String name, String visibilityPattern, int maxShield, int maxAmmo, int reloadTime, String firingPattern) {
        this.name = name;
        this.visibilityPattern = visibilityPattern;
        this.maxShield = maxShield;
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.firingPattern = firingPattern;
    }

    public void setPosition(Position position) {}

    public Position getPosition() {return this.position;}

    public void setDirection(int angle) {}

    public Direction getDirection() {return this.direction;}

    public String getVisibilityPattern() {return this.visibilityPattern;}

    public int getAmmo() {return this.currentAmmo;}

    public void decreaseAmmo() {this.currentAmmo--;}

    public void resetAmmo() {
        // Timer t = new Timer();
        // t.schedule(new TimerTask(){this.currentAmmo = this.maxAmmo;} , reloadTime*1000);
    }
 
    public int getShield() {return this.currentShield;}

    public void decreaseShield() {this.currentShield--;}

    public void resetShield() {this.currentShield = this.maxShield;}

}
