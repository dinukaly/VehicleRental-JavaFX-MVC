package javafx.test.javafx.dto;

public class VehicleDto {
    private String brand;
    private String model;
    private int qty;
    private double price;

    public VehicleDto(String brand, String model, int qty, double price) {
        this.brand = brand;
        this.model = model;
        this.qty = qty;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
