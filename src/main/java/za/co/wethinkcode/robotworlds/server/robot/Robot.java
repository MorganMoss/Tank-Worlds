package za.co.wethinkcode.robotworlds.server.robot;

import za.co.wethinkcode.robotworlds.server.Direction;
import za.co.wethinkcode.robotworlds.server.Position;

public abstract class Robot {
    private final String robotName;
    private final int visibilityDistance;
    private final int maxShield;
    private final int maxAmmo;
    private final int reloadTime;
    private final String firingPattern;
    private final int fireDistance;

    private Position position;
    private Direction direction;
    private int currentShield;
    private int currentAmmo;
    private boolean paused;

    public Robot(String robotName, int visibilityDistance, int maxShield, int maxAmmo, int reloadTime, String firingPattern, int fireDistance) {
        this.robotName = robotName;
        this.visibilityDistance = visibilityDistance;
        this.maxShield = maxShield;
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.firingPattern = firingPattern;
        this.fireDistance = fireDistance;
    }

    public String getRobotName() {
        return robotName;
    }

    public Position getPosition() {
        return this.position;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getVisibilityDistance() {
        return this.visibilityDistance;
    }

    public int getFiringDistance() {
        return fireDistance;
    }

    public int getCurrentAmmo() {
        return this.currentAmmo;
    }

    public int getCurrentShield() {
        return this.currentShield;
    }

    public void setDirection(int angle) {

        switch (Math.round((angle / 90f)%4)) {
            case 1:
                this.direction = Direction.EAST;
                break;
            case 2:
                this.direction = Direction.SOUTH;
                break;
            case 3:
                this.direction = Direction.WEST;
                break;
            default:
                this.direction = Direction.NORTH;
                break;
        }
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    //TODO : implement this method

    public void decreaseShield() {
        this.currentShield--;
    }

    public void decreaseAmmo() {
        this.currentAmmo--;
    }

    public void resetShield() {
        this.currentShield = this.maxShield;
    }

    //TODO : implement this method

    public void resetAmmo() {
        // Timer t = new Timer();
        // t.schedule(new TimerTask(){this.currentAmmo = this.maxAmmo;} , reloadTime*1000);
    }

    public boolean isPaused() {
        return false;
    }
}
