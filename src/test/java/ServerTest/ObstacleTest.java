package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.server.obstacle.SquareObstacle;
import static org.junit.jupiter.api.Assertions.*;

public class ObstacleTest {

    @Test
    public void squareIsPositionBlockedFalse() {
        Position position = new Position(5, 5);
        Obstacle obstacle = new SquareObstacle(3, new Position(-1,-1));
        assertFalse(obstacle.isPositionBlocked(position));
    }

    @Test
    public void squareIsPositionBlockedTrue() {
        Position position = new Position(1, -1);
        Obstacle obstacle = new SquareObstacle(3, new Position(-1,-1));
        assertTrue(obstacle.isPositionBlocked(position));
    }
}
