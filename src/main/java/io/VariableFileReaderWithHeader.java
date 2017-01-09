package io;

import util.Utils;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Andrzej on 2016-12-19.
 */
public class VariableFileReaderWithHeader extends FileReader {

    public VariableFileReaderWithHeader(RandomAccessFile in) {
        this.in = in;
    }

    public VariableFileReaderWithHeader(String delimiter) {
        this.delimiter = delimiter;
    }

    private void init() {
        try {
            in.seek(0L);
            in.readLine();
            long position = in.getFilePointer();
            delimiter = Utils.getDelimiter(in.readLine());
            in.seek(position);

        } catch (IOException e) {
            System.err.println("Error occured during initialization of VariableFileReaderWithHeader: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
