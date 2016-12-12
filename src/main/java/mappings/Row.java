package mappings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKJ1 on 2016-12-12.
 */
public class Row {
    private List<Column<?>> columns = new ArrayList<Column<?>>();
    private long currentPosition = 0;

    public Row(long currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setPosition(long position) {
        currentPosition = position;
    }

    public long getPosition() {
        return currentPosition;
    }

    public void addColumn(Column<?> column) {
        columns.add(column);
    }

    public Column<?> getColumn(int index) {
        return columns.get(index);
    }
}
