package za.co.wethinkcode.robotworlds.client.SwingGUI.Map;

import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Enemy;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Tank;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MiniMap extends Map{
    Tank player;

    @Override
    public List<Obstacle> getObstacles() {return null;}

    private List<Obstacle> miniMapObstacles;

    public void setMiniMapObstacles(List<Obstacle> worldObstacles){
        this.miniMapObstacles = worldObstacles;
    }

    public static void draw(Graphics g, Tank player){
        ArrayList<Position> enemyPositions = TankWorld.getEnemyPositions();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,100,100);
        g.setColor(Color.GREEN);
        g.drawRect(player.getAbsoluteX(),player.getAbsoluteY(),1,1);

        for (Position enemyPosition: enemyPositions){
            g.setColor(Color.RED);

            if (enemyPositions.size() != 0){
                g.drawRect(enemyPosition.getX()+50,-(enemyPosition.getY())+50,1,1);
            }
        }

    }



}
