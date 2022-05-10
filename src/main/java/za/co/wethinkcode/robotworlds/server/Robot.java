package za.co.wethinkcode.robotworlds.server;

import java.util.Random;

public class Robot {

    private final String robotName;
    private final String robotType;
    private final int maxAmmo;
    private final int fireDistance;
    private final int size;

    private Position position;
    private Direction direction;
    private String lastCommand;
    private int currentShield;
    private int currentAmmo;
    private int kills=0;
    private int deaths=0;
    private boolean paused;

    public Robot(World world, String robotName, String robotType) {
        this.robotName = robotName;
        this.position = setLaunchPosition(world);
        this.direction = Direction.NORTH;
        this.paused = false;
        this.lastCommand = "launch";

        switch (robotType.toLowerCase()){
            case "sniper":
                this.robotType = "sniper";
                this.maxAmmo = 5;
                this.fireDistance = 15;
                this.size=20;
                this.currentShield = 3;
                this.currentAmmo = 5;
                break;
            case "machine":
                this.robotType = "machine";
                this.maxAmmo = 20;
                this.fireDistance = 8;
                this.size=10;
                this.currentShield = 1;
                this.currentAmmo = 20;
                break;
            case "bomber":
                this.robotType = "bomber";
                this.maxAmmo = 10;
                this.fireDistance = 5;
                this.size=10;
                this.currentShield = 10;
                this.currentAmmo = 10;
                break;
            default:
                this.robotType = "tank";
                this.maxAmmo = 15;
                this.fireDistance = 10;
                this.size=10;
                this.currentShield = 3;
                this.currentAmmo = 15;
                break;
        }
    }

    public int getDeaths(){
        return this.deaths;
    }

    public String getRobotName() {
        return robotName;
    }

    public String getRobotType() { return robotType; }

    public Position getPosition() {
        return this.position;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getVisibilityDistance() {
        return World.getVisibilityDistance();
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

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public void decreaseAmmo() {
        this.currentAmmo--;
    }

    public void resetShield() {
        this.currentShield = World.getMaxShield();
    }

    public void resetAmmo() {
        this.currentAmmo = this.maxAmmo;
    }

    public boolean isPaused() {
        return this.paused;
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
