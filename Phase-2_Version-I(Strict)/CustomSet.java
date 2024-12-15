import java.io.Serializable;

public class CustomSet<T> implements Serializable {
    private CustomDictionary<T, Boolean> map;

    public CustomSet() {
        map = new CustomDictionary<>();
    }

    public void add(T value) {
        if (!map.containsKey(value)) {
            map.put(value, true);
        }
    }

    public boolean contains(T value) {
        return map.containsKey(value);
    }
}
