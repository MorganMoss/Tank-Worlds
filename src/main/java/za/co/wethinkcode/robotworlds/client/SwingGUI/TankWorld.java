package za.co.wethinkcode.robotworlds.client.SwingGUI;
import za.co.wethinkcode.robotworlds.client.Client;
import za.co.wethinkcode.robotworlds.client.GUI;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Brick;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Pit;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles.Projectile;

import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.*;
import za.co.wethinkcode.robotworlds.exceptions.NoNewInput;
import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.awt.Color;

public class TankWorld extends JComponent implements GUI {
    private static final int WIDTH = 600, HEIGHT = 600;
    String clientName = this.getClientName();
    private static final int REPAINT_INTERVAL = 50;
    private static Boolean launched = false;

    //shows state HUD/ExplosionAnimation on repaint if set to true
    private boolean showState = false;
    private boolean showFireAnimation = false;

    //animation control integers
    int explodeCount = 1;
    int explosionX;
    int explosionY;

    //World objects lists
    private static ArrayList<Enemy> enemyList = new ArrayList<>();
    private static ArrayList<Brick> obstacleList = new ArrayList<>();
    private static ArrayList<Projectile> projectileList = new ArrayList<>();

    //Adds projectile to world
    public static void addProjectile(Projectile projectile){
        projectileList.add(projectile);
    }

    //getters
    public static int getScreenWidth(){return WIDTH;}
    public static int getScreenHeight(){return HEIGHT;}

    public String getClientName() {
        return Client.getMyClientName();
    }
    //setters
    public void setEnemyName(String enemyName) {
        enemy1.setName(enemyName);
    }

    //FIFO stack for requests
    private static Request request = new Request("Robot","idle");
    static Request queue1 = new Request("Robot","launch");
    private static LinkedList<Request> lastRequest = new LinkedList<Request>();

    //Sends user input to Server as request objects
    @Override
    public Request getInput() throws NoNewInput {
        if (lastRequest.getLast() != queue1){
            return lastRequest.removeLast();
        }else{
            throw new NoNewInput();}
    }

    // PaintComponent is our real showOutput
    @Override
    public void showOutput(Response response) {
        System.out.println(response);
    }

    //TODO: SPAWN PLAYERS AND ENEMIES DYNAMICALLY FROM SERVER
    Player player = new Player("sniper","Morgan");
    Enemy enemy1 = new Enemy();
    Enemy enemy2 = new Enemy();
    Pit onePit = new Pit(new Position(400,300));


    public TankWorld()  {

        player.setName(clientName);
        //Add first element of request stack
        lastRequest.add(queue1);
        //TODO: ADD ENEMIES FROM SERVER REQUEST
        if (!enemyList.contains(enemy1)){
            enemyList.add(enemy1);
        }
        if (!enemyList.contains(enemy2)){
            enemyList.add(enemy2);}

        // KEY LISTENER FOR USER INPUT
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                showState=false;
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_UP:
                        System.out.println(player.getY());
                        request = new Request(clientName,"forward");
                        lastRequest.add(request);
                        if(notCollision(player,"forward")){
                            player.moveForward();
                        }else player.moveBack();
                        break;

                    case KeyEvent.VK_DOWN:
                        request = new Request(clientName,"back");
                        lastRequest.add(request);
                        System.out.println("request: "+request.serialize());

                        if(notCollision(player,"back")){
                            player.moveBack();
                        }else player.moveForward();
                        break;

                    case KeyEvent.VK_LEFT:
                        request = new Request(clientName,"left");
                        lastRequest.add(request);
                        player.turnLeft();
                        break;

                    case KeyEvent.VK_RIGHT:
                        request = new Request(clientName,"right");
                        lastRequest.add(request);

                        player.turnRight();
                        break;

                    case KeyEvent.VK_SPACE:
                        request = new Request(clientName,"fire");
                        lastRequest.add(request);
//                        client.HelperMethods.playAudio("shoot.wav");
                        if (player.getAmmo()!=0){
                            player.fire();
                        }
                        break;

                    case KeyEvent.VK_R:
                        request = new Request(clientName,"reload");
                        lastRequest.add(request);
                        player.reload();
                        break;

                    case KeyEvent.VK_M:
//                        client.HelperMethods.playAudio(Tools.nextBoolean() ? "supershoot.wav" : "supershoot.aiff");
                        request = new Request(clientName,"repair");
                        lastRequest.add(request);
                        player.repair();
                        break;

                    case KeyEvent.VK_S:
                        request = new Request(clientName,"state");
                        showState=true;
                        break;

                    case KeyEvent.VK_L:
                        request = new Request(clientName,"launch");
                        lastRequest.add(request);
                        launched=true;
                        break;

                    case KeyEvent.VK_ESCAPE:
                        request = new Request(clientName,"quit");
                        lastRequest.add(request);
                        System.exit(0);
                        break;
                }
            }
        });
        //TODO: CONNECT WITH UPDATED SERVER
