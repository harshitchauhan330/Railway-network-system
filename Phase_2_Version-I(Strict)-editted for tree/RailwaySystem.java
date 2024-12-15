import java.io.*;

public class RailwaySystem implements Serializable {
    private static final long serialVersionUID = 1L;

    // Using a CustomMap to store lines
    private CustomMap<String, Line> lines;

    public RailwaySystem() {
        this.lines = new CustomMap<>();
    }

    /**
     * Processes a user command.
     */
    public String processCommand(String command) {
        String[] lines = command.split("\n");

        if (lines.length == 0 || lines[0].trim().isEmpty()) {
            return "Comando inválido.";
        }

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
    private String insertLine(String lineName, String[] inputLines) {
        CustomTreeSet<String> stations = new CustomTreeSet<>();
        for (int i = 1; i < inputLines.length; i++) {
            String station = inputLines[i].trim();
            if (!station.isEmpty()) {
                stations.add(station);
            }
        }

        if (stations.size() == 0) {
            return "Comando inválido.";
        }

        if (lines.containsKey(lineName)) {
            return "Linha existente.";
        }

        lines.put(lineName, new Line(lineName, stations));
        saveState();
        return "Inserção de linha com sucesso.";
    }

    /**
     * Inserts a new schedule for a train on a specific line.
     */
    private String insertSchedule(String[] inputLines) {
        if (inputLines.length < 3) {
            return "Comando inválido.";
        }

        String[] firstLineParts = inputLines[0].split(" ");
        if (firstLineParts.length < 2) {
            return "Comando inválido.";
        }

        String lineName = firstLineParts[1];
        Line line = lines.get(lineName);
        if (line == null) {
            return "Linha inexistente.";
        }

        int trainNumber;
        try {
            trainNumber = Integer.parseInt(inputLines[1].trim());
        } catch (NumberFormatException e) {
            return "Número de comboio inválido.";
        }

        Schedule schedule = new Schedule(trainNumber);
        for (int i = 2; i < inputLines.length; i++) {
            String stationTime = inputLines[i].trim();
            if (!stationTime.isEmpty()) {
                schedule.addStationTime(stationTime);
            }
        }

        if (!line.validateScheduleOrder(schedule)) {
            return "Horário inválido.";
        }

        line.addSchedule(schedule);
        saveState();
        return "Criação de horário com sucesso.";
    }

    /**
     * Removes a line from the railway system.
     */
    private String removeLine(String lineName) {
        if (lines.remove(lineName) != null) {
            saveState();
            return "Remoção de linha com sucesso.";
        }
        return "Linha inexistente.";
    }

    /**
     * Lists the stations of a specific line.
     */
    private String listStations(String lineName) {
        Line line = lines.get(lineName);
        return line != null ? line.listStations() : "Linha inexistente.";
    }

    /**
     * Lists the lines that pass through a specific station.
     */
    private String listLinesByStation(String stationName) {
        CustomTreeSet<String> linesWithStation = new CustomTreeSet<>();
        for (String lineName : lines.keySet()) {
            Line line = lines.get(lineName);
            if (line.containsStation(stationName)) {
                linesWithStation.add(lineName);
            }
        }
        return linesWithStation.size() > 0
                ? linesWithStation.toString()
                : "Nenhuma linha passa por esta estação.";
    }

    /**
     * Removes a schedule from a specific line.
     */
    private String removeSchedule(String lineName, String scheduleLine) {
        Line line = lines.get(lineName);
        if (line == null) {
            return "Linha inexistente.";
        }

        int trainNumber;
        try {
            trainNumber = Integer.parseInt(scheduleLine.trim());
        } catch (NumberFormatException e) {
            return "Número de comboio inválido.";
        }

        if (line.removeSchedule(trainNumber)) {
            saveState();
            return "Remoção de horário com sucesso.";
        }
        return "Horário não encontrado.";
    }

    /**
     * Lists all schedules of a specific line.
     */
    private String listSchedules(String lineName) {
        Line line = lines.get(lineName);
        return line != null ? line.listSchedules() : "Linha inexistente.";
    }

    /**
     * Lists the trains by station.
     */
    private String listTrainsByStation(String stationName) {
        CustomTreeSet<String> trainsAtStation = new CustomTreeSet<>();
        for (String lineName : lines.keySet()) {
            Line line = lines.get(lineName);
            if (line.containsStation(stationName)) {
                for (Schedule schedule : line.getSchedules()) {
                    trainsAtStation.add("Comboio " + schedule.getTrainNumber() + " da linha " + lineName);
                }
            }
        }
        return trainsAtStation.size() > 0
                ? trainsAtStation.toString()
                : "Nenhum comboio disponível nesta estação.";
    }

    /**
     * Saves the current state of the railway system to a file.
     */
    private void saveState() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("railway_system.ser"))) {
            out.writeObject(this);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o estado do sistema.");
        }
    }

    /**
     * Loads the saved state of the railway system from a file.
     */
    public static RailwaySystem loadState() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("railway_system.ser"))) {
            return (RailwaySystem) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar o estado do sistema.");
            return new RailwaySystem(); // Return a new system if loading fails
        }
    }
}
