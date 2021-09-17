package batch.raw.query.controller;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class BatchController {
    private static final Logger log = LoggerFactory.getLogger(BatchController.class);

    private static String SELECT_QUERY;
    private static String TABLE_NAME;

    public static DatabaseController databaseController;

    public BatchController(DatabaseController databaseController) {
        PropertiesConfiguration config=new PropertiesConfiguration();
        config.setEncoding("UTF-8");
        try {
            config.load("application.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        SELECT_QUERY = config.getString("query.select");
        TABLE_NAME = config.getString("query.tableName");
        this.databaseController = databaseController;
    }

    public static void runBatch() {
        log.info("Launch Batch");
        try {
            databaseController.executeBatch(SELECT_QUERY,TABLE_NAME);
            log.info("Batch Finished with success");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            log.error(throwables.getMessage());
        }finally {
            try {
                databaseController.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }
}
