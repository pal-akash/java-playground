package service;
import entity.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ContactService {

    public Contact addToContact() {
        Contact newContact = new Contact();
        Scanner sc = new Scanner(System.in);

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
            System.out.println("Enter email: ");
            String email = sc.next();
            System.out.println("Enter label for email: ");
            String labelForEmail = sc.next();
            newContact.addEmail(email, labelForEmail);
        }

        System.out.println("Would you like to add any address? Y/N");
        if (sc.next().equals("Y")) {
            System.out.println("Enter address: ");
            String address = sc.next();
            System.out.println("Enter label for address: ");
            String labelForAddress = sc.next();
            newContact.addAddress(address, labelForAddress);
        }

        System.out.println("Would you like to add any significant date? Y/N");
        if (sc.next().equals("Y")) {
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

        System.out.println("Would you like to add contact label? Y/N");
        if (sc.next().equals("Y")) {
            System.out.println("Enter label for contact: ");
            String labelForContact = sc.next();
            newContact.setLabel(labelForContact);
        }
        return newContact;
    }

    public void showAllContact(List<Contact> contactList) {
        for (Contact contact : contactList) {
            System.out.println(contact.getName());
        }
    }

    public Contact searchContact(List<Contact> contactList, Scanner sc) {
        System.out.println("Would you like to search using phone number or name? (P/N)");
        if (sc.next().equals("P")) {
            System.out.println("Enter phone number: ");
            BigInteger phoneNumber = sc.nextBigInteger();
            for (Contact contact : contactList) {
                List<PhoneInfo> phoneList = contact.getPhoneList();
                for (PhoneInfo phoneInfo : phoneList) {
                    if (phoneInfo.getNumber().equals(phoneNumber)) {
                        System.out.println("Contact found!");
                        return contact;
                    }
                }
            }
        } else {
            System.out.println("Enter name: ");
            String name = sc.next();
            for (Contact contact : contactList) {
                if (contact.getName().contains(name)) {
                    System.out.println("Contact found!");
                    return contact;
                }
            }
        }
        System.out.println("No such contact found!");
        return null;
    }

    public void displayContact(Contact particularContact, Scanner sc) {
        System.out.println(particularContact.getName());
        List<PhoneInfo> phoneList = particularContact.getPhoneList();
        System.out.println("Phone numbers: ");
        for (PhoneInfo phoneInfo : phoneList) {
            System.out.println(phoneInfo.getNumber());
        }
        System.out.println("Would you like to see full contact details? Y/N");
        if(sc.next().equals("Y")){
            System.out.println("\nFull name: \n" + particularContact.getName());
            System.out.println("\nContact label: \n" + particularContact.getLabel());
            System.out.println("\nPhone numbers: ");
            for (PhoneInfo phoneInfo : phoneList) {
                System.out.println(phoneInfo.getNumber() + " " + phoneInfo.getLabel());
            }
            List<AddressInfo> addressInfoList = particularContact.getAddressList();
            List<EmailInfo> emailInfoList = particularContact.getEmailList();
            List<SignificantDateInfo> significantDateInfoList= particularContact.getSignificantDateList();
            System.out.println("\nAddresses: ");
            for (AddressInfo addressInfo : addressInfoList) {
                System.out.println(addressInfo.getAddress() + " " + addressInfo.getLabel());
            }
            System.out.println("\nEmails: ");
            for (EmailInfo emailInfo : emailInfoList) {
                System.out.println(emailInfo.getEmail() + " " + emailInfo.getLabel());
            }
            System.out.println("\nSignificant dates are: ");
            for (SignificantDateInfo significantDateInfo : significantDateInfoList) {
                System.out.println(significantDateInfo.getDate() + " " + significantDateInfo.getLabel());
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
            LocalDate newSignificantDate;
            System.out.println("Enter year: ");
            year = sc.nextInt();

            System.out.println("Enter month: ");
            month = sc.nextInt();

            System.out.println("Enter day: ");
            day = sc.nextInt();

            newSignificantDate = LocalDate.of(year, month, day);
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
                LocalDate newSignificantDate;
                System.out.println("Enter year: ");
                year = sc.nextInt();

                System.out.println("Enter month: ");
                month = sc.nextInt();

                System.out.println("Enter day: ");
                day = sc.nextInt();

                newSignificantDate = LocalDate.of(year, month, day);
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
}



