package com.akramlebcir.mac.drivinglicense.Model;

import java.util.ArrayList;

public class DriverLicense {
    private String eid;
    private String creationdate;
    private String expirationdate;
    private int numberofpoints;
    private String type;
    private ArrayList<Infraction> infractions;

    public DriverLicense(){}
    public DriverLicense(String eid, String creationdate, String expirationdate, int numberofpoints, String type, ArrayList<Infraction> infractions) {
        this.eid = eid;
        this.creationdate = creationdate;
        this.expirationdate = expirationdate;
        this.numberofpoints = numberofpoints;
        this.type = type;
        this.infractions = infractions;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

    public int getNumberofpoints() {
        return numberofpoints;
    }

    public void setNumberofpoints(int numberofpoints) {
        this.numberofpoints = numberofpoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Infraction> getInfractions() {
        return infractions;
    }

    public void setInfractions(ArrayList<Infraction> infractions) {
        this.infractions = infractions;
    }
}
