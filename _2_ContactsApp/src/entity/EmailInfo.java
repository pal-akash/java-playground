package entity;

public class EmailInfo implements LabelInfo {
    private String email;
    private String label;

    public EmailInfo(String email, String label){
        this.email=email;
        this.label=label;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
