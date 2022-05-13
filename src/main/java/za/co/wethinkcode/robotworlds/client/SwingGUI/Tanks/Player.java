package za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks;

import za.co.wethinkcode.robotworlds.client.SwingGUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles.Shell;
import za.co.wethinkcode.robotworlds.client.SwingGUI.SoundPlayer;
import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Tank {
    private int oldSize = 0;
    private final ArrayList<Image> tankImages = new ArrayList<>();
    private Image deadImage;

    public Player(String tankType,String name){

        setName(name);
        setX(-10);
        setY(-10);
        switch (tankType.toLowerCase()){
            case "sniper":
                setSprite("sniper");
                break;
            case "machine":
                setSprite("machine");
                break;
            case "bomber":
                setSprite("bomber");
                break;
            default:
                setSprite("tank");
        }
    }

    @Override
    public void setSize(int size) {
        if (oldSize != size){
            oldSize = this.size;
            super.setSize(size);
            MediaTracker tracker = new MediaTracker(new java.awt.Container());

            deadImage = HelperMethods.getImage("deademoji.png").getScaledInstance(size,size, Image.SCALE_FAST);
            tracker.addImage(deadImage,0);

            Image tankImageUp = Direction.Up.getImage(getSpriteName()).getScaledInstance(size,size, Image.SCALE_FAST);
            tracker.addImage(tankImageUp,1);
            Image tankImageDown = Direction.Down.getImage(getSpriteName()).getScaledInstance(size,size, Image.SCALE_FAST);
            tracker.addImage(tankImageDown,2);
            Image tankImageLeft = Direction.Left.getImage(getSpriteName()).getScaledInstance(size,size, Image.SCALE_FAST);
            tracker.addImage(tankImageLeft,3);
            Image tankImageRight = Direction.Right.getImage(getSpriteName()).getScaledInstance(size,size, Image.SCALE_FAST);
            tracker.addImage(tankImageRight,4);

            tankImages.clear();

            tankImages.add(tankImageUp);
            tankImages.add(tankImageDown);
            tankImages.add(tankImageLeft);
            tankImages.add(tankImageRight);
            // wait for image to be ready
            try {
                tracker.waitForAll();
            } catch (InterruptedException ex) {
                throw new RuntimeException("Image loading interrupted", ex);
            }
        }


    }

    public void fire() {
        SoundPlayer audio = new SoundPlayer("assets/audios/shoot.wav");

        try {
            audio.play();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Shell newBullet = new Shell();
        newBullet.discharge(this);
        decreaseAmmo();
    }

    //Display
    public void draw(Graphics g) {
        //name
        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString(getTankName(), getX(), getY()-20);
        //health bar
        g.setColor(Color.RED);
        g.fillRect(getX(), getY() - 10, getTankHealth() * 7, 10);

        if (TankWorld.getShowBoundaries()) {
            //Range
            g.setColor(Color.GREEN);
            g.drawRect(getX() - (getRange() - 1)+size/2, getY() - (getRange() - 1)+size/2, getRange() * 2, getRange() * 2);
            g.setColor(Color.RED);
            g.drawRect(getX(), getY(), getSize(), getSize());
        }

        boolean playerDead = getTankHealth()==0;

        int index=0;
        switch (getDirection()){
            case Left:
                index = 2;
                break;
            case Right:
                index = 3;
                break;
            case Down:
                index = 1;
                break;
        }

        if (!playerDead) {
            g.drawImage(tankImages.get(index), getX(), getY(), null);
        } else {
            g.drawImage(deadImage, getX(), getY(), null);
        }
    }

    public void showState(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString("Missiles: " + getAmmo()+"/"+getMaxAmmo(), 10, 150);
        g.drawString("Health: " + getTankHealth()+"/"+getMaxHealth(), 10, 170);
        g.drawString("Kills: " + TankWorld.getKills(), 10, 210);
        g.drawString("Deaths: " + TankWorld.getDeaths(), 10, 230);
    }
}
