package io;

import mapping.Result;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by AKJ1 on 2016-12-12.
 */
public abstract class FileReader {
    protected RandomAccessFile in;
    protected String delimiter;

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
            result = new Result(in.getFilePointer());
            String[] entry = in.readLine().split(delimiter);
            result.addEntry(entry);
        } catch (IOException e) {
            System.err.println("IOException in readEntry method: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result readEntries(long amount) {
        Result result = null;
        try {
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
