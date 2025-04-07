package javafx.test.javafx.dto;

public class OrderDetailDto {
    private int vehicleId;
    private int quantity;
    private double unitPrice;

    public OrderDetailDto(int id, int qty, double total) {
        this.vehicleId = id;
        this.quantity = qty;
        this.unitPrice = total;
    }


    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailDto{" + "vehicleId=" + vehicleId + ", quantity=" + quantity + ", unitPrice=" + unitPrice + '}';
    }
}
