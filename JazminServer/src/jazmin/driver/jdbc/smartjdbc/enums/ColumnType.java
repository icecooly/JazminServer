package jazmin.driver.jdbc.smartjdbc.enums;


/**
 * 
 * @author skydu
 *
 */
public enum ColumnType {
	NONE,
	INT,
    BIGINT,
    TINYINT,
    SMALLINT,
    MEDIUMINT,
    LONG,
    FLOAT,
    DOUBLE,
    BIGDECIMAL,
    CHAR,
    VARCHAR,
    TEXT,
    MEDIUMTEXT,
    DATETIME,
    JSONB,
    TEXT_ARRAY,
    VARCHAR_ARRAY,
    INT_ARRAY,
    FLOAT_ARRAY,
    BOOL;

    /**
     * column orderby weight
     * @param columnType
     * @return
     */
    public static int getOrderByWeight(ColumnType columnType) {
        if (null == columnType) {
            return 22;
        }
        switch (columnType) {
            case BOOL: return 1;
            case TINYINT: return 2;
            case SMALLINT: return 3;
            case MEDIUMINT: return 4;
            case INT: return 5;
            case BIGINT: return 6;
            case LONG: return 7;
            case FLOAT: return 8;
            case DOUBLE: return 9;
            case DATETIME: return 10;
            case BIGDECIMAL: return 11;
            case CHAR: return 12;
            case VARCHAR: return 13;
            case INT_ARRAY: return 14;
            case FLOAT_ARRAY: return 15;
            case MEDIUMTEXT: return 16;
            case NONE : return 17;
            case JSONB: return 18;
            case TEXT_ARRAY: return 19;
            case VARCHAR_ARRAY: return 20;
            case TEXT: return 21;
            default: return 22;
        }
    }
    
}
