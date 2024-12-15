import java.io.Serializable;

public class Line implements Serializable {
    private String name;
    private CustomTreeSet<String> stations; // TreeSet for stations
    private CustomTreeSet<Schedule> schedules; // TreeSet for schedules

    public Line(String name) {
        this.name = name;
        this.stations = new CustomTreeSet<>();
        this.schedules = new CustomTreeSet<>();
    }

    public void addStation(String station) {
        stations.add(station);
    }

    public String listStations() {
        StringBuilder result = new StringBuilder(name + "\n");
        for (String station : stations.inOrderTraversal()) {
            result.append(station).append("\n");
        }
        return result.toString();
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public boolean containsStation(String station) {
        return stations.contains(station);
    }

    public boolean removeSchedule(String stationAndTime) {
        return schedules.removeIf(schedule -> schedule.passesThrough(stationAndTime.split(" ")[0]));
    }

    public String listSchedules() {
        StringBuilder result = new StringBuilder(name + " Schedules:\n");
        for (Schedule schedule : schedules.inOrderTraversal()) {
            result.append(schedule.listStationTimes());
        }
        return result.toString();
    }

    public boolean validateScheduleOrder(Schedule schedule) {
        // Example logic: Ensure all stations in schedule exist in the correct order in
        // 'stations'.
        for (String stationTime : schedule.getStationTimes()) {
            String station = stationTime.split(" ")[0];
            if (!stations.contains(station)) {
                return false;
            }
        }
        return true;
    }

    public CustomTreeSet<String> getTrainsByStation(String stationName) {
        CustomTreeSet<String> trainNumbers = new CustomTreeSet<>();
        for (Schedule schedule : schedules.inOrderTraversal()) {
            if (schedule.passesThrough(stationName)) {
                trainNumbers.add("Train " + schedule.getTrainNumber());
            }
        }
        return trainNumbers;
    }

    public Line(String name, CustomTreeSet<String> stations) {
        this.name = name;
        this.stations = stations;
    }

    public List<Schedule> getSchedules() {
        return schedules; // Assuming 'schedules' is a list or set of schedules in the Line class
    }

}
