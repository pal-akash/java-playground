package entity;

import java.util.Date;

public class SignificantDateInfo implements LabelInfo{
    private Date date;
    private String label;

    public SignificantDateInfo(Date date, String label){
        this.date=date;
        this.label=label;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }
}
