package za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks;

import za.co.wethinkcode.robotworlds.client.SwingGUI.WorldObject;

public abstract class Tank implements WorldObject {
    private String name;
    private String sprite;
    private Direction direction=Direction.Up;
    private int health;
    private int maxHealth;
    private int x;
    private int y;
    private int absoluteX;
    private int absoluteY;
    private int range;
    private int ammo;
    private int maxAmmo;
    private int kills=0;
    int size;


    //getters
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int getAmmo(){return this.ammo;}
    public String getTankName(){return this.name;}
    public String getSpriteName(){return this.sprite;}
    public int getTankHealth(){return this.health;}
    public Direction getDirection(){return this.direction;}
    public int getAbsoluteX() {return absoluteX;}
    public int getAbsoluteY() {return absoluteY;}
    public int getSize() {
        return this.size;
    }
    public int getRange() {
        return range;
    }
    public int getKills() {return kills;}
    public int getMaxAmmo() {
        return maxAmmo;
    }
    public int getMaxHealth() {
        return maxHealth;
    }


    //Position setters
    public void setName(String name) {this.name=name;}
    public void setX(int x){this.x=x;}
    public void setY(int y){this.y=y;}
    public void setAbsoluteX(int x){this.absoluteX=x;}
    public void setAbsoluteY(int y){this.absoluteY=y;}
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    //State setters
    public void setTankHealth(int health){this.health =health;}
    public void setTankDirection(Direction direction){this.direction =direction;}
    public void takeHit(){this.health--;}
    public void reload(){this.ammo = maxAmmo;}
    public void repair(){this.health=5;}
    public void setAmmo(int ammo){this.ammo=ammo;}
    public void setMaxAmmo(int ammo) {this.maxAmmo = ammo;}
    public void decreaseAmmo() {this.ammo--;}
    public void setRange(int range){this.range=range;}
    public void setSprite(String sprite){this.sprite=sprite;}
    public void setKills(int kills){this.kills=kills;}
    public void setSize(int size) {this.size=size;}
    //Movement
    public void turnLeft(){setTankDirection(this.getDirection().getLeft());}
    public void turnRight(){setTankDirection(this.getDirection().getRight());}
}
