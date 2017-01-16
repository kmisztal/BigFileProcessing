import io.FileProvider;
import io.FileReader;
import io.LocalFileProvider;
import io.VariableFileReaderWithHeader;
import mapping.Buffer;
import mapping.Mapper;
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
        }
        else
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
