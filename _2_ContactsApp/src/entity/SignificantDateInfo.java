package entity;

import java.time.LocalDate;


public class SignificantDateInfo implements LabelInfo{
    private LocalDate date;
    private String label;

    public SignificantDateInfo(LocalDate date, String label){
        this.date=date;
        this.label=label;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
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
