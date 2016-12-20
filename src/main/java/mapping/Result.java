package mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrzej on 2016-12-19.
 */
public class Result {
    private List<String[]> entries = new ArrayList<>();
    private long position;

    public Result(long position) {
        this.position = position;
    }

    public long getPosition() { return this.position; }

    public void addEntry(String[] entry) {
        entries.add(entry);
    }

    public String get(int i, int j) {
        return entries.get(i)[j];
    }

    public String[] get(int i) {
        return entries.get(i);
    }

    public List<String[]> getAll() {
        return entries;
    }
}
