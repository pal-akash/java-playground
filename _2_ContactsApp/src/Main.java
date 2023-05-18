import service.ContactService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ContactService newContactService = new ContactService();
        newContactService.fetchAllContact();
        int choice;
        System.out.println("Welcome to the contact app!");
        do{
            System.out.println("Enter your choice: "
                    + "\n1. Add new contact \n2. Edit contact\n3. Delete contact "
                    + "\n4. Search contact\n5. Show all contact\n6. Delete all contact \n7. Exit");
            choice=sc.nextInt();
            switch (choice) {
                case 1 -> newContactService.addToContact(sc);
                case 2 -> newContactService.editContact(sc);
                case 3 -> newContactService.searchForDelete(sc);
                case 4 -> newContactService.searchForDisplayContact(sc);
                case 5 -> newContactService.showAllContact();
                case 6 -> newContactService.deleteAllContact();
                case 7 -> System.out.println("Exiting app...");
                default -> System.out.println("Invalid input! Please try again.");
            }
        } while (choice != 7);
    }
}