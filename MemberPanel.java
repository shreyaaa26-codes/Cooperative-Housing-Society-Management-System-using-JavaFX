package society;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class MemberPanel {

    private final VBox        view;
    private final SocietyData data = SocietyData.getInstance();
    private TableView<Member> tableView;
    private Label             statusLabel;

    // Form fields
    private TextField   tfFlat, tfName, tfContact, tfEmail;
    private ComboBox<String> cbFlatType, cbMembershipType;

    public MemberPanel() {
        view = new VBox(16);
        view.getStyleClass().add("panel");
        view.setPadding(new Insets(24));
        buildUI();
    }

    private void buildUI() {
        Label heading    = new Label("Member Management");
        heading.getStyleClass().add("panel-heading");
        Label subheading = new Label("Register and manage society members");
        subheading.getStyleClass().add("panel-subheading");

        TitledPane formPane = buildForm();
        tableView = buildTable();
        VBox.setVgrow(tableView, Priority.ALWAYS);

        statusLabel = new Label();
        statusLabel.getStyleClass().add("status-label");

        view.getChildren().addAll(new VBox(2, heading, subheading),
                formPane, tableView, statusLabel);
    }

    // ── Form ──────────────────────────────────────────────────────────────
    private TitledPane buildForm() {
        GridPane grid = new GridPane();
        grid.setHgap(14); grid.setVgap(12);
        grid.setPadding(new Insets(16));

        tfFlat    = field("e.g. A-201");
        tfName    = field("Full name");
        tfContact = field("10-digit number");
        tfEmail   = field("email@example.com");

        cbFlatType = new ComboBox<>(
            FXCollections.observableArrayList("1BHK", "2BHK", "3BHK"));
        cbFlatType.setPromptText("Select"); cbFlatType.setMaxWidth(Double.MAX_VALUE);

        cbMembershipType = new ComboBox<>(
            FXCollections.observableArrayList("Owner", "Tenant"));
        cbMembershipType.setPromptText("Select"); cbMembershipType.setMaxWidth(Double.MAX_VALUE);

        grid.add(lbl("Flat Number"),      0, 0); grid.add(tfFlat,            1, 0);
        grid.add(lbl("Owner / Tenant Name"), 2, 0); grid.add(tfName,         3, 0);
        grid.add(lbl("Contact Number"),   0, 1); grid.add(tfContact,         1, 1);
        grid.add(lbl("Email"),            2, 1); grid.add(tfEmail,           3, 1);
        grid.add(lbl("Flat Type"),        0, 2); grid.add(cbFlatType,        1, 2);
        grid.add(lbl("Membership Type"),  2, 2); grid.add(cbMembershipType,  3, 2);

        ColumnConstraints c = new ColumnConstraints(130);
        ColumnConstraints d = new ColumnConstraints(170);
        grid.getColumnConstraints().addAll(c, d, c, d);

        Button btnAdd   = new Button("➕  Add Member");
        btnAdd.getStyleClass().add("btn-primary");
        btnAdd.setOnAction(e -> handleAdd());

        Button btnClear = new Button("Clear");
        btnClear.getStyleClass().add("btn-ghost");
        btnClear.setOnAction(e -> clearForm());

        HBox actions = new HBox(10, btnAdd, btnClear);
        actions.setPadding(new Insets(4, 16, 14, 16));

        TitledPane pane = new TitledPane("Add New Member", new VBox(0, grid, actions));
        pane.getStyleClass().add("titled-pane");
        pane.setExpanded(true);
        return pane;
    }

    // ── Table ─────────────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    private TableView<Member> buildTable() {
        TableView<Member> tv = new TableView<>(data.getMembers());
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.getStyleClass().add("data-table");
        tv.setPlaceholder(new Label("No members found."));

        TableColumn<Member, String> colFlat = col("Flat No.", "flatNumber");
        TableColumn<Member, String> colName = col("Name",       "ownerName");
        TableColumn<Member, String> colPhone= col("Contact",    "contactNumber");
        TableColumn<Member, String> colType = col("Flat Type",  "flatType");
        TableColumn<Member, String> colMem  = col("Type",       "membershipType");
        TableColumn<Member, String> colStat = col("Status",     "status");

        colStat.setCellFactory(c -> statusCell());

        // Action column – toggle active/inactive
        TableColumn<Member, Void> colAction = new TableColumn<>("Action");
        colAction.setCellFactory(c -> new TableCell<>() {
            private final Button btn = new Button("Toggle Status");
            { btn.getStyleClass().add("btn-ghost");
              btn.setOnAction(e -> {
                  Member m = getTableView().getItems().get(getIndex());
                  m.setStatus("Active".equals(m.getStatus()) ? "Inactive" : "Active");
                  getTableView().refresh();
              });
            }
            @Override protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tv.getColumns().addAll(colFlat, colName, colPhone, colType, colMem, colStat, colAction);
        return tv;
    }

    // ── Handlers ──────────────────────────────────────────────────────────
    private void handleAdd() {
        String flat    = tfFlat.getText().trim().toUpperCase();
        String name    = tfName.getText().trim();
        String contact = tfContact.getText().trim();
        String email   = tfEmail.getText().trim();
        String ftype   = cbFlatType.getValue();
        String mtype   = cbMembershipType.getValue();

        if (flat.isEmpty() || name.isEmpty() || contact.isEmpty()
                || email.isEmpty() || ftype == null || mtype == null) {
            showStatus("⚠  Please fill in all fields.", false); return;
        }
        if (!contact.matches("\\d{10}")) {
            showStatus("⚠  Contact number must be 10 digits.", false); return;
        }
        if (data.flatExists(flat)) {
            showStatus("⚠  Flat " + flat + " is already registered.", false); return;
        }

        data.getMembers().add(new Member(flat, name, contact, email, ftype, mtype));
        showStatus("✔  Member for Flat " + flat + " added successfully.", true);
        clearForm();
    }

    private void clearForm() {
        tfFlat.clear(); tfName.clear(); tfContact.clear(); tfEmail.clear();
        cbFlatType.setValue(null); cbMembershipType.setValue(null);
        statusLabel.setText("");
    }

    // ── Helpers ───────────────────────────────────────────────────────────
    private <T> TableColumn<Member, T> col(String title, String prop) {
        TableColumn<Member, T> c = new TableColumn<>(title);
        c.setCellValueFactory(new PropertyValueFactory<>(prop));
        return c;
    }

    private TableCell<Member, String> statusCell() {
        return new TableCell<>() {
            @Override protected void updateItem(String s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) { setText(null); setStyle(""); return; }
                setText(s);
                setStyle("Active".equals(s)
                    ? "-fx-text-fill:#4ade80; -fx-font-weight:bold;"
                    : "-fx-text-fill:#f87171; -fx-font-weight:bold;");
            }
        };
    }

    private TextField field(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.getStyleClass().add("text-field");
        return tf;
    }

    private Label lbl(String t) {
        Label l = new Label(t); l.getStyleClass().add("form-label"); return l;
    }

    private void showStatus(String msg, boolean ok) {
        statusLabel.setText(msg);
        statusLabel.setStyle(ok ? "-fx-text-fill:#4ade80;" : "-fx-text-fill:#f87171;");
    }

    public VBox getView() { return view; }
}
