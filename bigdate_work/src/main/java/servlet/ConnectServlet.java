package servlet;

import Dao.SparkDao;
import Dao.impl.SparkDaoImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@javax.servlet.annotation.WebServlet(name = "ConnectServlet",urlPatterns = {"/servlet/ConnectServlet"})
public class ConnectServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request , javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request , javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 获得响应的输出流
        PrintWriter printWriter=response.getWriter();
        // 获得客户端请求参数
        String username=request.getParameter("username");//用户账号信息
        String password=request.getParameter("password");//密码
        String ip=request.getParameter("ip");//Spark主机的IP地址
        String Spark_db=request.getParameter("Spark_db");//Spark_db为数据库名称
        System.out.printf(username+"\n"+password+"\n"+ip+"\n"+Spark_db);

        //实例化数据访问对象
        SparkDao dao=new SparkDaoImpl();
        try {
            Connection success = dao.createConnection(Spark_db,username,password);
            if (success!=null){
                printWriter.print(1);//建立连接成功
            }else {
                printWriter.print(0);//建立连接失败
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
