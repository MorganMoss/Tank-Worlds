package za.co.wethinkcode.robotworlds.server.robot;

import za.co.wethinkcode.robotworlds.server.Direction;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;

import java.util.Random;

public class Robot {

    private final String robotName;
    private final int maxShield;
    private final int maxAmmo;
    private final int visibilityDistance;
    private final int fireDistance;
    private final int reloadTime;

    private Position position;
    private Direction direction;
    private int currentShield;
    private int currentAmmo;
    private int range=5;
    private int kills=0;
    private int deaths=0;
    private boolean paused;

    public Robot(World world, String robotName, int visibilityDistance, int maxShield, int maxAmmo, int reloadTime, int fireDistance) {
        //These are immutable
        this.robotName = robotName;
        this.maxShield = maxShield;
        this.maxAmmo = maxAmmo;
        this.visibilityDistance = visibilityDistance;
        this.fireDistance = fireDistance;
        this.reloadTime = reloadTime;
        //These are initialized, but are mutable
        this.currentAmmo = maxAmmo;
        this.currentShield = maxShield;
        this.paused = false;
        this.direction = Direction.NORTH;
        this.position = setLaunchPosition(world);
    }
    public int getDeaths(){
        return this.deaths;
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

    public int getReloadTime() {
        return reloadTime;
    }

    public boolean getPaused() {
        return this.paused;
    }

    public void setPaused(Boolean bool) {
        this.paused = bool;
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

    public Position setLaunchPosition(World world) {
        Position launchPosition;
        Random random = new Random();
        do {
            int limit = -50;
            int x = random.nextInt(world.getMapSize().getX()+1+limit) - (world.getMapSize().getX()+limit/2)/2;
            int y = random.nextInt(world.getMapSize().getY()+1+limit) - (world.getMapSize().getY()+limit/2)/2;
            launchPosition = new Position(x,y);
        } while(!world.getWorldMap().get(launchPosition.getX()).get(launchPosition.getY()).equals(" "));
        return launchPosition;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void decreaseShield() {
        this.currentShield--;
    }

    public void decreaseAmmo() {
        this.currentAmmo--;
    }

    public void resetShield() {
        this.currentShield = this.maxShield;
    }

    public void resetAmmo() {
        this.currentAmmo = this.maxAmmo;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public int getRange() {
        return this.range;
    }

    public int getKills() {
        return this.kills;
    }

    public boolean isFiring() {
        return false;
    }

    @Override
    public String toString() {
        return "name : " +robotName+
                "\nposition : [" +position.getX()+ ", " +position.getY()+ "]" +
                "\ndirection : " +direction.toString()+
                "\nshields : " +currentShield+
                "\nshots : " +currentAmmo+
                "\nstatus : n/a";
    }
}
