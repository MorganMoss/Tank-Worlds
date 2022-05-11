package za.co.wethinkcode.robotworlds.shared;

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
}
