package za.co.wethinkcode.robotworlds;

abstract class Robot {
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

    public void setPosition(Position position) {}
    public Position getPosition() {return this.position;}
    public void setDirection(int Angle) {}
    public Direction getDirection() {return this.direction;}
    public String getVisibilityPattern() {return this.visibilityPattern;}

    public int getAmmo() {return this.currentAmmo;}
    public void decreaseAmmo() {}
    public void resetAmmo() {}

    public int getShield() {return this.currentShield;}
    public void decreaseShield() {}
    public void resetShield() {}



}
