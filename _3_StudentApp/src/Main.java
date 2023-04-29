import app.student.Student;
import app.student.StudentDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Student management App");
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int c;
        do{
            System.out.println("PRESS 1 to ADD student\nPRESS 2 to DELETE student\nPRESS 3 to DISPLAY student\nPRESS 4 to UPDATE student\nPRESS 5 to EXIT app");
            c = Integer.parseInt((br.readLine()));
            switch(c){
                case 1->
                {
                    System.out.println("Enter student name: ");
                    String name=br.readLine();

                    System.out.println("Enter student phone number: ");
                    String phone=br.readLine();

                    System.out.println("Enter student city: ");
                    String city=br.readLine();

                    //create student object to store student
                    Student st=new Student(name, phone, city);
                    boolean ans = StudentDao.insertStudentToDB(st);
                    if(ans){
                        System.out.println("Student added successfully...");
                    }else{
                        System.out.println("Something went wrong. Please try again...");
                    }

                }
                case 2->
                {
                    System.out.println("What would you like to enter? id/name");
                    String response= br.readLine();
                    if(response.equals("id")){
                        System.out.println("Enter student id: ");
                        int studentId = Integer.parseInt(br.readLine());
                        boolean ans = StudentDao.deleteStudentFromDBusingID(studentId);

                        if(ans){
                            System.out.println("Student deleted successfully...");
                        }else{
                            System.out.println("Something went wrong. Please try again...");
                        }
                    }else{
                        System.out.println("Enter student name: ");
                        String studentName = br.readLine();
                        boolean ans = StudentDao.deleteStudentFromDBusingName(studentName);

                        if(ans){
                            System.out.println("Student deleted successfully...");
                        }else{
                            System.out.println("Something went wrong. Please try again...");
                        }
                    }
                }
                case 3->
                {
                    StudentDao.displayAllStudent();
                }
                case 4->
                {
                    System.out.println("Enter id of student you want to update..");
                    int id = Integer.parseInt(br.readLine());
                    System.out.println("What would you like to update? sname/sphone/scity");
                    String choice = br.readLine();
                    System.out.println("Enter new data: ");
                    String newData=br.readLine();
                    boolean ans=StudentDao.updateStudent(id,choice, newData);
                    if(ans){
                        System.out.println("Student updated successfully...");
                    }else{
                        System.out.println("Something went wrong. Please try again...");
                    }
                }
                case 5 -> System.out.println("Thank you for using my application!!\nExiting...");
            }
        }while(c!=5);

    }
}