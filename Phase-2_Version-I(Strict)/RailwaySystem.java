import java.io.*;

public class RailwaySystem implements Serializable {
    private static final long serialVersionUID = 1L;
    private CustomArrayList<Line> lines;

    public RailwaySystem() {
        this.lines = new CustomArrayList<>();
    }

    /**
     * Processes a user command.
     */
    public String processCommand(String command) {
        // Split the command by newline to handle multi-line input
        String[] lines = command.split("\n");

        if (lines.length == 0 || lines[0].trim().isEmpty()) {
            return "Comando inválido.";
        }

        // Split the first line into operation and line name
        String[] firstLineParts = lines[0].trim().split(" ", 2);
        String operation = firstLineParts[0].toUpperCase();
        String remainingInput = firstLineParts.length > 1 ? firstLineParts[1].trim() : "";

        try {
            switch (operation) {
                case "IL": // Insert Line
                    return insertLine(remainingInput, lines);
                case "IH": // Insert Schedule
                    return insertSchedule(lines);
                case "RL": // Remove Line
                    return removeLine(remainingInput);
                case "CL": // List Stations of a Line
                    return listStations(remainingInput);
                case "CE": // List Lines by Station
                    return listLinesByStation(remainingInput);
                case "RH": // Remove Schedule
                    return removeSchedule(remainingInput, lines[1].trim());
                case "CH": // List Schedules of a Line
                    return listSchedules(remainingInput);
                case "LC": // List Trains by Station
                    return listTrainsByStation(remainingInput);
                case "TA": // Terminate Application
                    saveState();
                    return "Aplicação terminada.";
                default:
                    return "Comando inválido.";
            }
        } catch (Exception e) {
            return "Erro ao processar comando: " + e.getMessage();
        }
    }

    /**
     * Inserts a new line into the railway system.
     */
    private String insertLine(String lineName, String[] lines) {
        // Check if there are stations to add
        CustomArrayList<String> stations = new CustomArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String station = lines[i].trim();
            if (!station.isEmpty()) {
                stations.add(station);
            }
        }

        if (stations.size() == 0) {
            return "Comando inválido.";
        }

        // Check if the line already exists
        for (int i = 0; i < this.lines.size(); i++) {
            if (this.lines.get(i).getName().equalsIgnoreCase(lineName)) {
                return "Linha existente.";
            }
        }

