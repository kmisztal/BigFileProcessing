package io;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by Andrzej on 2016-12-19.
 */
public interface FileProvider {
    RandomAccessFile obtainFileHandler(File path);
}
