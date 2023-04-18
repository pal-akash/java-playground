package entity;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Contact {
    private String firstName;
    private String middleName;
    private String lastName;
    private String label;

    private List<PhoneInfo> phoneList;
    private List<EmailInfo> emailList;
    private List<AddressInfo> addressList;
    private List<SignificantDateInfo> significantDateList;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        if (Objects.isNull(this.lastName)) {
            return getFirstName();
        } else if (Objects.isNull(this.middleName)) {
            return getFullName();
        } else {
            return getFullNameWithMiddleName();
        }
    }

    private String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    private String getFullNameWithMiddleName() {
        return this.firstName + " " + this.middleName + " " + this.lastName;
    }

    public List<PhoneInfo> getPhoneList() {
        return phoneList;
    }

    public void addPhone(BigInteger number, String label) {
        if (Objects.isNull(this.phoneList)) {
            this.phoneList = new ArrayList<>();
        }
        this.phoneList.add(new PhoneInfo(number, label));
    }

    public List<EmailInfo> getEmailList() {
        return emailList;
    }

    public void addEmail(String email, String label) {
        if(Objects.isNull(this.emailList)){
            this.emailList=new ArrayList<>();
        }
        this.emailList.add(new EmailInfo(email, label));
    }

    public List<AddressInfo> getAddressList() {
        return addressList;
    }

    public void addAddress(List<AddressInfo> addressList) {
        this.addressList = addressList;
    }

    public void addAddress(String address, String label) {
        if (Objects.isNull(this.addressList)) {
            this.addressList = new ArrayList<>();
        }
        this.addressList.add(new AddressInfo(address, label));
    }

    public List<SignificantDateInfo> getSignificantDateList() {
        return significantDateList;
    }

    public void addSignificantDate(LocalDate date, String label) {
        if(Objects.isNull(this.significantDateList)){
            this.significantDateList=new ArrayList<>();
        }
        this.significantDateList.add(new SignificantDateInfo(date, label));
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
