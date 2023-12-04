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

        System.setProperty("webdriver.chrome.driver", "F:\\MAC Program\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        Document doc;
        //adding try block to check for exceptions
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://flights.agoda.com/flights/yto-yvr/2023-12-28/1adults?sort=price_a");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.sIC5-currency-picker")));
        element.click();

        wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.chXn-content")));

        element = element.findElement(By.xpath("//button[contains(div, 'Canadian dollar')]"));
        element.click();

             //   element.click();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));



    String pageText = webDriver.getPageSource();
    //System.out.println(pageText);

        Document document = Jsoup.parse(pageText);

        // Use the text() method to get the plain text
        String textContent = document.text().replaceAll("\\p{Zs}", " ");
        System.out.println(textContent);

    }
}
