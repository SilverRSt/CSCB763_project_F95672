package com.example.projetcparts_header_navigation;

/**
 * Entity Cloth for the database
 */
public class Cloth {
    private String type;
    private String brand;
    private int quantity;

    public Cloth(String type, String brand, int quantity) {
        this.type = type;
        this.brand = brand;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
