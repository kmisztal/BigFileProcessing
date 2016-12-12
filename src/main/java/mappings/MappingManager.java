package mappings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKJ1 on 2016-12-12.
 */
public class MappingManager implements Closeable {
    private BigFileReader reader;
    private List<Row> rows = new ArrayList<>();
    private long currentPosition = 0;
    private long bufferSize = 5;

    public MappingManager(String fileName) throws FileNotFoundException {
        reader = new BigFileReader(fileName);
    }
    public MappingManager(String fileName, long bufferSize) throws FileNotFoundException {
        reader = new BigFileReader(fileName);
        this.bufferSize = bufferSize;
    }

    public void loadFile() {

    }

    public void next() {

    }

    public void next(long position) {

    }

    public void nextLine() {

    }

    private void mapFile() {

    }

    private void mapRow() {

    }

    public void setBufferSize(long bufferSize) {
        this.bufferSize = bufferSize;
    }

    public long getBufferSize() {
        return bufferSize;
    }

    @Override
    public void close() {
        reader.close();
    }
}
