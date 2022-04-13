package za.co.wethinkcode.robotworlds.client.old;
//package za.co.wethinkcode.robotworlds.client;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.util.Scanner;
//
//class TankWar extends JComponent {
//    Scanner scanner = new Scanner(System.in);
//    private static final int WIDTH = 800, HEIGHT = 600;
//
//    private static final int REPAINT_INTERVAL = 50;
//
//    private int x = WIDTH / 2, y = HEIGHT / 2 - 40;
//    private int enemyX=WIDTH / 2+50,enemyY=HEIGHT / 2 - 100;
//    private int bulletY = y;
//    private int bulletX = x;
//    private int bulletsLeft = 100;
//    private int health = 5;
//    private int kills = 3;
//    private int deaths = 2;
//    private int enemiesLeft = 3;
//    private static String request ="tankworld";
//    public static String getRequest(){return request;};
//
//    private Direction tankDirection = Direction.Up;
//    private boolean fire = false;
//    Enemy enemy1 = new Enemy();
//
//    private TankWar() {
//        System.out.println("Enter Socket Name: ");
//        ClientSocket.name=scanner.nextLine();
//
//        this.addKeyListener(new KeyAdapter() {
//            // GET PLAYER COMMAND
//            @Override
//            public void keyPressed(KeyEvent e) {
//                switch (e.getKeyCode()) {
//                    case KeyEvent.VK_SPACE:
////                        client.HelperMethods.playAudio("shoot.wav");
//                        fire = true;
//                        bulletsLeft--;
//                        if (withinRange(tankDirection,x,y,enemyX,enemyY)){
//                            enemy1.takeHit(7);
//                            }
//                        request = "fire";
//                        ClientSocket.run();
//                        System.out.println(ClientSocket.response);
//                        break;
//                    case KeyEvent.VK_M:
////                        client.HelperMethods.playAudio(Tools.nextBoolean() ? "supershoot.wav" : "supershoot.aiff");
//                        repair();
//                        request = "repair";
//                        ClientSocket.run();
//                        System.out.println(ClientSocket.response);
//                        break;
//
//                    case KeyEvent.VK_R:
////                        client.HelperMethods.playAudio(Tools.nextBoolean() ? "supershoot.wav" : "supershoot.aiff");
//                        request = "reload";
//                        ClientSocket.run();
//                        System.out.println(ClientSocket.response);
//                        reload();
//                        break;
//                    case KeyEvent.VK_LEFT:
//                        System.out.println(x);
//                        request = "left";
//                        ClientSocket.run();
//                        System.out.println(ClientSocket.response);
//                        if(tankDirection == Direction.Up){
//                            tankDirection = Direction.Left;
//                        }else if (tankDirection == Direction.Right){
//                            tankDirection = Direction.Up;
//                        }else if (tankDirection == Direction.Left){
//                            tankDirection = Direction.Down;
//                        }else if (tankDirection == Direction.Down){
//                            tankDirection = Direction.Right;
//                        }
//                        break;
//                    case KeyEvent.VK_UP:
//                        request = "forward";
//                        ClientSocket.run();
//                        System.out.println(ClientSocket.response);
//                        System.out.println(y);
//                        if(tankDirection == Direction.Up){
//                            y -= 5;
//                        }else if (tankDirection == Direction.Right){
//                            x +=5;
//                        }else if (tankDirection == Direction.Left){
//                            x -=5;
//                        }else if (tankDirection == Direction.Down){
//                            y +=5;
//                        }
//                        break;
//                    case KeyEvent.VK_DOWN:
//                        request = "back";
//                        ClientSocket.run();
//                        System.out.println(ClientSocket.response);
//                        if(tankDirection == Direction.Up){
//                            y += 5;
//                        }else if (tankDirection == Direction.Right){
//                            x -=5;
//                        }else if (tankDirection == Direction.Left){
//                            x +=5;
//                        }else if (tankDirection == Direction.Down){
//                            y -=5;
//                        }
//                        break;
//                    case KeyEvent.VK_RIGHT:
//                        request = "right";
//                        ClientSocket.run();
//                        System.out.println(ClientSocket.response);
//                        if(tankDirection == Direction.Up){
//                            tankDirection = Direction.Right;
//                        }else if (tankDirection == Direction.Right){
//                            tankDirection = Direction.Down;
//                        }else if (tankDirection == Direction.Left){
//                            tankDirection = Direction.Up;
//                        }else if (tankDirection == Direction.Down){
//                            tankDirection = Direction.Left;
//                        }
//                        break;
//
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, WIDTH, HEIGHT);
//
//
//        // draw obstacles *TO DO find better design
//        g.setColor(Color.GRAY);
//        g.fillRect(250, 100, 300, 20);
//        g.fillRect(100, 200, 20, 150);
//        g.fillRect(680, 200, 20, 150);
//        // DRAW crater
//        g.setColor(Color.MAGENTA);
//        g.drawImage(HelperMethods.getImage("crater.png"), WIDTH / 2, HEIGHT / 2 - 100, null);
//
//        // draw HUD *TO DO: Hide and only show on state command
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("Default", Font.BOLD, 14));
//        g.drawString("Missiles: " + bulletsLeft, 10, 50);
//        g.drawString("Health: " + health, 10, 70);
//        g.drawString("Enemies Left: " + enemiesLeft, 10, 90);
//        g.drawString("Kills: " + kills, 10, 110);
//        g.drawString("Deaths: " + deaths, 10, 130);
//        double kdRatio = kills/deaths;
//        g.drawString("K/D: " + kdRatio, 10, 150);
//
//
//        // spawn player tank ***NB implement designs for other tanks
//        g.drawString("Mfundo", x, y-20);
//        g.setColor(Color.RED);
//        g.fillRect(x, y - 10, 35, 10);
//        g.drawImage(tankDirection.getImage("tank"), x, y, null);
//
//
//        // spawn enemy tank
//        g.drawString("Client_1", enemyX, enemyY-20);
//        g.setColor(Color.RED);
//        g.fillRect(enemyX, enemyY-10, enemy1.getTankHealth(), 10);
//        boolean enemyDead = enemy1.getTankHealth()==0;
//        if(enemyDead){g.drawImage(HelperMethods.getImage(enemy1.getDeadImage()), enemyX, enemyY, null);
//        }else{
//            g.drawImage(enemy1.getTankDirection().getImage(enemy1.getSpriteName()), enemyX, enemyY, null);}
//        enemyMovement();
//
//
//        //Explode fire command animation
//        if (fire){
//        shoot(g,tankDirection);
//        if (tankDirection == Direction.Up){
//        g.drawImage(HelperMethods.getImage(7 + ".gif"), x-12, y-100, null);
//        }else if (tankDirection==Direction.Left){
//            g.drawImage(HelperMethods.getImage(7 + ".gif"), x-100, y-12, null);
//        }else if (tankDirection==Direction.Right){
//            g.drawImage(HelperMethods.getImage(7 + ".gif"), x+100, y-12, null);
//        }else if (tankDirection == Direction.Down){
//            g.drawImage(HelperMethods.getImage(7 + ".gif"), x-12, y+100, null);
//        }
//        while (bulletY!=0){
//            bulletY=bulletY-1;
//            }
//        }
//
//        //respawn enemy
//        fire=false;
//        if(enemy1.getTankHealth()==-7){
//            enemy1.setTankHealth(35);
//            kills++;
//        }
//
//    }
//
//    //find a way to link explotion
//    private boolean shoot(Graphics g,Direction direction){
//        boolean bullet = false;
//        int bulletY = y;
//        int bulletX = x;
//        if (direction == Direction.Up){
//            g.drawImage(Direction.Up.getImage("missile"), bulletX+14, bulletY -8, null);
//        }else if(direction == Direction.Left){
//            g.drawImage(Direction.Left.getImage("missile"), bulletX-8, bulletY +14, null);
//        }else if(direction == Direction.Down){
//            g.drawImage(Direction.Down.getImage("missile"), bulletX+14, bulletY +8, null);
//        }else if(direction == Direction.Right){
//            g.drawImage(Direction.Right.getImage("missile"), bulletX+8, bulletY +14, null);
//        }
//        return bullet;
//    }
//
//
//    //trash implementation
//    private boolean withinRange(Direction direction,int x, int y,int a,int b){
//        boolean within = false;
//        if (direction == Direction.Up){
//            System.out.println("reached");
//            within = x > enemyX-30 && x < enemyX+30 && y > enemyY+60 && y < enemyY+130;
//        }else if (direction == Direction.Right){
//            within = x > enemyX-30 && x < enemyX+30 && y > enemyY+60 && y < enemyY+130;
//        }
//        System.out.println(within);
//        return within;
//    }
//
//    //should accept robot name as an argument and use setter method to repair
//    private void repair(){
//        health = 35;
//    }
//
//    //should accept robot name as an argument and use setter method to repair
//    private void reload(){
//        bulletsLeft = 100;
//    }
//
//    private int step = 0;
//
//    private void enemyMovement(){
//        if (enemy1.getTankHealth()>0&&enemyY>5){
//        enemy1.setTankDirection(Direction.Up);
//        enemyY -= 5;
////        if (enemyY==5){
////            while (enemyHealth>0){
////
////        enemyTankDirection = client.Direction.Down;
////        enemyY += 5;
////                if (enemyY==595){
////                    enemyMovement();
////                }
////            }
////        }
//        }
//
//    }
//
//    private void start() {
//        new SwingWorker<Void, Void>() {
//            @Override
//            protected Void doInBackground() {
//                while (true) {
//                    try {
//                        repaint();
//                        HelperMethods.sleepSilently(REPAINT_INTERVAL);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.execute();
//    }
//
//    public static void main(String[] args) {
//
//        HelperMethods.setTheme();
//        JFrame frame = new JFrame("T A N K W O R L D S");
//        frame.setIconImage(HelperMethods.getImage("/icon.png"));
//        frame.setSize(WIDTH, HEIGHT);
//        frame.setLocation(400, 100);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setResizable(false);
//
//        TankWar tankWar = new TankWar();
//        frame.add(tankWar);
//        tankWar.setFocusable(true);
//        frame.setVisible(true);
//        tankWar.start();
//    }
//}
//
