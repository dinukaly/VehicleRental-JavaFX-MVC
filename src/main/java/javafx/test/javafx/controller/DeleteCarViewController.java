package javafx.test.javafx.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.test.javafx.model.VehicleModel;

public class DeleteCarViewController {
    public TextField carId;
    public AnchorPane deletePane;

    public void deleteCar(ActionEvent actionEvent) {
        int id = Integer.parseInt(carId.getText());
        if(id>0) {

            boolean i= VehicleModel.deleteCar(id);

            if (i==true) {
                Alert.AlertType alertType = Alert.AlertType.CONFIRMATION;
                Alert alert = new Alert(alertType);
                alert.setTitle("CONFIRMATION ALERT");
                alert.setHeaderText("Car Delete successfully");
                alert.show();

            } else {
                Alert.AlertType alertType = Alert.AlertType.ERROR;
                Alert alert = new Alert(alertType);
                alert.setTitle("ERROR ALERT");
                alert.setHeaderText("Car not Delete successfully");
                alert.show();
            }



        }
    }
}
