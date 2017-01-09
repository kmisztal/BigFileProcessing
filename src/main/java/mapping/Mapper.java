package mapping;

import io.FileReader;
import parsing.Parser;
import parsing.ParsingException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public Metadata getMetadata() {
        return metadata;
    }

    public void putIntoBuffer(Buffer buffer) throws ParsingException {
        List<Row> rows = processEntitiesIntoRows(buffer.getBufferSize());
        buffer.add(rows);
    }

    public List<Row> processEntitiesIntoRows(long amount) throws ParsingException {
        List<Row> rows = new ArrayList<>();
        while(amount-- > 0) {
            rows.add(getRowFromReader());
        }
        return rows;
    }

    public Row getRowFromReader() throws ParsingException {
        Result result = reader.readEntry();
        if (!parser.parse(result, metadata))
            throw new ParsingException(result.getRowNumber());
        Row row = new Row(result.getRowNumber());
        int columnNumber = 0;
        for (String data : result.get(0)) {
            row.addColumnData(getColumnData(row.getRowNumber(), columnNumber++, data));
        }
        return row;
    }

    private ColumnData getColumnData(long rowNumber, int columnNumber, String value) throws ParsingException {
        Header header = metadata.getHeader(columnNumber);
        ColumnData columnData = new ColumnData();
        columnData.setIndex(columnNumber);
        columnData.setName(header.getName());
        try {
            switch (header.getType()) {
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
        } catch (Exception e) {
            throw new ParsingException(rowNumber);
        }
        return columnData;
    }

    private void obtainMetadata() {
        metadata = new Metadata(reader.readLine());
    }

}
