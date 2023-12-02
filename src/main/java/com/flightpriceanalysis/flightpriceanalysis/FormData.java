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
            this.date = date;

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

            this.noofpersons = noofpersons;


    }

    public String getclasstype() {
        return classtype;
    }

    public void setclasstype(String classtype) {
           this.classtype = classtype;

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
          this.source = source;

    }

    public String getdestination() {
        return destination;
    }

    public void setdestination(String destination) {

            this.destination = destination;


    }

    private boolean validateSourceDest(String input) {
        Pattern pattern = Pattern.compile(SOURCE_DEST_REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public void validationofdata()
    {
        if (!validateSourceDest(destination)) {
            throw new IllegalArgumentException("Invalid destination format. Should contain only alphabetic characters and spaces.");

        }

        if (!validateSourceDest(source)) {

            throw new IllegalArgumentException("Invalid source format. Should contain only alphabetic characters and spaces.");
        }

        if (!validateClassType(classtype)) {

            throw new IllegalArgumentException("Invalid class type. Available options: Economy, Premium Economy");
        }

        if (!(noofpersons >= MIN_PERSONS)) {

            throw new IllegalArgumentException("Number of persons should be greater than or equal to " + MIN_PERSONS);
        }

        if (!validateDate(date)) {

            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD format.");
        }
    }

    @Override
    public String toString() {
        return source + "-" + destination + "-" + date + "-" + noofpersons + "-" + classtype;
    }
}