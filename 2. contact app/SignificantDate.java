import java.util.ArrayList;
import java.util.List;
class SignificantDate implements ContactInfo{
    List<String> listOfDates = new ArrayList<>();
    List<String> listOfLabel = new ArrayList<>();

    public void setData(String sigDate){
        listOfDates.add(sigDate);
    }
    public void setLabel(String label){
        listOfLabel.add(label);
    }
}