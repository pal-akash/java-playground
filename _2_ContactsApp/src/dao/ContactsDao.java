package dao;

import constants.SQLStatements;
import entity.*;
import service.ConnectionProvider;

import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.sql.Date;

public class ContactsDao {
    private Connection con = null;

    public Contact addContactDao(Contact newContact){
        int lastSavedId=-1;
        try{
            con=ConnectionProvider.createConnection();
            lastSavedId = saveBasicContactDetail(newContact);
            boolean phonenumflag = savePhoneNumbers(lastSavedId, newContact);
            if(phonenumflag){
                System.out.println("Basic contact details saved to DB!");
            }

            if(!Objects.isNull(newContact.getEmailList()) && !newContact.getEmailList().isEmpty()){
                boolean emailflag = saveEmailAddress(lastSavedId, newContact);
                if(emailflag){
                    System.out.println("Emails saved to DB!");
                }
            }else{
                System.out.println("No email saved!");
            }

            if(!Objects.isNull(newContact.getAddressList()) && !newContact.getAddressList().isEmpty()){
                boolean addressflag = saveAddress(lastSavedId, newContact);
                if(addressflag){
                    System.out.println("Address saved to DB!");
                }
            }else{
                System.out.println("No address saved to DB!");
            }

            if(!Objects.isNull(newContact.getSignificantDateList()) && !newContact.getSignificantDateList().isEmpty()){
                boolean sigdateflag = saveSignificantDate(lastSavedId, newContact);
                if(sigdateflag){
                    System.out.println("Significant date saved to DB!");
                }
            }else{
                System.out.println("No significant date saved to DB!");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return fetchContactFromDB(lastSavedId);
    }

    public Contact fetchContactFromDB(int contactId){
        Contact newContact=new Contact();
        try {
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query = "select id, fname, mname, lname, contactlabel from contactapp.contact where id=?";
            preparedStatement = con.prepareStatement(SQLStatements.SELECT_CONTACT_DETAILS_FROM_CONTACT_USING_ID);
            preparedStatement.setInt(1, contactId);
            ResultSet output = preparedStatement.executeQuery();

            if (output.next()) {
                newContact.setContactId(output.getInt(1));
                newContact.setFirstName(output.getString(2));
                newContact.setMiddleName(output.getString(3));
                newContact.setLastName(output.getString(4));
                newContact.setLabel(output.getString(5));
            }



            PreparedStatement psForPhone;
            String queryForPhone = "select phonenumber, phonenumlabel from phonenum where cid=?";
            psForPhone = con.prepareStatement(queryForPhone);
            psForPhone.setInt(1, contactId);
            ResultSet opForPhone = psForPhone.executeQuery();
            if (!Objects.isNull(opForPhone)) {
                while (opForPhone.next()) {
                    newContact.addPhone(new BigInteger(opForPhone.getString(1)), opForPhone.getString(2));
                }
            }

            PreparedStatement psForEmail;
            String queryForEmail = "select emailaddress, emaillabel from email where cid=?";
            psForEmail = con.prepareStatement(queryForEmail);
            psForEmail.setInt(1, contactId);
            ResultSet opForEmail = psForEmail.executeQuery();
            if (!Objects.isNull(opForEmail)) {
                while (opForEmail.next()) {
                    newContact.addEmail(opForEmail.getString(1), opForEmail.getString(2));
                }
            }

            PreparedStatement psForAddress;
            String queryForAddress = "select addressdetail, addresslabel from address where cid=?";
            psForAddress = con.prepareStatement(queryForAddress);
            psForAddress.setInt(1, contactId);
            ResultSet opForAddress = psForAddress.executeQuery();
            if (!Objects.isNull(opForAddress)) {
                while (opForAddress.next()) {
                    newContact.addAddress(opForAddress.getString(1), opForAddress.getString(2));
                }
            }

            PreparedStatement psForSigdate;
            String queryForSigDate = "select significantdate, sigdatelabel from sigdate where cid=?";
            psForSigdate = con.prepareStatement(queryForSigDate);
            psForSigdate.setInt(1, contactId);
            ResultSet opForSigdate = psForSigdate.executeQuery();
            if (!Objects.isNull(opForSigdate)) {
                while (opForSigdate.next()) {
                    newContact.addSignificantDate(opForSigdate.getDate(1).toLocalDate(), opForSigdate.getString(2));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return newContact;
    }

    public int saveBasicContactDetail(Contact newContact){
        int newId=-1;
        try{
            con=ConnectionProvider.createConnection();
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

    public boolean savePhoneNumbers(int cid, Contact newContact){
        boolean flag=false;
        try{
            con=ConnectionProvider.createConnection();
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

    public boolean saveEmailAddress(int cid, Contact newContact){
        boolean flag=false;
        try{
            con=ConnectionProvider.createConnection();
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

    public boolean saveAddress(int cid, Contact newContact){
        boolean flag=false;
        try{
            con=ConnectionProvider.createConnection();
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

    public boolean saveSignificantDate(int cid, Contact newContact){
        boolean flag=false;
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            for(SignificantDateInfo significantDateInfo : newContact.getSignificantDateList()){
                String query = "insert into sigdate values(default, ?,?,?)";
                Date sqlDate = Date.valueOf(significantDateInfo.getDate());
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
