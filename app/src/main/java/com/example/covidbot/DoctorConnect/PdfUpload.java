package com.example.covidbot.DoctorConnect;

public class PdfUpload {
    public String pdfname;
    public String url;

    public PdfUpload() {
    }

    public PdfUpload(String pdfname, String url) {
        this.pdfname = pdfname;
        this.url = url;
    }

    public String getPdfname() {
        return pdfname;
    }

    public void setPdfname(String pdfname) {
        this.pdfname = pdfname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
