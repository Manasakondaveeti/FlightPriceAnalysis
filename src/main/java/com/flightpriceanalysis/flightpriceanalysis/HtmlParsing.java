package com.flightpriceanalysis.flightpriceanalysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//these lines are used to import the classes from the jsoup library

import java.io.IOException; // this library is useful for error handling
import java.util.ArrayList;
import java.util.List;

public class HtmlParsing {

    //Below is a static function names htmlParserfunction which takes url as an input and returns the list of words fetched from the html document
    static List<String> htmlParsefunction(String url) {
        //Document doc;
        Document doc;
        //adding try block to check for exceptions
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36")
                    .get();
            //This block tries to connect to the URL that is specified using Jsoup, a user agent string is provided to mimic a web browser. It is then retrieving HTML content from the URL and storing it in the doc variable.
            String body = doc.body().text();
            //body contains data in text form

            //now we split it into array of strings
            String[] str =  body.split("\\s+");
            List<String> name = new ArrayList<String>();
            for (int i = 0; i < str.length; i++) {
                //deleting non alphanumeric characters
                name.add(str[i].replaceAll("[^\\w]", "").toLowerCase());
            }
            //in above we turn words into lowercase and add them in the list 'name'
            System.out.println("Length of string is: "+ str.length);
            System.out.println("Words are:" +name);
            System.out.println("The number of words are:" +name.size());
            return name;
        } catch (IOException e) {
            //if any issue occurs it will enter into this block
            e.printStackTrace();
        }
        return null;
    }



}
