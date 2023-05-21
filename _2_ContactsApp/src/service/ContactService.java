package service;
import dao.ContactsDao;
import entity.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

public class ContactService {
    public static List<Contact> contactList = new ArrayList<>();
    private final ContactsDao contactsDao = new ContactsDao();

    public void addToContact(Scanner sc) {
        Contact newContact = new Contact();

        System.out.println("Enter first name: ");
        String firstName = sc.next();
        while (firstName.isBlank()) {
            System.out.println("Invalid first name. Please enter again: ");
            firstName = sc.next();
        }
        newContact.setFirstName(firstName);

        System.out.println("Enter middle name: (Enter space to leave blank)");
        String middleName = sc.next();
        if (middleName.isEmpty()) {
            newContact.setMiddleName("");
        }
        newContact.setMiddleName(middleName);

        System.out.println("Enter last name: ");
        String lastName = sc.next();
        if (lastName.isBlank()) {
            lastName = "";
        }
        newContact.setLastName(lastName);

        System.out.println("Would you like to add contact label? Y/N");
        if (sc.next().equals("Y")) {
            System.out.println("Enter label for contact: ");
            String labelForContact = sc.next();
            newContact.setLabel(labelForContact);
        }

        System.out.println("How many number would you like to add?");
        int num = sc.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.println("Enter phone number: ");
            BigInteger phoneNumber = sc.nextBigInteger();
            System.out.println("Enter label for phone number: ");
            String labelForPhoneNumber = sc.next();
            newContact.addPhone(phoneNumber, labelForPhoneNumber);
        }

        System.out.println("Would you like to add any email? Y/N");
        if (sc.next().equals("Y")) {
            System.out.println("How many email would you like to add?");
            num = sc.nextInt();
            for (int i = 0; i < num; i++) {
                System.out.println("Enter email: ");
                String email = sc.next();
                System.out.println("Enter label for email: ");
                String labelForEmail = sc.next();
                newContact.addEmail(email, labelForEmail);
            }
        }

        System.out.println("Would you like to add any address? Y/N");
        if (sc.next().equals("Y")) {
            System.out.println("How many address would you like to add?");
            num = sc.nextInt();
            for (int i = 0; i < num; i++) {
                System.out.println("Enter address: ");
                String address = sc.next();
                System.out.println("Enter label for address: ");
                String labelForAddress = sc.next();
                newContact.addAddress(address, labelForAddress);
            }
        }

        System.out.println("Would you like to add any significant date? Y/N");
        if (sc.next().equals("Y")) {
            System.out.println("How many significant date would you like to add?");
            num = sc.nextInt();
            for (int i = 0; i < num; i++) {
                int year, month, day;
                LocalDate significantDate;
                System.out.println("Enter year: ");
                year = sc.nextInt();

                System.out.println("Enter month: ");
                month = sc.nextInt();

                System.out.println("Enter day: ");
                day = sc.nextInt();

                significantDate = LocalDate.of(year, month, day);
                System.out.println("Enter label for significant date: ");
                String labelForSignificantDate = sc.next();
                newContact.addSignificantDate(significantDate, labelForSignificantDate);
            }
        }
    public void showAllContact() {
        contactsDao.showAllContactDao();
    }

    public void searchForDisplayContact(Scanner sc) {
        System.out.println("Would you like to search using phone number or name? (P/N)");
        boolean isFound=false;
        if (sc.next().equals("P")) {
            System.out.println("Enter phone number: ");
            BigInteger phoneNumber = sc.nextBigInteger();
            for (Contact contact : contactList) {
                for (PhoneInfo phoneInfo : contact.getPhoneList()) {
                    if (phoneInfo.getNumber().equals(phoneNumber)) {
                        isFound=true;
                        System.out.println("Contact found!");
                        displayBasicContactDetails(contact);
                        System.out.println("Would you like to see full contact details? Y/N");
                        if(sc.next().equals("Y")){
                            displayFullContactDetails(contact);
                        }
                    }
                }
            }
        } else {
            System.out.println("Enter name: ");
            String nameInput = sc.next();
            for (Contact contact : contactList) {
                if (contact.getName().contains(nameInput)) {
                    System.out.println("Contact found!");
                    isFound=true;
                    displayBasicContactDetails(contact);
                    System.out.println("Would you like to see full contact details? Y/N");
                    if(sc.next().equals("Y")){
                        displayFullContactDetails(contact);
                    }
                }
            }
        }
        if(!isFound){
            System.out.println("No such contact found!");
        }
    }

