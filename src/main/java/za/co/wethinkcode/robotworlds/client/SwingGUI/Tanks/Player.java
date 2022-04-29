package za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks;

import za.co.wethinkcode.robotworlds.client.SwingGUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles.Shell;

import java.awt.*;

public class Player extends Tank {


    //TODO: SERVER GET ENEMY COUNT
    private int enemiesLeft = 1;
//    private int x=100;
//    private int y=200;

    public Player(String tankType,String name){

        switch (tankType){
            case "sniper":
                super.setSprite("sniper");
                super.setName(name);
                super.setTankHealth(3);
                super.setAmmo(5);
                super.setMaxAmmo(5);
                super.setRange(125);
                super.setSize(40);
                super.setX(100);
                super.setY(200);

                break;
            case "machine":
                super.setSprite("machine");
                super.setName(name);
                super.setTankHealth(1);
                super.setAmmo(20);
                super.setMaxAmmo(20);
                super.setRange(50);
                super.setSize(40);
                super.setX(100);
                super.setY(200);
                break;
            case "bomber":
                super.setSprite("bomber");
                super.setName(name);
                super.setTankHealth(2);
                super.setAmmo(5);
                super.setMaxAmmo(5);
                super.setRange(75);
                super.setSize(40);
                super.setX(100);
                super.setY(200);
                break;
            default:
                super.setSprite("tank");
                super.setName(name);
                super.setTankHealth(2);
                super.setAmmo(10);
                super.setMaxAmmo(10);
                super.setRange(75);
                super.setSize(40);
                super.setX(100);
                super.setY(200);
        }

    }

    public void fire() {
        Shell newBullet = new Shell();
        newBullet.discharge(this);
        super.decreaseAmmo();
    }


    public String getDeadImage(){
        return "deademoji.png";}


    //Display
    public void draw(Graphics g) {
        //name
        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString(super.getTankName(), super.getX(), super.getY()-20);

        //health bar
        g.setColor(Color.RED);
        g.fillRect(super.getX(), super.getY()-10, super.getTankHealth()*7, 10);

        //Range
        g.setColor(Color.GREEN);
        g.drawRect(super.getX()-(super.getRange()-1), super.getY()-(super.getRange()-1), super.getRange()*2, super.getRange()*2);
        g.setColor(Color.RED);
        g.drawRect(super.getX(), super.getY(), super.getSize(), super.getSize());

        boolean playerDead = super.getTankHealth()==0;
        Image tankImage = super.getDirection().getImage(super.getSpriteName());

        if (playerDead) {
            g.drawImage(HelperMethods.getImage("deademoji.png"),
                    super.getX(), super.getY(), null);
        }
        else{
            g.drawImage(tankImage, super.getX(), super.getY(), null);
        }
    }

    public void showState(Graphics g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString("Missiles: " + super.getAmmo(), 10, 50);
        g.drawString("Health: " + super.getTankHealth(), 10, 70);
        g.drawString("Enemies Left: " + enemiesLeft, 10, 90);
        g.drawString("Kills: " + super.getKills(), 10, 110);
        g.drawString("Deaths: " + super.getDeaths(), 10, 130);
//        double kdRatio = kills/deaths;
//        g.drawString("K/D: " + kdRatio, 10, 150);

    }
}
