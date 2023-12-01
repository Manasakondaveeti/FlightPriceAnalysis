package com.flightpriceanalysis.flightpriceanalysis;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormData {
    private String source;
    private String destination;
    private Integer noofpersons;
    private String date;
    private String classtype;

    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$"; // Regex for YYYY-MM-DD format
    private static final String SOURCE_DEST_REGEX = "^[a-zA-Z\\s]+$"; // Regex for alphabetic characters and spaces
    private static final String CLASSTYPE_REGEX = "^(economy|premium economy)$"; // Regex for specific class types
    private static final int MIN_PERSONS = 1; // Minimum number of persons allowed

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        if (validateDate(date)) {
            this.date = date;
        } else {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD format.");
        }
    }

    private boolean validateDate(String date) {
        Pattern pattern = Pattern.compile(DATE_REGEX);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public Integer getnoofpersons() {
        return noofpersons;
    }

    public void setnoofpersons(Integer noofpersons) {
        if (noofpersons >= MIN_PERSONS) {
            this.noofpersons = noofpersons;
        } else {
            throw new IllegalArgumentException("Number of persons should be greater than or equal to " + MIN_PERSONS);
        }
    }

    public String getclasstype() {
        return classtype;
    }

    public void setclasstype(String classtype) {
        if (validateClassType(classtype)) {
            this.classtype = classtype;
        } else {
            throw new IllegalArgumentException("Invalid class type. Available options: Economy, Business, First");
        }
    }

    private boolean validateClassType(String classtype) {
        Pattern pattern = Pattern.compile(CLASSTYPE_REGEX);
        Matcher matcher = pattern.matcher(classtype);
        return matcher.matches();
    }

    public String getsource() {
        return source;
    }

    public void setsource(String source) {
        if (validateSourceDest(source)) {
            this.source = source;
        } else {
            throw new IllegalArgumentException("Invalid source format. Should contain only alphabetic characters and spaces.");
        }
    }

    public String getdestination() {
        return destination;
    }

    public void setdestination(String destination) {
        if (validateSourceDest(destination)) {
            this.destination = destination;
        } else {
            throw new IllegalArgumentException("Invalid destination format. Should contain only alphabetic characters and spaces.");
        }
    }

    private boolean validateSourceDest(String input) {
        Pattern pattern = Pattern.compile(SOURCE_DEST_REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    @Override
    public String toString() {
        return source + "-" + destination + "-" + date + "-" + noofpersons + "-" + classtype;
    }
}