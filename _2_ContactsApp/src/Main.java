import entity.Contact;
import service.ContactService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Contact> contactList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        ContactService newContactService = new ContactService();
        int choice;
        System.out.println("Welcome to the contact app!");
        do{
            System.out.println("Enter your choice: \n1. Add new contact \n2. Edit contact\n3. Delete contact \n4. Search contact\n5. Show all contact\n6. Exit");
            choice=sc.nextInt();
            switch (choice) {
                case 1 ->
                {
                    contactList.add(newContactService.addToContact());
                    System.out.println("Your contact has been successfully saved!");
                }
                case 2 -> System.out.println("This section is for edit contact and yet to be coded");
                case 3 -> System.out.println("This section is for deleting contact and yet to be coded");
                case 4 ->
                {
                    Contact foundContact = newContactService.searchContact(contactList, sc);
                    newContactService.displayContact(foundContact);
                }
                case 5 -> newContactService.showAllContact(contactList);
                case 6 -> System.out.println("Exiting app...");
                default -> System.out.println("Invalid input! Please try again.");
            }
        }while(choice!=6);
    }
}