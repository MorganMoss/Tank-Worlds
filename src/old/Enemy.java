package za.co.wethinkcode.robotworlds.client.old;

import java.awt.*;

public class Enemy extends Thread{
    private String name = "Enemy1";
    public String getTankName(){return this.name;}

    private String sprite = "etank";
    public String getSpriteName(){return this.sprite;}

    private String deadImage = "deademoji.png";
    public String getDeadImage(){return this.deadImage;}

    private int enemyHealth = 35;
    public int getTankHealth(){return this.enemyHealth;}
    public void setTankHealth(int health){this.enemyHealth=health;}

    private Direction enemyTankDirection = Direction.Up;
    public Direction getTankDirection(){return this.enemyTankDirection;}
    public void setTankDirection(Direction direction){this.enemyTankDirection =direction;}
    public void takeHit(int hit){this.enemyHealth-=hit;}


    @Override
    public void run(){

    }
    private void enemySpawn(Graphics g){

    }
}
