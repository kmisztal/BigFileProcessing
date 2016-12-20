package mapping;

import java.util.*;

/**
 * Created by Andrzej on 2016-12-19.
 */
public class Buffer {
    private Queue<Row> rows = new LinkedList<>();
    private Mapper mapper;

    private final long DEFAULT_BUFFER_SIZE = 5;
    private long bufferSize = DEFAULT_BUFFER_SIZE;

    public Buffer(long bufferSize) {
        this.bufferSize = bufferSize;
    }

    public Buffer(Mapper mapper) {
        this.mapper = mapper;
    }

    public Buffer(long bufferSize, Mapper mapper) {
        this.bufferSize = bufferSize;
        this.mapper = mapper;
    }

    public Buffer() {}

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public void setBufferSize(long bufferSize) {
        this.bufferSize = bufferSize;
    }

    public long getBufferSize() {
        return bufferSize;
    }

    public void offer(Row row) {
        rows.offer(row);
    }

    public void offer(Queue<Row> rows) {
        this.rows = rows;
    }

    public Row poll() {
        load();
        return rows.poll();
    }

    public Row peek() {
        load();
        return rows.peek();
    }

    public void load() {
        if (rows.size() == 0)
            mapper.putIntoBuffer(this);
    }
}
