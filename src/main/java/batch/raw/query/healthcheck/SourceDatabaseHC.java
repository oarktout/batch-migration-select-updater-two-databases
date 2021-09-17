package batch.raw.query.healthcheck;

import batch.raw.query.controller.DatabaseController;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Statement;

@Component("sourceDatabaseHC")
public class SourceDatabaseHC
        implements HealthIndicator, HealthContributor {

    @Override
    public Health health() {
        try(Statement stmt = DatabaseController.getSourceConnection().createStatement()){
            stmt.execute("select 1 FROM ETUDIANTS");
        } catch (SQLException ex) {
            return Health.down(ex).build();
        }
        return Health.up().build();
    }
}

