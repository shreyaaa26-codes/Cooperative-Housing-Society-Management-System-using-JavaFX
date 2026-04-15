package society;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class ComplaintPanel {

    private final VBox           view;
    private final SocietyData    data = SocietyData.getInstance();
    private TableView<Complaint> tableView;
    private Label                statusLabel;

    // Form fields
    private ComboBox<Member>  cbMember;
    private ComboBox<String>  cbCategory;
    private TextArea          taDescription;

    // Update status
    private ComboBox<Complaint> cbComplaint;
    private ComboBox<String>    cbNewStatus;

    public ComplaintPanel() {
        view = new VBox(16);
        view.getStyleClass().add("panel");
        view.setPadding(new Insets(24));
        buildUI();
    }

    private void buildUI() {
        Label heading    = new Label("Complaint Management");
        heading.getStyleClass().add("panel-heading");
        Label subheading = new Label("Raise and resolve member complaints");
        subheading.getStyleClass().add("panel-subheading");

        TitledPane raisePane  = buildRaiseForm();
        TitledPane updatePane = buildUpdateForm();

        HBox forms = new HBox(16, raisePane, updatePane);
        HBox.setHgrow(raisePane, Priority.ALWAYS);
        HBox.setHgrow(updatePane, Priority.ALWAYS);

        tableView = buildTable();
        VBox.setVgrow(tableView, Priority.ALWAYS);

        statusLabel = new Label();
        statusLabel.getStyleClass().add("status-label");

        view.getChildren().addAll(new VBox(2, heading, subheading),
                forms, tableView, statusLabel);
    }

    // ── Raise Complaint Form ──────────────────────────────────────────────
    private TitledPane buildRaiseForm() {
        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(12);
        grid.setPadding(new Insets(14));

        cbMember = new ComboBox<>(data.getMembers());
        cbMember.setPromptText("Select member"); cbMember.setMaxWidth(Double.MAX_VALUE);

        cbCategory = new ComboBox<>(FXCollections.observableArrayList(
            "Water", "Electricity", "Lift", "Security", "Parking", "Sanitation", "Other"));
        cbCategory.setPromptText("Category"); cbCategory.setMaxWidth(Double.MAX_VALUE);

        taDescription = new TextArea();
        taDescription.setPromptText("Describe the issue…");
        taDescription.setPrefRowCount(3);
        taDescription.setWrapText(true);
        taDescription.getStyleClass().add("text-area");

        grid.add(lbl("Member"),      0, 0); grid.add(cbMember,     1, 0);
        grid.add(lbl("Category"),    0, 1); grid.add(cbCategory,   1, 1);
        grid.add(lbl("Description"), 0, 2); grid.add(taDescription,1, 2);

        ColumnConstraints c1 = new ColumnConstraints(110);
        ColumnConstraints c2 = new ColumnConstraints(190);
        grid.getColumnConstraints().addAll(c1, c2);

        Button btn = new Button("📝  Raise Complaint");
        btn.getStyleClass().add("btn-primary");
        btn.setOnAction(e -> handleRaise());

        VBox content = new VBox(10, grid, btn);
        content.setPadding(new Insets(0, 14, 14, 14));

        TitledPane p = new TitledPane("Raise New Complaint", content);
        p.getStyleClass().add("titled-pane");
        p.setExpanded(true);
        return p;
    }

    // ── Update Status Form ────────────────────────────────────────────────
    private TitledPane buildUpdateForm() {
        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(12);
        grid.setPadding(new Insets(14));

        cbComplaint = new ComboBox<>();
        refreshOpenComplaints();
        cbComplaint.setPromptText("Select complaint"); cbComplaint.setMaxWidth(Double.MAX_VALUE);

        cbNewStatus = new ComboBox<>(FXCollections.observableArrayList(
            "Open", "In Progress", "Resolved"));
        cbNewStatus.setPromptText("New status"); cbNewStatus.setMaxWidth(Double.MAX_VALUE);

        grid.add(lbl("Complaint"),   0, 0); grid.add(cbComplaint, 1, 0);
        grid.add(lbl("New Status"),  0, 1); grid.add(cbNewStatus, 1, 1);

        ColumnConstraints c1 = new ColumnConstraints(110);
        ColumnConstraints c2 = new ColumnConstraints(190);
        grid.getColumnConstraints().addAll(c1, c2);

        Button btn = new Button("✔  Update Status");
        btn.getStyleClass().add("btn-success");
        btn.setOnAction(e -> handleUpdate());

        VBox content = new VBox(10, grid, btn);
        content.setPadding(new Insets(0, 14, 14, 14));

        TitledPane p = new TitledPane("Update Complaint Status", content);
        p.getStyleClass().add("titled-pane");
        p.setExpanded(true);
        return p;
    }

    // ── Table ─────────────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    private TableView<Complaint> buildTable() {
        TableView<Complaint> tv = new TableView<>(data.getComplaints());
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.getStyleClass().add("data-table");
        tv.setPlaceholder(new Label("No complaints found."));

        TableColumn<Complaint, String> colFlat  = new TableColumn<>("Flat");
        colFlat.setCellValueFactory(new PropertyValueFactory<>("flatNumber"));

        TableColumn<Complaint, String> colBy    = new TableColumn<>("Raised By");
        colBy.setCellValueFactory(new PropertyValueFactory<>("raisedBy"));

        TableColumn<Complaint, String> colCat   = new TableColumn<>("Category");
        colCat.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Complaint, String> colDesc  = new TableColumn<>("Description");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Complaint, String> colDate  = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateRaised"));

        TableColumn<Complaint, String> colStat  = new TableColumn<>("Status");
        colStat.setCellValueFactory(new PropertyValueFactory<>("resolutionStatus"));
        colStat.setCellFactory(c -> new TableCell<>() {
            @Override protected void updateItem(String s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) { setText(null); setStyle(""); return; }
                setText(s);
                switch (s) {
                    case "Open":        setStyle("-fx-text-fill:#f87171; -fx-font-weight:bold;"); break;
                    case "In Progress": setStyle("-fx-text-fill:#60a5fa; -fx-font-weight:bold;"); break;
                    case "Resolved":    setStyle("-fx-text-fill:#4ade80; -fx-font-weight:bold;"); break;
                    default:            setStyle(""); break;
                }
            }
        });

        tv.getColumns().addAll(colFlat, colBy, colCat, colDesc, colDate, colStat);
        return tv;
    }

    // ── Handlers ──────────────────────────────────────────────────────────
    private void handleRaise() {
        Member m    = cbMember.getValue();
        String cat  = cbCategory.getValue();
        String desc = taDescription.getText().trim();

        if (m == null || cat == null || desc.isEmpty()) {
            showStatus("⚠  Fill in all fields.", false); return;
        }
        data.getComplaints().add(
            new Complaint(m.getFlatNumber(), m.getOwnerName(), cat, desc));
        refreshOpenComplaints();
        showStatus("✔  Complaint raised for Flat " + m.getFlatNumber() + ".", true);
        cbMember.setValue(null); cbCategory.setValue(null); taDescription.clear();
    }

    private void handleUpdate() {
        Complaint c   = cbComplaint.getValue();
        String newSt  = cbNewStatus.getValue();

        if (c == null || newSt == null) {
            showStatus("⚠  Select a complaint and status.", false); return;
        }
        c.setResolutionStatus(newSt);
        tableView.refresh();
        refreshOpenComplaints();
        showStatus("✔  Complaint status updated to "" + newSt + "".", true);
        cbComplaint.setValue(null); cbNewStatus.setValue(null);
    }

    private void refreshOpenComplaints() {
        cbComplaint.setItems(data.getComplaints().filtered(
            c -> !"Resolved".equals(c.getResolutionStatus())));
        cbComplaint.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(Complaint c) {
                return c == null ? "" : c.getFlatNumber() + " – " + c.getCategory()
                    + " (" + c.getDateRaised() + ")";
            }
            @Override public Complaint fromString(String s) { return null; }
        });
    }

    public void refresh() { refreshOpenComplaints(); tableView.refresh(); }

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
