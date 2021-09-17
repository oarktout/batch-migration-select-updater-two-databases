package batch.raw.query.operation;

import batch.raw.query.type.TypeDatabaseEnum;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class QueryUtil {

    public static Map<String,String> getColumnInfo(ResultSet resultSet) throws SQLException {
        Map<String,String> result= new HashMap<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for(int i=1; i<= metaData.getColumnCount();i++){
            String name=metaData.getColumnName(i);
            String type= TypeDatabaseEnum.valueOf(metaData.getColumnTypeName(i)).getValue();
            result.put(name,type);
        }
        return result;
    }

    public static Map<Object,String> getColumnWithValue(ResultSet resultSet, Map<String, String> columns) throws SQLException {
        Map<Object,String> result= new HashMap<>();
        while(resultSet.next()){
            for (String column: columns.keySet()) {
                Object value = resultSet.getObject(column);
                result.put(value,column);
            }
        }
        return result;
    }

}
