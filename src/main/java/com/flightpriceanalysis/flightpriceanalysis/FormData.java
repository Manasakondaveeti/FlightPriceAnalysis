package com.flightpriceanalysis.flightpriceanalysis;

import java.util.Date;

public class FormData {


    private String source;
    private String destination;
    private Integer noofpersons;

    private String date;
    ;
    private String classtype;

    public String getdate() {
        return date;
    }



    public void setdate(String date) {
        this.date = date;
    }

    public Integer getnoofpersons() {
        return noofpersons;
    }



    public void setnoofpersons(Integer noofpersons) {
        this.noofpersons = noofpersons;
    }

    public String getclasstype() {
        return classtype;
    }

    public void setclasstype(String classtype) {
        this.classtype = classtype;
    }

    public String getsource() {
        return source;
    }

    public void setsource(String source) {
        this.source = source;
    }

    public String getdestination() {
        return destination;
    }

    public void setdestination(String destination) {
        this.destination = destination;
    }
}
