package parsing;

import mapping.Metadata;
import mapping.Result;

/**
 * Created by Andrzej on 2016-12-19.
 */
public interface Parser {
    boolean parse(Result result, Metadata metadata);
}
