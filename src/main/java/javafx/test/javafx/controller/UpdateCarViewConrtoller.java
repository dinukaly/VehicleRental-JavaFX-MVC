package javafx.test.javafx.controller;

import javafx.test.javafx.dto.VehicleDto;
import javafx.test.javafx.model.VehicleModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateCarViewConrtoller {
    @FXML
    private AnchorPane root;

    @FXML
    private Text searchBrand;

    @FXML
    private Text searchModel;

    @FXML
    private Text searchPrice;

    @FXML
    private Text searchQty;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    void btnBack(ActionEvent event) {
        try {

            Stage stage = (Stage) this.root.getScene().getWindow();


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/carsale/home-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());


            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearch(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());

        VehicleDto vehicle = VehicleModel.searchCar(id);

        searchBrand.setText(vehicle.getBrand());
        searchModel.setText(vehicle.getModel());
        searchQty.setText(String.valueOf(vehicle.getQty()));
        searchPrice.setText(String.valueOf(vehicle.getPrice()));


    }

    @FXML
    void btnUpdate(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());

        String brand = txtBrand.getText();
        String model = txtModel.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());

        boolean i = VehicleModel.updateCar(new VehicleDto(brand, model, qty, price), id);

        if (i == true) {
            Alert.AlertType alertType = Alert.AlertType.CONFIRMATION;
            Alert alert = new Alert(alertType);
            alert.setTitle("CONFIRMATION ALERT");
            alert.setHeaderText("Car update successfully");
            alert.show();

        } else {
            Alert.AlertType alertType = Alert.AlertType.ERROR;
            Alert alert = new Alert(alertType);
            alert.setTitle("ERROR ALERT");
            alert.setHeaderText("Car not update successfully");
            alert.show();
        }


    }

}
