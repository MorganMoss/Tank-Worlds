package za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks;

import za.co.wethinkcode.robotworlds.client.SwingGUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles.Shell;
import za.co.wethinkcode.robotworlds.client.SwingGUI.SoundPlayer;
import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.shared.protocols.Request;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Tank {
    private int oldSize = 0;

    private final ArrayList<Image> tankImages = new ArrayList<>();
    private Image deadImage;

    public Player(String tankType,String name){

        super.setName(name);
        super.setX(100);
        super.setY(200);
        switch (tankType.toLowerCase()){
            case "sniper":
                super.setSprite("sniper");
                break;
            case "machine":
                super.setSprite("machine");
                break;
            case "bomber":
                super.setSprite("bomber");
                break;
            default:
                super.setSprite("tank");
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

            Image tankImageUp = Direction.Up.getImage(super.getSpriteName()).getScaledInstance(size,size, Image.SCALE_FAST);
            tracker.addImage(tankImageUp,1);
            Image tankImageDown = Direction.Down.getImage(super.getSpriteName()).getScaledInstance(size,size, Image.SCALE_FAST);
            tracker.addImage(tankImageDown,2);
            Image tankImageLeft = Direction.Left.getImage(super.getSpriteName()).getScaledInstance(size,size, Image.SCALE_FAST);
            tracker.addImage(tankImageLeft,3);
            Image tankImageRight = Direction.Right.getImage(super.getSpriteName()).getScaledInstance(size,size, Image.SCALE_FAST);
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
        super.decreaseAmmo();
    }


    public String getDeadImage(){
        return "deademoji.png";
    }


    //Display
    public void draw(Graphics g) {
        //name
        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString(super.getTankName(), super.getX(), super.getY()-20);
        //health bar
        g.setColor(Color.RED);
        g.fillRect(super.getX(), super.getY() - 10, super.getTankHealth() * 7, 10);

        if (TankWorld.getShowBoundaries()) {
            //Range
            g.setColor(Color.GREEN);
            g.drawRect(super.getX() - (super.getRange() - 1)+size/2, super.getY() - (super.getRange() - 1)+size/2, super.getRange() * 2, super.getRange() * 2);
            g.setColor(Color.RED);
            g.drawRect(super.getX(), super.getY(), super.getSize(), super.getSize());
        }

        boolean playerDead = super.getTankHealth()==0;

        int index=0;
        switch (getDirection()){
            case Left:
                index = 2;
                break;
            case Up:
                index = 0;
                break;
            case Right:
                index = 3;
                break;
            case Down:
                index = 1;
                break;
        }

        if (!playerDead) {
            g.drawImage(tankImages.get(index), super.getX(), super.getY(), null);
        } else {
            g.drawImage(deadImage, super.getX(), super.getY(), null);
        }
    }

    public void showState(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString("Missiles: " + super.getAmmo(), 10, 150);
        g.drawString("Health: " + super.getTankHealth(), 10, 170);
        g.drawString("Kills: " + super.getKills(), 10, 210);
        g.drawString("Deaths: " + super.getDeaths(), 10, 230);
    }
}
