package batch.raw.query.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import javax.sql.DataSource;

public class DatabaseCommonConfig {

    public static DataSource dataSource(String prefix) {

        PropertiesConfiguration config= null;
        try {
            config = new PropertiesConfiguration("application.properties");
            config.setEncoding("UTF-8");
            config=(PropertiesConfiguration)config.interpolatedConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        assert config != null;
        String url = config.getString(prefix + ".jdbc-url");
        String username = config.getString(prefix + ".username");
        String password = config.getString(prefix + ".password");
        String driver = config.getString(prefix + ".driverClassName");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setMinimumIdle(3);
        hikariConfig.setMaximumPoolSize(3);
        return new HikariDataSource(hikariConfig);
    }
}
