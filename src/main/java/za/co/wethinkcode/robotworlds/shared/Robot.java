package za.co.wethinkcode.robotworlds.shared;

public class Robot {
    public static final String[] ROBOT_TYPES = new String[]{"Sniper", "Machine", "Bomber", "Tank"};

    private final String robotName;
    private final String robotType;
    private final int maxAmmo;
    private int maxShield;
    private int visibilityDistance;
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
    private boolean isFiring = false;


    public Robot(String robotName, String robotType) {
        this.robotName = robotName;
        this.direction = Direction.NORTH;
        this.paused = false;
        this.lastCommand = "launch";
        this.status="normal";

        switch (robotType.toLowerCase()){
            case "sniper":
                this.robotType = "sniper";
                this.size = 10;
                this.fireDistance = 18;
                this.maxAmmo = 3;
                this.maxShield = 1;
                this.visibilityDistance = 20;
                break;
            case "machine":
                this.robotType = "machine";
                this.size = 10;
                this.fireDistance = 4;
                this.maxAmmo = 20;
                this.maxShield = 3;
                this.visibilityDistance = 6;
                break;
            case "bomber":
                this.robotType = "bomber";
                this.size = 10;
                this.fireDistance = 8;
                this.maxAmmo = 10;
                this.maxShield = 5;
                this.visibilityDistance = 10;
                break;
            default:
                this.robotType = "tank";
                this.size = 10;
                this.fireDistance = 10;
                this.maxAmmo = 5;
                this.maxShield = 3;
                this.visibilityDistance = 12;
                break;
        }
        this.currentShield = this.maxShield;
        this.currentAmmo = this.maxAmmo;
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
        return visibilityDistance;
    }

    public int getFiringDistance() {
        return fireDistance;
    }

    public int getMaxShield() {
        return maxShield;
    }
    public int getMaxAmmo() {
        return maxAmmo;
    }

    public int getCurrentAmmo() {
        return this.currentAmmo;
    }

    public int getCurrentShield() {
        return this.currentShield;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setVisibilityDistance(int visibilityDistance) {
        this.visibilityDistance = visibilityDistance;
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

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void decreaseShield() {
        this.currentShield--;
        System.out.println(currentShield);
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public void decreaseAmmo() {
        this.currentAmmo--;
    }

    public void setMaxShield(int maxShield) {
        this.maxShield = maxShield;
        this.currentShield = maxShield;
    }

    public void resetShield() {
        this.currentShield = maxShield;
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

    public boolean hasDied() {
        return currentShield==0;
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
