package com.akramlebcir.mac.drivinglicense.model;

/**
 * Created by Ravi Tamada on 21/02/17.
 * www.androidhive.info
 */

public class Infraction {
    private int id;
    private int point;
    private int level;
    private String infractionname;
    private String infractiondate;
    private String police;
    private boolean isPay;
    private int price;

    public Infraction() {

    }

    public Infraction(int id,int point, int level, String infractionname, String infractiondate, String police, boolean isPay, int price) {
        this.id = id;
        this.point = point;
        this.level = level;
        this.infractionname = infractionname;
        this.infractiondate = infractiondate;
        this.police = police;
        this.isPay = isPay;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getInfractionname() {
        return infractionname;
    }

    public void setInfractionname(String infractionname) {
        this.infractionname = infractionname;
    }

    public String getInfractiondate() {
        return infractiondate;
    }

    public void setInfractiondate(String infractiondate) {
        this.infractiondate = infractiondate;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
