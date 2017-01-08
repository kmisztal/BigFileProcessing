package mapping;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrzej on 2016-12-19.
 */
public class Metadata {
    private List<Header> headers = new ArrayList<>();

    private final String columnNameTypeDelimiter = ":";

    public Metadata(String line) {
        String delimiter = Utils.getDelimiter(line);
        String[] headers = line.split(delimiter);
        if (line.contains(columnNameTypeDelimiter))
            findOutHeadersWithTheirsKnownTypes(headers);
        else
            findOutHeadersWithTheirsUnknownTypes(headers);
    }

    public List<Header> getHeaders() {
        return headers;
    }

    private void findOutHeadersWithTheirsKnownTypes(String[] columnNameTypePairs) {
        for(int i = 0; i < columnNameTypePairs.length; i++) {
            String[] columnNameTypePair = columnNameTypePairs[i].split(columnNameTypeDelimiter);
            String name = columnNameTypePair[0];
            String type = columnNameTypePair[1];
            DataType dataType = DataType.getDataType(type);
            headers.add(new Header(name, dataType));
        }
    }

    private void findOutHeadersWithTheirsUnknownTypes(String[] headers) {
        throw new NotImplementedException();
    }

    public Header getHeader(int index) {
        return headers.get(index);
    }

    public int getNumberOfColumns() {
        return headers.size();
    }

}
