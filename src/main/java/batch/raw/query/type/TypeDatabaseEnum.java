package batch.raw.query.type;

import java.sql.Date;
//TODO add mapping type corresponding to the database types of source and destination
public enum TypeDatabaseEnum {
    VARCHAR2("VARCHAR(255)", String.class),
    varchar("VARCHAR(255)", String.class),
    NUMBER("NUMERIC",Long.class),
    FLOAT("NUMERIC",Long.class),
    LONG("NUMERIC",Long.class),
    DATE("DATETIME", Date.class),
    CHAR("CHAR",Character.class);

    private final String value;
    private final Class<?> type;

    TypeDatabaseEnum(String value, Class<?> type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public Class<?> getType(){
        return type;
    }
}
