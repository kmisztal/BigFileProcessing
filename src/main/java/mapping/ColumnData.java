package mapping;

/**
 * Created by AKJ1 on 2016-12-12.
 */
public class ColumnData {
    private String name;
    private long index;
    private Object data;

    public ColumnData(String name, long index, Object data) {
        this.setName(name);
        this.setIndex(index);
        this.setData(data);
    }

    public ColumnData() {}

    @Override
    public String toString() {
        return getData().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
