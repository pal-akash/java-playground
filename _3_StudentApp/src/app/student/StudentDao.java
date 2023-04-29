package app.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentDao {
    public static boolean insertStudentToDB(Student st){ //no need to make object if static
        boolean flag=false;
        try{
            //jdbc code
            Connection con = ConnectionProvider.createConnection();
            String query = "insert into students(sname, sphone, scity) values(?,?,?)";
            //prepared statement
            PreparedStatement preparedStatement = con.prepareStatement(query);
            //set the value of parameters
            preparedStatement.setString(1, st.getStudentName());
            preparedStatement.setString(2, st.getStudentPhone());
            preparedStatement.setString(3, st.getStudentCity());

            //query complete. now execute
            preparedStatement.executeUpdate();
            flag=true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean deleteStudentFromDBusingID(int studentId){
        boolean flag=false;
        try{
            Connection con=ConnectionProvider.createConnection();
            String query = "delete from students where sid=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,studentId);
            preparedStatement.executeUpdate();
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean deleteStudentFromDBusingName(String studentName){
        boolean flag=false;
        try{
            Connection con=ConnectionProvider.createConnection();
            String query = "delete from students where sname=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,studentName);
            preparedStatement.executeUpdate();
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public static void displayAllStudent(){
        try{
            Connection con=ConnectionProvider.createConnection();
            String query = "select*from students;";
            Statement statement = con.createStatement();
            ResultSet output = statement.executeQuery(query);
            while(output.next()){
                int id=output.getInt(1);
                String name=output.getString(2);
                String phone = output.getString(3);
                String city = output.getString(4);

                System.out.println("Id: " + id);
                System.out.println("Name: " + name);
                System.out.println("Phone: " + phone);
                System.out.println("City: " + city);
                System.out.println("-----------------------");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean updateStudent(int id, String field, String newData){
        boolean flag=false;
        try{
            String query = "";
            Connection con=ConnectionProvider.createConnection();
            if(field.equals("sname")){
                query = "update students set sname = ? where sid=?";
            }
            else if(field.equals("sphone")){
                query = "update students set sphone = ? where sid=?";
            }
            else if(field.equals("scity")){
                query = "update students set scity = ? where sid=?";
            }
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,newData);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }




}
