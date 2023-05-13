package dao;

import com.mysql.cj.protocol.Resultset;
import entity.*;
import service.ConnectionProvider;


import java.math.BigInteger;
import java.sql.*;
import java.util.Objects;

public class ContactsDao {
    private final Connection con = ConnectionProvider.createConnection();
    public boolean addContactDao(Contact newContact){
        boolean flag=false;
        try{
            int lastSavedId = saveBasicContactDetail(newContact, con);
            boolean phonenumflag = savePhoneNumbers(lastSavedId, newContact, con);
            if(phonenumflag){
                System.out.println("Basic contact details saved!");
            }

            if(!Objects.isNull(newContact.getEmailList()) && !newContact.getEmailList().isEmpty()){
                boolean emailflag = saveEmailAddress(lastSavedId, newContact, con);
                if(emailflag){
                    System.out.println("Emails saved!");
                }
            }else{
                System.out.println("No email saved!");
            }

            if(!Objects.isNull(newContact.getAddressList()) && !newContact.getAddressList().isEmpty()){
                boolean addressflag = saveAddress(lastSavedId, newContact, con);
                if(addressflag){
                    System.out.println("Address saved!");
                }
            }else{
                System.out.println("No address saved!");
            }

            if(!Objects.isNull(newContact.getSignificantDateList()) && !newContact.getSignificantDateList().isEmpty()){
                boolean sigdateflag = saveSignificantDate(lastSavedId, newContact, con);
                if(sigdateflag){
                    System.out.println("Significant date saved!");
                }
            }else{
                System.out.println("No significant date saved!");
            }
            flag=true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public int saveBasicContactDetail(Contact newContact, Connection con){
        int newId=-1;
        try{
            String query = "insert into contact values(default, ?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newContact.getFirstName());
            preparedStatement.setString(2, newContact.getMiddleName());
            preparedStatement.setString(3, newContact.getLastName());
            preparedStatement.setString(4, newContact.getLabel());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                newId = resultSet.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return newId;
    }

    public boolean savePhoneNumbers(int cid, Contact newContact,  Connection con){
        boolean flag=false;
        try{
            PreparedStatement preparedStatement;
            for(PhoneInfo phoneinfo : newContact.getPhoneList()){
                String query = "insert into phonenum values(default, ?,?,?)";
                preparedStatement = con.prepareStatement(query);
                String phonenumber = phoneinfo.getNumber().toString();
                preparedStatement.setInt(1, cid);
                preparedStatement.setString(2, phonenumber);
                preparedStatement.setString(3, phoneinfo.getLabel());
                preparedStatement.executeUpdate();
            }
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean saveEmailAddress(int cid, Contact newContact,  Connection con){
        boolean flag=false;
        try{
            PreparedStatement preparedStatement;
            for(EmailInfo emailInfo : newContact.getEmailList()){
                String query = "insert into email values(default, ?,?,?)";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, cid);
                preparedStatement.setString(2, emailInfo.getEmail());
                preparedStatement.setString(3, emailInfo.getLabel());
                preparedStatement.executeUpdate();
            }
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean saveAddress(int cid, Contact newContact,  Connection con){
        boolean flag=false;
        try{
            PreparedStatement preparedStatement;
            for(AddressInfo addressInfo : newContact.getAddressList()){
                String query = "insert into address values(default, ?,?,?)";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, cid);
                preparedStatement.setString(2, addressInfo.getAddress());
                preparedStatement.setString(3, addressInfo.getLabel());
                preparedStatement.executeUpdate();
            }
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean saveSignificantDate(int cid, Contact newContact, Connection con){
        boolean flag=false;
        try{
            PreparedStatement preparedStatement;
            for(SignificantDateInfo significantDateInfo : newContact.getSignificantDateList()){
                String query = "insert into email values(default, ?,?,?)";
                java.sql.Date sqlDate = new java.sql.Date(significantDateInfo.getDate().getTime());
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, cid);
                preparedStatement.setDate(2,  sqlDate);
                preparedStatement.setString(3, significantDateInfo.getLabel());
                preparedStatement.executeUpdate();
            }
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
