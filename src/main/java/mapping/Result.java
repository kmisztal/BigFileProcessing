package mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrzej on 2016-12-19.
 */
public class Result {
    private List<String[]> entries = new ArrayList<>();
    private long rowNumber;

    public Result(long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public long getRowNumber() {
        return this.rowNumber;
    }

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
