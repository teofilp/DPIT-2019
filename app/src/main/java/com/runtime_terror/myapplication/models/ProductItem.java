package com.runtime_terror.myapplication.models;

public class ProductItem {
    String image;
    String title;
    String reqs;
    int qty;
    String description;
    double price;
    boolean prepared;
    boolean isFood;
    boolean ordered;

    public ProductItem() {

    }

    public ProductItem(String image, String title, double price, String reqs, int qty, boolean prepared, String description, boolean ordered){
        this.image = image;
        this.title = title;
        this.price = price;
        this.reqs = reqs;
        this.qty = qty;
        this.description=description;
        this.prepared = prepared;
        this.ordered= ordered;
    }

    public ProductItem(String image, String title, double price, String reqs, int qty, boolean prepared, String description, boolean isFood, boolean ordered){
        this.image = image;
        this.title = title;
        this.price = price;
        this.reqs = reqs;
        this.qty = qty;
        this.description=description;
        this.prepared = prepared;
        this.isFood = isFood;
        this.ordered = ordered;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription(){return  description;}

    public void setDescription(String description){this.description = description;}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReqs() {
        return reqs;
    }

    public void setReqs(String reqs) {
        this.reqs = reqs;
    }

    public int getQty() {
        return qty;
    }


    public void setOrdered(boolean ordered){this.ordered = ordered;}

    public boolean isOrdered(){return ordered;}

    public void setQty(int qty) {
        this.qty = qty;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFood() {
        return isFood;
    }
    public static ProductItem copyProduct(ProductItem original) {
        ProductItem item = new ProductItem();

        item.setImage(original.getImage());
        item.setQty(original.getQty());
        item.setDescription(original.getDescription());
        item.setReqs(original.getReqs());
        item.setIsFood(original.isFood());
        item.setPrice(original.getPrice());
        item.setOrdered(original.isOrdered());
        item.setPrepared(original.isPrepared());
        item.setTitle(original.getTitle());

        return item;
    }
    public void setIsFood(boolean food) {
        isFood = food;
    }

}


