package servlet;

import Dao.SparkDao;
import Dao.impl.SparkDaoImpl;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "GetDatabasesServlet",urlPatterns = {"/servlet/GetDatabasesServlet"})
public class GetDatabasesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {

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
        SparkDao dao=new SparkDaoImpl();
        Connection connection= null;
        try {
            connection = dao.createConnection(Spark_db,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray=dao.getDatabases(connection);
            if (jsonArray!=null){
                printWriter.print(jsonArray);
            }else {
                printWriter.print(0);//失败
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
