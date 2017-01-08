package parsing;

/**
 * Created by Andrzej on 2016-12-20.
 */
public class ParsingException extends Exception {
    private long lineNumber;

    public ParsingException(long lineNumber) {
        this.lineNumber = lineNumber;
    }
    @Override
    public String getMessage() {
        return "Cannot parse line number " + lineNumber;
    }
}
