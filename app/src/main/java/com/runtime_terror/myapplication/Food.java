package com.runtime_terror.myapplication;

public class Food {
    String image;
    String title;
    String reqs;
    String qty;
    Boolean prepared;

    public Food(String image, String title, String reqs, String qty, Boolean prepared){
        this.image = image;
        this.title = title;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Boolean getPrepared() {
        return prepared;
    }

    public void setPrepared(Boolean prepared) {
        this.prepared = prepared;
    }
}
