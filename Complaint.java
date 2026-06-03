package society;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Complaint {
    private final StringProperty flatNumber;
    private final StringProperty raisedBy;
    private final StringProperty category;   // Water / Electricity / Lift / Security / Other
    private final StringProperty description;
    private final StringProperty dateRaised;
    private final StringProperty resolutionStatus; // Open / In Progress / Resolved

    public Complaint(String flatNumber, String raisedBy, String category, String description) {
        this.flatNumber        = new SimpleStringProperty(flatNumber);
        this.raisedBy          = new SimpleStringProperty(raisedBy);
        this.category          = new SimpleStringProperty(category);
        this.description       = new SimpleStringProperty(description);
        this.dateRaised        = new SimpleStringProperty(LocalDate.now().toString());
        this.resolutionStatus  = new SimpleStringProperty("Open");
    }

    public String getFlatNumber()       { return flatNumber.get(); }
    public StringProperty flatNumberProperty() { return flatNumber; }

    public String getRaisedBy()         { return raisedBy.get(); }
    public StringProperty raisedByProperty() { return raisedBy; }

    public String getCategory()         { return category.get(); }
    public StringProperty categoryProperty() { return category; }

    public String getDescription()      { return description.get(); }
    public StringProperty descriptionProperty() { return description; }

    public String getDateRaised()       { return dateRaised.get(); }
    public StringProperty dateRaisedProperty() { return dateRaised; }

    public String getResolutionStatus() { return resolutionStatus.get(); }
    public void   setResolutionStatus(String v) { resolutionStatus.set(v); }
    public StringProperty resolutionStatusProperty() { return resolutionStatus; }
}
