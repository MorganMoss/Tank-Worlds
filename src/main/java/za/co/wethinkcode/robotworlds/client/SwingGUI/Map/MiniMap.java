package za.co.wethinkcode.robotworlds.client.SwingGUI.Map;

import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Player;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Tank;
import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.shared.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MiniMap {
    Tank player;

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

        g.setColor(Color.RED);
        for (Position enemyPosition: enemyPositions){
            if (enemyPositions.size() != 0){
                g.drawRect(enemyPosition.getX()+50,-(enemyPosition.getY())+50,1,1);
            }
        }

    }



}
