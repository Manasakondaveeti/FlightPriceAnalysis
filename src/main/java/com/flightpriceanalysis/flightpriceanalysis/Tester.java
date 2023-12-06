package com.flightpriceanalysis.flightpriceanalysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tester {

    public static void main(String args[]) throws Exception {


        String discPattern = "C\\$\\d{1,3}(,\\d{3})*(\\.\\d{2})?\\sOFF";
        Pattern pattern2 = Pattern.compile(discPattern);
        Matcher matcher2 = pattern2.matcher("hgdsh Get up to C$15 OFF our fees");

        // Check if there is a match
        if (matcher2.find()) {
            // Access the matched group
            System.out.println(matcher2.group(0));
        } else {
            System.out.println("No match found");
        }

    }


}
