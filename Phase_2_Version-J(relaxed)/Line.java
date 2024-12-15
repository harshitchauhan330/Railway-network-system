
import java.io.*;
import java.util.*;

public class Line implements Serializable {
    private String name;
    private List<String> stations;
    private List<Schedule> schedules;

    public Line(String name, List<String> stations) {
        this.name = name;
        this.stations = new ArrayList<>(stations);
        this.schedules = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean containsStation(String station) {
        return stations.stream().anyMatch(s -> s.equalsIgnoreCase(station));
    }

    public List<String> getTrainsByStation(String station) {
        List<String> result = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.passesThrough(station)) {
                result.add(schedule.getTrainInfo());
            }
        }
        return result;
    }

    public String getBestTimetable(String departure, String destination, String expectedArrival) {
        // Logic for finding the best timetable for a given line and stations
        // Placeholder for Phase 2 detailed implementation
        return null;
    }
}