    public void displayBasicContactDetails(Contact contact){
            System.out.println("Full name: " + contact.getName());
            System.out.println("Contact label: " + contact.getLabel());
            System.out.println("Phone numbers: ");
            for (PhoneInfo phoneInfo : contact.getPhoneList()) {
                System.out.println(phoneInfo.getNumber() + " " + phoneInfo.getLabel());
            }
    }

    public void displayFullContactDetails(Contact contact){
            System.out.println("Full name: " + contact.getName());
            System.out.println("Contact label: " + contact.getLabel());
            System.out.println("Phone numbers: ");
            for (PhoneInfo phoneInfo : contact.getPhoneList()) {
                System.out.println(phoneInfo.getNumber() + " " + phoneInfo.getLabel());
            }
            List<AddressInfo> addressInfoList = contact.getAddressList();
            List<EmailInfo> emailInfoList = contact.getEmailList();
            List<SignificantDateInfo> significantDateInfoList= contact.getSignificantDateList();
            System.out.println("Addresses: ");
            for (AddressInfo addressInfo : addressInfoList) {
                System.out.println(addressInfo.getAddress() + " " + addressInfo.getLabel());
            }
            System.out.println("Emails: ");
            for (EmailInfo emailInfo : emailInfoList) {
                System.out.println(emailInfo.getEmail() + " " + emailInfo.getLabel());
            }
            System.out.println("Significant dates are: ");
            for (SignificantDateInfo significantDateInfo : significantDateInfoList) {
                System.out.println(significantDateInfo.getDate() + " " + significantDateInfo.getLabel());
            }
    }

    public void showAllContact(){
        for(Contact contact : contactList){
            System.out.println("Full name: " + contact.getName());
            for(PhoneInfo phoneInfo : contact.getPhoneList()){
                System.out.println("Phone numbers: " + phoneInfo.getNumber());
            }
        }
    }

    public void searchForDelete(Scanner sc){
        System.out.println("Would you like to search using phone number or name? (P/N)");
        int foundId;
        boolean isFound=false;
        if (sc.next().equals("P")){
            System.out.println("Enter phone number: ");
            BigInteger phoneNumber = sc.nextBigInteger();
            for (Contact contact : contactList) {
                for (PhoneInfo phoneInfo : contact.getPhoneList()) {
                    if (phoneInfo.getNumber().equals(phoneNumber)) {
                        isFound=true;
                        foundId=contact.getContactId();
                        System.out.println("Contact found!\nDeleting contact...");
                        contactsDao.deleteContactDao(foundId);
                        contactList.remove(contact);
                        System.out.println("Contact deleted successfully!");
                        break;
                    }
                }
                if(isFound){
                    break;
                }
            }
        }else{
            System.out.println("Enter name: ");
            String inputName = sc.next();
            for (Contact contact : contactList) {
                if (contact.getName().contains(inputName)) {
                    isFound=true;
                    foundId=contact.getContactId();
                    System.out.println("Contact found!\nDeleting contact...");
                    contactsDao.deleteContactDao(foundId);
                    contactList.remove(contact);
                    System.out.println("Contact deleted successfully!");
                    break;
                }
            }
        }
        if(!isFound){
            System.out.println("No such contact found!");
        }
    }

    public Contact searchForEdit(Scanner sc){
        System.out.println("Would you like to search using phone number or name? (P/N)");
        Contact searchedContact = new Contact();
        if (sc.next().equals("P")){
            System.out.println("Enter phone number: ");
            BigInteger phoneNumber = sc.nextBigInteger();
            for (Contact contact : contactList) {
                for (PhoneInfo phoneInfo : contact.getPhoneList()) {
                    if (phoneInfo.getNumber().equals(phoneNumber)) {
                        System.out.println("Contact found!");
                        searchedContact = contact;
                    }
                }
            }
        }else{
            System.out.println("Enter name: ");
            String inputName = sc.next();
            for (Contact contact : contactList) {
                if (contact.getName().contains(inputName)) {
                    searchedContact = contact;
                }
            }
        }
        return searchedContact;
    }

