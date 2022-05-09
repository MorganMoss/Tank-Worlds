package za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks;


import za.co.wethinkcode.robotworlds.client.SwingGUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;

import java.awt.*;
import java.util.Random;

public class Enemy extends Tank {
    Random rand = new Random();

    private String name = "Enemy1";
    private String sprite = "etank";
    private String deadImage = "deademoji.png";
    private Direction direction = Direction.Up;
    private int health = 5;
    private int ammo = 5;
    private int range;
    private int deaths=0;
    int size=40;
    private int x=rand.nextInt(500);
    private int y=rand.nextInt(500);


    //getters
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public String getTankName(){return this.name;}
    public String getSpriteName(){return this.sprite;}
    public String getDeadImage(){return this.deadImage;}
    public int getTankHealth(){return this.health;}
    @Override
    public int getRange() {return this.range;}
    @Override
    public int getSize(){return this.size;};

    //setters
    @Override
    public void setX(int x){this.x=x;}
    @Override
    public void setY(int y){this.y=y;}
    public void addX(){x+=5;}

    public void fire() {ammo--;}
    public Direction getDirection(){return this.direction;}

    public void reload() {ammo=5;}
    public void minusX(){x-=5;}
    public void addY(){y+=5;}
    public void minusY(){y-=5;}
    public void setName(String enemyName) {this.name = enemyName;}
    public void setTankHealth(int health){this.health =health;}
    public void setTankDirection(Direction direction){this.direction =direction;}
    @Override
    public int getAmmo() {
        return this.ammo;
    }
    @Override
    public void setRange(int range){this.range=range;}
    @Override
    public void setSprite(String sprite){this.sprite=sprite;}
    @Override
    public void setDeaths(int deaths){this.deaths=deaths;}

    public void takeHit(){health-=1;}
    public void repair(){this.health=5;}

    public void turnLeft(){this.direction = this.direction.getLeft();}
    public void turnRight(){this.direction = this.direction.getRight();}

    //draw tank on swing graphics
    public void draw(Graphics g){
        g.drawString(name, this.x, this.y-20);
        if (TankWorld.getShowBoundaries()){
            g.setColor(Color.RED);
            g.fillRect(this.x, this.y-10, health*7, 10);
        }
        boolean enemyDead = health==0;

        if(enemyDead){g.drawImage(HelperMethods.getImage(this.getDeadImage()), this.x, this.y, null);
        }else{
            g.drawImage(this.getDirection().getImage(this.getSpriteName()),
                     this.x, this.y, null);}

        //respawn enemy
        if(health==-1){
            health+=6;
//            player.addKill();
        }
    }

    public void showState(Graphics g) {

    }
}