        // Add the new line with stations
        this.lines.add(new Line(lineName, stations));
        saveState(); // Ensure to save the state after inserting a line
        return "Inserção de linha com sucesso.";
    }

    /**
     * Inserts a new schedule for a train on a specific line.
     */
    private String insertSchedule(String[] parts) {
        // Ensure there are enough parts for line name, train number, and stations
        if (parts.length < 3) {
            return "Comando inválido."; // Invalid command
        }

        // First line is the operation and line name (e.g., "IH GreenLine")
        String firstLine = parts[0].trim();
        String[] firstLineParts = firstLine.split(" ");
        String identifyingCase = firstLineParts[0]; // "IH"
        String lineName = firstLineParts[1]; // "GreenLine"

        // Find the line by its name
        Line line = getLine(lineName);
        if (line == null) {
            return "Linha inexistente."; // Line doesn't exist
        }

        // Second part is the train number (e.g., "101")
        int trainNumber;
        try {
            trainNumber = Integer.parseInt(parts[1].trim()); // Train number
        } catch (NumberFormatException e) {
            return "Número de comboio inválido."; // Invalid train number
        }

        // Create a new schedule for the train
        Schedule schedule = new Schedule(trainNumber);

        // Process stations (starting from the third element onward)
        for (int i = 2; i < parts.length; i++) {
            String[] stationData = parts[i].split(" "); // Split each station and time by space
            if (stationData.length == 2) {
                String stationName = stationData[0].trim(); // Station name
                String stationTime = stationData[1].trim(); // Station time

                // Add station and time to the schedule
                schedule.addStationTime(stationName + " " + stationTime); // Add both station and time as a single entry
            }
        }

        // Validate the schedule
        if (!isScheduleValid(line, schedule)) {
            return "Horário inválido."; // Invalid schedule
        }

        // Add the schedule to the line
        line.addSchedule(schedule);

        // Save the state after adding the schedule
        saveState();

        return "Criação de horário com sucesso."; // Schedule created successfully
    }

    /**
     * Removes a line from the railway system.
     */
    private String removeLine(String lineName) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getName().equalsIgnoreCase(lineName)) {
                lines.remove(i);
                return "Remoção de linha com sucesso.";
            }
        }
        return "Linha inexistente.";
    }

    /**
     * Lists the stations of a specific line.
     */
    private String listStations(String lineName) {
        Line line = getLine(lineName);
        return line != null ? line.listStations() : "Linha inexistente.";
    }

    /**
     * Lists all lines that pass through a specific station.
     */
    private String listLinesByStation(String stationName) {
        CustomArrayList<String> linesByStation = new CustomArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).containsStation(stationName)) {
                linesByStation.add(lines.get(i).getName());
            }
        }

        if (linesByStation.size() == 0) {
            return "Estação inexistente.";
        }

        StringBuilder result = new StringBuilder(stationName + "\n");
        for (int i = 0; i < linesByStation.size(); i++) {
            result.append(linesByStation.get(i)).append("\n");
        }
        return result.toString();
    }

    /**
     * Removes a schedule based on train number and departure station.
     */
    private String removeSchedule(String lineName, String stationAndTime) {
        Line line = getLine(lineName);
        if (line == null)
            return "Linha inexistente.";

        for (int i = 0; i < line.getSchedules().size(); i++) {
            Schedule schedule = line.getSchedules().get(i);
            if (schedule.getStationTimes().get(0).equalsIgnoreCase(stationAndTime)) {
                line.getSchedules().remove(i);
                saveState(); // Save after removal
                return "Remoção de horário com sucesso.";
            }
        }
        return "Horário inexistente.";
    }

    /**
     * Lists all schedules for a specific line.
     */
    private String listSchedules(String lineName) {
        Line line = getLine(lineName);
        if (line == null)
            return "Linha inexistente.";

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < line.getSchedules().size(); i++) {
            result.append(line.getSchedules().get(i).listStationTimes());
        }
        return result.toString();
    }

    /**
     * Lists all trains passing through a specific station.
     */
    private String listTrainsByStation(String stationName) {
        CustomArrayList<String> trains = new CustomArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            CustomArrayList<Schedule> schedules = lines.get(i).getSchedules();
            for (int j = 0; j < schedules.size(); j++) {
                if (schedules.get(j).passesThrough(stationName)) {
                    trains.add("Comboio " + schedules.get(j).getTrainNumber());
                }
            }
        }

        if (trains.size() == 0) {
            return "Estação inexistente.";
        }

        StringBuilder result = new StringBuilder(stationName + "\n");
        for (int i = 0; i < trains.size(); i++) {
            result.append(trains.get(i)).append("\n");
        }
        return result.toString();
    }

    /**
     * Validates a schedule before adding it to a line.
     */
    private boolean isScheduleValid(Line line, Schedule schedule) {
        return line.validateScheduleOrder(schedule);
    }

    /**
     * Retrieves a line by its name.
     */
    private Line getLine(String lineName) {
        for (int i = 0; i < this.lines.size(); ++i) {
            if (this.lines.get(i).getName().equalsIgnoreCase(lineName)) {
                return this.lines.get(i);
            }
        }
        return null;
    }

    /**
     * Saves the state of the railway system to a file.
     */
    public void saveState() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("state.dat"))) {
            out.writeObject(this); // Save the entire system's state
        } catch (IOException e) {
            System.err.println("Erro ao salvar estado: " + e.getMessage());
        }
    }

    /**
     * Loads the state of the railway system from a file.
     */
    public static RailwaySystem loadState() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("state.dat"))) {
            return (RailwaySystem) in.readObject(); // Load the system state
        } catch (FileNotFoundException e) {
            return new RailwaySystem(); // If no state file exists, create a new system
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar estado: " + e.getMessage());
            return new RailwaySystem(); // Handle errors and create a new system
        }
    }

}
