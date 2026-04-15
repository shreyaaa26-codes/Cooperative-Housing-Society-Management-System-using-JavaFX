package society;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class MaintenancePanel {

    private final VBox              view;
    private final SocietyData       data = SocietyData.getInstance();
    private TableView<MaintenanceRecord> tableView;
    private Label                   statusLabel;

    // Generate bill form
    private ComboBox<Member>  cbMember;
    private TextField         tfMonth, tfAmount;

    // Record payment form
    private ComboBox<MaintenanceRecord> cbRecord;
    private TextField                   tfPaid;

    public MaintenancePanel() {
        view = new VBox(16);
        view.getStyleClass().add("panel");
        view.setPadding(new Insets(24));
        buildUI();
    }

    private void buildUI() {
        Label heading    = new Label("Maintenance Management");
        heading.getStyleClass().add("panel-heading");
        Label subheading = new Label("Generate bills and record payments");
        subheading.getStyleClass().add("panel-subheading");

        TitledPane generatePane = buildGenerateForm();
        TitledPane paymentPane  = buildPaymentForm();

        HBox forms = new HBox(16, generatePane, paymentPane);
        HBox.setHgrow(generatePane, Priority.ALWAYS);
        HBox.setHgrow(paymentPane, Priority.ALWAYS);

        tableView = buildTable();
        VBox.setVgrow(tableView, Priority.ALWAYS);

        statusLabel = new Label();
        statusLabel.getStyleClass().add("status-label");

        view.getChildren().addAll(new VBox(2, heading, subheading),
                forms, tableView, statusLabel);
    }

    // ── Generate Bill Form ────────────────────────────────────────────────
    private TitledPane buildGenerateForm() {
        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(12);
        grid.setPadding(new Insets(14));

        cbMember = new ComboBox<>(data.getMembers());
        cbMember.setPromptText("Select member"); cbMember.setMaxWidth(Double.MAX_VALUE);

        tfMonth  = new TextField();
        tfMonth.setPromptText("e.g. May 2025");
        tfMonth.getStyleClass().add("text-field");

        tfAmount = new TextField();
        tfAmount.setPromptText("Amount in ₹");
        tfAmount.getStyleClass().add("text-field");

        grid.add(lbl("Member"),  0, 0); grid.add(cbMember, 1, 0);
        grid.add(lbl("Month"),   0, 1); grid.add(tfMonth,  1, 1);
        grid.add(lbl("Amount (₹)"), 0, 2); grid.add(tfAmount, 1, 2);

        ColumnConstraints c1 = new ColumnConstraints(100);
        ColumnConstraints c2 = new ColumnConstraints(180);
        grid.getColumnConstraints().addAll(c1, c2);

        Button btn = new Button("📄  Generate Bill");
        btn.getStyleClass().add("btn-primary");
        btn.setOnAction(e -> handleGenerate());

        VBox content = new VBox(10, grid, btn);
        content.setPadding(new Insets(0, 14, 14, 14));

        TitledPane p = new TitledPane("Generate Maintenance Bill", content);
        p.getStyleClass().add("titled-pane");
        p.setExpanded(true);
        return p;
    }

    // ── Record Payment Form ───────────────────────────────────────────────
    private TitledPane buildPaymentForm() {
        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(12);
        grid.setPadding(new Insets(14));

        cbRecord = new ComboBox<>();
        refreshPendingRecords();
        cbRecord.setPromptText("Select bill"); cbRecord.setMaxWidth(Double.MAX_VALUE);

        tfPaid = new TextField();
        tfPaid.setPromptText("Amount received");
        tfPaid.getStyleClass().add("text-field");

        grid.add(lbl("Bill"),         0, 0); grid.add(cbRecord, 1, 0);
        grid.add(lbl("Amount Paid (₹)"), 0, 1); grid.add(tfPaid, 1, 1);

        ColumnConstraints c1 = new ColumnConstraints(120);
        ColumnConstraints c2 = new ColumnConstraints(180);
        grid.getColumnConstraints().addAll(c1, c2);

        Button btn = new Button("✔  Record Payment");
        btn.getStyleClass().add("btn-success");
        btn.setOnAction(e -> handlePayment());

        VBox content = new VBox(10, grid, btn);
        content.setPadding(new Insets(0, 14, 14, 14));

        TitledPane p = new TitledPane("Record Payment", content);
        p.getStyleClass().add("titled-pane");
        p.setExpanded(true);
        return p;
    }

    // ── Table ─────────────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    private TableView<MaintenanceRecord> buildTable() {
        TableView<MaintenanceRecord> tv = new TableView<>(data.getMaintenance());
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.getStyleClass().add("data-table");
        tv.setPlaceholder(new Label("No maintenance records found."));

        TableColumn<MaintenanceRecord, String> colFlat  = new TableColumn<>("Flat");
        colFlat.setCellValueFactory(new PropertyValueFactory<>("flatNumber"));

        TableColumn<MaintenanceRecord, String> colName  = new TableColumn<>("Owner");
        colName.setCellValueFactory(new PropertyValueFactory<>("ownerName"));

        TableColumn<MaintenanceRecord, String> colMonth = new TableColumn<>("Month");
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn<MaintenanceRecord, Double> colDue   = new TableColumn<>("Due (₹)");
        colDue.setCellValueFactory(new PropertyValueFactory<>("amountDue"));

        TableColumn<MaintenanceRecord, Double> colPaid  = new TableColumn<>("Paid (₹)");
        colPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));

        TableColumn<MaintenanceRecord, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        colStatus.setCellFactory(c -> new TableCell<>() {
            @Override protected void updateItem(String s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) { setText(null); setStyle(""); return; }
                setText(s);
                switch (s) {
                    case "Paid":    setStyle("-fx-text-fill:#4ade80; -fx-font-weight:bold;"); break;
                    case "Partial": setStyle("-fx-text-fill:#fbbf24; -fx-font-weight:bold;"); break;
                    default:        setStyle("-fx-text-fill:#f87171; -fx-font-weight:bold;"); break;
                }
            }
        });

        tv.getColumns().addAll(colFlat, colName, colMonth, colDue, colPaid, colStatus);
        return tv;
    }

    // ── Handlers ──────────────────────────────────────────────────────────
    private void handleGenerate() {
        Member m   = cbMember.getValue();
        String mon = tfMonth.getText().trim();
        String amt = tfAmount.getText().trim();

        if (m == null || mon.isEmpty() || amt.isEmpty()) {
            showStatus("⚠  Fill in all fields.", false); return;
        }
        double amount;
        try { amount = Double.parseDouble(amt); }
        catch (NumberFormatException ex) { showStatus("⚠  Invalid amount.", false); return; }

        // Check duplicate
        for (MaintenanceRecord r : data.getMaintenance()) {
            if (r.getFlatNumber().equals(m.getFlatNumber()) && r.getMonth().equals(mon)) {
                showStatus("⚠  Bill for " + m.getFlatNumber() + " – " + mon + " already exists.", false);
                return;
            }
        }

        data.getMaintenance().add(new MaintenanceRecord(
            m.getFlatNumber(), m.getOwnerName(), mon, amount));
        refreshPendingRecords();
        showStatus("✔  Bill generated for Flat " + m.getFlatNumber() + ".", true);
        cbMember.setValue(null); tfMonth.clear(); tfAmount.clear();
    }

    private void handlePayment() {
        MaintenanceRecord rec  = cbRecord.getValue();
        String paidStr = tfPaid.getText().trim();

        if (rec == null || paidStr.isEmpty()) {
            showStatus("⚠  Select a bill and enter amount.", false); return;
        }
        double paid;
        try { paid = Double.parseDouble(paidStr); }
        catch (NumberFormatException ex) { showStatus("⚠  Invalid amount.", false); return; }

        if (paid <= 0) { showStatus("⚠  Amount must be positive.", false); return; }

        rec.setAmountPaid(rec.getAmountPaid() + paid);
        tableView.refresh();
        refreshPendingRecords();
        showStatus("✔  Payment of ₹" + (int) paid + " recorded for Flat " + rec.getFlatNumber() + ".", true);
        cbRecord.setValue(null); tfPaid.clear();
    }

    private void refreshPendingRecords() {
        cbRecord.setItems(FXCollections.observableArrayList(
            data.getMaintenance().filtered(r -> !"Paid".equals(r.getPaymentStatus()))));
        cbRecord.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(MaintenanceRecord r) {
                return r == null ? "" : r.getFlatNumber() + " – " + r.getMonth() + " (₹" + (int)r.getAmountDue() + ")";
            }
            @Override public MaintenanceRecord fromString(String s) { return null; }
        });
    }

    public void refresh() { refreshPendingRecords(); tableView.refresh(); }

    // ── Helpers ───────────────────────────────────────────────────────────
    private Label lbl(String t) {
        Label l = new Label(t); l.getStyleClass().add("form-label"); return l;
    }
    private void showStatus(String msg, boolean ok) {
        statusLabel.setText(msg);
        statusLabel.setStyle(ok ? "-fx-text-fill:#4ade80;" : "-fx-text-fill:#f87171;");
    }

    public VBox getView() { return view; }
}
