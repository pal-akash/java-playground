package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
    static Connection con;
    public static Connection createConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String user="root";
            String password="Akash01@mysql";
            String url="jdbc:mysql://localhost:3306/contactapp";

            con=DriverManager.getConnection(url,user,password);

        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }
}
