package za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks;

import za.co.wethinkcode.robotworlds.client.SwingGUI.HelperMethods;

import java.awt.*;

public class Player extends Tank {

    private String name = "Enemy1";
    private String sprite = "tank";
    private String deadImage = "deademoji.png";
    private Direction direction = Direction.Up;
    private int health = 5;
    private int ammo = 100;
    private int kills = 2;
    private int deaths = 1;

    //TODO: SERVER GET ENEMY COUNT
    private int enemiesLeft = 1;

    private int x=100;
    private int y=200;

    public int getX(){return this.x;}
    public int getY(){return this.y;}

    //GETTERS
    public String getTankName(){return this.name;}
    public String getSpriteName(){return this.sprite;}
    public String getDeadImage(){return this.deadImage;}
    public int getTankHealth(){return this.health;}
    public Direction getTankDirection(){return this.direction;}

    //SETTERS
    public void setName(String name){this.name=name;}
    public void setTankDirection(Direction direction){this.direction =direction;}
    public void takeHit(){this.health --;}
    public void decreaseAmmo(){this.ammo --;}
    public void repair(){this.health=5;}
    public void reload(){this.ammo=100;}
    public void addKill(){this.kills++;}
    public void addDeaths(){this.deaths++;}

    //draw tank on swing graphics
    public void spawn(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString(name, this.x, this.y-20);
        g.setColor(Color.RED);
        g.fillRect(this.x, this.y-10, this.getTankHealth()*7, 10);
        boolean playerDead = this.getTankHealth()==0;
        if(playerDead){g.drawImage(HelperMethods.getImage(this.getDeadImage()), this.x, this.y, null);
        }else{
            g.drawImage(this.getTankDirection().getImage(this.getSpriteName()),
                    this.x, this.y, null);}
    }

    public void showState(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString("Missiles: " + ammo, 10, 50);
        g.drawString("Health: " + health, 10, 70);
        g.drawString("Enemies Left: " + enemiesLeft, 10, 90);
        g.drawString("Kills: " + kills, 10, 110);
        g.drawString("Deaths: " + deaths, 10, 130);
        double kdRatio = kills/deaths;
        g.drawString("K/D: " + kdRatio, 10, 150);
    }

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

    //find a way to link explosion
    public boolean shoot(Graphics g){
        boolean bullet = false;
        int bulletY = y;
        int bulletX = x;
        if (direction == Direction.Up){
            g.drawImage(Direction.Up.getImage("missile"), bulletX+14, bulletY -8, null);
        }else if(direction == Direction.Left){
            g.drawImage(Direction.Left.getImage("missile"), bulletX-8, bulletY +14, null);
        }else if(direction == Direction.Down){
            g.drawImage(Direction.Down.getImage("missile"), bulletX+14, bulletY +8, null);
        }else if(direction == Direction.Right){
            g.drawImage(Direction.Right.getImage("missile"), bulletX+8, bulletY +14, null);
        }
        if (direction == Direction.Up){
            g.drawImage(HelperMethods.getImage(7 + ".gif"), x-12, y-100, null);
        }else if (direction==Direction.Left){
            g.drawImage(HelperMethods.getImage(7 + ".gif"), x-100, y-12, null);
        }else if (direction==Direction.Right){
            g.drawImage(HelperMethods.getImage(7 + ".gif"), x+100, y-12, null);
        }else if (direction == Direction.Down){
            g.drawImage(HelperMethods.getImage(7 + ".gif"), x-12, y+100, null);
        }
        while (bulletY!=0){
            bulletY=bulletY-1;
        }
        return bullet;
    }

}
