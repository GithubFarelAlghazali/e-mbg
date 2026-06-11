/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app;

/**
 *
 * @author HP
 */
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminDashboardApp extends Application {

    private BorderPane root;
    private VBox sidebar;
    private final String BLUE = "#7DD3FC";
    private final String DARK_BLUE = "#0284C7";
    private final String LIGHT_BLUE = "#EFF6FF";
    private final String BORDER = "#DCEAF7";

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.setLeft(createSidebar());
        root.setTop(createTopbar());
        showDashboard();

        Scene scene = new Scene(root, 1200, 760);
        stage.setTitle("MBG System - Admin Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createSidebar() {
        sidebar = new VBox(14);
        sidebar.setPadding(new Insets(24, 18, 24, 18));
        sidebar.setPrefWidth(240);
        sidebar.setStyle("-fx-background-color: " + LIGHT_BLUE + "; -fx-border-color: " + BORDER + ";");

        Label title = new Label("MBG System");
        title.setFont(Font.font("Arial", 18));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: " + DARK_BLUE + ";");

        Label subtitle = new Label("Management Portal");
        subtitle.setStyle("-fx-text-fill: #64748B;");

        Button dashboard = menuButton("▦  Dashboard");
        Button vendors = menuButton("▣  Vendors");
        Button schools = menuButton("⌂  Schools");
        Button shipments = menuButton("▤  Shipments");
        Button inventory = menuButton("▥  Inventory");

        dashboard.setOnAction(e -> showDashboard());
        vendors.setOnAction(e -> showVendors());
        schools.setOnAction(e -> showSchools());
        shipments.setOnAction(e -> showShipments());
        inventory.setOnAction(e -> showInventory());

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button report = new Button("+ New Report");
        report.setMaxWidth(Double.MAX_VALUE);
        report.setStyle("-fx-background-color: " + BLUE + "; -fx-text-fill: #075985; -fx-font-weight: bold; -fx-background-radius: 8;");
        report.setPadding(new Insets(12));

        Button logout = menuButton("↪  Logout");

        sidebar.getChildren().addAll(title, subtitle, new Separator(),
                dashboard, vendors, schools, shipments, inventory,
                spacer, report, logout);

        return sidebar;
    }

    private Button menuButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12));
        btn.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: #334155;
            -fx-font-size: 14;
            -fx-background-radius: 8;
        """);

        btn.setOnMouseEntered(e -> btn.setStyle("""
            -fx-background-color: #DBEAFE;
            -fx-text-fill: #0284C7;
            -fx-font-size: 14;
            -fx-background-radius: 8;
            -fx-font-weight: bold;
        """));

        btn.setOnMouseExited(e -> btn.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: #334155;
            -fx-font-size: 14;
            -fx-background-radius: 8;
        """));

        return btn;
    }

    private HBox createTopbar() {
        HBox topbar = new HBox(20);
        topbar.setPadding(new Insets(14, 24, 14, 24));
        topbar.setAlignment(Pos.CENTER_LEFT);
        topbar.setStyle("-fx-background-color: white; -fx-border-color: " + BORDER + ";");

        TextField search = new TextField();
        search.setPromptText("Search...");
        search.setPrefWidth(320);
        search.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: " + BORDER + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label bell = new Label("🔔");
        Label setting = new Label("⚙");
        Label profile = new Label("A");
        profile.setAlignment(Pos.CENTER);
        profile.setPrefSize(34, 34);
        profile.setStyle("-fx-background-color: " + BLUE + "; -fx-background-radius: 50%; -fx-text-fill: #075985; -fx-font-weight: bold;");

        topbar.getChildren().addAll(search, spacer, bell, setting, profile);
        return topbar;
    }

    private VBox pageBase(String title, String desc) {
        VBox page = new VBox(20);
        page.setPadding(new Insets(28));
        page.setStyle("-fx-background-color: #F8FAFC;");

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("Arial", 26));
        titleLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #0F172A;");

        Label descLbl = new Label(desc);
        descLbl.setStyle("-fx-text-fill: #64748B;");

        page.getChildren().addAll(titleLbl, descLbl);
        return page;
    }

    private VBox card(String title, String value, String note) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setPrefWidth(220);
        card.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-border-color: #DCEAF7;
        """);

        Label t = new Label(title);
        t.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12; -fx-font-weight: bold;");

        Label v = new Label(value);
        v.setFont(Font.font("Arial", 26));
        v.setStyle("-fx-font-weight: bold; -fx-text-fill: #0F172A;");

        Label n = new Label(note);
        n.setStyle("-fx-text-fill: " + DARK_BLUE + "; -fx-font-size: 12;");

        card.getChildren().addAll(t, v, n);
        return card;
    }

    private TableView<String[]> createTable(String[] columns, String[][] data) {
        TableView<String[]> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(330);

        for (int i = 0; i < columns.length; i++) {
            final int index = i;
            TableColumn<String[], String> col = new TableColumn<>(columns[i]);
            col.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue()[index]));
            table.getColumns().add(col);
        }

        table.getItems().addAll(data);
        table.setStyle("-fx-background-color: white; -fx-border-color: #DCEAF7;");
        return table;
    }

    private void showDashboard() {
        VBox page = pageBase("Dashboard Overview", "Real-time metrics for current operations.");

        HBox cards = new HBox(20);
        cards.getChildren().addAll(
                card("TOTAL VENDOR", "1,245", "+12% from last month"),
                card("TOTAL SEKOLAH", "3,892", "Aktif terdaftar"),
                card("PENGIRIMAN HARI INI", "45,210", "Paket makanan"),
                card("PENGIRIMAN SELESAI", "94%", "Berhasil dikirim")
        );

        Label recent = new Label("Recent Deliveries");
        recent.setFont(Font.font("Arial", 18));
        recent.setStyle("-fx-font-weight: bold;");

        TableView<String[]> table = createTable(
                new String[]{"Vendor", "Sekolah", "Menu", "Status", "Estimasi"},
                new String[][]{
                        {"CV. Dapur Nusantara", "SDN 01 Pagi Jakarta", "Nasi Ayam Bakar", "Dimasak", "10:30 AM"},
                        {"PT. Rasa Indonesia", "SMPN 12 Bandung", "Nasi Ikan Goreng", "Diantar", "11:15 AM"},
                        {"Katering Mapan", "SMAN 3 Surabaya", "Nasi Telur Balado", "Sampai", "09:45 AM"},
                        {"CV. Berkah Jaya", "SDN 05 Petang", "Nasi Sayur Lodeh", "Diantar", "11:30 AM"}
                }
        );

        page.getChildren().addAll(cards, recent, table);
        root.setCenter(page);
    }

    private void showVendors() {
        VBox page = pageBase("Vendor Verification", "Review and manage food supplier registrations.");

        HBox cards = new HBox(20);
        cards.getChildren().addAll(
                card("PENDING REVIEW", "24", "+12 since yesterday"),
                card("APPROVED VENDORS", "1,204", "+3 verified today"),
                card("REJECTION RATE", "4.2%", "This month")
        );

        TableView<String[]> table = createTable(
                new String[]{"Nama Vendor", "Alamat", "Tanggal Daftar", "Status", "Actions"},
                new String[][]{
                        {"PT Sumber Pangan", "Jl. Merdeka No 12, Jakarta", "12 Oct 2023", "Pending Review", "View Detail"},
                        {"Katering Bu Dasiyem", "Jl. Kaliurang KM 5, Sleman", "12 Oct 2023", "Pending Review", "View Detail"},
                        {"CV Makmur Jaya", "Kawasan Industri Pulo Gadung", "10 Oct 2023", "Approved", "View Detail"},
                        {"Toko Berkah", "Pasar Induk Kramat Jati", "09 Oct 2023", "Rejected", "View Reason"}
                }
        );

        page.getChildren().addAll(cards, table);
        root.setCenter(page);
    }

    private void showSchools() {
        VBox page = pageBase("School Verification", "Review and approve new educational institutions joining the MBG program.");

        HBox cards = new HBox(20);
        cards.getChildren().addAll(
                card("PENDING REVIEW", "24", "+5 since yesterday"),
                card("APPROVED WEEK", "112", "+12% vs last week"),
                card("REJECTED WEEK", "3", "No change")
        );

        TableView<String[]> table = createTable(
                new String[]{"Nama Sekolah", "NPSN", "Tanggal Daftar", "Status", "Actions"},
                new String[][]{
                        {"SDN 01 Menteng Pagi", "20101234", "24 Oct 2023", "Menunggu Verifikasi", "Review"},
                        {"SMPN 255 Jakarta", "20105678", "23 Oct 2023", "Menunggu Verifikasi", "Review"},
                        {"SMAN 8 Jakarta", "20109012", "22 Oct 2023", "Menunggu Verifikasi", "Review"},
                        {"SDN 15 Klender", "20103456", "21 Oct 2023", "Terverifikasi", "Processed"}
                }
        );

        page.getChildren().addAll(cards, table);
        root.setCenter(page);
    }

    private void showShipments() {
        VBox page = pageBase("Distribution Monitoring", "Track daily meal deliveries from vendors to schools in real-time.");

        HBox filter = new HBox(12);
        filter.setPadding(new Insets(16));
        filter.setStyle("-fx-background-color: #EFF6FF; -fx-background-radius: 12; -fx-border-color: #DCEAF7; -fx-border-radius: 12;");

        TextField date = new TextField("10/27/2023");
        ComboBox<String> vendor = new ComboBox<>();
        vendor.getItems().addAll("All Vendors", "Dapur Nusantara", "Catering Sehat Ibu");
        vendor.setValue("All Vendors");

        ComboBox<String> school = new ComboBox<>();
        school.getItems().addAll("All Schools", "SDN 01 Pagi", "SMPN 15 Jakarta");
        school.setValue("All Schools");

        Button btn = new Button("Filter");
        btn.setStyle("-fx-background-color: " + BLUE + "; -fx-text-fill: #075985; -fx-font-weight: bold;");

        filter.getChildren().addAll(date, vendor, school, btn);

        TableView<String[]> table = createTable(
                new String[]{"Vendor", "School", "Menu", "Jumlah Porsi", "Status", "ETA"},
                new String[][]{
                        {"Catering Sehat Ibu", "SDN 01 Pagi", "Nasi Ayam Bakar, Sayur Asem, Pisang", "450", "Dimasak", "10:30 AM"},
                        {"Dapur Nusantara", "SMPN 15 Jakarta", "Nasi Ikan Goreng, Capcay, Jeruk", "620", "Diantar", "11:15 AM"},
                        {"CV Maju Bersama", "SMAN 8", "Nasi Rendang Sapi, Daun Singkong", "850", "Sampai", "09:45 AM"},
                        {"Catering Sehat Ibu", "SDN 05 Petang", "Nasi Ayam Bakar, Sayur Asem", "320", "Diantar", "11:30 AM"}
                }
        );

        page.getChildren().addAll(filter, table);
        root.setCenter(page);
    }

    private void showInventory() {
        VBox page = pageBase("Inventory", "Manage kitchen stock and food ingredients.");

        HBox cards = new HBox(20);
        cards.getChildren().addAll(
                card("TOTAL BAHAN", "86", "Aktif"),
                card("STOK MENIPIS", "12", "Perlu restock"),
                card("EXPIRED SOON", "5", "Cek gudang")
        );

        TableView<String[]> table = createTable(
                new String[]{"Nama Bahan", "Kategori", "Stok", "Satuan", "Status"},
                new String[][]{
                        {"Beras", "Karbohidrat", "250", "Kg", "Aman"},
                        {"Ayam", "Protein", "80", "Kg", "Aman"},
                        {"Telur", "Protein", "35", "Rak", "Menipis"},
                        {"Wortel", "Sayur", "20", "Kg", "Menipis"}
                }
        );

        page.getChildren().addAll(cards, table);
        root.setCenter(page);
    }

    public static void main(String[] args) {
        launch(args);
    }
}