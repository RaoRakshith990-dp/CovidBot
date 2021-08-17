package com.example.covidbot.DoctorConnect;

public class Booking4details {
    String bookdate;
    String booktime;
    String patientname;
    String patientemail;
    String appttypes;
    public Booking4details() {
    }

    public Booking4details(String bookdate, String booktime, String patientname, String patientemail, String appttypes) {
        this.bookdate = bookdate;
        this.booktime = booktime;
        this.patientname = patientname;
        this.patientemail = patientemail;
        this.appttypes = appttypes;
    }

    public String getBookdate() {
        return bookdate;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    public String getBooktime() {
        return booktime;
    }

    public void setBooktime(String booktime) {
        this.booktime = booktime;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getPatientemail() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail = patientemail;
    }

    public String getAppttypes() {
        return appttypes;
    }

    public void setAppttypes(String appttypes) {
        this.appttypes = appttypes;
    }
}
