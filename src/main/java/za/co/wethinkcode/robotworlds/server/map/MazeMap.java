package za.co.wethinkcode.robotworlds.server.map;

import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.server.obstacle.SquareObstacle;
import za.co.wethinkcode.robotworlds.shared.Position;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class MazeMap extends Map{
    private static final String basicMaze =
                        "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "                                         \n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
                    +   "XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX\n"
            ;
    /**
     * The real maze string, can be used to generate a proper maze.
     */
    private static final String realMaze =
                        "XXXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXX\n"
                    +   "X     X     X X   X X   X     X X   X   X\n"
                    +   "XXX X XXX X XXXXX XXX X X X XXX XXX X X X\n"
                    +   "X   X   X X X X       X   X         X X X\n"
                    +   "X XXXXX X X X XXXXX XXX XXXXXXXXX XXX X X\n"
                    +   "X     X X       X   X     X X X   X X X X\n"
                    +   "XXX XXXXX X X XXX X XXXXXXX X X X X XXX X\n"
                    +   "X   X   X X X   X X   X X     X X       X\n"
                    +   "XXX XXX XXXXX XXXXXXX X XXX XXXXXXX X X X\n"
                    +   "X               X X   X       X X   X X X\n"
                    +   "XXX XXX X X XXXXX X XXX X XXX X X X XXXXX\n"
                    +   "X   X   X X         X   X   X X   X     X\n"
                    +   "XXX X XXX XXXXXXX X X X XXX X X XXX X X X\n"
                    +   "X   X X   X X   X X X X   X X X X   X X X\n"
                    +   "XXXXXXX XXX XXX XXXXXXX X XXXXXXXXXXXXX X\n"
                    +   "X     X X     X   X   X X         X     X\n"
                    +   "X X X X XXX XXX X X X XXX X XXXXX XXXXX X\n"
                    +   "X X X           X   X X X X   X       X X\n"
                    +   "XXXXXXXXX XXXXX X XXXXX XXX X XXXXXXX X X\n"
                    +   "X X X X     X X X X       X X     X X X X\n"
                    +   "X X X XXX XXX XXXXXXX X XXXXX X X X XXX X\n"
                    +   "X   X X X         X   X       X X       X\n"
                    +   "X XXX X XXX X XXXXXXXXX XXXXXXX X XXXXX X\n"
                    +   "X X       X X X       X X X   X X X     X\n"
                    +   "X XXX X XXX XXXXX XXX X X XXX X XXX XXXXX\n"
                    +   "    X X     X     X   X     X     X   X  \n"
                    +   "XXX XXX X X XXX XXXXXXXXX X XXX XXXXXXX X\n"
                    +   "X   X X X X X X X       X X X     X   X X\n"
                    +   "X X X XXX X X X X XXXXXXXXXXX X XXXXX X X\n"
                    +   "X X   X   X       X         X X   X X   X\n"
                    +   "X X X XXX X XXXXXXX X XXXXXXXXX X X XXX X\n"
                    +   "X X X   X X     X   X   X       X     X X\n"
                    +   "XXXXX XXX XXX X X XXX XXXXXXXXX X X XXX X\n"
                    +   "X X       X   X   X X     X   X X X X   X\n"
                    +   "X X XXXXXXXXX XXX X XXXXXXX XXXXX XXXXX X\n"
                    +   "X   X     X     X X   X     X   X     X X\n"
                    +   "XXXXX XXXXX XXX XXX X X XXX XXX X XXX X X\n"
                    +   "X       X X X     X X   X   X   X X   X X\n"
                    +   "XXXXX XXX XXX XXXXX XXX XXX X XXXXX X X X\n"
                    +   "X     X X X     X X   X X X     X X X   X\n"
                    +   "XXX X X X XXXXX X X   X X X XXX X XXXXX X\n"
                    +   "X X X       X     X   X X     X   X X X X\n"
                    +   "X X XXX XXXXXXXXX X XXX XXX XXX XXX X X X\n"
                    +   "X X   X       X   X X X X X X X X   X   X\n"
                    +   "X XXX XXXXX XXX XXXXX X X XXX X XXX X XXX\n"
                    +   "X X   X   X   X       X   X X   X X     X\n"
                    +   "X XXX X XXXXX X XXX XXX XXX XXXXX X XXXXX\n"
                    +   "X     X     X     X X         X       X X\n"
                    +   "X XXX X X X XXX XXX X XXX X XXXXXXX XXX X\n"
                    +   "X X   X X X       X X   X X       X X X X\n"
                    +   "X XXX X X XXX X X XXX XXXXXXXXXXX X X X X\n"
                    +   "X X   X X X   X X X   X X       X       X\n"
                    +   "XXXXX XXX X XXXXXXX XXX X XXXXX X XXXXX X\n"
                    +   "X   X X   X     X X   X     X   X   X   X\n"
                    +   "X X XXX X XXX XXX XXXXXXXXXXX X X XXXXX X\n"
                    +   "X X     X   X     X     X X X X   X X X  \n"
                    +   "X XXXXXXXXX X XXX X X XXX X XXX X X X XXX\n"
                    +   "X   X       X   X   X X   X   X X X   X X\n"
                    +   "XXXXXXX XXX X XXXXX X XXX X X X XXX XXX X\n"
                    +   "X   X   X X X X     X X X X X       X X X\n"
                    +   "X XXXXXXX X XXXXX XXX X X X XXX XXX X X X\n"
                    +   "X   X         X   X X X   X X     X   X X\n"
                    +   "X XXXXXXXXXXXXXXXXX X XXX X XXXXXXXXX X X\n"
                    +   "X   X X X   X   X       X X     X X     X\n"
                    +   "X XXX X XXX X XXX X XXXXX XXX XXX XXXXX X\n"
                    +   "X   X X       X X X     X     X       X X\n"
                    +   "X X X X X XXXXX X XXXXXXXXX XXX XXX X X X\n"
                    +   "X X     X   X               X   X   X   X\n"
                    +   "XXXXX XXXXXXXXXXX XXX XXXXXXX XXXXX XXXXX\n"
                    +   "X         X X     X         X   X       X\n"
                    +   "XXXXX X X X XXX XXX X X XXXXXXX XXXXXXXXX\n"
                    +   "X     X X       X   X X   X              \n"
                    +   "XXXXX X X X XXX XXX X X XXXXXXX XXXXXXXXX\n"
                    +   "X   X X       X X X     X     X       X X\n"
                    +   "X X X X X XXXXX X XXXXXXXXX XXX XXX X X X\n"
                    +   "X X     X   X               X   X   X   X\n"
                    +   "XXXXX XXXXXXXXXXX XXX XXXXXXX XXXXX XXXXX\n"
                    +   "X         X X     X         X   X       X\n"
                    +   "XXXXX X X X XXX XXX X X XXXXXXX XXXXXXXXX\n"
                    +   "X     X X       X   X X   X              \n"
                    +   "XXXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXX\n"
            ;


    protected static String maze = realMaze;

    @Override
    public List<Obstacle> getObstacles() {
        List<Obstacle> obstacles = new ArrayList<>();

        int x;
        int y = 40;

        for (String line : maze.split("\n")){
            x = -20;
            for(char c : line.toCharArray()){
                if (c == 'X'){
                    obstacles.add(new SquareObstacle(1, new Position(x*2, y*2+2)));
                }
                x++;
            }
            y--;
        }

        return obstacles;
    }

    @Override
    public Position getMapSize() {
        return new Position(80,160);
//        return new Position(maze.split("\n")[0].length()*2, maze.split("\n").length*2);
    }


}
