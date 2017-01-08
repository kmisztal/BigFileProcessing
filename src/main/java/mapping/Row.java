package mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKJ1 on 2016-12-12.
 */
public class Row {
    private List<ColumnData> columnDatas = new ArrayList<ColumnData>();
    private long position = 0;

    public Row(long position) {
        this.position = position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getPosition() {
        return position;
    }

    public void addColumnData(ColumnData columnData) {
        columnDatas.add(columnData);
    }

    public ColumnData getColumnData(int index) {
        return columnDatas.get(index);
    }
}
