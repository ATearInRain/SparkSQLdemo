package entity;

public class User {
    //用户账号信息
    private String username;
    //密码
    private String password;
    //Spark主机的IP地址
    private String ip;
    //Spark_db为数据库名称
    private String Spark_db;

    public User(String username , String password , String ip , String spark_db) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        Spark_db = spark_db;
    }

}
