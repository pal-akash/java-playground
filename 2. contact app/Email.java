import java.util.ArrayList;
import java.util.List;
class Email implements ContactInfo{
    List<String> listOfEa = new ArrayList<>();
    List<String> listOfLabel = new ArrayList<>();

    public void setData(String emailAddress){
        listOfEa.add(emailAddress);
    }
    public void setLabel(String label){
        listOfLabel.add(label);
    }
}
