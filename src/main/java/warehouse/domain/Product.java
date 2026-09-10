package warehouse.domain;

import java.time.LocalDate;

public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private LocalDate registrationDate;

    public Product(String id, String name, double price, int quantity,  String category, LocalDate registrationDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.registrationDate = registrationDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

}
