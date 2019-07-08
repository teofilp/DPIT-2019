package com.runtime_terror.myapplication;

public class Food {
    String image;
    String title;
    String reqs;
    int qty;
    double price;
    boolean prepared;

    public Food(String image, String title, double price, String reqs, int qty, boolean prepared){
        this.image = image;
        this.title = title;
        this.price = price;
        this.reqs = reqs;
        this.qty = qty;
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

    public boolean getPrepared() {
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
