package za.co.wethinkcode.robotworlds.client.SwingUI.Tanks;

public abstract class Tank {
    private String name;
    private String sprite;
    private String deadImage;
    private Direction direction;
    private int health;
    private int x;
    private int y;
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public void moveLeft(){this.x--;}
    public void moveRight(){this.x++;}
    public void moveUp(){this.y--;}
    public void moveDown(){this.y++;}

    public String getTankName(){return this.name;}
    public String getSpriteName(){return this.sprite;}
    public String getDeadImage(){return this.deadImage;}

    public int getTankHealth(){return this.health;}
    public void setTankHealth(int health){this.health =health;}
    public Direction getTankDirection(){return this.direction;}
    public void setTankDirection(Direction direction){this.direction =direction;}
    public void takeHit(){this.health --;}
    public void repair(){this.health=5;}
}
