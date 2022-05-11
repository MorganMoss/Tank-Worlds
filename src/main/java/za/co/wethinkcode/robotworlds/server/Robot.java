package za.co.wethinkcode.robotworlds.server;

import java.util.Random;

public class Robot {
    public static final String[] ROBOT_TYPES = new String[]{"Sniper", "Machine", "Bomber", "Tank"};
    private final String robotName;
    private final String robotType;
    private final int maxAmmo;
    private final int fireDistance;
    private final int size;

    private String lastCommand;
    private Position position;
    private Direction direction;
    private int currentShield;
    private int currentAmmo;
    private int kills=0;
    private int deaths=0;
    private boolean paused;
    private String status;

    public Robot(World world, String robotName, String robotType) {
        this.robotName = robotName;
        this.position = setLaunchPosition(world);
        this.direction = Direction.NORTH;
        this.paused = false;
        this.lastCommand = "launch";
        this.status="normal";

        switch (robotType.toLowerCase()){
            case "sniper":
                this.robotType = "sniper";
                this.size = 20;
                this.fireDistance = 15;
                this.maxAmmo = 5;
                this.currentAmmo = this.maxAmmo;
                this.currentShield = World.getMaxShield();
                break;
            case "machine":
                this.robotType = "machine";
                this.size = 10;
                this.fireDistance = 8;
                this.maxAmmo = 20;
                this.currentAmmo = this.maxAmmo;
                this.currentShield = World.getMaxShield();
                break;
            case "bomber":
                this.robotType = "bomber";
                this.size = 10;
                this.fireDistance = 5;
                this.maxAmmo = 10;
                this.currentAmmo = this.maxAmmo;
                this.currentShield = World.getMaxShield();
                break;
            default:
                this.robotType = "tank";
                this.size = 10;
                this.fireDistance = 10;
                this.maxAmmo = 15;
                this.currentAmmo = this.maxAmmo;
                this.currentShield = World.getMaxShield();
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

    public void setStatus(String status) {
        this.status = status;
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
                "\nstatus : "+status;
    }
}
