package io;

import mapping.Result;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKJ1 on 2016-12-12.
 */
public abstract class FileReader {

    protected RandomAccessFile in;
    protected String delimiter;
    protected long count = 0;

    public void setRandomAccessFile(RandomAccessFile in) {
        this.in = in;
    }

    public String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Result readEntry() {
        Result result = null;
        try {
            count++;
            result = new Result(count);
            String[] entry = in.readLine().split(delimiter);
            result.addEntry(entry);
        } catch (IOException e) {
            System.err.println("IOException in readEntry method: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result readEntries(long amount) {
        return readEntries(0, amount);
    }

    public Result readEntries(long first, long amount) {
        Result result = null;
        long pos = getRowNumber(first);
        try {
            in.seek(pos);
            result = new Result(in.getFilePointer());
            while(amount-- > 0) {
                String[] entry = in.readLine().split(delimiter);
                result.addEntry(entry);
            }
        } catch (IOException e) {
            System.err.println("IOException in readEntries method: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private long getRowNumber(long nr) {
        try (RandomAccessFile raf = new RandomAccessFile("positions.out","r")){
            raf.seek(nr*8);
            return raf.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public long[] getNumberOfRowsAndRowsPositions() throws IOException {
        in.seek(0);
        List<Long> positions = new ArrayList<>();
        long numberOfRows = 0;
        while (in.readLine() != null) {
            numberOfRows++;
            positions.add(in.getFilePointer());
        }
        positions.remove(positions.size() - 1);
        positions.add(numberOfRows);

        in.seek(0);
        return positions.stream().mapToLong(i -> i).toArray();
    }

    public String[] getColumnTypes() throws IOException {
        in.seek(0);
        String line = in.readLine();
        String columns[] = line.split(",");
        int numberOfColumns = columns.length;
        String columnTypes[] = new String[numberOfColumns];
        int i = 0;
        for (String value : columns) {
            columnTypes[i] = value.split(":")[1];
            i++;
        }
        in.seek(0);
        return columnTypes;
    }

    public String[] getFirstFiveRows() throws IOException {
        String rows[] = new String[5];
        in.seek(0);
        String line = in.readLine();
        for (int i = 0; i < 5; i++) {
            rows[i] = in.readLine();
        }
        in.seek(0);
        return rows;
    }

    public Result peekEntry() {
        Result result = null;
        try {
            long currentPosition = in.getFilePointer();
            result = new Result(currentPosition);
            String[] entry = in.readLine().split(delimiter);
            result.addEntry(entry);
            in.seek(currentPosition);
        } catch (IOException e) {
            System.err.println("IOException in peekEntry method: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result peekEntries(long amount) {
        Result result = null;
        try {
            long currentPosition = in.getFilePointer();
            result = new Result(currentPosition);
            while(amount-- > 0) {
                String[] entry = in.readLine().split(delimiter);
                result.addEntry(entry);
            }
            in.seek(currentPosition);
        } catch (IOException e) {
            System.err.println("IOException in peekEntries method: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public void moveToAnEntry(long index) {
        throw new NotImplementedException();
    }
}
