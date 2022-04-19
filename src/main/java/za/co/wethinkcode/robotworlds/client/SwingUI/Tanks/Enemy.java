package za.co.wethinkcode.robotworlds.client.SwingUI.Tanks;


import za.co.wethinkcode.robotworlds.client.SwingUI.HelperMethods;

import java.awt.*;

public class Enemy extends Tank {

    private String name = "Enemy1";
    private String sprite = "etank";
    private String deadImage = "deademoji.png";
    private Direction direction = Direction.Up;
    private int health = 5;

    private int x=100;
    private int y=200;

    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public void moveLeft(){this.direction = this.direction.getLeft();}
    public void moveRight(){this.direction = this.direction.getRight();}
    public void moveForward(){
        if(this.direction == Direction.Up){
            y -= 5;
        }else if (this.direction == Direction.Right){
            x +=5;
        }else if (this.direction == Direction.Left){
            x -=5;
        }else if (this.direction == Direction.Down){
            y +=5;
        }
    }

    public void moveBack(){
        if(this.direction == Direction.Up){
            y += 5;
        }else if (this.direction == Direction.Right){
            x -=5;
        }else if (this.direction == Direction.Left){
            x +=5;
        }else if (this.direction == Direction.Down){
            y -=5;
        }
    }
    //getters
    public String getTankName(){return this.name;}
    public String getSpriteName(){return this.sprite;}
    public String getDeadImage(){return this.deadImage;}
    public int getTankHealth(){return this.health;}

    //setters
    public void setName(String enemyName) {this.name = enemyName;}
    public void setTankHealth(int health){this.health =health;}
    public Direction getTankDirection(){return this.direction;}
    public void setTankDirection(Direction direction){this.direction =direction;}
    public void takeHit(){this.health --;}
    public void repair(){this.health=5;}

    //draw tank on swing graphics
    public void spawn(Graphics g){
        g.drawString(name, this.x, this.y-20);
        g.setColor(Color.RED);
        g.fillRect(this.x, this.y-10, this.getTankHealth()*7, 10);
        boolean enemyDead = this.getTankHealth()==0;
        if(enemyDead){g.drawImage(HelperMethods.getImage(this.getDeadImage()), this.x, this.y, null);
        }else{
            g.drawImage(this.getTankDirection().getImage(this.getSpriteName()),
                     this.x, this.y, null);}
    }
}
