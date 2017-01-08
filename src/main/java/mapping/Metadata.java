package mapping;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrzej on 2016-12-19.
 */
public class Metadata {
    private Map<Integer, Header> headers = new HashMap<>();
    private final String columnNameTypeDelimiter = ":";
    public class Header {
        private String name;
        private DataType type;

        public Header(String name, DataType type) {
            this.setName(name);
            this.setType(type);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DataType getType() {
            return type;
        }

        public void setType(DataType type) {
            this.type = type;
        }
    }

    public Metadata(String line) {
        String delimiter = Utils.getDelimiter(line);
        String[] headers = line.split(delimiter);
        if (line.contains(columnNameTypeDelimiter))
            findOutHeadersWithTheirsKnownTypes(headers);
        else
            findOutHeadersWithTheirsUnknownTypes(headers);
    }

    private void findOutHeadersWithTheirsKnownTypes(String[] columnNameTypePairs) {
        for(int i = 0; i < columnNameTypePairs.length; i++) {
            String[] columnNameTypePair = columnNameTypePairs[i].split(columnNameTypeDelimiter);
            String name = columnNameTypePair[0];
            String type = columnNameTypePair[1];
            DataType dataType = DataType.getDataType(type);
            headers.put(i, new Header(name, dataType));
        }
    }

    private void findOutHeadersWithTheirsUnknownTypes(String[] headers) {
        throw new NotImplementedException();
    }

    public Metadata.Header getHeader(int index) {
        return headers.get(index);
    }
}
