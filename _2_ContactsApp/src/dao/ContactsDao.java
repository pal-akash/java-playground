package dao;

import constants.SQLStatements;
import entity.*;
import service.ConnectionProvider;

import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public ArrayList fetchAllContactFromDB(){
        ArrayList<Contact> allContactList = new ArrayList<>();
        try {
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            preparedStatement = con.prepareStatement(SQLStatements.SELECT_ALL_CONTACT);
            ResultSet output = preparedStatement.executeQuery();

            if (output.next()) {
                Contact newContact=new Contact();
                newContact.setContactId(output.getInt(1));
                newContact.setFirstName(output.getString(2));
                newContact.setMiddleName(output.getString(3));
                newContact.setLastName(output.getString(4));
                newContact.setLabel(output.getString(5));

                PreparedStatement psForPhone;
                String queryForPhone = "select phonenumber, phonenumlabel from phonenum where cid=?";
                psForPhone = con.prepareStatement(queryForPhone);
                psForPhone.setInt(1, output.getInt(1));
                ResultSet opForPhone = psForPhone.executeQuery();
                while (opForPhone.next()) {
                    newContact.addPhone(new BigInteger(opForPhone.getString(1)), opForPhone.getString(2));
                }

                PreparedStatement psForEmail;
                String queryForEmail = "select emailaddress, emaillabel from email where cid=?";
                psForEmail = con.prepareStatement(queryForEmail);
                psForEmail.setInt(1, output.getInt(1));
                ResultSet opForEmail = psForEmail.executeQuery();
                while (opForEmail.next()) {
                    newContact.addEmail(opForEmail.getString(1), opForEmail.getString(2));
                }

                PreparedStatement psForAddress;
                String queryForAddress = "select addressdetail, addresslabel from address where cid=?";
                psForAddress = con.prepareStatement(queryForAddress);
                psForAddress.setInt(1, output.getInt(1));
                ResultSet opForAddress = psForAddress.executeQuery();
                while (opForAddress.next()) {
                    newContact.addAddress(opForAddress.getString(1), opForAddress.getString(2));
                }


                PreparedStatement psForSigdate;
                String queryForSigDate = "select significantdate, sigdatelabel from sigdate where cid=?";
                psForSigdate = con.prepareStatement(queryForSigDate);
                psForSigdate.setInt(1, output.getInt(1));
                ResultSet opForSigdate = psForSigdate.executeQuery();
                while (opForSigdate.next()) {
                    newContact.addSignificantDate(opForSigdate.getDate(1).toLocalDate(), opForSigdate.getString(2));
                }
                allContactList.add(newContact);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return allContactList;
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

    public void deleteContactDao(int contactId){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String queryForSigDate = "delete from sigdate where cid=?";
            String queryForAddress = "delete from address where cid=?";
            String queryForEmail = "delete from email where cid=?";
            String queryForPhoneNum = "delete from phonenum where cid=?";
            String queryForContact = "delete from contact where id=?";
            preparedStatement=con.prepareStatement(queryForSigDate);
            preparedStatement.setInt(1,contactId);
            preparedStatement.executeUpdate();
            preparedStatement=con.prepareStatement(queryForAddress);
            preparedStatement.setInt(1,contactId);
            preparedStatement.executeUpdate();
            preparedStatement=con.prepareStatement(queryForEmail);
            preparedStatement.setInt(1,contactId);
            preparedStatement.executeUpdate();
            preparedStatement=con.prepareStatement(queryForPhoneNum);
            preparedStatement.setInt(1,contactId);
            preparedStatement.executeUpdate();
            preparedStatement=con.prepareStatement(queryForContact);
            preparedStatement.setInt(1,contactId);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void editFirstNameDao(int contactId, String newFirstName){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query = "update contact set fname=? where id=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setString(1,newFirstName);
            preparedStatement.setInt(2,contactId);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void editMiddleNameDao(int contactId, String newMiddeName){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query = "update contact set mname=? where id=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setString(1,newMiddeName);
            preparedStatement.setInt(2,contactId);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void editLastNameDao(int contactId, String newLastName){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query = "update contact set lname=? where id=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setString(1,newLastName);
            preparedStatement.setInt(2,contactId);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addNewNumFromEditDao(int contactId, String newNumber, String newNumLabel){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query="update phonenum set phonenumber=?, phonenumlabel=? where cid=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setString(1,newNumber);
            preparedStatement.setString(2,newNumLabel);
            preparedStatement.setInt(3,contactId);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addNewEmailFromEditDao(int contactId, String newEmail, String newEmailLabel){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query="update email set emailaddress=?, emaillabel=? where cid=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setString(1,newEmail);
            preparedStatement.setString(2,newEmailLabel);
            preparedStatement.setInt(3,contactId);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addNewSigdateFromEditDao(int contactId, LocalDate newSigdate, String newSigdateLabel){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            Date newSigdateToDB = Date.valueOf(newSigdate);
            String query="update sigdate set significantdate=?, sigdatelabel=? where cid=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setDate(1,newSigdateToDB);
            preparedStatement.setString(2,newSigdateLabel);
            preparedStatement.setInt(3,contactId);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addNewAddressFromEditDao(int contactId, String newAddress, String newAddressLabel){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query="update address set addressdetail=?, addresslabel=? where cid=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setString(1,newAddress);
            preparedStatement.setString(2,newAddressLabel);
            preparedStatement.setInt(3,contactId);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteNumberFromEdit(int searchedId, BigInteger phoneNumber){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String phoneNum = phoneNumber.toString();
            String query="delete from phonenum where cid=? and phonenumber=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setInt(1,searchedId);
            preparedStatement.setString(2,phoneNum);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteEmailFromEdit(int searchedId, String email){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query="delete from email where cid=? and emailaddress=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setInt(1,searchedId);
            preparedStatement.setString(2,email);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteAddressFromEdit(int searchedId, String address){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query="delete from address where cid=? and addressdetail=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setInt(1,searchedId);
            preparedStatement.setString(2,address);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteSigDateFromEdit(int searchedId, LocalDate significantDate){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query="delete from sigdate where cid=? and significantdate=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setInt(1,searchedId);
            preparedStatement.setDate(2,Date.valueOf(significantDate));
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteAllContactDao(){
        try{
            con=ConnectionProvider.createConnection();
            PreparedStatement preparedStatement;
            String query = "delete from sigdate";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.executeUpdate();

            query="delete from address";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.executeUpdate();

            query="delete from email";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.executeUpdate();

            query="delete from phonenum";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.executeUpdate();

            query="delete from contact";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
