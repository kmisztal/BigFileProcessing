package parsing;

import mapping.Metadata;
import mapping.Result;
import parsing.Parser;

/**
 * Created by Andrzej on 2016-12-20.
 */
public class DefaultParser implements Parser {
    @Override
    public boolean parse(Result result, Metadata metadata) {
        return true;
    }
}
