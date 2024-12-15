
import java.io.*;
import java.util.*;

public class RailwaySystem implements Serializable {
    private Map<String, Line> lines; // Stores all lines by their name

    public RailwaySystem() {
        lines = new HashMap<>();
    }

    public void addLine(String name, List<String> stations) throws IllegalArgumentException {
        if (lines.containsKey(name.toLowerCase())) {
            throw new IllegalArgumentException("Linha existente.");
        }
        lines.put(name.toLowerCase(), new Line(name, stations));
    }

    public void listLinesByStation(String station) {
        Set<String> result = new TreeSet<>();
        for (Line line : lines.values()) {
            if (line.containsStation(station)) {
                result.add(line.getName());
            }
        }
        if (result.isEmpty()) {
            System.out.println("Estação inexistente.");
        } else {
            System.out.println(station);
            result.forEach(System.out::println);
        }
    }

    public void listTrainsByStation(String station) {
        List<String> result = new ArrayList<>();
        for (Line line : lines.values()) {
            result.addAll(line.getTrainsByStation(station));
        }
        if (result.isEmpty()) {
            System.out.println("Estação inexistente.");
        } else {
            result.sort(Comparator.comparingInt(String::hashCode)); // Sort logic example
            result.forEach(System.out::println);
        }
    }

    public void findBestTimetable(String lineName, String departureStation, String destinationStation,
            String expectedArrival) {
        Line line = lines.get(lineName.toLowerCase());
        if (line == null) {
            System.out.println("Linha inexistente.");
            return;
        }
        String result = line.getBestTimetable(departureStation, destinationStation, expectedArrival);
        if (result == null) {
            System.out.println("Percurso impossível.");
        } else {
            System.out.println(result);
        }
    }

    public void saveState(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }

    public static RailwaySystem loadState(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (RailwaySystem) ois.readObject();
        }
    }
}
