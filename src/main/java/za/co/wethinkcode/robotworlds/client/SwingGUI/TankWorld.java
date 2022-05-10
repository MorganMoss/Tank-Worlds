package za.co.wethinkcode.robotworlds.client.SwingGUI;

import za.co.wethinkcode.robotworlds.client.GUI;
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
    private static final int WIDTH = 588, HEIGHT = 616;
    private static final int REPAINT_INTERVAL = 50;
    private static final ArrayList<Enemy> enemyList = new ArrayList<>();
    private static final List<Obstacle> obstacleList = new ArrayList<>();
    private static final ArrayList<Projectile> projectileList = new ArrayList<>();
    private static final LinkedList<Request> lastRequest = new LinkedList<>();

    private static Boolean launched = false;
    private static Boolean showBoundaries = false;

    //shows state HUD/ExplosionAnimation on repaint if set to true
    private boolean showState = false;
    private boolean showFireAnimation = false;
    //animation control integers
    private int explodeCount = 1;
    private int explosionX;
    private int explosionY;

    private Player player;
    private String clientName;
    private String robotType;
    //FIFO stack for requests
    private static Request request;
    private JFrame frame;

    private static int x_scale;
    private static int y_scale;


    public static int getScreenWidth(){return WIDTH;}
    public static int getScreenHeight(){return HEIGHT;}

    public static int getX_scale() {
        return x_scale;
    }
    public static int getY_scale() {
        return y_scale;
    }

    public static Boolean getShowBoundaries() {
        return showBoundaries;
    }

    public static void addProjectile(Projectile projectile){projectileList.add(projectile);}

    public TankWorld()  {
        frame = setupGUI();
        frame.add(this);
        frame.setVisible(true);
        setFocusable(true);
        start();

        // KEY LISTENER FOR USER INPUT
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                showState=false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (launched) {
                            request = new Request(clientName,"forward");
                            lastRequest.add(request);
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        if (launched) {
                            request = new Request(clientName,"back");
                        }
                        lastRequest.add(request);
                        break;

                    case KeyEvent.VK_LEFT:
                        if (launched) {
                            request = new Request(clientName,"left");
                            lastRequest.add(request);

                            player.turnLeft();
                        }
                        break;

                    case KeyEvent.VK_RIGHT:
                        if (launched) {
                            request = new Request(clientName,"right");
                            lastRequest.add(request);

                            player.turnRight();
                        }
                        break;

                    case KeyEvent.VK_SPACE:
                        if (launched) {
                            request = new Request(clientName, "fire");
                            lastRequest.add(request);

//                        client.HelperMethods.playAudio("shoot.wav");

                            if (player.getAmmo() != 0) {
                                player.fire();
                            }
                        }
                        break;

                    case KeyEvent.VK_R:
                        if (launched) {
                            request = new Request(clientName, "reload");
                            lastRequest.add(request);

                            player.reload();
                        }
                        break;

                    case KeyEvent.VK_M:
//                        client.HelperMethods.playAudio(Tools.nextBoolean() ? "supershoot.wav" : "supershoot.aiff");
                        if (launched) {
                            request = new Request(clientName, "repair");
                            lastRequest.add(request);

                            player.repair();
                        }
                        break;

                    case KeyEvent.VK_S:
                        if (launched) {
                            request = new Request(clientName,"state");
                            showState=true;
                        }
                        break;

                    case KeyEvent.VK_L:
                        if (!launched){
                            //TODO: Please put this in a pop up rather than the terminal.
                                Scanner scanner = new Scanner(System.in);
                                System.out.print("Enter Tank name : ");
                                clientName = scanner.nextLine();
                                Scanner scanner2 = new Scanner(System.in);
                                System.out.print("Enter the type of tank : ");
                                robotType = scanner2.nextLine();

//                                robotType = "sniper";

                                request = new Request(clientName,"launch", Collections.singletonList(robotType));
                                lastRequest.add(request);
                        }
                        break;

                    case KeyEvent.VK_ESCAPE:
                        if (launched) {
                            request = new Request(clientName,"quit");
                            lastRequest.add(request);
                        }
                        System.exit(0);
                        break;

                    case KeyEvent.VK_B:
                        if (launched){
                            showBoundaries = !showBoundaries;
                        }
                        break;
                }
            }
        });
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
        try {
            return lastRequest.removeLast();
        } catch (NoSuchElementException noInput){
            throw new NoNewInput();
        }
    }

    // PaintComponent is our real showOutput
    @Override
    public void showOutput(Response response) {
        if (!launched && response.getCommandResponse().equalsIgnoreCase("success")){
            player = new Player(robotType, clientName);
            launched = true;
        }

        runServerCorrections(response);
    }

    /*Swing component that paints onto the window*/
    @Override
    protected void paintComponent(Graphics g) {
        //Draw desert Sand
        System.setProperty("myColor", "0XCE8540");
        g.setColor(new Color(205,133, 63));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.GRAY);

        for (Obstacle obstacle:obstacleList){
            obstacle.draw(g);
        }

        for (Enemy enemy : enemyList){
            enemy.draw(g);
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
        if (launched){
            player.draw(g);
            g.setColor(Color.RED);
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


    //TODO: BUILD INTERFACE () TO CONVERT SERVER OBJECTS
    public void runServerCorrections(Response response) {
        HashMap<String, Robot> enemies = response.getEnemyRobots();

        player.setName(response.getRobot().getRobotName());

        HashMap<Integer, HashMap<Integer, String>> map = response.getMap();
        //Goes through all the positions you can see
        int y_size = map.get(0).size();
        int x_size = map.size();

        x_scale = getScreenWidth() / x_size;
        y_scale = getScreenHeight() / y_size;

        int y_offset = /*getScreenHeight()%y_scale/2+*/2;
        int x_offset = /*getScreenWidth()%x_scale/2*/0;

        frame.setSize((TankWorld.getScreenWidth()/x_scale)*x_scale, (TankWorld.getScreenHeight()/y_scale)*y_scale);

        enemyList.clear();
        obstacleList.clear();

        for (int y = 0; y < y_size; y++) {
            for (int x = 0; x < x_size; x++) {
                String valueAtPosition = map.get(x).get(y);
                switch (valueAtPosition.toLowerCase()) {
                    //obstacle
                    case "x":
                        obstacleList.add(new Brick(new Position((x+x_offset) * x_scale, getScreenHeight() - (y+y_offset) * y_scale)));
                        break;
                    //open
                    case " ":
                        break;
                    //robot
                    default:
                        //TODO: break up into separate methods
                        if (valueAtPosition.equalsIgnoreCase(player.getTankName())){
                            if (player.getX() != (x+x_offset) * x_scale || player.getY() != getScreenHeight() - (y+y_offset) * y_scale){
                                player.setX((x+x_offset) * x_scale);
                                player.setY(getScreenHeight() - (y+y_offset) * y_scale);
                            }
                            player.setAmmo(response.getRobot().getCurrentAmmo());
                            player.setTankHealth(response.getRobot().getCurrentShield());
                            player.setAmmo(response.getRobot().getCurrentAmmo());
                            player.setTankHealth(response.getRobot().getCurrentShield());
                            player.setRange(response.getRobot().getFiringDistance()*x_scale);
                            player.setKills(response.getRobot().getKills());
                            player.setDeaths(response.getRobot().getDeaths());

                            switch(response.getRobot().getDirection()) {
                                case NORTH:
                                    player.setTankDirection(Direction.Up);
                                    break;
                                case EAST:
                                    player.setTankDirection(Direction.Right);
                                    break;
                                case SOUTH:
                                    player.setTankDirection(Direction.Down);
                                    break;
                                case WEST:
                                    player.setTankDirection(Direction.Left);
                                    break;
                            }
                            break;
                        }

                        Robot robot = response.getEnemyRobots().get(valueAtPosition);

                        Enemy enemy = new Enemy();

                        enemy.setX((x+x_offset) * x_scale);
                        enemy.setY(getScreenHeight() - (y+y_offset) * y_scale);
                        enemy.setName(robot.getRobotName());
                        enemy.setAmmo(robot.getCurrentAmmo());
                        enemy.setTankHealth(robot.getCurrentShield());
                        enemy.setRange(robot.getFiringDistance());
                        enemy.setKills(robot.getKills());

                        switch (robot.getDirection()) {
                            case NORTH:
                                enemy.setTankDirection(Direction.Up);
                                break;
                            case EAST:
                                enemy.setTankDirection(Direction.Right);
                                break;
                            case SOUTH:
                                enemy.setTankDirection(Direction.Down);
                                break;
                            case WEST:
                                enemy.setTankDirection(Direction.Left);
                                break;
                        }

                        if (robot.isFiring()) {
                            enemy.fire();
                        }

                        enemyList.add(enemy);

                        break;
                }
            }
        }

    }

    public void start() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                while (true) {
                    try {
                        if (launched) {
                            repaint();
                        }
                        HelperMethods.sleepSilently(REPAINT_INTERVAL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

}


