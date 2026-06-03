package society;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class DashboardPanel {

    private final VBox view;
    private final SocietyData data = SocietyData.getInstance();

    public DashboardPanel() {
        view = new VBox(24);
        view.getStyleClass().add("panel");
        view.setPadding(new Insets(28));
        buildUI();
    }

    private void buildUI() {
        Label heading    = new Label("Dashboard");
        heading.getStyleClass().add("panel-heading");
        Label subheading = new Label("Society at a glance");
        subheading.getStyleClass().add("panel-subheading");

        // ── Stat cards ──────────────────────────────────────────────
        int totalMembers   = data.getMembers().size();
        long pendingDues   = data.countPendingMaintenance();
        long openComplaints = data.countOpenComplaints();
        long activeMem     = data.getMembers().stream()
                                .filter(m -> "Active".equals(m.getStatus())).count();

        HBox cards = new HBox(16,
            statCard("👥",  String.valueOf(totalMembers),   "Total Members",        "#6366f1"),
            statCard("✅",  String.valueOf(activeMem),      "Active Members",       "#22c55e"),
            statCard("💰",  String.valueOf(pendingDues),    "Pending Maintenance",  "#f59e0b"),
            statCard("📝",  String.valueOf(openComplaints), "Open Complaints",      "#ef4444")
        );
        cards.setAlignment(Pos.CENTER_LEFT);

        // ── Recent complaints ────────────────────────────────────────
        Label recentHeading = new Label("Recent Complaints");
        recentHeading.getStyleClass().add("section-heading");

        VBox complaintList = new VBox(8);
        int limit = Math.min(3, data.getComplaints().size());
        for (int i = 0; i < limit; i++) {
            Complaint c = data.getComplaints().get(i);
            complaintList.getChildren().add(complaintRow(c));
        }
        if (data.getComplaints().isEmpty()) {
            Label none = new Label("No complaints raised yet.");
            none.getStyleClass().add("panel-subheading");
            complaintList.getChildren().add(none);
        }

        // ── Recent maintenance ───────────────────────────────────────
        Label maintHeading = new Label("Recent Maintenance Status");
        maintHeading.getStyleClass().add("section-heading");

        VBox maintList = new VBox(8);
        int mlimit = Math.min(4, data.getMaintenance().size());
        for (int i = 0; i < mlimit; i++) {
            MaintenanceRecord r = data.getMaintenance().get(i);
            maintList.getChildren().add(maintenanceRow(r));
        }

        view.getChildren().addAll(
            new VBox(2, heading, subheading),
            cards,
            recentHeading, complaintList,
            maintHeading,  maintList
        );
    }

    // ── Card builder ─────────────────────────────────────────────────────
    private VBox statCard(String icon, String value, String label, String accentColor) {
        Label iconLbl  = new Label(icon);
        iconLbl.setStyle("-fx-font-size:28px;");
        Label valueLbl = new Label(value);
        valueLbl.setStyle("-fx-font-size:28px; -fx-font-weight:bold; -fx-text-fill:" + accentColor + ";");
        Label labelLbl = new Label(label);
        labelLbl.setStyle("-fx-font-size:12px; -fx-text-fill:#94a3b8;");

        VBox card = new VBox(4, iconLbl, valueLbl, labelLbl);
        card.setPadding(new Insets(18));
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefWidth(185);
        card.getStyleClass().add("stat-card");
        // Colored left border
        card.setStyle("-fx-border-color: transparent transparent transparent " + accentColor + ";"
                    + "-fx-border-width: 0 0 0 4;");
        return card;
    }

    // ── Row builders ──────────────────────────────────────────────────────
    private HBox complaintRow(Complaint c) {
        Label cat   = new Label(c.getCategory());
        cat.getStyleClass().add("badge");

        Label name  = new Label(c.getRaisedBy() + "  (" + c.getFlatNumber() + ")");
        name.setStyle("-fx-font-size:13px; -fx-text-fill:#e2e8f0;");

        Label desc  = new Label(c.getDescription());
        desc.setStyle("-fx-font-size:12px; -fx-text-fill:#94a3b8;");
        desc.setWrapText(true);

        VBox  info  = new VBox(2, name, desc);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label status = new Label(c.getResolutionStatus());
        statusBadge(status, c.getResolutionStatus());

        HBox row = new HBox(12, cat, info, status);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10));
        row.getStyleClass().add("list-row");
        return row;
    }

    private HBox maintenanceRow(MaintenanceRecord r) {
        Label flat   = new Label(r.getFlatNumber());
        flat.getStyleClass().add("badge");

        Label name   = new Label(r.getOwnerName());
        name.setStyle("-fx-font-size:13px; -fx-text-fill:#e2e8f0;");
        Label month  = new Label(r.getMonth());
        month.setStyle("-fx-font-size:12px; -fx-text-fill:#94a3b8;");

        VBox  info   = new VBox(2, name, month);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label amount = new Label("₹" + (int) r.getAmountDue());
        amount.setStyle("-fx-font-size:13px; -fx-text-fill:#e2e8f0;");

        Label status = new Label(r.getPaymentStatus());
        statusBadge(status, r.getPaymentStatus());

        HBox row = new HBox(12, flat, info, amount, status);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10));
        row.getStyleClass().add("list-row");
        return row;
    }

    private void statusBadge(Label lbl, String status) {
        lbl.getStyleClass().add("badge");
        switch (status) {
            case "Paid":        lbl.setStyle("-fx-background-color:#14532d; -fx-text-fill:#4ade80; -fx-background-radius:6; -fx-padding:3 8;"); break;
            case "Partial":     lbl.setStyle("-fx-background-color:#78350f; -fx-text-fill:#fbbf24; -fx-background-radius:6; -fx-padding:3 8;"); break;
            case "Pending":     lbl.setStyle("-fx-background-color:#450a0a; -fx-text-fill:#f87171; -fx-background-radius:6; -fx-padding:3 8;"); break;
            case "Open":        lbl.setStyle("-fx-background-color:#450a0a; -fx-text-fill:#f87171; -fx-background-radius:6; -fx-padding:3 8;"); break;
            case "In Progress": lbl.setStyle("-fx-background-color:#1e3a5f; -fx-text-fill:#60a5fa; -fx-background-radius:6; -fx-padding:3 8;"); break;
            case "Resolved":    lbl.setStyle("-fx-background-color:#14532d; -fx-text-fill:#4ade80; -fx-background-radius:6; -fx-padding:3 8;"); break;
            default:            lbl.setStyle("-fx-background-color:#1e293b; -fx-text-fill:#94a3b8; -fx-background-radius:6; -fx-padding:3 8;");
        }
    }

    public VBox getView() { return view; }
}
