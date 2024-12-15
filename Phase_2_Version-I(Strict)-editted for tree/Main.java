import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Load or initialize the railway system
        RailwaySystem railwaySystem = RailwaySystem.loadState();

        Scanner scanner = new Scanner(System.in);
        StringBuilder commandBlock = new StringBuilder();
        System.out.println("Enter commands (press Enter twice to execute a block):");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                // Process the command block when an empty line is entered
                String command = commandBlock.toString();
                if (!command.isEmpty()) {
                    String result = railwaySystem.processCommand(command);
                    System.out.println(result);
                }
                commandBlock.setLength(0); // Reset for next command block
            } else {
                commandBlock.append(line).append("\n");
            }
        }

    }
}
