package steps;

import java.util.List;

public class Produto {
    private int id;
    private String title;
    private double price;
    private double discountPercentage;
    private int stock;
    private double rating;
    private List<String> images;
    private String thumbnail;
    private String description;
    private String brand;
    private String category;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
