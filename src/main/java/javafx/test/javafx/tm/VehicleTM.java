package javafx.test.javafx.tm;


public class VehicleTM {
    private int id;
    private String brand;
    private String model;
    private int qty;
    private double price;
    public VehicleTM(int id, String brand, String model, int qty, double price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.qty = qty;
        this.price = price;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
