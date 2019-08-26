package com.runtime_terror.myapplication.models;

public class Food {
    String image;
    String title;
    String reqs;
    int qty;
    String description;
    double price;
    boolean prepared;

    public Food(String image, String title, double price, String reqs, int qty, boolean prepared,String description){
        this.image = image;
        this.title = title;
        this.price = price;
        this.reqs = reqs;
        this.qty = qty;
        this.description=description;

        this.prepared = prepared;
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
}
