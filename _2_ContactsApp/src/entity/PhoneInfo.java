package entity;

import java.math.BigInteger;

public class PhoneInfo implements LabelInfo{

    public BigInteger number;
    public String label;

    public PhoneInfo(BigInteger number, String label) {
        this.number = number;
        this.label = label;
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    @Override
    public String getLabel() {
        return this.getLabel();
    }


    @Override
    public void setLabel(String label) {
        this.label = label;
    }
}
