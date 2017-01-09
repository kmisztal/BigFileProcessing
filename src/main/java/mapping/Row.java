package mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKJ1 on 2016-12-12.
 */
public class Row {
    private List<ColumnData> columnDatas = new ArrayList<ColumnData>();

    private long rowNumber = 0;

    public Row(long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setRowNumber(long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public long getRowNumber() {
        return rowNumber;
    }

    public void addColumnData(ColumnData columnData) {
        columnDatas.add(columnData);
    }

    public List<ColumnData> getColumnDatas() {
        return columnDatas;
    }


    public ColumnData getColumnData(int index) {
        return columnDatas.get(index);
    }
}
