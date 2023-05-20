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
        if (sc.next().equals("P")) {
            System.out.println("Enter phone number: ");
            BigInteger phoneNumber = sc.nextBigInteger();
            boolean notFound=true;
            for (Contact contact : contactList) {
                for (PhoneInfo phoneInfo : contact.getPhoneList()) {
                    if (phoneInfo.getNumber().equals(phoneNumber)) {
                        notFound=false;
                        System.out.println("Contact found!");
                        displayBasicContactDetails(contact);
                        System.out.println("Would you like to see full contact details? Y/N");
                        if(sc.next().equals("Y")){
                            displayFullContactDetails(contact);
                        }
                    }
                }
            }
            if(notFound){
                System.out.println("No such contact found!");
            }
        } else {
            System.out.println("Enter name: ");
            String nameInput = sc.next();
            boolean notFound=true;
            for (Contact contact : contactList) {
                if (contact.getName().contains(nameInput)) {
                    System.out.println("Contact found!");
                    notFound=false;
                    displayBasicContactDetails(contact);
                    System.out.println("Would you like to see full contact details? Y/N");
                    if(sc.next().equals("Y")){
                        displayFullContactDetails(contact);
                    }
                }
            }
            if(notFound){
                System.out.println("No such contact found!");
            }
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
        }
    }

    public void deleteContact(List<Contact> contactList, Scanner sc) {
        Contact searchedContact = searchContact(contactList, sc);
        if (Objects.isNull(searchedContact)) {
            System.out.println("Can't execute delete operation.");
        } else {
            contactList.remove(searchedContact);
            System.out.println("Contact has been successfully removed!");
        }
    }

    public void editContact(List<Contact> contactList, Scanner sc) {
        Contact editableContact = searchContact(contactList, sc);
        int ch;
        do {
            System.out.println("What would you like to edit? \nEnter: \n1 for Name\n2 for Phone number \n3 for Address \n4 for Email \n5 for Significant Date \n6 to finish editing");
            ch = sc.nextInt();
            switch (ch) {
                case 1 -> {
                    System.out.println("Would you like to edit first name? Y/N");
                    String response = sc.next();
                    if(response.equals("Y")){
                        editFirstName(editableContact,sc);
                    }
                    System.out.println("Would you like to edit middle name? Y/N");
                    response = sc.next();
                    if(response.equals("Y")){
                        editMiddleName(editableContact,sc);
                    }
                    System.out.println("Would you like to edit last name? Y/N");
                    response = sc.next();
                    if(response.equals("Y")) {
                        editLastName(editableContact, sc);
                    }
                }
                case 2 -> editPhoneNumber(editableContact,sc);

                case 3 -> editAddress(editableContact,sc);
                case 4 -> editEmail(editableContact,sc);
                case 5 -> editSignificantDate(editableContact, sc);
                case 6-> System.out.println("All edits have been successfully saved. Exiting.");

                default-> System.out.println("Invalid input! Please try again.");
            }
        } while (ch != 6);
    }

    public void editFirstName(Contact editContact, Scanner sc){
        System.out.println("Enter new first name: ");
        String newFirstName = sc.next();
        editContact.setFirstName(newFirstName);
        System.out.println("First name has been successfully saved");
    }

    public void editMiddleName(Contact editContact, Scanner sc){
        System.out.println("Enter new middle name: ");
        String newMiddleName = sc.next();
        editContact.setMiddleName(newMiddleName);
        System.out.println("Middle name has been successfully saved");
    }

    public void editLastName(Contact editContact, Scanner sc){
        System.out.println("Enter new last name: ");
        String newLastName = sc.next();
        editContact.setLastName(newLastName);
        System.out.println("Last name has been successfully saved");
    }

    public void editPhoneNumber(Contact editableContact, Scanner sc){
        System.out.println("Would you like to add a number? Y/N");
        String response=sc.next();
        if(response.equals("Y")){
            System.out.println("Enter new phone number: ");
            BigInteger newNumber = sc.nextBigInteger();
            System.out.println("Enter label: ");
            String newLabelForPhoneNumber = sc.next();
            PhoneInfo newPhoneNumber = new PhoneInfo(newNumber,newLabelForPhoneNumber);

            editableContact.getPhoneList().add(newPhoneNumber);
        }

        System.out.println("Would you like to delete or edit any existing number? Y/N");
        response=sc.next();
        if(response.equals("Y")){
            System.out.println("Available phone numbers for this contact are: ");
            List<PhoneInfo> phoneList = editableContact.getPhoneList();
            for(int i=0; i<phoneList.size(); i++){
                System.out.println("ID " + (i+1) + ". " + phoneList.get(i).getNumber() + " " + phoneList.get(i).getLabel());
            }
            System.out.println("Enter id of the number you want to delete or edit?");
            int id=sc.nextInt()-1;
            System.out.println("Enter E for edit or D for delete: ");
            response=sc.next();
            if(response.equals("E")){
                System.out.println("Enter new number: ");
                BigInteger newNumber = sc.nextBigInteger();
                phoneList.get(id).setNumber(newNumber);
                System.out.println("Would you like to change label as well? Y/N");
                String innerResponse = sc.next();
                if(innerResponse.equals("Y")){
                    System.out.println("Enter new label: ");
                    String newLabelForPhoneNumber = sc.next();
                    phoneList.get(id).setLabel(newLabelForPhoneNumber);
                }
            }else{
                phoneList.remove(id);
            }
        }
        System.out.println("Phone numbers has been successfully saved!");
    }

    public void editAddress(Contact editableContact, Scanner sc){
        System.out.println("Would you like to enter a new address? Y/N");
        String response=sc.next();
        if(response.equals("Y")){
            System.out.println("Enter new address: ");
            String newAddress = sc.next();
            System.out.println("Enter label for new address: ");
            String labelForNewAddress = sc.next();
            AddressInfo newAddressInfo = new AddressInfo(newAddress, labelForNewAddress);

            editableContact.getAddressList().add(newAddressInfo);
        }
        System.out.println("Would you like to delete or edit any existing address? Y/N");
        response=sc.next();
        if(response.equals("Y")){
            System.out.println("Available addresses for this contact are: ");
            List<AddressInfo> addressList = editableContact.getAddressList();
            for(int i=0; i<addressList.size(); i++){
                System.out.println("ID " + (i+1) + ". " + addressList.get(i).getAddress() + " " + addressList.get(i).getLabel());
            }
            System.out.println("Enter id of the address you want to delete or edit?");
            int id=sc.nextInt()-1;
            System.out.println("Enter E for edit or D for delete: ");
            response =sc.next();
            if(response.equals("E")){
                System.out.println("Enter new address: ");
                String newAddress = sc.next();
                addressList.get(id).setAddress(newAddress);
                System.out.println("Would you like to change label as well? Y/N");
                String innerResponse = sc.next();
                if(innerResponse.equals("Y")){
                    System.out.println("Enter new label: ");
                    String newLabelForAddress = sc.next();
                    addressList.get(id).setLabel(newLabelForAddress);
                }
            }else{
                addressList.remove(id);
            }
        }
        System.out.println("Address has been successfully saved!");
    }

    public void editEmail(Contact editableContact, Scanner sc){
        System.out.println("Would you like to enter a new email? Y/N");
        String response=sc.next();
        if(response.equals("Y")){
            System.out.println("Enter new email: ");
            String newEmail = sc.next();
            System.out.println("Enter label for new email: ");
            String labelForNewEmail = sc.next();
            EmailInfo newEmailInfo = new EmailInfo(newEmail, labelForNewEmail);

            editableContact.getEmailList().add(newEmailInfo);
        }
        System.out.println("Would you like to delete or edit any existing email? Y/N");
        response=sc.next();
        if(response.equals("Y")){
            System.out.println("Available emails for this contact are: ");
            List<EmailInfo> emailInfoList = editableContact.getEmailList();
            for(int i=0; i<emailInfoList.size(); i++){
                System.out.println("ID " + (i+1) + ". " + emailInfoList.get(i).getEmail() + " " + emailInfoList.get(i).getLabel());
            }
            System.out.println("Enter id of the email you want to delete or edit?");
            int id=sc.nextInt()-1;
            System.out.println("Enter E for edit or D for delete: ");
            response =sc.next();
            if(response.equals("E")){
                System.out.println("Enter new address: ");
                String newEmail = sc.next();
                emailInfoList.get(id).setEmail(newEmail);
                System.out.println("Would you like to change label as well? Y/N");
                String innerResponse = sc.next();
                if(innerResponse.equals("Y")){
                    System.out.println("Enter new label: ");
                    String newLabelForEmail = sc.next();
                    emailInfoList.get(id).setLabel(newLabelForEmail);
                }
            }else{
                emailInfoList.remove(id);
            }
        }
        System.out.println("Email has been successfully saved!");
    }

    public void editSignificantDate(Contact editableContact, Scanner sc){
        System.out.println("Would you like to enter a new significant date? Y/N");
        String response=sc.next();
        if(response.equals("Y")){
            SignificantDateInfo newSignificantDateInfo;
            int year, month, day;
            Date newSignificantDate;
            System.out.println("Enter year: ");
            year = sc.nextInt();

            System.out.println("Enter month: ");
            month = sc.nextInt();

            System.out.println("Enter day: ");
            day = sc.nextInt();

            newSignificantDate = new Date(year, month, day);
            System.out.println("Enter label for significant date: ");
            String labelForNewSignificantDate = sc.next();

            newSignificantDateInfo = new SignificantDateInfo(newSignificantDate, labelForNewSignificantDate);
            editableContact.getSignificantDateList().add(newSignificantDateInfo);
        }
        System.out.println("Would you like to delete or edit any existing significant date? Y/N");
        response=sc.next();
        if(response.equals("Y")){
            System.out.println("Available significant dates are for this contact are: ");
            List<SignificantDateInfo> significantDateInfoList = editableContact.getSignificantDateList();
            for(int i=0; i<significantDateInfoList.size(); i++){
                System.out.println("ID " + (i+1) + ". " + significantDateInfoList.get(i).getDate() + " " + significantDateInfoList.get(i).getLabel());
            }
            System.out.println("Enter id of the significant date you want to delete or edit?");
            int id=sc.nextInt()-1;
            System.out.println("Enter E for edit or D for delete: ");
            response =sc.next();
            if(response.equals("E")){
                System.out.println("Enter new significant date: ");
                int year, month, day;
                Date newSignificantDate;
                System.out.println("Enter year: ");
                year = sc.nextInt();

                System.out.println("Enter month: ");
                month = sc.nextInt();

                System.out.println("Enter day: ");
                day = sc.nextInt();

                newSignificantDate = new Date(year, month, day);
                significantDateInfoList.get(id).setDate(newSignificantDate);

                System.out.println("Would you like to change the label as well? Y/N");
                String innerResponse = sc.next();
                if(innerResponse.equals("Y")){
                    System.out.println("Enter new label: ");
                    String newLabelForSignificantDate = sc.next();
                    significantDateInfoList.get(id).setLabel(newLabelForSignificantDate);
                }
            }else{
                significantDateInfoList.remove(id);
            }
        }
        System.out.println("Significant date has been successfully saved!");
    }


    public void fetchAllContact(){
        contactList=contactsDao.fetchAllContactFromDB();
        if(contactList.isEmpty()){
            System.out.println("No contact available! Please add new contact!");
        }else{
            System.out.println("All contacts has been fetched!");
        }
    }
}



