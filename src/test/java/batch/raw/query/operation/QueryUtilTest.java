package batch.raw.query.operation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class QueryUtilTest {

    private static final String COLUMN_NAME_1="Nom";
    private static final String COLUMN_NAME_2="Naissance";
    private static final String COLUMN_TYPE_1="varchar";
    private static final String COLUMN_TYPE_2="DATE";
    private static ResultSet resultSet;
    private static ResultSetMetaData metaData;


    @BeforeAll
    static void setUp() {
        resultSet=Mockito.mock(ResultSet.class);
        metaData=Mockito.mock(ResultSetMetaData.class);
        try {
            initMocks();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void initMocks() throws SQLException {
        Mockito.when(resultSet.getMetaData()).thenReturn(metaData);
        Mockito.when(metaData.getColumnCount()).thenReturn(2);
        Mockito.when(metaData.getColumnName(1)).thenReturn(COLUMN_NAME_1);
        Mockito.when(metaData.getColumnName(2)).thenReturn(COLUMN_NAME_2);
        Mockito.when(metaData.getColumnTypeName(1)).thenReturn(COLUMN_TYPE_1);
        Mockito.when(metaData.getColumnTypeName(2)).thenReturn(COLUMN_TYPE_2);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getObject(COLUMN_NAME_1)).thenReturn("Jonh").thenReturn("Othman");
        Mockito.when(resultSet.getObject(COLUMN_NAME_2)). thenReturn("05/03/1995").thenReturn("27/02/1995");
    }

    @Test
    void getColumnInfo() {
        try {
            Map<String, String> columnInfo = QueryUtil.getColumnInfo(resultSet);
            Assertions.assertEquals(2,columnInfo.size());
            Assertions.assertEquals("VARCHAR(255)",columnInfo.get(COLUMN_NAME_1));
            Assertions.assertEquals("DATETIME",columnInfo.get(COLUMN_NAME_2));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getColumnWithValue() {
        Map<String,String> column=new HashMap<>();
        column.put(COLUMN_NAME_1,"VARCHAR(255)");
        column.put(COLUMN_NAME_2,"DATETIME");
        try {
            Map<Object, String> columnWithValue = QueryUtil.getColumnWithValue(resultSet, column);
            Assertions.assertEquals(4,columnWithValue.size());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}