package mapping;

import io.FileReader;
import parsing.Parser;
import parsing.ParsingException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Andrzej on 2016-12-19.
 */
public class Mapper {
    private FileReader reader;
    private Parser parser;
    private Metadata metadata;

    public Mapper(FileReader reader, Parser parser) {
        this.reader = reader;
        this.parser = parser;
        obtainMetadata();
    }
    public Mapper() { obtainMetadata(); }

    public void setFileReader(FileReader reader) {
        this.reader = reader;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public void putIntoBuffer(Buffer buffer) {
        Queue<Row> rows = processEntitiesIntoRows(buffer.getBufferSize());
        buffer.offer(rows);
    }

    public Queue<Row> processEntitiesIntoRows(long amount) {
        Queue<Row> rows = new LinkedList<>();
        while(amount-- > 0) {
            try {
                rows.add(getRowFromReader());
            } catch (ParsingException e) {
                e.printStackTrace();
            }
        }
        return rows;
    }

    public Row getRowFromReader() throws ParsingException {
        Result result = reader.readEntry();
        if (!parser.parse(result, metadata))
            throw new ParsingException(result.getPosition());
        Row row = new Row(result.getPosition());
        int position = 0;
        for (String data : result.get(0)) {
            row.addColumnData(getColumnData(position++, data));
        }
        return row;
    }

    private void obtainMetadata() {
        metadata = new Metadata(reader.readLine());
    }

    private ColumnData getColumnData(int position, String value) {
        Metadata.Header header = metadata.getHeader(position);
        ColumnData columnData = new ColumnData();
        columnData.setIndex(position);
        columnData.setName(header.getName());
        switch(header.getType()) {
            case INT:
                columnData.setData(Integer.valueOf(value));
                break;
            case LONG:
                columnData.setData(Long.valueOf(value));
                break;
            case FLOAT:
                columnData.setData(Float.valueOf(value));
                break;
            case DOUBLE:
                columnData.setData(Double.valueOf(value));
                break;
            case STRING:
                columnData.setData(value);
                break;
            case TIMESTAMP:
                columnData.setData(LocalDate.parse(value));
                break;
            case STATE:
                columnData.setData(value);
                break;
        }
        return columnData;
    }

}
