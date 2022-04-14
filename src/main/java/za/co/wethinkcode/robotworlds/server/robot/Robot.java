package za.co.wethinkcode.robotworlds.server.robot;

import java.util.Timer;
import java.util.TimerTask;

import za.co.wethinkcode.robotworlds.server.Direction;
import za.co.wethinkcode.robotworlds.server.Position;

public abstract class Robot {
    private final int name;
    private final String visibilityPattern;
    private final int maxShield;
    private final int maxAmmo;
    private final int reloadTime;
    private final String firingPattern;
    private final char serverNumber;
    private Position position;
    private Direction direction;
    private int currentShield;
    private int currentAmmo;

    public Robot(int name, String visibilityPattern, int maxShield, int maxAmmo, int reloadTime, String firingPattern, char serverNumber) {
        this.name = name;
        this.visibilityPattern = visibilityPattern;
        this.maxShield = maxShield;
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.firingPattern = firingPattern;
        this.serverNumber = serverNumber;
    }

    public int getName() {
        return name;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {return this.position;}

    //TODO : implement this method
    public void setDirection(int angle) {}

    public Direction getDirection() {return this.direction;}

    public String getVisibilityPattern() {return this.visibilityPattern;}

    public int getAmmo() {return this.currentAmmo;}

    public void decreaseAmmo() {this.currentAmmo--;}

    //TODO : implement this method
    public void resetAmmo() {
        // Timer t = new Timer();
        // t.schedule(new TimerTask(){this.currentAmmo = this.maxAmmo;} , reloadTime*1000);
    }
 
    public int getShield() {return this.currentShield;}

    public void decreaseShield() {this.currentShield--;}

    public void resetShield() {this.currentShield = this.maxShield;}

    /**
     * Gets the number this robot was assigned by the server
     * @return the number assigned by the server
     */
    public Character getNumber() {
        return serverNumber;
    }
}
