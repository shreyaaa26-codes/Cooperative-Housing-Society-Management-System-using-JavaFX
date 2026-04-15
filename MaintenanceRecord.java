package society;

import javafx.beans.property.*;

public class MaintenanceRecord {
    private final StringProperty flatNumber;
    private final StringProperty ownerName;
    private final StringProperty month;        // e.g. "April 2025"
    private final DoubleProperty amountDue;
    private final DoubleProperty amountPaid;
    private final StringProperty paymentStatus; // Paid / Pending / Partial

    public MaintenanceRecord(String flatNumber, String ownerName,
                             String month, double amountDue) {
        this.flatNumber    = new SimpleStringProperty(flatNumber);
        this.ownerName     = new SimpleStringProperty(ownerName);
        this.month         = new SimpleStringProperty(month);
        this.amountDue     = new SimpleDoubleProperty(amountDue);
        this.amountPaid    = new SimpleDoubleProperty(0.0);
        this.paymentStatus = new SimpleStringProperty("Pending");
    }

    public String getFlatNumber()    { return flatNumber.get(); }
    public StringProperty flatNumberProperty() { return flatNumber; }

    public String getOwnerName()     { return ownerName.get(); }
    public StringProperty ownerNameProperty() { return ownerName; }

    public String getMonth()         { return month.get(); }
    public StringProperty monthProperty() { return month; }

    public double getAmountDue()     { return amountDue.get(); }
    public DoubleProperty amountDueProperty() { return amountDue; }

    public double getAmountPaid()    { return amountPaid.get(); }
    public void   setAmountPaid(double v) {
        amountPaid.set(v);
        if (v <= 0)               paymentStatus.set("Pending");
        else if (v >= amountDue.get()) paymentStatus.set("Paid");
        else                           paymentStatus.set("Partial");
    }
    public DoubleProperty amountPaidProperty() { return amountPaid; }

    public String getPaymentStatus() { return paymentStatus.get(); }
    public StringProperty paymentStatusProperty() { return paymentStatus; }
}
