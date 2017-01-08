package io;

/**
 * Created by Andrzej on 2016-12-19.
 */
public enum Delimiter {
    COMMA(","), COLON(":"), SEMICOLON(";"), TABULATOR("\\t"), SPACE(" ");

    private String delimiterSymbol;
    Delimiter(String delimiterSymbol) {
        this.delimiterSymbol = delimiterSymbol;
    }
    public String getDelimiter() {
        return this.delimiterSymbol;
    }
}
