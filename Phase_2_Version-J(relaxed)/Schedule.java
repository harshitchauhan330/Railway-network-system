import java.io.Serializable;

public class Schedule implements Serializable {
    private String lineName;
    private int trainNumber;
    private String[] stationTimes; // Format: "Station hh:mm"
    private int count;

    public Schedule(String lineName, int trainNumber) {
        this.lineName = lineName;
        this.trainNumber = trainNumber;
        this.stationTimes = new String[100]; // Assuming max stops
        this.count = 0;
    }

    // Add a station and time to the schedule
    public void addStationTime(String stationTime) {
        stationTimes[count++] = stationTime;
    }

    // Check if the train passes through a specific station
    public boolean passesThrough(String station) {
        for (int i = 0; i < count; i++) {
            String stopStation = stationTimes[i].split(" ")[0];
            if (stopStation.equalsIgnoreCase(station)) {
                return true;
            }
        }
        return false;
    }

    // Get train information as a formatted string
    public String getTrainInfo() {
        StringBuilder info = new StringBuilder("Train " + trainNumber + " on " + lineName + ": ");
        for (int i = 0; i < count; i++) {
            info.append(stationTimes[i]);
            if (i < count - 1) {
                info.append(", ");
            }
        }
        return info.toString();
    }

    // Validate if times are strictly increasing
    public boolean validate() {
        for (int i = 1; i < count; i++) {
            String time1 = stationTimes[i - 1].split(" ")[1];
            String time2 = stationTimes[i].split(" ")[1];
            if (!isTimeIncreasing(time1, time2)) {
                return false;
            }
        }
        return true;
    }

    private boolean isTimeIncreasing(String time1, String time2) {
        int t1 = Integer.parseInt(time1.replace(":", ""));
        int t2 = Integer.parseInt(time2.replace(":", ""));
        return t1 < t2;
    }
}
