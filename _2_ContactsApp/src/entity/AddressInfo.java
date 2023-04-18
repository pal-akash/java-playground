package entity;

public class AddressInfo implements LabelInfo{
    private String address;
    private String label;

    public AddressInfo(String address, String label){
        this.address=address;
        this.label=label;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
