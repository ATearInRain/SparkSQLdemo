package Dao;

import java.sql.Connection;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public interface SparkDao {

    public abstract Connection createConnection(String url,String user,String pass)throws SQLException;

    public abstract JSONArray getDatabases(Connection con)throws  SQLException;

    public abstract JSONArray getTables(Connection con)throws SQLException;

    public abstract JSONArray getSql(Connection con,String sql)throws SQLException;
}
