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

import java.io.File;

enum Configuration {
    DEFAULT
}

public class BigFile {
    private FileReader fileReader;
    private Parser parser;
    private FileProvider fileProvider;
    Mapper mapper;
    Buffer buffer;

    public BigFile() {
        configure(Configuration.DEFAULT);
    }

    public void configure(Configuration configuration) {
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

}
