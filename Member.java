package society;

import javafx.beans.property.*;

public class Member {
    private final StringProperty flatNumber;
    private final StringProperty ownerName;
    private final StringProperty contactNumber;
    private final StringProperty email;
    private final StringProperty flatType;       // 1BHK / 2BHK / 3BHK
    private final StringProperty membershipType; // Owner / Tenant
    private final StringProperty status;         // Active / Inactive

    public Member(String flatNumber, String ownerName, String contactNumber,
                  String email, String flatType, String membershipType) {
        this.flatNumber      = new SimpleStringProperty(flatNumber);
        this.ownerName       = new SimpleStringProperty(ownerName);
        this.contactNumber   = new SimpleStringProperty(contactNumber);
        this.email           = new SimpleStringProperty(email);
        this.flatType        = new SimpleStringProperty(flatType);
        this.membershipType  = new SimpleStringProperty(membershipType);
        this.status          = new SimpleStringProperty("Active");
    }

    public String getFlatNumber()     { return flatNumber.get(); }
    public StringProperty flatNumberProperty() { return flatNumber; }

    public String getOwnerName()      { return ownerName.get(); }
    public void   setOwnerName(String v) { ownerName.set(v); }
    public StringProperty ownerNameProperty() { return ownerName; }

    public String getContactNumber()  { return contactNumber.get(); }
    public StringProperty contactNumberProperty() { return contactNumber; }

    public String getEmail()          { return email.get(); }
    public StringProperty emailProperty() { return email; }

    public String getFlatType()       { return flatType.get(); }
    public StringProperty flatTypeProperty() { return flatType; }

    public String getMembershipType() { return membershipType.get(); }
    public StringProperty membershipTypeProperty() { return membershipType; }

    public String getStatus()         { return status.get(); }
    public void   setStatus(String v) { status.set(v); }
    public StringProperty statusProperty() { return status; }

    @Override public String toString() {
        return flatNumber.get() + " – " + ownerName.get();
    }
}
