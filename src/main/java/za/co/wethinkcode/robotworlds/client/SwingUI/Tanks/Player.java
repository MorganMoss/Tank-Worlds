package za.co.wethinkcode.robotworlds.client.SwingUI.Tanks;

import za.co.wethinkcode.robotworlds.client.SwingUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingUI.Projectiles.Shell;

import java.awt.*;

public class Player extends Tank {

    private String name;
    private String sprite;
    private Direction direction = Direction.Up;
    private int health;
    private final int startAmmo;
    private int ammo;
    private int kills = 0;
    private int deaths = 0;
    private int range;
    private final int size;

    //TODO: SERVER GET ENEMY COUNT
    private int enemiesLeft = 1;
    private int x=100;
    private int y=200;

    public Player(String tankType,String name){

        switch (tankType){
            case "sniper":
                this.sprite="sniper";
                this.name = name;
                this.health = 3;
                this.ammo = 5;
                this.startAmmo = 5;
                this.range=125;
                this.size = 40;

                break;
            case "machine":
                this.sprite="machine";
                this.name = name;
                this.health = 1;
                this.ammo = 20;
                this.startAmmo = 20;
                this.range=50;
                this.size = 25;
                break;
            case "bomber":
                this.sprite="bomber";
                this.name = name;
                this.health = 2;
                this.ammo = 3;
                this.startAmmo = 3;
                this.size = 50;
                break;
            default:
                this.sprite="tank";
                this.name = name;
                this.health = 2;
                this.ammo = 10;
                this.startAmmo = 10;
                this.range=75;
                this.size = 25;
        }

    }

    //SETTERS
    @Override
    public void addX(){this.x+=5;}
    @Override
    public void minusX(){this.x-=5;}
    @Override
    public void addY(){this.y+=5;}
    @Override
    public void minusY(){this.y-=5;}
    @Override
    public void setTankDirection(Direction direction){this.direction =direction;}
    @Override
    public void setRange(int range){this.range=range;}
    @Override
    public void setSprite(String sprite){this.sprite=sprite;}
    @Override
    public void setAmmo(int ammo){this.ammo=ammo;}
    public void setName(String name){this.name=name;}
    public void takeHit(){this.health--;}
    public void repair(){this.health=5;}
    public void reload(){this.ammo=startAmmo;}
    @Override
    public void addKill(){this.kills++;}
    @Override
    public void addDeaths(){this.deaths++;}
    @Override
    public void fire() {
        Shell newBullet = new Shell();
        newBullet.discharge(this);
        this.ammo--;
    }
    @Override
    public void setDeaths(int deaths){this.deaths=deaths;}


    //GETTERS
    public int getAmmo(){return this.ammo;}
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    @Override
    public int getRange(){return this.range;}
    public String getTankName(){return this.name;}
    public String getSpriteName(){return this.sprite;}
    public String getDeadImage(){
        return "deademoji.png";}
    public int getTankHealth(){return this.health;}
    public Direction getDirection(){return this.direction;}
    @Override
    public int getSize(){return this.size;}

    //Display
    public void draw(Graphics g) {
        //name
        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString(name, this.x, this.y-20);

        //health bar
        g.setColor(Color.RED);
        g.fillRect(this.x, this.y-10, health*7, 10);

        //position
        g.setColor(Color.GREEN);
        g.drawRect(this.x-(range-1), this.y-(range-1), range*2, range*2);

        boolean playerDead = health==0;
        Image tankImage = this.getDirection().getImage(this.getSpriteName());

        if (playerDead) {
            g.drawImage(HelperMethods.getImage(this.getDeadImage()),
                    this.x, this.y, null);
        }
        else{
            g.drawImage(tankImage, this.x, this.y, null);
        }
    }

    public void showState(Graphics g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString("Missiles: " + ammo, 10, 50);
        g.drawString("Health: " + health, 10, 70);
        g.drawString("Enemies Left: " + enemiesLeft, 10, 90);
        g.drawString("Kills: " + kills, 10, 110);
        g.drawString("Deaths: " + deaths, 10, 130);
//        double kdRatio = kills/deaths;
//        g.drawString("K/D: " + kdRatio, 10, 150);

    }

}
