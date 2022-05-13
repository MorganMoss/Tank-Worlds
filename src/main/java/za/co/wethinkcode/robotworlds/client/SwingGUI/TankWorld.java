package za.co.wethinkcode.robotworlds.client.SwingGUI;

import za.co.wethinkcode.robotworlds.client.GUI;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Map.MiniMap;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Brick;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles.Projectile;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Player;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.shared.Robot;
import za.co.wethinkcode.robotworlds.shared.exceptions.NoNewInput;
import za.co.wethinkcode.robotworlds.shared.protocols.Request;
import za.co.wethinkcode.robotworlds.shared.protocols.Response;

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
    private static final ArrayList<Player> enemyList = new ArrayList<Player>();
    private static final ArrayList<Position> enemyPositions = new ArrayList<>();
    private static final List<Obstacle> obstacleList = new ArrayList<>();
    private static final ArrayList<Projectile> projectileList = new ArrayList<>();
    private static final LinkedList<Request> lastRequest = new LinkedList<>();

    private final JFrame frame;
    private final JFormattedTextField nameBox;
    private final JComboBox<String> tankBox;
    private final JButton launchButton;

    private static Boolean launched = false;
    private static Boolean showBoundaries = false;
    private boolean showState = false;
    //shows state HUD/ExplosionAnimation on repaint if set to true
    private boolean showFireAnimation = false;

    private final ArrayList<Image> explosion = new ArrayList<>();
    //animation control integers
    private int explodeCount = 1;
    private int explosionX;
    private int explosionY;

    private Player player;
    private static Request request;
    private String robotName;
    private String robotType;

    private static int x_scale = 25;
    private static int y_scale = 25;

    public TankWorld()  {
        nameBox = new JFormattedTextField();
        tankBox  = new JComboBox<>(Robot.ROBOT_TYPES);
        launchButton = new JButton("LAUNCH");
        launchButton.addActionListener(e -> launch());

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
        SoundPlayer audio = new SoundPlayer("assets/audios/start.wav");
        try {
            audio.play();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        start();

        // KEY LISTENER FOR USER INPUT
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                showState=false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (launched) {
                            request = new Request(robotName,"forward");
                            lastRequest.add(request);
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        if (launched) {
                            request = new Request(robotName,"back");
                        }
                        lastRequest.add(request);
                        break;

                    case KeyEvent.VK_LEFT:
                        if (launched) {
                            request = new Request(robotName,"left");
                            lastRequest.add(request);

                            player.turnLeft();
                        }
                        break;

                    case KeyEvent.VK_RIGHT:
                        if (launched) {
                            request = new Request(robotName,"right");
                            lastRequest.add(request);

                            player.turnRight();
                        }
                        break;

                    case KeyEvent.VK_SPACE:
                        if (launched) {
                            request = new Request(robotName, "fire");
                            lastRequest.add(request);

                            if (player.getAmmo() != 0) {
                                player.fire();
                            }
                        }
                        break;

                    case KeyEvent.VK_R:
                        if (launched) {
                            request = new Request(robotName, "reload");
                            lastRequest.add(request);

                            player.reload();
                            SoundPlayer audio = new SoundPlayer("assets/audios/reload.wav");
                            try {
                                audio.play();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;

                    case KeyEvent.VK_H:
//                        client.HelperMethods.playAudio(Tools.nextBoolean() ? "supershoot.wav" : "supershoot.aiff");
                        if (launched) {
                            request = new Request(robotName, "repair");
                            lastRequest.add(request);

                            player.repair();
                            SoundPlayer audio = new SoundPlayer("assets/audios/health.wav");
                            try {
                                audio.play();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;

                    case KeyEvent.VK_S:
                        if (launched) {
                            request = new Request(robotName,"state");
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
            }
        });
    }

    public static JFrame setupGUI() {
        HelperMethods.setTheme();
        JFrame frame = new JFrame("T A N K W O R L D S");
        frame.setIconImage(HelperMethods.getImage("icon.png"));
        frame.setSize(TankWorld.getScreenWidth(), TankWorld.getScreenHeight());
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        return frame;
    }

    public static ArrayList<Position> getEnemyPositions() {
        return enemyPositions;
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

    private void launch() {
        if (launched) return;

        robotName = nameBox.getText();
        robotType = (String) tankBox.getSelectedItem();

        if (robotName.equals("")) {
            JOptionPane.showMessageDialog(frame,
                    "Please Enter a name",
                    "Error: No Name",
                    JOptionPane.ERROR_MESSAGE);
            nameBox.requestFocus();
            return;
        }

        launchButton.setEnabled(false);

        request = new Request(robotName,"launch", Collections.singletonList(robotType));
        lastRequest.add(request);
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
                frame.setSize((TankWorld.getScreenWidth()/x_scale)*x_scale, (TankWorld.getScreenHeight()/y_scale)*y_scale);

                player = new Player(robotType, robotName);

                launched = true;
                nameBox.setVisible(false);
                tankBox.setVisible(false);
                launchButton.setVisible(false);

                HashMap<Integer, HashMap<Integer, String>> map = response.getMap();
                //Goes through all the positions you can see
                int y_size = map.get(0).size();
                int x_size = map.size();

                x_scale = getScreenWidth() / x_size;
                y_scale = getScreenHeight() / y_size;
                // gets the fire animation ready
                for (int i = 1; i < 10; i++){
                    Image temp = HelperMethods.getImage(i+".gif")
                            .getScaledInstance(x_scale, y_scale,  Image.SCALE_DEFAULT);
                    MediaTracker tracker = new MediaTracker(new java.awt.Container());
                    tracker.addImage(temp, 0);
                    try {
                        tracker.waitForAll();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException("Image loading interrupted", ex);
                    }
                    explosion.add(temp);
                }

                player.setName(response.getRobot().getRobotName());
                player.setSize(x_scale);



            } else {
                JOptionPane.showMessageDialog(frame,
                        "Player with this name already exists. Please try again",
                        "Error: Player Exists",
                        JOptionPane.ERROR_MESSAGE);
                nameBox.requestFocus();
                nameBox.setText("");
                launchButton.setEnabled(true);
                return;
            }
        }

        if (response.getCommandResponse().equalsIgnoreCase("Robot with that name already exists.")){
            launchButton.setEnabled(true);
            return;
        }

        if (response.getCommandResponse().equalsIgnoreCase("You are dead")){
            SoundPlayer audio = new SoundPlayer("assets/audios/death.wav");
            try {
                audio.play();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(frame,
                    "You have been killed.",
                    "G A M E  O V E R",
                    JOptionPane.PLAIN_MESSAGE);
            launched = false;
            nameBox.setVisible(true);
            tankBox.setVisible(true);
            launchButton.setVisible(true);
            launchButton.setEnabled(true);
            return;
        }

        runServerCorrections(response);
    }
    /*Swing component that paints onto the window*/
    @Override
    protected void paintComponent(Graphics g) {
        if (!launched) {
            g.setColor(new Color(213, 191, 191));
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.BLACK);
            nameBox.setVisible(true);
            tankBox.setVisible(true);
            launchButton.setVisible(true);
            g.drawString("Choose a Tank:",WIDTH/2-34, HEIGHT/2-10-5);
            g.drawString("Enter a Name:",WIDTH/2-34, HEIGHT/2-10-75);

            g.drawString("Controls:",WIDTH/2-40, HEIGHT/2-10+50+100);
            g.drawString("Arrow Keys to move",WIDTH/2-30, HEIGHT/2-10+60+100);
            g.drawString("R - Reload",WIDTH/2-30, HEIGHT/2-10+70+100);
            g.drawString("H - Repair",WIDTH/2-30, HEIGHT/2-10+80+100);
            g.drawString("S - State",WIDTH/2-30, HEIGHT/2-10+90+100);
            g.drawString("Space - Fire",WIDTH/2-30, HEIGHT/2-10+100+100);
            g.drawString("B - See hit-boxes",WIDTH/2-30, HEIGHT/2-10+110+100);
            g.drawString("Exc - Quit",WIDTH/2-30, HEIGHT/2-10+120+100);
            return;
        }

        //Draw desert Sand
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
        boolean hit = false;
        for (Projectile projectile : new ArrayList<>(projectileList)){
            projectile.project(projectile.getDirection());
            projectile.draw(g);

            for (Player enemy:enemyList){
                if(projectile.isHitting(enemy) && enemy!=projectile.getTank()){
                    hit=true;
                    enemy.takeHit();
                    player.addKill();
                    break;
                }
            }

            if (projectile.isHitting(player) && projectile.getTank()!=player){
                hit=true;
                player.takeHit();
                projectile.getTank().addKill();
            }

            for (Obstacle obstacle : obstacleList){
                if(projectile.isHitting(obstacle)){
                    hit=true;
                    break;
                }
            }

            if(projectile.reachedRange(projectile.getDirection())) {
                hit = true;
            }

            if (hit) {
                SoundPlayer audio = new SoundPlayer("assets/audios/explode.wav");
                try {
                    audio.play();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                explosionX = projectile.getX();
                explosionY = projectile.getY();
                showFireAnimation = true;
                //REMOVE PROJECTILE FROM WORLD
                projectileList.remove(projectile);
            }
        }

        player.draw(g);

        if (showFireAnimation){
            fireAnimation(g);
        }

        if(showBoundaries){
            g.setColor(Color.BLACK);
            g.drawString("("+player.getX() +","+ player.getY()+")", player.getX(), player.getY()-35);
        }
        g.setColor(Color.RED);
        //Explode fire command animation
    }

    public void fireAnimation(Graphics g){
        g.drawImage(explosion.get(explodeCount), explosionX, explosionY, null);
        g.drawImage(explosion.get(explodeCount), explosionX, explosionY, null);
        g.drawImage(explosion.get(explodeCount), explosionX, explosionY, null);

        explodeCount++;
        if (explodeCount == 9){
            showFireAnimation = false;
            explodeCount=1;
        }
    }

    /**
     * Updates state of world objects and minimap according to response from server
     * */
    public void runServerCorrections(Response response) {
        HashMap<String, Robot> enemies = response.getEnemyRobots();
        enemyPositions.clear();

        //Minimap positions
        player.setAbsoluteX(response.getRobot().getPosition().getX()+50);
        player.setAbsoluteY(-(response.getRobot().getPosition().getY())+50);
        for(Robot enemy : enemies.values()){
            enemyPositions.add(enemy.getPosition());
        }

        HashMap<Integer, HashMap<Integer, String>> map = response.getMap();
        //Goes through all the positions you can see
        int y_size = map.get(0).size();
        int x_size = map.size();

        int y_offset = 2;
        int x_offset = 0;


        obstacleList.clear();

        ArrayList<String> enemyNames = new ArrayList<>();

        for (Player enemy : enemyList){
            enemyNames.add(enemy.getTankName());
        }

        for (int y = 0; y < y_size; y++) {
            for (int x = 0; x < x_size; x++) {
                String valueAtPosition = map.get(x).get(y);
                switch (valueAtPosition) {
                    //obstacle
                    case "X":
                        obstacleList.add(new Brick(new Position((x+x_offset) * x_scale, getScreenHeight() - (y+y_offset) * y_scale)));
                        break;
                    //open
                    case " ":
                        break;
                    //robot
                    default:
                        //TODO: break up into separate methods
                        //the player
                        if (valueAtPosition.equals(player.getTankName())){
                            if (player.getX() != (x+x_offset) * x_scale || player.getY() != getScreenHeight() - (y+y_offset) * y_scale){
                                player.setX((x+x_offset) * x_scale);
                                player.setY(getScreenHeight() - (y+y_offset) * y_scale);
                            }
                            player.setAmmo(response.getRobot().getCurrentAmmo());
                            player.setTankHealth(response.getRobot().getCurrentShield());
                            player.setAmmo(response.getRobot().getCurrentAmmo());
                            player.setMaxAmmo(response.getRobot().getMaxAmmo());
                            player.setTankHealth(response.getRobot().getCurrentShield());
                            player.setRange(response.getRobot().getFiringDistance()*x_scale);

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

                        //the enemies
                        Robot robot = response.getEnemyRobots().get(valueAtPosition);
                        if (robot != null) {
                            boolean enemyExists = false;
                            //recurring enemies
                            for (Player enemy : enemyList) {
                                if (enemy.getTankName().equals(valueAtPosition)) {
                                    enemyExists = true;
                                    enemy.setX((x + x_offset) * x_scale);
                                    enemy.setY(getScreenHeight() - (y + y_offset) * y_scale);
                                    enemy.setAmmo(robot.getCurrentAmmo());
                                    enemy.setRange(robot.getFiringDistance()*x_scale);
                                    enemy.setTankHealth(robot.getCurrentShield());
                                    enemy.setKills(robot.getKills());
                                    enemy.setRange(response.getRobot().getFiringDistance() * x_scale);

                                    enemyNames.remove(valueAtPosition);

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

                                    if (Objects.equals(robot.getLastCommand(), "fire")) {
                                        if (enemy.getAmmo() > 0) {
                                            enemy.fire();
                                        }
                                    }

                                    break;
                                }
                            }
                            //new enemies
                            if (!enemyExists) {
                                Player enemy = new Player(robot.getRobotType(), robot.getRobotName());
                                enemy.setSize(x_scale);
                                enemy.setX((x + x_offset) * x_scale);
                                enemy.setY(getScreenHeight() - (y + y_offset) * y_scale);
                                enemy.setName(robot.getRobotName());
                                enemy.setSprite(robot.getRobotType());
                                enemy.setAmmo(robot.getCurrentAmmo());
                                enemy.setTankHealth(robot.getCurrentShield());
                                enemy.setRange(response.getRobot().getFiringDistance() * x_scale);
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

                                if (Objects.equals(robot.getLastCommand(), "fire")) {
                                    if (enemy.getAmmo() > 0) {
                                        enemy.fire();
                                    }
                                }

                                enemyList.add(enemy);
                            }
                            break;
                        }
                }
            }
        }
        //Remove old enemies
        for (String enemyName : enemyNames){
            enemyList.removeIf(enemy -> enemyName.equals(enemy.getTankName()));
        }

    }

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
}


