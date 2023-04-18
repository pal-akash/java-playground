public class Contact {
    private String firstName;
    private String middleName;
    private String lastName;

    private Phone phoneInfo;
    private Email emailInfo;
    private SignificantDate sigDateInfo;
    private Address addressInfo;

    public Contact(String firstName, String middleName, String lastName, Phone phoneInfo, Email emailInfo, SignificantDate sigDateInfo, Address addressInfo){
        this.firstName=firstName;
        this.middleName=middleName;
        this.lastName=lastName;
        this.phoneInfo=phoneInfo;
        this.emailInfo=emailInfo;
        this.sigDateInfo=sigDateInfo;
        this.addressInfo=addressInfo;
    }

    public Contact(String firstName, Phone phoneInfo){
        this.firstName=firstName;
        this.phoneInfo=phoneInfo;
    }

    public void addPhoneinfo(String data, String label) {
        Phone phoneInfo = new Phone();
        phoneInfo.setData(data);
        phoneInfo.setLabel(label);
        this.phoneInfo.listOfPhoneNum
    }

    void deleteContact(){

    }

    void showAllContact(){

    }

    void searchContact(Contact loc){
        
    }

    void displayContact(){
        System.out.println("First Name: " + firstName);
        System.out.println("Middle Name: " + middleName);
        System.out.println("Last Name: " + lastName);
        System.out.println("First phone: " + phoneInfo);
        System.out.println("Email phone: " + emailInfo);
        System.out.println("Significant date: " + sigDateInfo);
        System.out.println("Address: " + addressInfo);
    }

}
