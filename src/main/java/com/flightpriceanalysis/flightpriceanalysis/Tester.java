package com.flightpriceanalysis.flightpriceanalysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Tester {

    public static void main(String args[]) throws Exception
    {


        Document doc;
        //adding try block to check for exceptions

            doc = Jsoup.connect("https://www.cheapoair.ca/air/listing?&d1=yto&r1=yvr&dt1=12/25/2023&tripType=ONEWAYTRIP&cl=economy&ad=1&se=0&ch=0&infs=0&infl=0")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36")
                    .get();
            String body = doc.body().text();
            System.out.println(body);

    }
}
