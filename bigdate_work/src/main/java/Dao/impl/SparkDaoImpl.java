package Dao.impl;

import Dao.SparkDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.Properties;
import java.sql.Statement;

public class SparkDaoImpl implements SparkDao {

    public Connection createConnection(String url , String user , String pass) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("driverClassName", "org.apache.hive.jdbc.HiveDriver");
        properties.setProperty("user", user);
        properties.setProperty("password", pass);
        Connection con = DriverManager.getConnection(url, properties);

        return con;
    }


    public JSONArray getDatabases(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String sql1 = "SHOW DATABASES";
        ResultSet res = stmt.executeQuery(sql1);
        JSONArray array = new JSONArray();
        while(res.next()){
            JSONObject jsonObject = new JSONObject();
            String columnName = "database";
            String value = res.getString(columnName);
            try {
                jsonObject.put(columnName, value);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            array.put(jsonObject);

        }
        return array;
    }


    public JSONArray getTables(Connection con) throws SQLException {
        DatabaseMetaData db = con.getMetaData();
        ResultSet rs = db.getTables(null, null, null, new String[] { "TABLE" });
        JSONArray array = new JSONArray();
        while(rs.next()) {
            JSONObject jsonobj = new JSONObject();
            String columname = "tableName";
            String value = rs.getString("TABLE_NAME");
            try {
                jsonobj.put(columname, value);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            array.put(jsonobj);
        }
        return array;
    }


    public JSONArray getSql(Connection con,String sql) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        JSONArray array = new JSONArray();

        ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (res.next()) {

            JSONObject jsonObj = new JSONObject();
            for (int i = 1; i <= columnCount; i++) {
                String columnName =metaData.getColumnLabel(i);
                String value = res.getString(columnName);
                jsonObj.put(columnName, value);
            }
            array.put(jsonObj);
        }
        return array;
    }
}
