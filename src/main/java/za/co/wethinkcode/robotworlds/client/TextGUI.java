package za.co.wethinkcode.robotworlds.client;

import java.util.NoSuchElementException;
import java.util.Scanner;

import za.co.wethinkcode.robotworlds.protocol.Response;

/**
 * Example of how to implement GUI
 */
public class TextGUI implements GUI {
    Scanner in = new Scanner(System.in);
    String enemyName;
    
    @Override
    public String getInput() throws NoNewInput {
        try {
            return in.nextLine();
        } catch (NoSuchElementException elevated) {
            throw new NoNewInput();
        }
    }

    @Override
    public String getClientName() {
        return null;
    }

    @Override
    public void showOutput(Response response) {
        System.out.println(response.serialize());
    }

    @Override
    public void enemyMovement(String command) {

    }

    @Override
    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }
}

