package mappings;

/**
 * Created by AKJ1 on 2016-12-12.
 */
public class Column<T> {
    private String name;
    private long index;
    private T data;

    public Column(String name, long index, T data) {
        this.setName(name);
        this.setIndex(index);
        this.setData(data);
    }

    public Column() {}

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