//        runServerCorrections(Client.getResponse());
    }

    /*Swing component that paints onto the window*/
    @Override
    protected void paintComponent(Graphics g) {
        //Draw desert Sand
        System.setProperty("myColor", "0XCE8540");
        g.setColor(new Color(205,133, 63));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // draw obstacles *TODO find better design and implement map object
        g.setColor(Color.GRAY);

        for(int i=0;i<600;i+=25){
            Brick xBrick = new Brick(new Position(i,0));
            xBrick.draw(g);
            obstacleList.add(xBrick);
        }
        for(int i=0;i<400;i+=25){
            Brick yBrick = new Brick(new Position(300,i));
            yBrick.draw(g);
            obstacleList.add(yBrick);
        }
        Brick worldBrick = new Brick(new Position(100,200));
        worldBrick.draw(g);
        Brick worldBrick2 = new Brick(new Position(450,400));
        worldBrick2.draw(g);

        //TODO: JScrollPane()

        // DRAW crater
        onePit.draw(g);
        // draw HUD *TO DO: Hide and only show on state command
        if(showState){
            player.showState(g);
        }

        //spawn projectiles check collisions
        if(projectileList.size()!=0){
            int i=0;
            boolean hit = false;
            for (Projectile projectile : projectileList){
                i++;
                projectile.project(projectile.getDirection());
                projectile.draw(g);
                for (Enemy enemy:enemyList){
                    if(projectile.isHitting(enemy)){
                        explosionX = enemy.getX();
                        explosionY = enemy.getY();
                        showFireAnimation = true;
                        enemy.takeHit();
                        player.addKill();
                        hit=true;
                    }

                }

                for (Obstacle obstacle : obstacleList){
                    if(projectile.isHitting(obstacle)){
                        explosionX = obstacle.getX();
                        explosionY = obstacle.getY();
                        showFireAnimation = true;
                        hit=true;
                    }
                }
                if(projectile.reachedRange(projectile.getDirection())){
                    hit=true;
                    explosionX = projectile.getX();
                    explosionY = projectile.getY();
                    showFireAnimation = true;                }

                //TODO: IMPLEMENT RANGE CHECK
//                if(projectile.reachedRange(projectile.getDirection())){
//                    hit=true;
//                }
            }
            //REMOVE PROJECTILE FROM WORLD
            if(hit){
                projectileList.remove(i-1);
            }
        }

        // spawn player tank TODO: implement designs for other tanks
        if(launched){
            player.draw(g);

            // spawn enemy tank
            enemy1.draw(g);

            enemy2.draw(g);

            //TODO: REMOVE DEBUG RECTANGLES
            int playerX = player.getX();
            int playerY = player.getY();
            int width=0;
            int height=0;

            switch (player.getDirection()){
                case Right:
                    width+=player.getRange();
                    height+=1;
                    break;
                case Left:
                    width+=player.getRange();
                    playerX-=player.getRange();
                    height+=1;
                    break;
                case Up:
                    width+=1;
                    playerY-=player.getRange();
                    height+=player.getRange();
                    break;
                case Down:
                    width+=1;
                    height+=player.getRange();
                    break;
            }

            g.setColor(Color.RED);
            g.drawRect(playerX,playerY,width,height);
            g.drawRect(enemy1.getX(),enemy1.getY(),40,40);
            g.drawRect(enemy2.getX(),enemy2.getY(),40,40);

            //Explode fire command animation
            if (showFireAnimation){
                fireAnimation(g,player,enemyList,explodeCount);
            }
            explodeCount++;
            if (explodeCount==10){
            showFireAnimation =false;
            explodeCount=1;
            }

        }else {
            g.setColor(Color.red);
            g.drawString("PRESS >L< BUTTON TO LAUNCH",200,200);
        }
    }


    /**
     * Checks path & position blocked for a range by geo-blocking their positions
     * using rectangles
     *
     * REFACTORED TO CHECK FOR COLIDING WorldObjects
     * */
    public static boolean intersects(WorldObject object1, WorldObject object2){
        Rectangle playerRect = new Rectangle(object1.getX(),object1.getY(),object1.getSize(),object1.getSize());
        Rectangle enemyRect = new Rectangle(object2.getX(),object2.getY(),object2.getSize(),object2.getSize());
        return playerRect.intersects(enemyRect);

    }

    public static boolean notCollision(Tank player, String command){
        for (Enemy enemy:enemyList){
            if (intersects(player,enemy)){
                return false;
                }
            }
        for (Obstacle obstacle:obstacleList){
            if (intersects(player,obstacle)){
                return false;
            }
        }
        return true;
    }





    //TODO: find a way to animate explosion
    public boolean fireAnimation(Graphics g, Tank tank,ArrayList<Enemy> enemyList, int explodeCount){

        boolean bullet = false;

                g.drawImage(HelperMethods.getImage(explodeCount + ".gif"), explosionX, explosionY, null);
                g.drawImage(HelperMethods.getImage(explodeCount + ".gif"), explosionX, explosionY, null);

        return bullet;
    }

    //TODO: IMPLEMENT WALL AND OBSTACLE BLOCKING
    //Is Position Blocked
    private boolean isPositionBlocked(Player player, Enemy enemy){
    return true;
    }

    //TODO: CONNECT WITH RUNSERVERCORRECTIONS
    //Handles enemy movement using input from server
    public void enemyMovement(String command) {
        if (Objects.equals(command, "forward")){
            enemy1.moveForward();
        }else if (Objects.equals(command, "back")){
            enemy1.moveBack();
        }else if (Objects.equals(command, "left")){
            enemy1.turnLeft();
        }else if (Objects.equals(command, "right")){
            enemy1.turnRight();
        }
    }

    //TODO: BUILD INTERFACE () TO CONVERT SERVER OBJECTS
    public void runServerCorrections(Response response){
        HashMap<String, Robot> enemies = response.getEnemyRobots();
        Robot serverPlayer = response.getRobot();

        player.setX(serverPlayer.getPosition().getX());
        player.setY(serverPlayer.getPosition().getY());
        player.setName(serverPlayer.getRobotName());
        player.setAmmo(serverPlayer.getCurrentAmmo());
        player.setTankHealth(serverPlayer.getCurrentShield());
        player.setRange(serverPlayer.getRange());
        player.setSprite(serverPlayer.getClass().getName());
        player.setKills(serverPlayer.getKills());
        player.setDeaths(serverPlayer.getDeaths());
//        player.setTankDirection(serverPlayer.getDirection());


        int i=0;
        for(Robot serverEnemy:enemies.values()){
                enemyList.get(i).setX(serverEnemy.getPosition().getX());
                enemyList.get(i).setY(serverEnemy.getPosition().getY());
                enemyList.get(i).setName(serverEnemy.getRobotName());
                enemyList.get(i).setAmmo(serverEnemy.getCurrentAmmo());
                enemyList.get(i).setTankHealth(serverEnemy.getCurrentShield());
                enemyList.get(i).setRange(serverEnemy.getRange());
                enemyList.get(i).setSprite(serverEnemy.getClass().getName());
                enemyList.get(i).setKills(serverEnemy.getKills());
                enemyList.get(i).setDeaths(serverEnemy.getDeaths());
//                enemyList.get(i).setTankDirection(serverEnemy.getDirection());
                if(serverEnemy.isFiring()){
                    enemyList.get(i).fire();
                }
            }
        }



    public void start() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                while (true) {
                    try {
                        repaint();
                        HelperMethods.sleepSilently(REPAINT_INTERVAL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

}

//TODO: MAKE INTO A FUNCTION TO RUN ON CLIENT

//    public static void run(String[] args) {
//        HelperMethods.setTheme();
//        JFrame frame = new JFrame("T A N K W O R L D S");
//        frame.setIconImage(HelperMethods.getImage("icon.png"));
////        JLabel background = new JLabel(new ImageIcon(HelperMethods.getImage("/icon.png")));
//        frame.setSize(WIDTH, HEIGHT);
//        frame.setLocation(400, 100);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setResizable(false);
//
//        TankWorld tankWar = new TankWorld();
//        frame.add(tankWar);
//        tankWar.setFocusable(true);
//        frame.setVisible(true);
//        tankWar.start();
//    }


