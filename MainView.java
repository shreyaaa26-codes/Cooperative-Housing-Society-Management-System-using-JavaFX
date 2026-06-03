package society;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainView {

    private final BorderPane root;
    private final StackPane  contentArea;

    private DashboardPanel   dashboardPanel;
    private MemberPanel      memberPanel;
    private MaintenancePanel maintenancePanel;
    private ComplaintPanel   complaintPanel;

    private Button activeBtn;

    public MainView() {
        root = new BorderPane();
        root.getStyleClass().add("main-root");

        root.setLeft(buildSidebar());

        contentArea = new StackPane();
        contentArea.getStyleClass().add("content-area");
        root.setCenter(contentArea);

        showDashboard();
    }

    // ── Sidebar ───────────────────────────────────────────────────────────
    private VBox buildSidebar() {
        VBox sidebar = new VBox(0);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(210);

        // Header
        VBox header = new VBox(4);
        header.getStyleClass().add("sidebar-header");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(22, 18, 18, 18));

        Label icon     = new Label("🏘");
        icon.setStyle("-fx-font-size:30px;");
        Label title    = new Label("Society");
        title.getStyleClass().add("sidebar-title");
        Label subtitle = new Label("Manager");
        subtitle.getStyleClass().add("sidebar-subtitle");
        header.getChildren().addAll(icon, title, subtitle);

        Region sep = new Region();
        sep.getStyleClass().add("sidebar-sep");
        sep.setPrefHeight(1);

        // Nav buttons
        Button btnDash  = navBtn("📊   Dashboard");
        Button btnMem   = navBtn("👥   Members");
        Button btnMaint = navBtn("💰   Maintenance");
        Button btnComp  = navBtn("📝   Complaints");

        btnDash .setOnAction(e -> { setActive(btnDash);  showDashboard(); });
        btnMem  .setOnAction(e -> { setActive(btnMem);   showMembers(); });
        btnMaint.setOnAction(e -> { setActive(btnMaint); showMaintenance(); });
        btnComp .setOnAction(e -> { setActive(btnComp);  showComplaints(); });

        setActive(btnDash);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label ver = new Label("v1.0  •  JavaFX / Eclipse");
        ver.getStyleClass().add("sidebar-version");
        ver.setPadding(new Insets(0, 0, 14, 14));

        sidebar.getChildren().addAll(header, sep,
                btnDash, btnMem, btnMaint, btnComp,
                spacer, ver);
        return sidebar;
    }

    private Button navBtn(String text) {
        Button b = new Button(text);
        b.getStyleClass().add("nav-btn");
        b.setMaxWidth(Double.MAX_VALUE);
        b.setAlignment(Pos.CENTER_LEFT);
        return b;
    }

    private void setActive(Button btn) {
        if (activeBtn != null) activeBtn.getStyleClass().remove("nav-btn-active");
        activeBtn = btn;
        btn.getStyleClass().add("nav-btn-active");
    }

    // ── Panel switching ───────────────────────────────────────────────────
    private void showDashboard() {
        dashboardPanel = new DashboardPanel();          // always refresh counts
        contentArea.getChildren().setAll(dashboardPanel.getView());
    }

    private void showMembers() {
        if (memberPanel == null) memberPanel = new MemberPanel();
        contentArea.getChildren().setAll(memberPanel.getView());
    }

    private void showMaintenance() {
        if (maintenancePanel == null) maintenancePanel = new MaintenancePanel();
        else maintenancePanel.refresh();
        contentArea.getChildren().setAll(maintenancePanel.getView());
    }

    private void showComplaints() {
        if (complaintPanel == null) complaintPanel = new ComplaintPanel();
        else complaintPanel.refresh();
        contentArea.getChildren().setAll(complaintPanel.getView());
    }

    public BorderPane getRoot() { return root; }
}
