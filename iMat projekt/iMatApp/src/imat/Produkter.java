package imat;

import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

public class Produkter {
    private int id;
    private String category;
    private String name;
    private double price;
    private String unit;
    private String imagePath;

    public Produkter(int id, String category, String name, double price, String unit, String imagePath) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Product toProduct() {
        Product product = new Product();
        product.setProductId(id);
        product.setCategory(ProductCategory.valueOf(category));
        product.setName(name);
        product.setPrice(price);
        product.setUnit(unit);
        product.setImageName(imagePath);
        return product;
    }
}
