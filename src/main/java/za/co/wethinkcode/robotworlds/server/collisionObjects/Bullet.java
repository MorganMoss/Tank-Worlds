package za.co.wethinkcode.robotworlds.server.collisionObjects;


import za.co.wethinkcode.robotworlds.server.Direction;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import java.awt.*;

public class Bullet implements CollisionObject {

    private Robot robot;
    private Position position;
    private final Direction direction;

    public Bullet(Robot robot) {
        this.robot = robot;
        this.position = robot.getPosition();
        this.direction = robot.getDirection();
    }

    public void discharge() {}

    public Position getPosition() {
        return this.position;
    }

    @Override
    public int getX() {
        return position.getX();
    }

    @Override
    public int getY() {
        return position.getY();
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void draw(Graphics g) {}

    public void project(){
        switch (direction){
            case NORTH:
                this.position = new Position(this.getX(),this.getY()+5);
                break;
            case SOUTH:
                this.position = new Position(this.getX(),this.getY()-5);
                break;
            case WEST:
                this.position = new Position(this.getX()-5,this.getY());
                break;
            case EAST:
                this.position = new Position(this.getX()+5,this.getY());
        }
    }
}
