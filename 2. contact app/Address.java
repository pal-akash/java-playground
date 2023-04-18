import java.util.ArrayList;
import java.util.List;
class Address implements ContactInfo{
    List<String> listOfAddress = new ArrayList<>();
    List<String> listOfLabel = new ArrayList<>();

    public void setData(String addr){
        listOfAddress.add(addr);
    }

    public void setLabel(String label){
        listOfLabel.add(label);
    }
}
