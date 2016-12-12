package mappings;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by AKJ1 on 2016-12-12.
 */
public class BigFileReader implements Closeable {
    private RandomAccessFile fileReader;
    private String delimiter;
    private long firstLineLength;
    private long lineLength;

    public BigFileReader(String fileName) throws FileNotFoundException {
        fileReader = new RandomAccessFile(fileName, "rw");
        obtainFirstLineLength();
        obtainLineLength();
    }

    public String getDelimiter() {

    }

    public void navigateToFirstLine() throws IOException {
        fileReader.seek(0);
    }

    public String read() {

    }

    public String read(long position) {

    }

    public String[] readLines(long amount) {

    }

    public String[] readLines(long amount, long position) {

    }

    public String peek() {

    }

    public String peek(long position) {

    }

    public String[] peekLines(long amount) {

    }

    public String[] peekLines(long amount, long position) {

    }

    private void skipFirstLine() {
        try {
            if (isOnFirstLine()) {
                fileReader.seek(0);
                fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void obtainFirstLineLength() {
        try {
            navigateToFirstLine();
            firstLineLength = fileReader.readLine().length();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnFirstLine() throws IOException {
         return fileReader.getFilePointer() < firstLineLength;
    }

    private void obtainLineLength() {
        skipFirstLine();
        try {
            lineLength = fileReader.readLine().length();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
