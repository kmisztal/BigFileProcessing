package mapping;

import parsing.ParsingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrzej on 2016-12-19.
 */
public class Buffer {
    public List<Row> getRows() {
        return rows;
    }

    private List<Row> rows = new ArrayList<>();
    private Mapper mapper;

    private long bufferSize;

    public Buffer(Mapper mapper) {
        this.mapper = mapper;
    }


    public void setBufferSize(long bufferSize) {
        this.bufferSize = bufferSize;
    }

    public long getBufferSize() {
        return bufferSize;
    }

    public void add(List<Row> rows) {
        this.rows = rows;
    }


    public void load() throws ParsingException {
        if (rows.size() == 0)
            mapper.putIntoBuffer(this);
    }
}