    public void editContact(Scanner sc) {
        Contact searchedContact = searchForEdit(sc);
        if(Objects.isNull(searchedContact)){
            System.out.println("No such contact found!");
        }
        int ch;
        do {
            System.out.println("What would you like to edit? \nEnter: \n1 for Name\n2 for Phone number \n3 for Address \n4 for Email \n5 for Significant Date \n6 to finish editing");
            ch = sc.nextInt();
            switch (ch) {
                case 1 -> {
                    System.out.println("Would you like to edit first name? Y/N");
                    String response = sc.next();
                    if(response.equals("Y")){
                        editFirstName(searchedContact,sc);
                    }
                    System.out.println("Would you like to edit middle name? Y/N");
                    response = sc.next();
                    if(response.equals("Y")){
                        editMiddleName(searchedContact,sc);
                    }
                    System.out.println("Would you like to edit last name? Y/N");
                    response = sc.next();
                    if(response.equals("Y")) {
                        editLastName(searchedContact, sc);
                    }
                }
                case 2 -> editPhoneNumber(searchedContact,sc);
                case 3 -> editAddress(searchedContact,sc);
                case 4 -> editEmail(searchedContact,sc);
                case 5 -> editSignificantDate(searchedContact, sc);
                case 6->
                {
                    Contact updatedContact = contactsDao.fetchContactFromDB(searchedContact.getContactId());
                    for(int i=0; i<contactList.size(); i++){
                        if(contactList.get(i).getContactId()== searchedContact.getContactId()){
                            contactList.set(i,updatedContact);
                        }
                    }
                    System.out.println("All edits have been successfully saved. Exiting...");
                }
                default-> System.out.println("Invalid input! Please try again.");
            }
        } while (ch != 6);
    }

    public void editFirstName(Contact searchedContact, Scanner sc){
        System.out.println("Enter new first name: ");
        String newFirstName = sc.next();
        contactsDao.editFirstNameDao(searchedContact.getContactId(), newFirstName);
        System.out.println("First name has been successfully saved");
    }

    public void editMiddleName(Contact searchedContact, Scanner sc){
        System.out.println("Enter new middle name: ");
        String newMiddleName = sc.next();
        contactsDao.editMiddleNameDao(searchedContact.getContactId(), newMiddleName);
        System.out.println("Middle name has been successfully saved");
    }

    public void editLastName(Contact searchedContact, Scanner sc){
        System.out.println("Enter new last name: ");
        String newLastName = sc.next();
        contactsDao.editLastNameDao(searchedContact.getContactId(), newLastName);
        System.out.println("Last name has been successfully saved");
    }

    public void editPhoneNumber(Contact searchedContact, Scanner sc){
        System.out.println("Would you like to add a number? Y/N");
        if(sc.next().equals("Y")){
            System.out.println("Enter new phone number: ");
            BigInteger newNumber = sc.nextBigInteger();
            String newNumbertoDb = newNumber.toString();
            System.out.println("Enter label: ");
            String newNumLabel = sc.next();
            contactsDao.addNewNumFromEditDao(searchedContact.getContactId(), newNumbertoDb, newNumLabel);
        }
        System.out.println("Would you like to delete any existing number? Y/N");
        if(sc.next().equals("Y")){
            System.out.println("Available phone numbers for this contact are: ");
            for(int i=0; i<searchedContact.getPhoneList().size(); i++){
                System.out.println("ID " + (i+1) + ", " + searchedContact.getPhoneList().get(i).getNumber() + " " + searchedContact.getPhoneList().get(i).getLabel());
            }
            System.out.println("Enter id of the number you want to delete?");
            int id=sc.nextInt()-1;
            contactsDao.deleteNumberFromEdit(searchedContact.getContactId(), searchedContact.getPhoneList().get(id).getNumber());
        }
        System.out.println("Changes has been successfully saved!");
    }

