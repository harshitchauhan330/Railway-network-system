import java.io.Serializable;

public class Line implements Serializable {
    private String name;
    private CustomArrayList<String> stations;
    private CustomArrayList<Schedule> schedules;

    public Line(String name, CustomArrayList<String> stations) {
        this.name = name;
        this.stations = stations;
        this.schedules = new CustomArrayList<>();
    }

    // Getter for line name
    public String getName() {
        return name;
    }

    // Getter for stations
    public CustomArrayList<String> getStations() {
        return stations; // Return the list of stations
    }

    // Getter for schedules
    public CustomArrayList<Schedule> getSchedules() {
        return schedules; // Return the list of schedules
    }

    // Adds a new schedule to the line
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    // Checks if a station exists on the line
    public boolean containsStation(String station) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).equalsIgnoreCase(station)) {
                return true;
            }
        }
        return false;
    }

    // Lists all stations in the line
    public String listStations() {
        StringBuilder result = new StringBuilder(name + "\n");
        for (int i = 0; i < stations.size(); i++) {
            result.append(stations.get(i)).append("\n");
        }
        return result.toString();
    }

    // Validates if a schedule's stations match the line's station order
    // In Line.java, change validateScheduleOrder method:
    public boolean validateScheduleOrder(Schedule schedule) {
        int lastIndex = -1;

        // Iterate over each station-time entry in the schedule
        for (int i = 0; i < schedule.getStationTimes().size(); i++) {
            // Extract only the station name (the first part before the space)
            String stationTime = schedule.getStationTimes().get(i).trim(); // Get the station-time entry
            String station = stationTime.split(" ")[0].trim(); // Split by space and take the first part (station name)

            // Look for the station in the line's list of stations
            int currentIndex = stations.indexOf(station);

            // Check if the station is found and the order is correct
            if (currentIndex == -1 || currentIndex <= lastIndex) {
                return false; // Invalid order or station not found
            }

            lastIndex = currentIndex; // Update lastIndex for the next station comparison
        }

        return true; // All stations are in the correct order
    }

}
