import java.io.Serializable;

public class KeyValueStore implements Serializable {
    private String[] keys;
    private Object[] values;
    private int size;

    public KeyValueStore() {
        this.keys = new String[1000]; // Fixed size for simplicity
        this.values = new Object[1000];
        this.size = 0;
    }

    public void put(String key, Object value) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
        }
        keys[size] = key;
        values[size] = value;
        size++;
    }

    public Object get(String key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return values[i];
            }
        }
        return null;
    }

    public boolean containsKey(String key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void remove(String key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                // Shift elements to fill the gap
                for (int j = i; j < size - 1; j++) {
                    keys[j] = keys[j + 1];
                    values[j] = values[j + 1];
                }
                keys[size - 1] = null;
                values[size - 1] = null;
                size--;
                return;
            }
        }
    }
}
