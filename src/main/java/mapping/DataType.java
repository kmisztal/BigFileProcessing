package mapping;

/**
 * Created by Andrzej on 2016-12-19.
 */
public enum DataType {
    INT, LONG, FLOAT, DOUBLE, STRING, TIMESTAMP, STATE;
    public static DataType getDataType(String type) {
        DataType dataType;
        switch (type) {
            case "int":
                dataType = DataType.INT;
                break;
            case "long":
                dataType = DataType.LONG;
                break;
            case "float":
                dataType = DataType.FLOAT;
                break;
            case "double":
                dataType = DataType.DOUBLE;
                break;
            case "string":
                dataType = DataType.STRING;
                break;
            case "timestamp":
                dataType = DataType.TIMESTAMP;
                break;
            case "state":
                dataType = DataType.STATE;
                break;
            default:
                throw new RuntimeException("No such data type: " + type);
        }
        return dataType;
    }
}
