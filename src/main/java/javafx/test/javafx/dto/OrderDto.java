package javafx.test.javafx.dto;

import java.util.ArrayList;
import java.util.Date;

public class OrderDto {
    private Date orderDate;
    private double subTotal;

    private ArrayList<OrderDetailDto> orderDetails;

    public OrderDto(String formattedDate, double subTotalPrice, ArrayList<OrderDetailDto> orderDetailDtos) {
        this.orderDate = java.sql.Date.valueOf(formattedDate);
        this.subTotal = subTotalPrice;
        this.orderDetails = orderDetailDtos;
    }


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public ArrayList<OrderDetailDto> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetailDto> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderDate=" + orderDate +
                ", subTotal=" + subTotal +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
