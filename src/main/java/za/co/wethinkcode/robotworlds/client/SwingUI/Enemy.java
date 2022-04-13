package za.co.wethinkcode.robotworlds.client.SwingUI;

import java.awt.*;

public class Enemy extends Thread {

    private String name = "Enemy1";
    private String sprite = "etank";
    private String deadImage = "deademoji.png";
    private Direction direction = Direction.Up;
    private int health = 35;


    public String getTankName(){return this.name;}
    public String getSpriteName(){return this.sprite;}
    public String getDeadImage(){return this.deadImage;}

    public int getTankHealth(){return this.health;}
    public void setTankHealth(int health){this.health =health;}
    public Direction getTankDirection(){return this.direction;}
    public void setTankDirection(Direction direction){this.direction =direction;}
    public void takeHit(int hit){this.health -=hit;}


    @Override
    public void run(){

    }
    private void enemySpawn(Graphics g){

    }
}
