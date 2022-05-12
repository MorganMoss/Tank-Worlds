package za.co.wethinkcode.robotworlds.client.SwingGUI;

import za.co.wethinkcode.robotworlds.client.GUI;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Map.MiniMap;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Brick;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles.Projectile;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Player;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Tank;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.shared.Robot;
import za.co.wethinkcode.robotworlds.shared.exceptions.NoNewInput;
import za.co.wethinkcode.robotworlds.shared.protocols.Request;
import za.co.wethinkcode.robotworlds.shared.protocols.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.Color;
import java.util.List;

public class TankWorld extends JComponent implements GUI {
    private static final int WIDTH = 588, HEIGHT = 616;
    private static final int REPAINT_INTERVAL = 50;
    private static final ArrayList<Player> enemyList = new ArrayList<Player>();
    private static final ArrayList<Position> enemyPositions = new ArrayList<>();
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

    //LIFO stack for requests
    private static Request request;
    private JFrame frame;

    private static int x_scale;
    private static int y_scale;


    private JFormattedTextField nameBox;
    private JComboBox<String> tankBox;
    private JButton launchButton;


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

    public static ArrayList<Position> getEnemyPositions() {
        return enemyPositions;
    }

    public static void addProjectile(Projectile projectile){projectileList.add(projectile);}

    public TankWorld()  {

        nameBox = new JFormattedTextField();
        tankBox  = new JComboBox<>(Robot.ROBOT_TYPES);
        launchButton = new JButton("LAUNCH");
        launchButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
//                tf.setText("Welcome to Javatpoint.");
                launch();
            }
        });

        this.add(tankBox);
        this.add(nameBox);
        this.add(launchButton);

        nameBox.setBounds(WIDTH/2-100, HEIGHT/2-10-70, 200,30);
        tankBox.setBounds(WIDTH/2-100, HEIGHT/2-10, 200,30);
        launchButton.setBounds(WIDTH/2-50, HEIGHT/2+65, 100,30);

        nameBox.requestFocus();

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

                    case KeyEvent.VK_H:
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
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;

                    case KeyEvent.VK_B:
                        if (launched){
                            showBoundaries = !showBoundaries;
                        }
                        break;
                }
                enemyPositions.clear();
            }
        });
    }

    private void launch() {
        if (launched) return;

        clientName = nameBox.getText();
        robotType = (String) tankBox.getSelectedItem();

        if (clientName == "") {
            JOptionPane.showMessageDialog(frame,
                    "Please Enter a name",
                    "Error: No Name",
                    JOptionPane.ERROR_MESSAGE);
            nameBox.requestFocus();
            return;
        }

        request = new Request(clientName,"launch", Collections.singletonList(robotType));
        lastRequest.add(request);
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

    //PaintComponent is our real showOutput
    @Override
    public void showOutput(Response response) {
        if (!launched ){
            if (response.getCommandResponse().equalsIgnoreCase("success")){
                player = new Player(robotType, clientName);

                launched = true;
                nameBox.setVisible(false);
                tankBox.setVisible(false);
                launchButton.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Player with this name already exists. Please try again",
                        "Error: Player Exists",
                        JOptionPane.ERROR_MESSAGE);
                nameBox.requestFocus();
                nameBox.setText("");
            }
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

        //Draw obstacles
        for (Obstacle obstacle:obstacleList){
            obstacle.draw(g);
        }

        //Draw enemies
        for (Player enemy : enemyList){
            enemy.draw(g);
        }

        // Draw HUD
        if(showState){
            player.showState(g);
            MiniMap.draw(g,player);
        }

        //spawn projectiles check collisions
        //draw explosions at projectile exit points
        if(projectileList.size()!=0){
            int i=0;
            boolean hit = false;
            for (Projectile projectile : projectileList){
                i++;
                projectile.project(projectile.getDirection());
                projectile.draw(g);

                for (Player enemy:enemyList){
                    if(projectile.isHitting(enemy)){
                        if (enemy!=projectile.getTank()){
                        explosionX = enemy.getX();
                        explosionY = enemy.getY();
                        showFireAnimation = true;
                        enemy.takeHit();
                        player.addKill();
                        hit=true;}
                    }
                }

                if (projectile.isHitting(player)){
                    if(projectile.getTank()!=player){
                        explosionX = player.getX();
                        explosionY = player.getY();
                        showFireAnimation = true;
                        player.takeHit();
                        projectile.getTank().addKill();
                        hit=true;}
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
            if(showBoundaries){
            g.setColor(Color.BLACK);
            g.drawString("("+player.getX() +","+ player.getY()+")", player.getX(), player.getY()-35);
            }
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
            g.setColor(new Color(213, 191, 191));
            g.fillRect(0, 0, WIDTH, HEIGHT);

            g.setColor(Color.BLACK);
            nameBox.setVisible(true);
            tankBox.setVisible(true);

            g.drawString("Choose a Tank:",WIDTH/2-34, HEIGHT/2-10-5);
            g.drawString("Enter a Name:",WIDTH/2-34, HEIGHT/2-10-75);
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
        for (Player enemy:enemyList){
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
    public boolean fireAnimation(Graphics g, Tank tank, ArrayList<Player> enemyList, int explodeCount){

        boolean bullet = false;

                g.drawImage(HelperMethods.getImage(explodeCount + ".gif"), explosionX, explosionY, null);
                g.drawImage(HelperMethods.getImage(explodeCount + ".gif"), explosionX, explosionY, null);

        return bullet;
    }

    /**
     * Updates state of world objects and minimap according to response from server
     * */
    public void runServerCorrections(Response response) {

        HashMap<String, Robot> enemies = response.getEnemyRobots();

        player.setName(response.getRobot().getRobotName());

        //Minimap positions
        player.setAbsoluteX(response.getRobot().getPosition().getX()+50);
        player.setAbsoluteY(-(response.getRobot().getPosition().getY())+50);
        for(Robot enemy : enemies.values()){
            enemyPositions.add(enemy.getPosition());
        }
//        System.out.println((response.getRobot().getPosition().getX()+50)+","+((-(response.getRobot().getPosition().getY())+50)));


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
                            player.setMaxAmmo(response.getRobot().getMaxAmmo());
                            player.setTankHealth(response.getRobot().getCurrentShield());
//                            player.setRange(response.getRobot().getRange()*x_scale);
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

                        Player enemy = new Player(robot.getRobotType(),robot.getRobotName());

                        enemy.setX((x+x_offset) * x_scale);
                        enemy.setY(getScreenHeight() - (y+y_offset) * y_scale);
                        enemy.setName(robot.getRobotName());
                        enemy.setSprite(robot.getRobotType());
                        enemy.setAmmo(robot.getCurrentAmmo());
                        enemy.setTankHealth(robot.getCurrentShield());
                        enemy.setRange(response.getRobot().getFiringDistance()*x_scale);
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
//                        System.out.println(robot.getLastCommand());
                        if (Objects.equals(robot.getLastCommand(), "fire")) {
                            if(!(enemy.getAmmo()<=0)){
                            enemy.fire();}
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


