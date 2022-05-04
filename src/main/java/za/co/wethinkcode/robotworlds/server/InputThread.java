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
                case "dump":
                    server.dump();
                case "robots":
                    server.robots();
                default:
                    throw new IllegalArgumentException("Unsupported command: " + command);
            }
        }
    }
}
