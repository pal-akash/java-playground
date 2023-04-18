import java.util.*;
public class App {
    void runApp(){
        Scanner sc=new Scanner(System.in);
        List<Contact> listOfContact = new ArrayList<>();
        Contact contactObj;
        int choice;
        do{
            System.out.println("Enter choice: \n1.Add Contact\n2.Delete Contact\n3.Search Contact\n4. Exit App");
            choice=sc.nextInt();
            switch(choice){
                case 1:
                System.out.println("Enter first name: ");
                String fn=sc.next();
                while(fn==" " || fn.isEmpty()){
                    System.out.println("Invalid input. Please enter again!");
                    fn=sc.next();
                }

                System.out.println("Enter middle name: ");
                String mn=sc.next();

                System.out.println("Enter Last name: ");
                String ln = sc.next();

                Phone ph=new Phone();
                System.out.println("Enter phone details: ");
                ph.setData(sc.next());

                System.out.println("Enter label: \n1.Mobile\n2.Work\n3.Home\n4.Custom");
                ph.setLabel(sc.next());
            
                Email em=new Email();
                System.out.println("Enter Email address: ");
                String ea=sc.next();

                while(!ea.contains("@")){
                    System.out.println("Invalid input. Please try again!");
                    ea=sc.next();
                }
                em.setData(ea);

                System.out.println("Enter label: \n1. Personal\2. Work\3. Offcial\n4. Custom");
                em.setLabel(sc.next());
                
                SignificantDate sd=new SignificantDate();
                System.out.println("Enter date: ");
                sd.setData(sc.next());
                System.out.println("Enter event: ");
                sd.setLabel(sc.next());

                Address ad=new Address();
                System.out.println("Enter address: ");
                ad.setData(sc.next());
                System.out.println("Enter label: ");
                ad.setLabel(sc.next());

                contactObj=new Contact();

                listOfContact.add(contactObj);
                System.out.println("Your contact has been successfullly added!");
                break;

                case 2:
                System.out.println("Enter name or phone number: ");
                String searchElem=sc.next();
                for(int i=0; i<listOfContact.size(); i++){
                    if((listOfContact.get(i).firstName==searchElem)){
                        listOfContact.get(i).displayContact();
                    }
                }
                break;
                

                default:
                System.out.println("Invalid choice! Please try again.");
            }

        }while(choice!=4);
        sc.close();
    }
}
