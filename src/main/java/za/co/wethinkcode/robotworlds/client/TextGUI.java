package za.co.wethinkcode.robotworlds.client;

import java.util.Scanner;

import za.co.wethinkcode.robotworlds.protocol.Response;

/**
 * Example of how to implement GUI
 */
public class TextGUI implements GUI {
    private String currentInput;

    public TextGUI(){
        currentInput = "";
    }

    @Override
    public String getInput() throws NoNewInput {
        if (currentInput == ""){
            throw new NoNewInput();
        }
        String result = currentInput;
        currentInput = "";
        return result;
    }

    @Override
    public void showOutput(Response response) {
        System.out.println(response);
    }
    
    @Override
    public void run(){
        try (
            Scanner in = new Scanner(System.in);
            ){
                while (true) {
                    if (currentInput == ""){        
                    System.out.println("What would you like to do next?");
                    this.currentInput = in.nextLine();
                } 
            }
        }
    }
}
