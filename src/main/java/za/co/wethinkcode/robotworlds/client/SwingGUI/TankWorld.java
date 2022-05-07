package za.co.wethinkcode.robotworlds.client.SwingGUI;

import za.co.wethinkcode.robotworlds.client.Client;
import za.co.wethinkcode.robotworlds.client.GUI;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Map.BasicMap;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.*;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles.Projectile;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.*;
import za.co.wethinkcode.robotworlds.exceptions.NoNewInput;
import za.co.wethinkcode.robotworlds.protocol.*;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.Color;
import java.util.List;

public class TankWorld extends JComponent implements GUI {
    private static final int WIDTH = 600, HEIGHT = 600;
    private static final int REPAINT_INTERVAL = 50;
    private static Boolean launched = false;
    private static ArrayList<Enemy> enemyList = new ArrayList<>();
    private static List<Obstacle> obstacleList = BasicMap.getObstacles();
    private static ArrayList<Projectile> projectileList = new ArrayList<>();
    private static Request queue1 = new Request("Robot","launch");
    private static LinkedList<Request> lastRequest = new LinkedList<>();

    //shows state HUD/ExplosionAnimation on repaint if set to true
    private boolean showState = false;
    private boolean showFireAnimation = false;

    //animation control integers
    private int explodeCount = 1;
    private int explosionX;
    private int explosionY;

    private final String clientName;
    private String robotType;
    public static int getScreenWidth(){return WIDTH;}

    private Response currentResponse;

    //FIFO stack for requests
    private static Request request = new Request("Robot","idle");
    public static int getScreenHeight(){return HEIGHT;}
    public String getClientName() {return this.clientName;}
    public String getRobotType(){return this.robotType;}
    public void setEnemyName(String enemyName) {enemy1.setName(enemyName);}
    public static void addProjectile(Projectile projectile){projectileList.add(projectile);}

    public TankWorld()  {
        try (Scanner scanner = new Scanner(System.in);) {
            System.out.print("Enter Tank name : ");
            this.clientName = scanner.nextLine();
            System.out.print("Enter the type of tank : ");
            this.robotType = scanner.nextLine();
        }

        JFrame frame = setupGUI();
        frame.add(this);
        setFocusable(true);
        start();


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

                        player.moveForward();
                        if(!collision(player)){
                            request = new Request(clientName,"forward");
                            lastRequest.add(request);}
                        if(collision(player)){
                            player.moveBack();
                        }
                        break;

                    case KeyEvent.VK_DOWN:

                        System.out.println("request: "+request.serialize());
                        player.moveBack();
                        if(!collision(player)){
                            request = new Request(clientName,"back");
                            lastRequest.add(request);
                        }
                        if(collision(player)){
                            player.moveForward();
                        }
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
                        request = new Request(clientName,"launch", Collections.singletonList(robotType));
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
    }

    public static JFrame setupGUI() {
        HelperMethods.setTheme();
        JFrame frame = new JFrame("T A N K W O R L D S");
        frame.setIconImage(HelperMethods.getImage("icon.png"));
//        JLabel background = new JLabel(new ImageIcon(HelperMethods.getImage("/icon.png")));
        frame.setSize(TankWorld.getScreenWidth(), TankWorld.getScreenHeight());
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        return frame;
    }

    //Sends user input to Server as request objects
    @Override
    public Request getInput() throws NoNewInput {
        if (currentResponse!=null){
            runServerCorrections(currentResponse);
            currentResponse = null;
        }

        if (lastRequest.getLast() != queue1){
            Request request = lastRequest.removeLast();
            System.out.println("input from gui:\n"+request.serialize());
            return request;
        }else{
            throw new NoNewInput();
        }
    }

    // PaintComponent is our real showOutput
    @Override
    public void showOutput(Response response) {
        currentResponse = response;
    }

    //TODO: SPAWN PLAYERS AND ENEMIES DYNAMICALLY FROM SERVER
    Player player = new Player("sniper","Morgan");
    Enemy enemy1 = new Enemy();
    Enemy enemy2 = new Enemy();

    /*Swing component that paints onto the window*/
    @Override
    protected void paintComponent(Graphics g) {
        //Draw desert Sand
        System.setProperty("myColor", "0XCE8540");
        g.setColor(new Color(205,133, 63));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // draw obstacles *TODO find better design and implement map object
        g.setColor(Color.GRAY);

        for (Obstacle obstacle:obstacleList){
            obstacle.draw(g);
        }

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

            g.setColor(Color.RED);
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
     * REFACTORED TO CHECK FOR COLLIDING WorldObjects
     * */
    public static boolean intersects(WorldObject object1, WorldObject object2){
        Rectangle playerRect = new Rectangle(object1.getX(),object1.getY(),object1.getSize(),object1.getSize());
        Rectangle enemyRect = new Rectangle(object2.getX(),object2.getY(),object2.getSize(),object2.getSize());
        return playerRect.intersects(enemyRect);
    }

    public static boolean collision(Tank player){
        for (Enemy enemy:enemyList){
            if (intersects(player,enemy)){
                return true;
                }
            }
        for (Obstacle obstacle:obstacleList){
            if (intersects(player,obstacle)){
                return true;
            }
        }
        return false;
    }





    //TODO: find a way to animate explosion
    public boolean fireAnimation(Graphics g, Tank tank,ArrayList<Enemy> enemyList, int explodeCount){

        boolean bullet = false;

                g.drawImage(HelperMethods.getImage(explodeCount + ".gif"), explosionX, explosionY, null);
                g.drawImage(HelperMethods.getImage(explodeCount + ".gif"), explosionX, explosionY, null);

        return bullet;
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

        player.setX(serverPlayer.getPosition().getX()+300);
        player.setY(-serverPlayer.getPosition().getY()+300);
        player.setName(serverPlayer.getRobotName());
//        player.setAmmo(serverPlayer.getCurrentAmmo());
//        player.setTankHealth(serverPlayer.getCurrentShield());
//        player.setRange(serverPlayer.getRange());
//        player.setSprite(serverPlayer.getClass().getName());
//        player.setKills(serverPlayer.getKills());
//        player.setDeaths(serverPlayer.getDeaths());
//        player.setTankDirection(serverPlayer.getDirection());


        int i=0;
        for(Robot serverEnemy:enemies.values()){
                enemyList.get(i).setX(serverEnemy.getPosition().getX()+300);
                enemyList.get(i).setY(-serverEnemy.getPosition().getY()+300);
                enemyList.get(i).setName(serverEnemy.getRobotName());
                enemyList.get(i).setAmmo(serverEnemy.getCurrentAmmo());
                enemyList.get(i).setTankHealth(serverEnemy.getCurrentShield());
                enemyList.get(i).setRange(serverEnemy.getRange());
//                enemyList.get(i).setSprite(serverEnemy.getClass().getName());
                enemyList.get(i).setKills(serverEnemy.getKills());
                enemyList.get(i).setDeaths(serverEnemy.getDeaths());
//                enemyList.get(i).setTankDirection(serverEnemy.getDirection());
                if(serverEnemy.isFiring()){
                    enemyList.get(i).fire();
                }
                switch(serverEnemy.getDirection()){
                    case NORTH:
                        enemyList.get(i).setTankDirection(Direction.Up);
                        break;
                    case EAST:
                        enemyList.get(i).setTankDirection(Direction.Right);
                        break;
                    case SOUTH:
                        enemyList.get(i).setTankDirection(Direction.Down);
                        break;
                    case WEST:
                        enemyList.get(i).setTankDirection(Direction.Left);
                        break;
                }
                i++;
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


