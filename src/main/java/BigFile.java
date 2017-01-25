import io.FileProvider;
import io.FileReader;
import io.LocalFileProvider;
import io.VariableFileReaderWithHeader;
import mapping.Buffer;
import mapping.Mapper;
import mapping.Result;
import parsing.DefaultParser;
import parsing.Parser;
import parsing.ParsingException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

enum Configuration {
    DEFAULT
}

public class BigFile {
    FileReader fileReader;
    private Parser parser;
    FileProvider fileProvider;
    Mapper mapper;
    Buffer buffer;

    BigFile() {
        configure(Configuration.DEFAULT);
    }

    private void configure(Configuration configuration) {
        if (configuration == Configuration.DEFAULT) {
            fileProvider = new LocalFileProvider();
            fileReader = new VariableFileReaderWithHeader(",");
            parser = new DefaultParser();
        } else
            throw new NotImplementedException();
    }

    public void load(File path, long numberOfRowsToRead) throws ParsingException {
        fileReader.setRandomAccessFile(fileProvider.obtainFileHandler(path));
        mapper = new Mapper(fileReader, parser);
        buffer = new Buffer(mapper);
        buffer.setBufferSize(numberOfRowsToRead);
        buffer.load();
    }

    void examine(File path) throws IOException {
        fileReader.setRandomAccessFile(fileProvider.obtainFileHandler(path));
        long positions[] = fileReader.getNumberOfRowsAndRowsPositions();
        String columnTypes[] = fileReader.getColumnTypes();
        String firstFiveRows[] = fileReader.getFirstFiveRows();
        createPropertiesFile(positions, columnTypes, firstFiveRows);
        createPositionsFile(positions);

    }

    private void createPropertiesFile(long[] positions, String[] columnTypes, String[] firstFiveRows) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("numberOfRows=" + positions[positions.length - 1] + "\n");
        sb.append("numberOfColumns=" + columnTypes.length + "\n");
        for (int i = 0; i < columnTypes.length; i++) {
            sb.append("column" + i + "=" + columnTypes[i] + "\n");
        }
        for (int i = 0; i < 5; i++) {
            sb.append("//" + firstFiveRows[i] + "\n");
        }
        File file = new File("properties.txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(sb.toString());
        } finally {
            if (writer != null) writer.close();
        }
    }

    private void createPositionsFile(long[] positions) throws IOException {
        FileOutputStream out = null;

        ByteBuffer buffer = ByteBuffer.allocate(8 * positions.length);
        for (int i = 0; i < positions.length - 1; i++) {
            buffer.putLong(positions[i]);
        }
        buffer.rewind();

        try {
            out = new FileOutputStream("positions.out");
            FileChannel file = out.getChannel();
            file.write(buffer);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private boolean check_type(String tmp) {
        switch (tmp) {
            case "int":
            case "long":
            case "float":
            case "double":
                break;
            default:
                throw new RuntimeException("No such data type: " + tmp);
        }
        return true;
    }

    private long read_prop(long lastRow, long columnNumber) throws IOException {
        long rows = 0;
        long columns;
        try (java.io.FileReader fis = new java.io.FileReader("properties.txt")) {
            BufferedReader in = new BufferedReader(fis);
            rows = Long.parseLong(in.readLine().split("=")[1]) - 1;
            rows = rows > lastRow ? lastRow : rows;
            columns = Long.parseLong(in.readLine().split("=")[1]);
            if (columns < columnNumber) throw new IndexOutOfBoundsException();
            String type = "";
            for (int i = 0; i < columnNumber; i++) {
                type = in.readLine().split("=")[1];
            }
            check_type(type);
        } catch (FileNotFoundException e) {
            System.out.println("File properties.txt not found");
        }
        return rows;
    }

    public double stats_AVG(long firstRow, long lastRow, int columnNumber) throws IOException {
        long rows = read_prop(lastRow, columnNumber);
        double output = 0.0;
        long i = firstRow;
        for (; i <= rows - 100; i += 100) {
            Result R = fileReader.readEntries(i, 100);
            for (int j = 0; j < 100; j++) {
                output += Double.parseDouble(R.get(j, columnNumber));
            }
        }
        if (rows - i > 0) {
            Result R = fileReader.readEntries(i, rows - i);
            for (int j = 0; j < rows - i; j++) {
                output += Double.parseDouble(R.get(j, columnNumber));
            }
        }
        return output / (rows - firstRow);
    }

    public double[] stats_MINMAX(long firstRow, long lastRow, int columnNumber) throws IOException {
        long rows = read_prop(lastRow, columnNumber);
        double[] results = new double[2];
        results[0] = (double) Integer.MIN_VALUE;
        results[1] = (double) Integer.MAX_VALUE;
        long i = firstRow;
        double curr;
        for (; i <= rows - 100; i += 100) {
            Result R = fileReader.readEntries(i, 100);
            for (int j = 0; j < 100; j++) {
                curr = Double.parseDouble(R.get(j, columnNumber));
                if (curr < results[0]) results[0] = curr;
                else if (curr > results[1]) results[1] = curr;
            }
        }
        if (rows - i > 0) {
            Result R = fileReader.readEntries(i, rows - i);
            for (int j = 0; j < rows - i; j++) {
                curr = Double.parseDouble(R.get(j, columnNumber));
                if (curr < results[0]) results[0] = curr;
                else if (curr > results[1]) results[1] = curr;
            }
        }
        return results;
    }

    public double stats_STD(long firstRow, long lastRow, int columnNumber) throws IOException {
        long rows = read_prop(lastRow, columnNumber);
        double avg = 0.0;
        double Xsqr = 0.0;
        long i = firstRow;
        for (; i <= rows - 100; i += 100) {
            Result R = fileReader.readEntries(i, 100);
            for (int j = 0; j < 100; j++) {
                double var = Double.parseDouble(R.get(j, columnNumber));
                avg += var / (rows - firstRow);
                Xsqr += var * var / (rows - firstRow);
            }
        }
        if (rows - i > 0) {
            Result R = fileReader.readEntries(i, rows - i);
            for (int j = 0; j < rows - i; j++) {
                double var = Double.parseDouble(R.get(j, columnNumber));
                avg += var / (rows - firstRow);
                Xsqr += var * var / (rows - firstRow);
            }
        }
        return Math.sqrt(Xsqr - avg * avg);
    }


    //can read like this
    public void readFromPositionsFile(String file) throws IOException {
        Long output;
        FileInputStream fis = new FileInputStream(file);
        DataInputStream in = new DataInputStream(fis);
        while ((output = in.readLong()) > 0) {
            System.out.println(output);
        }

    }

}
