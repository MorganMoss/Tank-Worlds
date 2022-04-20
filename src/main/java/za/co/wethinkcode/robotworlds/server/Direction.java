package za.co.wethinkcode.robotworlds.server;

//TODO : make javadocs!!
public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public double getAngle() {
        switch (this){
            case NORTH: return 0;
            case EAST: return 90;
            case SOUTH: return 180;
            case WEST: return 270;
        }
        throw new IllegalStateException();
    }

    //TODO : Implement to have this object make a new object an angle equal to this.getAngle() + angle
    public Direction turn(int angle) {
        return null;
    }

}
