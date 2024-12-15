import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RailwaySystem railwaySystem = new RailwaySystem();
        String stateFile = "state.dat";

        try {
            railwaySystem = RailwaySystem.loadState(stateFile);
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
        }

        while (true) {
            String command = sc.nextLine();
            if (command.equalsIgnoreCase("TA")) {
                try {
                    railwaySystem.saveState(stateFile);
                } catch (Exception e) {
                    System.out.println("Error saving state: " + e.getMessage());
                }
                System.out.println("Aplicação terminada.");
                break;
            }

            // Command parsing and delegation to RailwaySystem methods
        }
        sc.close();
    }
}