    public void editAddress(Contact searchedContact, Scanner sc){
        System.out.println("Would you like to add an address? Y/N");
        if(sc.next().equals("Y")){
            System.out.println("Enter new address: ");
            String newAddressToDB = sc.next();
            System.out.println("Enter label: ");
            String newAddressLabel = sc.next();
            contactsDao.addNewAddressFromEditDao(searchedContact.getContactId(), newAddressToDB, newAddressLabel);
        }
        System.out.println("Would you like to delete any existing address? Y/N");
        if(sc.next().equals("Y")){
            System.out.println("Available addresses for this contact are: ");
            for(int i=0; i<searchedContact.getAddressList().size(); i++){
                System.out.println("ID " + (i+1) + ", " + searchedContact.getAddressList().get(i).getAddress() + " " + searchedContact.getAddressList().get(i).getLabel());
            }
            System.out.println("Enter id of the address you want to delete?");
            int id=sc.nextInt()-1;
            contactsDao.deleteAddressFromEdit(searchedContact.getContactId(), searchedContact.getAddressList().get(id).getAddress());
        }
        System.out.println("Changes has been successfully saved!");
    }

    public void editEmail(Contact searchedContact, Scanner sc){
        System.out.println("Would you like to add an email? Y/N");
        if(sc.next().equals("Y")){
            System.out.println("Enter new email: ");
            String newEmailToDB = sc.next();
            System.out.println("Enter label: ");
            String newEmailLabel = sc.next();
            contactsDao.addNewEmailFromEditDao(searchedContact.getContactId(), newEmailToDB, newEmailLabel);
        }
        System.out.println("Would you like to delete any existing email? Y/N");
        if(sc.next().equals("Y")){
            System.out.println("Available email for this contact are: ");
            for(int i=0; i<searchedContact.getEmailList().size(); i++){
                System.out.println("ID " + (i+1) + ", " + searchedContact.getEmailList().get(i).getEmail() + " " + searchedContact.getEmailList().get(i).getLabel());
            }
            System.out.println("Enter id of the email you want to delete?");
            int id=sc.nextInt()-1;
            contactsDao.deleteEmailFromEdit(searchedContact.getContactId(), searchedContact.getEmailList().get(id).getEmail());
        }
        System.out.println("Changes has been successfully saved!");
    }

    public void editSignificantDate(Contact searchedContact, Scanner sc){
        System.out.println("Would you like to enter a new significant date? Y/N");
        String response=sc.next();
        if(response.equals("Y")){
            int year, month, day;
            LocalDate newSignificantDate;
            System.out.println("Enter year: ");
            year = sc.nextInt();

            System.out.println("Enter month: ");
            month = sc.nextInt();

            System.out.println("Enter day: ");
            day = sc.nextInt();

            newSignificantDate = LocalDate.of(year,month,day);
            System.out.println("Enter label for significant date: ");
            String labelForNewSignificantDate = sc.next();

            contactsDao.addNewSigdateFromEditDao(searchedContact.getContactId(), newSignificantDate,labelForNewSignificantDate);
        }
        System.out.println("Would you like to delete any existing significant date? Y/N");
        response=sc.next();
        if(response.equals("Y")){
            System.out.println("Available significant dates are for this contact are: ");
            for(int i=0; i<searchedContact.getSignificantDateList().size(); i++){
                System.out.println("ID " + (i+1) + ". " + searchedContact.getSignificantDateList().get(i).getDate() + " " + searchedContact.getSignificantDateList().get(i).getLabel());
            }
            System.out.println("Enter id of the significant date you want to delete?");
            int id=sc.nextInt()-1;
            contactsDao.deleteSigDateFromEdit(searchedContact.getContactId(), searchedContact.getSignificantDateList().get(id).getDate());
        }
        System.out.println("Significant date has been successfully saved!");
    }

    public void deleteAllContact(){
        contactsDao.deleteAllContactDao();
        contactList.clear();
        System.out.println("All contacts has been successfully deleted!");
    }

    public void fetchAllContact(){
        contactList=contactsDao.fetchAllContactFromDB();
    }
}



