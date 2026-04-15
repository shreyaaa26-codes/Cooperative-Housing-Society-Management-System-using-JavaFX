package society;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SocietyData {

    private static final SocietyData INSTANCE = new SocietyData();

    private final ObservableList<Member>            members    = FXCollections.observableArrayList();
    private final ObservableList<MaintenanceRecord> maintenance = FXCollections.observableArrayList();
    private final ObservableList<Complaint>         complaints  = FXCollections.observableArrayList();

    private SocietyData() {
        seedData();
    }

    public static SocietyData getInstance() { return INSTANCE; }

    // ── Getters ──────────────────────────────────────────────────────────
    public ObservableList<Member>            getMembers()     { return members; }
    public ObservableList<MaintenanceRecord> getMaintenance() { return maintenance; }
    public ObservableList<Complaint>         getComplaints()  { return complaints; }

    // ── Lookup helpers ────────────────────────────────────────────────────
    public Member findMember(String flatNumber) {
        for (Member m : members)
            if (m.getFlatNumber().equalsIgnoreCase(flatNumber)) return m;
        return null;
    }

    public boolean flatExists(String flatNumber) {
        return findMember(flatNumber) != null;
    }

    public long countPendingMaintenance() {
        return maintenance.stream()
            .filter(r -> !"Paid".equals(r.getPaymentStatus())).count();
    }

    public long countOpenComplaints() {
        return complaints.stream()
            .filter(c -> "Open".equals(c.getResolutionStatus())).count();
    }

    // ── Seed demo data ────────────────────────────────────────────────────
    private void seedData() {
        // Members
        members.add(new Member("A-101", "Ramesh Sharma",  "9876543210", "ramesh@email.com",  "2BHK", "Owner"));
        members.add(new Member("A-102", "Priya Mehta",    "9123456780", "priya@email.com",   "1BHK", "Owner"));
        members.add(new Member("A-103", "Sunil Verma",    "9988776655", "sunil@email.com",   "3BHK", "Tenant"));
        members.add(new Member("B-201", "Kavitha Nair",   "9001234567", "kavitha@email.com", "2BHK", "Owner"));
        members.add(new Member("B-202", "Arjun Reddy",    "9812345678", "arjun@email.com",   "1BHK", "Tenant"));

        // Maintenance records
        maintenance.add(new MaintenanceRecord("A-101", "Ramesh Sharma",  "April 2025", 2500));
        maintenance.add(new MaintenanceRecord("A-102", "Priya Mehta",    "April 2025", 2000));
        maintenance.add(new MaintenanceRecord("A-103", "Sunil Verma",    "April 2025", 3000));
        maintenance.add(new MaintenanceRecord("B-201", "Kavitha Nair",   "April 2025", 2500));
        maintenance.add(new MaintenanceRecord("B-202", "Arjun Reddy",    "April 2025", 2000));

        // Mark one as paid, one partial
        maintenance.get(0).setAmountPaid(2500);
        maintenance.get(3).setAmountPaid(1000);

        // Complaints
        complaints.add(new Complaint("A-101", "Ramesh Sharma",  "Water",       "Water supply interrupted since 3 days."));
        complaints.add(new Complaint("A-102", "Priya Mehta",    "Electricity", "Power cut in staircase area."));
        complaints.add(new Complaint("B-202", "Arjun Reddy",    "Lift",        "Lift making unusual noise."));
    }
}
