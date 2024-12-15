import java.io.Serializable;

public class Schedule implements Serializable {
    private int trainNumber;
    private CustomArrayList<String> stationTimes;

    public Schedule(int trainNumber) {
        this.trainNumber = trainNumber;
        this.stationTimes = new CustomArrayList<>();
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public CustomArrayList<String> getStationTimes() {
        return stationTimes;
    }

    public void addStationTime(String stationTime) {
        stationTimes.add(stationTime);
    }

    public void removeStationTime(int index) {
        stationTimes.remove(index);
    }

    public boolean passesThrough(String station) {
        for (int i = 0; i < stationTimes.size(); i++) {
            if (stationTimes.get(i).split(" ")[0].equalsIgnoreCase(station)) {
                return true;
            }
        }
        return false;
    }

    public String listStationTimes() {
        StringBuilder result = new StringBuilder("Train " + trainNumber + "\n");
        for (int i = 0; i < stationTimes.size(); i++) {
            result.append(stationTimes.get(i)).append("\n");
        }
        return result.toString();
    }
}
