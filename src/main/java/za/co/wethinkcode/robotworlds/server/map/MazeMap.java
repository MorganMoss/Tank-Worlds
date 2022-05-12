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

    private static final String maggieMaze =
                        "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"
                    +   "X     X     XXX               X     X   X\n"
                    +   "XXX   XXX   XXXXX   X X   XXXXX     XXX X\n"
                    +   "X       X   XXX     XXX   XXXX      XXX X\n"
                    +   "X           X       XXX           XXXXX X\n"
                    +   "X     X         X         X       X   X X\n"
                    +   "XXX XXXXX   X XXX   XXXXXXX       X   X X\n"
                    +   "X   XXX     XXXXX     XXXXX             X\n"
                    +   "X   XXX     X          XXXX XXXXX   X   X\n"
                    +   "X                 XX                X   X\n"
                    +   "XXX XXX   X XXX   XXX     XXX   XXX XXXXX\n"
                    +   "X   X    XX         X      XX   XXX     X\n"
                    +   "XXX X     XXXX      X    XXXX   XXX XXX X\n"
                    +   "X   X     XXX       X     XXX   X   XXX X\n"
                    +   "X XXXXX XXX XXX                      XX X\n"
                    +   "X       X         X               X     X\n"
                    +   "X XXX             X   XXX   XXXX  XXXXX X\n"
                    +   "X XXX                 XXX     XX      X X\n"
                    +   "X     XXX XXXXX    XXXXXXX  X XXXXXXX   X\n"
                    +   "X X   X     X               X     XXX   X\n"
                    +   "X     XXX  XX XXXXXXX             XXX   X\n"
                    +   "X   XXXXX         XX      XX    X       X\n"
                    +   "X     XXXXX     XXXXX   XXXXX   XXXXXXX X\n"
                    +   "X X       X                     XXX     X\n"
                    +   "X XXX X XXX       XX    X XXX   XXX XXXXX\n"
                    +   "    X X           X   XXXXX             X\n"
                    +   "XXX XXX   X     XXX   XXXXX     XXXXXX  X\n"
                    +   "X   XXX   XXX   XXX     XXX        XXX  X\n"
                    +   "X   X XX  XXX    X        X   X  XXXXX  X\n"
                    +   "X                     XX    XXX  XX     X\n"
                    +   "X   X     XXXX      XXXXX    XX     XXXXX\n"
                    +   "X   X  X  XX        XXXXX               X\n"
                    +   "XXXXX  X  XXX     XXXXXXX           XX  X\n"
                    +   "XXX       X                 X X X   XXX X\n"
                    +   "X             XXX   XXX     XXXXX  XXXX X\n"
                    +   "X   X     X    XX    XX       XX        X\n"
                    +   "XXXXX     XXXX        X XXX       XXX   X\n"
                    +   "X       X XXX    XX   XXX          XXX  X\n"
                    +   "XXXX  XXX     XXXXX     XXX   XXX  XXX  X\n"
                    +   "X     XXX         X     XXX     X   X   X\n"
                    +   "X           XXX                   XXX   X\n"
                    +   "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n";

    protected static String maze = maggieMaze;

    @Override
    public List<Obstacle> getObstacles() {
        List<Obstacle> obstacles = new ArrayList<>();

        int x;
        int y = maze.split("\n").length/2;

        for (String line : maze.split("\n")){
            x = -maze.split("\n")[0].length()/2;
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
//        return new Position(80,160);
        return new Position(maze.split("\n")[0].length()*2, maze.split("\n").length*2-7);
    }


}
