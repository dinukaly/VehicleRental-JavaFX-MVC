package javafx.test.javafx.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.test.javafx.dto.OrderDetailDto;
import javafx.test.javafx.dto.OrderDto;
import javafx.test.javafx.dto.VehicleDto;
import javafx.test.javafx.model.OrderModel;
import javafx.test.javafx.model.VehicleModel;
import javafx.test.javafx.tm.ItemTM;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderViewController implements Initializable {
    public TextField txtId;
    public TextField txtBrand;
    public TextField txtModel;
    public TextField txtOnHand;
    public TextField txtPrice;
    private List<ItemTM> itemTMS;
    private ArrayList<OrderDetailDto> orderDetailDtos;
    @FXML
    private TableView<ItemTM> tableView;
    public TextField qtyWant;
    public Label subTotal;
    private double subTotalPrice = 0.0;

    public void searchVehicle(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtId.getText());
            VehicleDto vehicle = VehicleModel.searchCar(id);

            if (vehicle != null) {
                txtBrand.setText(vehicle.getBrand());
                txtModel.setText(vehicle.getModel());
                txtOnHand.setText(String.valueOf(vehicle.getQty()));
                txtPrice.setText(String.valueOf(vehicle.getPrice()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Vehicle not found!").show();
                txtBrand.clear();
                txtModel.clear();
                txtOnHand.clear();
                txtPrice.clear();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID!").show();
        }
    }

    public void addToCart(ActionEvent actionEvent) {
        try {
            int qty = Integer.parseInt(qtyWant.getText());
            int onHandQty = Integer.parseInt(txtOnHand.getText());

            if (qty <= 0 || qty > onHandQty) {
                new Alert(Alert.AlertType.WARNING, "Invalid quantity!").show();
                return;
            }

            String brand = txtBrand.getText();
            String model = txtModel.getText();
            double unitPrice = Double.parseDouble(txtPrice.getText());
            double total = unitPrice * qty;
            subTotalPrice += total;
            subTotal.setText(String.format("%.2f", subTotalPrice));

            itemTMS.add(new ItemTM(brand, model, qty, unitPrice, total));
            tableView.setItems(FXCollections.observableArrayList(itemTMS));

            int id = Integer.parseInt(txtId.getText());
            orderDetailDtos.add(new OrderDetailDto(id, qty, total));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
        }
    }

    public void placeOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);

        try {
            boolean success = OrderModel.placeOrder(new OrderDto(formattedDate, subTotalPrice, orderDetailDtos));
            if (success) {
                new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
                generateBill();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to place the order!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    private void generateBill() {
        VBox billContent = new VBox(10);
        billContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New'; -fx-font-size: 12;");

        Text title = new Text("XYZ Store - Order Bill");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        Text date = new Text("Date: " + currentDateTime);
        Text totalText = new Text("Total: $" + subTotalPrice);

        billContent.getChildren().addAll(title, date, new Text("\nItems:"));

        for (ItemTM item : itemTMS) {
            String itemLine = String.format("%s (%s) x %d @ $%.2f = $%.2f",
                    item.getBrand(), item.getModel(), item.getQty(), item.getUnitPrice(), item.getTotalPrice());
            billContent.getChildren().add(new Text(itemLine));
        }

        billContent.getChildren().add(new Text("\n" + totalText.getText()));

        Button printButton = new Button("Print Bill");
        printButton.setOnAction(e -> printBill(billContent));

        Stage billStage = new Stage();
        VBox root = new VBox(20, billContent, printButton);
        root.setStyle("-fx-padding: 20;");
        billStage.setScene(new Scene(root, 400, 600));
        billStage.setTitle("Order Bill");
        billStage.show();
    }

    private void printBill(VBox billContent) {
        Printer printer = Printer.getDefaultPrinter();
        if (printer == null) {
            new Alert(Alert.AlertType.ERROR, "No printer found!").show();
            return;
        }

        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if (job != null && job.showPrintDialog(billContent.getScene().getWindow())) {
            boolean success = job.printPage(billContent);
            if (success) {
                job.endJob();
                new Alert(Alert.AlertType.INFORMATION, "Bill printed successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to print the bill!").show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemTMS = new ArrayList<>();
        orderDetailDtos = new ArrayList<>();
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("brand"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("model"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }
}
