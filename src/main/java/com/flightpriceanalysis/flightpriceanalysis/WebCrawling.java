package com.flightpriceanalysis.flightpriceanalysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class WebCrawling {

        Map<String, String> citiesCodes = new HashMap<String, String>();
        static List<Map<String,String>>  listofflight;


        public List<Map<String,String>> getflightsCheap(FormData data) throws Exception
        {
            listofflight=new ArrayList<>();
            SpellChecking.initialLoadofWordcheck();
            citiesCodes=SpellChecking.citiesCodes;
            try {
                Files.write(Paths.get("src/search_frequency.txt"), (data.getsource().toLowerCase()+"\n").getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get("src/search_frequency.txt"), (data.getdestination().toLowerCase()+"\n").getBytes(), StandardOpenOption.APPEND);

            }
            catch (Exception e)
            {
                System.out.println("unable to write in search frequency.txt file");
            }

            String url,url2,url3;
            System.setProperty("webdriver.chrome.driver", "F:\\MAC Program\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

            Date date=(new SimpleDateFormat("yyyy-MM-dd").parse(data.getdate()));
            System.out.println(date);
            if(!data.getclasstype().equalsIgnoreCase("premium") ) {
                System.out.println(data.getsource().toLowerCase());
                System.out.println(citiesCodes);
                System.out.println(citiesCodes.get(data.getsource().toLowerCase()));

                url = "https://www.ca.kayak.com/flights/" + citiesCodes.get(data.getsource().toLowerCase()) + "-" + citiesCodes.get(data.getdestination().toLowerCase() )+ "/" +
                        (new SimpleDateFormat("yyyy-MM-dd").format(date)) + "/" + data.getnoofpersons() + "adults?" + "sort=bestflight_a";
                url2 = "https://www.cheapoair.ca/air/listing?&d1="+ citiesCodes.get(data.getsource().toLowerCase())+"&r1="+citiesCodes.get(data.getdestination().toLowerCase() )+"&dt1="+
                        (new SimpleDateFormat("MM/dd/yyyy").format(date))+"&tripType=ONEWAYTRIP&cl="+data.getclasstype()+"&ad="+data.getnoofpersons()+"&se=0&ch=0&infs=0&infl=0";
                url3 = "https://www.cheapflights.ca/flight-search/" + citiesCodes.get(data.getsource().toLowerCase()) + "-" + citiesCodes.get(data.getdestination().toLowerCase()) + "/"
                        + (new SimpleDateFormat("yyyy-MM-dd").format(date)) + "/" + data.getnoofpersons() + "adults";
            }
            else {
                url = "https://www.ca.kayak.com/flights/" +  citiesCodes.get(data.getsource().toLowerCase()) + "-" + citiesCodes.get(data.getdestination().toLowerCase() ) + "/" +
                        (new SimpleDateFormat("yyyy-MM-dd").format(date) ) + "/premium/" + data.getnoofpersons() + "adults?" + "sort=bestflight_a";
                url2 = "https://www.cheapoair.ca/air/listing?&d1=" +  citiesCodes.get(data.getsource().toLowerCase()) + "&r1=" + citiesCodes.get(data.getdestination().toLowerCase() ) + "&dt1=" +
                        (new SimpleDateFormat("MM/dd/yyyy").format(date)) + "&tripType=ONEWAYTRIP&cl=" + "PREMIUMECONOMY" + "&ad=" + data.getnoofpersons() + "&se=0&ch=0&infs=0&infl=0";
                url3 = "https://www.cheapflights.ca/flight-search/" + citiesCodes.get(data.getsource().toLowerCase()) + "-" + citiesCodes.get(data.getdestination().toLowerCase()) + "/"
                        + (new SimpleDateFormat("yyyy-MM-dd").format(date)) + "/premium/" + data.getnoofpersons() + "adults";
            }
            List<String> urls = new ArrayList<String>();
            urls.add(url);
            urls.add(url2);
            urls.add(url3);
            crawlUrls(2, urls, new ArrayList<String>());
            // Document document = Jsoup.connect(url).get();
            // document.text();


            // return pricecheaplist;
            return listofflight;
        }

        private  void crawlUrls(int level, List<String> urls, ArrayList<String> visited) throws Exception {
            // TODO Auto-generated method stub
            for(String url : urls) {
                crawl(level, url, visited);
            }

        }

        private  void crawl (int level, String next_link2, ArrayList<String> visited) throws Exception {

            System.out.println(next_link2);
            if (level <= 3) {
                Document doc = request(next_link2,visited);

                if (doc != null) {
                    for (Element link : doc.select("a[href]")) {
                        String next_link = link.absUrl("href");
                        if (visited.contains(next_link) == false) {
                            crawl(level++, next_link, visited);
                        }
                    }
                }
            }

        }


        private  Document request(String next_link2, ArrayList<String> v) throws Exception {

            List<String> ret =HtmlParsing.htmlParsefunction(next_link2);
            InvertedIndexing.constructTrie(next_link2, ret);


            WebDriver webDriver = new ChromeDriver();
            webDriver.get(next_link2);
            // Create a WebDriverWait for the page to load completely
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

            if(next_link2.contains("kayak")) {
                WebElement element = webDriver.findElement(By.cssSelector(".Hv20-option[aria-label='Cheapest']"));
                element.click();


                wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));


            }

            if(next_link2.contains("cheapoair")) {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.//span[contains(text(),'Cheapest')]]")));
                element.click();


                wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
            }

            if(next_link2.contains("cheapflights")) {

                WebElement element = webDriver.findElement(By.cssSelector(".Hv20-option[aria-label='Cheapest']"));
                element.click();

                wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

            }


            String pageText = webDriver.getPageSource();

            // Parse the HTML string with Jsoup
            Document document = Jsoup.parse(pageText);

            // Use the text() method to get the plain text
            String textContent = document.text().replaceAll("\\p{Zs}", " ");

          //  System.out.println(pageText);
          //  System.out.println(textContent);
            FindingPatternsUsingRegex findpat= new FindingPatternsUsingRegex();
            String filepath="";
            if(next_link2.contains("kayak")) {
                filepath = "src\\kayak.txt";
                findpat.findpatternusingregex(textContent,"kayak");
            } else if (next_link2.contains("cheapoair")) {
                filepath = "src\\cheapoair.txt";
                findpat.findpatternusingregex(textContent,"cheapoair");
            }
            else if (next_link2.contains("cheapflights")) {
                filepath = "src\\cheapflights.txt";
                findpat.findpatternusingregex(textContent,"cheapflights");
            }
            // Save HTML content to a text file
            try (FileWriter writer = new FileWriter(filepath)) {
                // Write the HTML content to the text file
                writer.write(textContent);
                System.out.println("Text content saved to: " + filepath);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
}
