import java.io.Serializable;

public class Schedule implements Serializable, Comparable<Schedule> {
    private int trainNumber;
    private CustomTreeSet<String> stationTimes;

    public Schedule(int trainNumber) {
        this.trainNumber = trainNumber;
        this.stationTimes = new CustomTreeSet<>();
    }

    public void addStationTime(String stationTime) {
        stationTimes.add(stationTime);
    }

    public boolean passesThrough(String station) {
        for (String stationTime : stationTimes.inOrderTraversal()) {
            if (stationTime.split(" ")[0].equalsIgnoreCase(station)) {
                return true;
            }
        }
        return false;
    }

    public String listStationTimes() {
        StringBuilder result = new StringBuilder("Train " + trainNumber + "\n");
        for (String stationTime : stationTimes.inOrderTraversal()) {
            result.append(stationTime).append("\n");
        }
        return result.toString();
    }

    public Iterable<String> getStationTimes() {
        return stationTimes.inOrderTraversal();
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    @Override
    public int compareTo(Schedule other) {
        return Integer.compare(this.trainNumber, other.trainNumber);
    }
}
