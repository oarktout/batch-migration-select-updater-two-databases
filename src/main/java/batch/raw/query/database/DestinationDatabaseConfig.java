package batch.raw.query.database;

import javax.sql.DataSource;

public class DestinationDatabaseConfig {

    public static final String DATASOURCE_PROPERTY_PREFIX = "data.destination";

    public static DataSource dataSource() {
       return DatabaseCommonConfig.dataSource(DATASOURCE_PROPERTY_PREFIX);
    }
}