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

public class Tester {

    public static void main(String args[]) throws Exception
    {

        String amt="C$237.83 ";

       String regex="C\\$\\d{1,3}(,\\d{3})*(\\.\\d{2})?\\s";
       System.out.println(amt.matches(regex));


    }
}
