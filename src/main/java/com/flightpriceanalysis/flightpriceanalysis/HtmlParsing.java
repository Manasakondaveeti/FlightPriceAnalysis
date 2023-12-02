package com.flightpriceanalysis.flightpriceanalysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlParsing {

    static List<String> htmlParsefunction(String url) {
        //Document doc;
        Document doc;
        //adding try block to check for exceptions
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36")
                    .get();
            String body = doc.body().text();
            //adding words in an array
            String[] str =  body.split("\\s+");
            List<String> name = new ArrayList<String>();
            for (int i = 0; i < str.length; i++) {
                //deleting non alphanumeric characters
                name.add(str[i].replaceAll("[^\\w]", "").toLowerCase());
            }
            System.out.println("Length of string is: "+ str.length);
            System.out.println("Words are:" +name);
            System.out.println("The number of words are:" +name.size());
            return name;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



}
