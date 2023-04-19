package service;
import entity.Contact;
import entity.PhoneInfo;

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
        int year, month, day;
        LocalDate significantDate;
        if (sc.next().equals("Y")) {
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
        String labelForContact = sc.next();
        if (labelForContact.equals("Y")) {
            newContact.setLabel(labelForContact);
        }
        return newContact;
    }

    public void showAllContact(List<Contact> contactList) {
        for (int i = 0; i < contactList.size(); i++) {
            System.out.println(contactList.get(i).getName());
        }
    }

    public Contact searchContact(List<Contact> contactList, Scanner sc) {
        System.out.println("Would you like to search using phone number or name? (P/N)");
        if (sc.next().equals("P")) {
            System.out.println("Enter phone number: ");
            BigInteger phoneNumber = sc.nextBigInteger();
            for (int i = 0; i < contactList.size(); i++) {
                List<PhoneInfo> phoneList = contactList.get(i).getPhoneList();
                for (int j = 0; j < phoneList.size(); j++) {
                    if (phoneList.get(j).getNumber().equals(phoneNumber)) {
                        return contactList.get(i);
                    }
                }
            }
        } else {
            System.out.println("Enter name: ");
            String name = sc.next();
            for (int i = 0; i < contactList.size(); i++) {
                if (contactList.get(i).getName().contains(name)) {
                    return contactList.get(i);
                }
            }
        }
        return null;
    }

    public void displayContact(Contact particularContact) {
        System.out.println(particularContact.getName());
        List<PhoneInfo> phoneList = particularContact.getPhoneList();
        for (int i = 0; i < phoneList.size(); i++) {
            System.out.println(phoneList.get(i).getNumber());
        }
    }

    public void deleteContact(List<Contact> contactList, Scanner sc) {
        Contact searchedContact = searchContact(contactList, sc);
        if (Objects.isNull(searchedContact)) {
            System.out.println("No such contact found!");
        } else {
            contactList.remove(searchedContact);
        }
        System.out.println("Contact has been successfully removed!");
    }

    public void editContact(List<Contact> contactList, Scanner sc) {
        Contact editableContact = searchContact(contactList, sc);
        int ch;
        do {
            System.out.println("What would you like to edit? \nEnter: \n1 for Name\n2 for Phone number \n3 for Address \n4 for Significant Date \n5 to finish editing");
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

                case 3 -> System.out.println("This section is for editing address and yet to be coded..");
                case 4 -> System.out.println("This section is for editing significant date and yet to be coded..");
                case 5-> System.out.println("All edits have been successfully saved. Exiting.");

                default-> System.out.println("Invalid input! Please try again.");
            }
        } while (ch != 5);
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

    public void editPhoneNumber(Contact editContact, Scanner sc){
        System.out.println("Would you like to add a number? Y/N");
        String response=sc.next();
        if(response.equals("Y")){
            System.out.println("Enter new phone number: ");
            BigInteger newNumber = sc.nextBigInteger();
            System.out.println("Enter label: ");
            String newLabelForPhoneNumber = sc.next();
            PhoneInfo newPhoneNumber = new PhoneInfo(newNumber,newLabelForPhoneNumber);

            editContact.getPhoneList().add(newPhoneNumber);
        }

        System.out.println("Would you like to delete or edit any existing number? Y/N");
        response=sc.next();
        if(response.equals("Y")){
            System.out.println("Available phone numbers are: ");
            List<PhoneInfo> phoneList = editContact.getPhoneList();
            for(int i=0; i<phoneList.size(); i++){
                System.out.println("ID " + (i+1) + ". " + phoneList.get(i).getNumber() + " " + phoneList.get(i).getLabel());
            }
            System.out.println("Enter id of the number you want to delete or edit?");
            int id=sc.nextInt();
            System.out.println("Enter e for edit or d for delete: ");
            String innerResponse=sc.next();
            if(innerResponse.equals("e")){
                System.out.println("Enter new number: ");
                BigInteger newNumber = sc.nextBigInteger();
                phoneList.get(id).setNumber(newNumber);
                System.out.println("Would you like to change label as well? Y/N");
                String innerInnerResponse = sc.next();
                if(innerInnerResponse.equals("Y")){
                    System.out.println("Enter new label: ");
                    String newLabelForPhoneNumber = sc.next();
                    phoneList.get(id).setLabel(newLabelForPhoneNumber);
                }
            }else{
                phoneList.remove(id-1);
            }
        }
        System.out.println("Phone numbers has been successfully saved!");
    }
}
