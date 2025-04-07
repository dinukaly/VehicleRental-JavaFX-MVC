package javafx.test.javafx.model;

import javafx.test.javafx.db.DBConnection;
import javafx.test.javafx.dto.VehicleDto;
import javafx.test.javafx.tm.VehicleTM;

import java.sql.*;
import java.util.ArrayList;

public class VehicleModel {

    public static boolean addVehicle(VehicleDto vehicledto) {
        try {
            Connection con = DBConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement("INSERT INTO vehicle(brand, model, qty, price) VALUES (?, ?, ?, ?)");
            ps.setObject(1, vehicledto.getBrand());
            ps.setObject(2, vehicledto.getModel());
            ps.setObject(3, vehicledto.getQty());
            ps.setObject(4, vehicledto.getPrice());

            int i = ps.executeUpdate();
            return i > 0;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteCar(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM vehicle WHERE id = ?");
            preparedStatement.setInt(1, id);

            int i = preparedStatement.executeUpdate();
            return i > 0;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<VehicleTM> getAllVehicles() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicle");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<VehicleTM> tms = new ArrayList<>();

            while (resultSet.next()) {
                VehicleTM tm = new VehicleTM(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getDouble(5)
                );
                tms.add(tm);
            }

            return tms;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static VehicleDto searchCar(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicle WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new VehicleDto(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getDouble(5)
                );
            } else {
                return null;
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateCar(VehicleDto vehicledto, int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE vehicle SET brand = ?, model = ?, qty = ?, price = ? WHERE id = ?"
            );
            preparedStatement.setString(1, vehicledto.getBrand());
            preparedStatement.setString(2, vehicledto.getModel());
            preparedStatement.setInt(3, vehicledto.getQty());
            preparedStatement.setDouble(4, vehicledto.getPrice());
            preparedStatement.setInt(5, id);

            int i = preparedStatement.executeUpdate();
            return i > 0;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
