import io.FileProvider;
import io.FileReader;
import io.LocalFileProvider;
import io.VariableFileReaderWithHeader;
import mapping.Buffer;
import mapping.Mapper;
import parsing.DefaultParser;
import parsing.Parser;

enum Configuration {
    DEFAULT;
}

public class BigFile {
    private FileReader fileReader;
    private Parser parser;
    private FileProvider fileProvider;
    private Mapper mapper = new Mapper();
    private Buffer buffer = new Buffer();

    public BigFile() {
        configure(Configuration.DEFAULT);
    }

    public void configure(Configuration configuration) {
        if (configuration == Configuration.DEFAULT) {
            fileProvider = new LocalFileProvider();
            fileReader = new VariableFileReaderWithHeader();
            parser = new DefaultParser();
        }
    }

    public void load(String path) {
        fileReader.setRandomAccessFile(fileProvider.obtainFileHandler(path));
        mapper.setFileReader(fileReader);
        mapper.setParser(parser);
        buffer.setMapper(mapper);
        buffer.load();
    }

}
