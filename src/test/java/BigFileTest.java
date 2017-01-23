import mapping.DataType;
import mapping.Header;
import mapping.Metadata;
import mapping.Row;
import org.junit.Assert;
import org.junit.Test;
import parsing.ParsingException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017-01-08.
 */
public class BigFileTest {

    private static final int numberOfRowsToRead = 5;

    @Test
    public void metadataTest() throws ParsingException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("testFile.csv").getFile());
        BigFile bg = new BigFile();
        bg.load(file, numberOfRowsToRead);
        Metadata metadata = bg.mapper.getMetadata();
        Assert.assertEquals(metadata.getNumberOfColumns(), 3);
        Header headerString = new Header("Name", DataType.STRING);
        Header headerInt = new Header("Age", DataType.INT);
        Header headerDouble = new Header("Salary", DataType.DOUBLE);
        ArrayList<Header> headers = new ArrayList<>();
        headers.add(headerString);
        headers.add(headerInt);
        headers.add(headerDouble);
        Assert.assertEquals(headers, metadata.getHeaders());
    }

    @Test
    public void bigFileTestSuccess() throws ParsingException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("testFile.csv").getFile());
        BigFile bg = new BigFile();
        bg.load(file, numberOfRowsToRead);
        Metadata metadata = bg.mapper.getMetadata();
        int expectedNumberOfColumns = metadata.getNumberOfColumns();
        List<Row> rows = bg.buffer.getRows();
        Assert.assertEquals(bg.buffer.getBufferSize(), rows.size());
        Assert.assertEquals(expectedNumberOfColumns, rows.get(0).getColumnDatas().size());

    }

    @Test
    public void bigFileTestParsingException() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("wrongFile.csv").getFile());
            BigFile bg = new BigFile();
            bg.load(file, numberOfRowsToRead);
        } catch (final ParsingException e) {
            final String msg = "Cannot parse line number " + 1;
            Assert.assertEquals(msg, e.getMessage());
        }
    }

    @Test
    public void generate() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("testFile.csv").getFile());
        BigFile bg = new BigFile();
        bg.examine(file);

        //bg.readFromPositionsFile("positions.out");

        bg.fileReader.readEntries(1,3).getAll().forEach(a -> {
            for (String s: a) {
                System.out.print(s + ";");
            }
            System.out.println();
        });


    }


}
