package za.co.wethinkcode.robotworlds.client;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import za.co.wethinkcode.robotworlds.protocol.Response;
import za.co.wethinkcode.robotworlds.server.Position;

/**
 * Example of how to implement GUI
 */
public class TextGUI implements GUI {
    Scanner in = new Scanner(System.in);

    @Override
    public String getInput() throws NoNewInput {
        try {
            return in.nextLine();
        } catch (NoSuchElementException elevated) {
            throw new NoNewInput();
        }
    }

    @Override
    public void showOutput(Response response) {
        HashMap<Integer, HashMap<Integer, Character>> map = response.getMap();
        for (int x =0; x < map.size(); x++){
            for (int y = 0; y < map.get(x).size(); y++){
                System.out.print(map.get(x).get(y));
            }
            System.out.print('\n');
        }
        System.out.println(response.getClientName() +  " : " + response.getResult());
    }
}

