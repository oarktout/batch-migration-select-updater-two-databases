package batch.raw.query.database;

import javax.sql.DataSource;

public class SourceDatabaseConfig {

    private static final String DATASOURCE_PROPERTY_PREFIX = "data.source";

    public static DataSource dataSource() {
        return DatabaseCommonConfig.dataSource(DATASOURCE_PROPERTY_PREFIX);
    }
}
