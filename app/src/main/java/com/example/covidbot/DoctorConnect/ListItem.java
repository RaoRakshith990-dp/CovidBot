package com.example.covidbot.DoctorConnect;

public class ListItem {
    String mimageuri;
    String doccity;
    String docname;
    String docexperience;
    String consultationfee;
    String docregistration;
    String code;
    public ListItem() {
    }

    public ListItem(String mimageuri, String doccity, String docname, String docexperience, String consultationfee, String docregistration, String code) {
        this.mimageuri = mimageuri;
        this.doccity = doccity;
        this.docname = docname;
        this.docexperience = docexperience;
        this.consultationfee = consultationfee;
        this.docregistration = docregistration;
        this.code = code;
    }

    public String getMimageuri() {
        return mimageuri;
    }

    public void setMimageuri(String mimageuri) {
        this.mimageuri = mimageuri;
    }

    public String getDoccity() {
        return doccity;
    }

    public void setDoccity(String doccity) {
        this.doccity = doccity;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDocexperience() {
        return docexperience;
    }

    public void setDocexperience(String docexperience) {
        this.docexperience = docexperience;
    }

    public String getConsultationfee() {
        return consultationfee;
    }

    public void setConsultationfee(String consultationfee) {
        this.consultationfee = consultationfee;
    }

    public String getDocregistration() {
        return docregistration;
    }

    public void setDocregistration(String docregistration) {
        this.docregistration = docregistration;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
