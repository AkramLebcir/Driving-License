package com.akramlebcir.mac.drivinglicense.model;

public class Infraction_detail {
    private String title;
    private String point;
    private String price;

    public Infraction_detail(String title, String point, String price) {
        this.title = title;
        this.point = point;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
