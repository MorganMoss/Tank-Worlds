package za.co.wethinkcode.robotworlds.client.SwingUI;

import za.co.wethinkcode.robotworlds.client.Client;
import za.co.wethinkcode.robotworlds.client.GUI;
import za.co.wethinkcode.robotworlds.client.NoNewInput;
import za.co.wethinkcode.robotworlds.client.SwingUI.Obstacles.Brick;
import za.co.wethinkcode.robotworlds.client.SwingUI.Obstacles.Pit;
import za.co.wethinkcode.robotworlds.client.SwingUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingUI.Tanks.Enemy;
import za.co.wethinkcode.robotworlds.client.SwingUI.Tanks.Player;
import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;
import za.co.wethinkcode.robotworlds.server.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TankWorld extends JComponent implements GUI {
    private static final int WIDTH = 600, HEIGHT = 600;
    public static int getScreenWidth(){return WIDTH;}
    public static int getScreenHeight(){return HEIGHT;}

    private static final int REPAINT_INTERVAL = 50;

    private int x = WIDTH / 2, y = HEIGHT / 2 - 40;
    private Direction tankDirection = Direction.Up;

    private int bulletY = y;
    private int bulletX = x;

    private boolean showState = false;
    private boolean onstart = true;
    private boolean fire = false;
    String clientName = this.getClientName();
    String enemyName;

    public void setEnemyName(String enemyName) {
        enemy1.setName(enemyName);
    }


    private static Request request = new Request("Robot","idle");

    //FIFO stack for requests
    static Request queue1= new Request("Robot","idle");
    private static LinkedList<Request> lastRequest = new LinkedList<Request>();


    @Override
    public String getInput() throws NoNewInput{
        if (lastRequest.getLast() != queue1){
            return lastRequest.removeLast().serialize();
        }else{
            throw new NoNewInput();}
    }

    @Override
    public String getClientName() {
        return Client.getMyClientName();
    }

    @Override
    public void showOutput(Response response) {
        System.out.println(response);
    }

    @Override
    public void enemyMovement(String command) {
        if (Objects.equals(command, "forward")){
            enemy1.moveForward();
        }else if (Objects.equals(command, "back")){
            enemy1.moveBack();
        }else if (Objects.equals(command, "left")){
            enemy1.moveLeft();
        }else if (Objects.equals(command, "right")){
            enemy1.moveRight();
        }
    }
    Player player = new Player();
    Enemy enemy1 = new Enemy();

    public TankWorld()  {

        player.setName(clientName);
        lastRequest.add(queue1);


        this.addKeyListener(new KeyAdapter() {
            // GET PLAYER COMMAND
            @Override
            public void keyPressed(KeyEvent e) {
                showState=false;
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_UP:
                        System.out.println(y);
                        request = new Request(clientName,"forward");
                        lastRequest.add(request);
                        player.moveForward();
                        break;

                    case KeyEvent.VK_DOWN:
                        request = new Request(clientName,"back");
                        lastRequest.add(request);
                        System.out.println("request: "+request.serialize());
                        player.moveBack();
                        break;

                    case KeyEvent.VK_LEFT:
                        request = new Request(clientName,"left");
                        lastRequest.add(request);
                        player.setTankDirection(player.getTankDirection().getLeft());
                        break;

                    case KeyEvent.VK_RIGHT:
                        request = new Request(clientName,"right");
                        lastRequest.add(request);
                        System.out.println("request: "+request.serialize());
                        player.setTankDirection(player.getTankDirection().getRight());
                        break;

                    case KeyEvent.VK_SPACE:
                        request = new Request(clientName,"fire");
                        lastRequest.add(request);
//                        client.HelperMethods.playAudio("shoot.wav");
                        fire = true;
                        player.decreaseAmmo();
                        System.out.println(enemy1.getX());
                        System.out.println(enemy1.getY());
                        if (withinRange(tankDirection,x,y,enemy1)){
                            enemy1.takeHit();
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

                    case KeyEvent.VK_ESCAPE:
                        request = new Request(clientName,"quit");
                        lastRequest.add(request);
                        System.exit(0);
                        break;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // draw obstacles *TODO find better design
        g.setColor(Color.GRAY);

        for(int i=0;i<600;i+=25){
        new Brick(new Position(i,0)).spawn(g);}
        new Brick(new Position(100,200)).spawn(g);
        new Brick(new Position(25,0)).spawn(g);

        //TODO: JScrollPane()

        // DRAW crater
        Pit onePit = new Pit(new Position(400,300));
        onePit.spawn(g);

        // draw HUD *TO DO: Hide and only show on state command
        if(showState){
            player.showState(g);
        }

        // spawn player tank ***NB implement designs for other tanks
        player.spawn(g);

        // spawn enemy tank
        enemy1.spawn(g);

        //Explode fire command animation
        if (fire){
        player.shoot(g);
        }

        //respawn enemy
        fire=false;
        if(enemy1.getTankHealth()==-1){
            enemy1.setTankHealth(5);
            player.addKill();
        }

    }


    //trash implementation
    private boolean withinRange(Direction direction,int x, int y,Enemy enemy){
        boolean within = false;
        if (direction == Direction.Up){
            within = x > enemy.getX()-30 && x < enemy.getX()+30 && y > enemy.getY()+60 && y < enemy.getY()+130;
        }else if (direction == Direction.Right){
            within = x > enemy.getX()-30 && x < enemy.getX()+30 && y > enemy.getY()+60 && y < enemy.getY()+130;
        }
        System.out.println(within);
        return within;
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

    public static void run(String[] args) {
        HelperMethods.setTheme();
        JFrame frame = new JFrame("T A N K W O R L D S");
        frame.setIconImage(HelperMethods.getImage("icon.png"));
//        JLabel background = new JLabel(new ImageIcon(HelperMethods.getImage("/icon.png")));
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        TankWorld tankWar = new TankWorld();
        frame.add(tankWar);
        tankWar.setFocusable(true);
        frame.setVisible(true);
        tankWar.start();
    }
}

