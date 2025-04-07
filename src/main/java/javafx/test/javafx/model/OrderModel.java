package javafx.test.javafx.model;

import javafx.test.javafx.db.DBConnection;
import javafx.test.javafx.dto.OrderDetailDto;
import javafx.test.javafx.dto.OrderDto;

import java.sql.*;

public class OrderModel {
    public static boolean placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        //disable autocommit
        connection.setAutoCommit(false);

        //insert into order table
        PreparedStatement ps1 = connection.prepareStatement("INSERT INTO orders(date, Amount) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps1.setObject(1, orderDto.getOrderDate());
        ps1.setObject(2, orderDto.getSubTotal());

        int orderSave = ps1.executeUpdate();

        if (orderSave > 0) {

            //get order id from generated keys
            ResultSet generatedKeys = ps1.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                //insert into order detail table
                for (OrderDetailDto orderDetailDto : orderDto.getOrderDetails()) {
                    PreparedStatement ps2 = connection.prepareStatement("INSERT INTO order_details(oid,vid,qty,price) VALUES (?, ?, ?, ?)");
                    ps2.setObject(1, orderId);
                    ps2.setObject(2, orderDetailDto.getVehicleId());
                    ps2.setObject(3, orderDetailDto.getQuantity());
                    ps2.setObject(4, orderDetailDto.getUnitPrice());

                    int orderDetailSave = ps2.executeUpdate();

                    if (orderDetailSave > 0) {
                        PreparedStatement ps3 = connection.prepareStatement("update  vehicle set qty=qty-? where id =?");
                        ps3.setObject(1, orderDetailDto.getQuantity());
                        ps3.setObject(2, orderDetailDto.getVehicleId());

                        int vehicleQtyUpdated = ps3.executeUpdate();

                        if (vehicleQtyUpdated <= 0) {
                            connection.commit();
                            connection.setAutoCommit(true);
                            return false;
                        }
                    } else {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } else {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
    }
}
