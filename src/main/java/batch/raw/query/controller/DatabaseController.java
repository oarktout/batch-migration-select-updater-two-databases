package batch.raw.query.controller;

import batch.raw.query.database.DestinationDatabaseConfig;
import batch.raw.query.database.SourceDatabaseConfig;
import batch.raw.query.operation.QueryUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class DatabaseController {

    public static final DataSource sourceDatasource = SourceDatabaseConfig.dataSource();
    public static final DataSource destinationDatasource = DestinationDatabaseConfig.dataSource();
    public static Connection destinationConnection;
    public static Connection sourceConnection;
    public String executionDate;

    public void executeBatch(String selectQuery,String tableName) throws SQLException {
        initDatabaseConnections();
        this.executionDate=retrieveTimeExecution();
        ResultSet resultSet = executeSelectQuery(selectQuery);

        Map<String, String> columns = QueryUtil.getColumnInfo(resultSet);
        Map<Object, String> values = QueryUtil.getColumnWithValue(resultSet,columns);

        createTable(tableName, columns);
        insertValues(tableName, values,resultSet);
    }

    private String retrieveTimeExecution() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    public static void initDatabaseConnections() throws SQLException {
        checkDestinationConnection();
        checkSourceConnection();
    }

    private static void checkSourceConnection() throws SQLException {
        if(sourceConnection==null||sourceConnection.isClosed()){
            sourceConnection = initConnection(sourceDatasource);
        }
    }

    private static void checkDestinationConnection() throws SQLException {
        if(destinationConnection==null||destinationConnection.isClosed()){
            destinationConnection = initConnection(destinationDatasource);
        }
    }

    public ResultSet executeSelectQuery(String selectQuery) throws SQLException {
        PreparedStatement preparedStatement = sourceConnection.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return preparedStatement.executeQuery();
    }

    @Transactional
    public void createTable(String tableName, Map<String,String> columns) throws SQLException {
        Statement stmt = destinationConnection.createStatement();
        stmt.executeUpdate(generateCreateTableQuery(tableName, columns));
        stmt.executeUpdate("TRUNCATE TABLE " + tableName + ";");
    }


    private String generateCreateTableQuery(String tableName, Map<String,String> columns){
        StringBuilder query=new StringBuilder( "CREATE TABLE IF NOT EXISTS "+tableName+" (\n");
        query.append(diggerColumns());
        for(Map.Entry<String,String> column:columns.entrySet()){
            query.append(column.getKey()).append(" ").append(column.getValue()).append(",\n");
        }
        query.replace(query.length()-2,query.length(),"\n");
        query.append(");");
        return query.toString();
    }

    private String diggerColumns() {
        StringBuilder query=new StringBuilder();
        query.append("digger_id int(11) AUTO_INCREMENT PRIMARY KEY,\n");
        query.append("digger_date datetime NOT NULL,\n");
        return query.toString();
    }

    @Transactional
    public void insertValues(String tableName, Map<Object, String> values, ResultSet resultSet) throws SQLException {
        Statement stmt = destinationConnection.createStatement();
        Set<String> columnsName = new HashSet<>(values.values());
        executeInsertQueries(tableName, resultSet, stmt, columnsName);
    }

    private void executeInsertQueries(String tableName, ResultSet resultSet, Statement stmt, Set<String> columnsName) throws SQLException {
        resultSet.beforeFirst();
        while(resultSet.next()){
            StringBuilder query=new StringBuilder( "INSERT INTO "+ tableName +" (");
            query.append("digger_date,");
            for (String column: columnsName) {
                query.append(column).append(",");
            }
            query.replace(query.length()-1,query.length(),"\n");
            query.append(") VALUES (");
            query.append("'").append(executionDate).append("',");
            for (String column: columnsName) {
                Object value = resultSet.getObject(column);
                if(value==null){
                    query.append("null").append(",");
                }else{
                    query.append("'").append(value.toString().replace('\'',' ')).append("',");
                }
            }
            query.replace(query.length()-1,query.length(),"\n");
            query.append(");");
            stmt.executeUpdate(query.toString());
        }
    }


    public void closeConnection() throws SQLException {
        sourceConnection.close();
        destinationConnection.close();
    }

    private static Connection initConnection(DataSource dataSource){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getDestinationConnection() {
        try {
            checkDestinationConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinationConnection;
    }

    public static Connection getSourceConnection() {
        try {
            checkSourceConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sourceConnection;
    }
}
