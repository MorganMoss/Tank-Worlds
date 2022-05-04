package za.co.wethinkcode.robotworlds.server;

import java.util.Scanner;

public class InputThread extends Thread{

    private final Server server;

    public InputThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "quit":
                    server.quit();
                    break;
                case "dump":
                    server.dump();
                    break;
                case "robots":
                    server.robots();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported command: " + command);
            }
        }
    }
}
