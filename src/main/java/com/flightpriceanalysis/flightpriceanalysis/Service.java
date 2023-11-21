package com.flightpriceanalysis.flightpriceanalysis;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;

public class Service {

    Map<String, String> citiesCodes = new HashMap<String, String>();
     List<Map<String,String>> listofflight;
   public void initialLoadofWordcheck()
    {


        citiesCodes.put("windsor", "yqg");
        citiesCodes.put("toronto", "yto");
        citiesCodes.put("montreal", "yul");
        citiesCodes.put("vancouver", "yvr");
    }

    public List<Map<String,String>> getflightsCheap(FormData data) throws Exception
    {
        listofflight=new ArrayList<>();

        initialLoadofWordcheck();
        String url,url2="";
        System.setProperty("webdriver.chrome.driver", "F:\\MAC Program\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        Date date=(new SimpleDateFormat("yyyy-MM-dd").parse(data.getdate()));
        System.out.println(date);
        if(data.getclasstype().equals("economy") ) {
            System.out.println(data.getsource().toLowerCase());
            System.out.println(citiesCodes);
            System.out.println(citiesCodes.get(data.getsource().toLowerCase()));

            url = "https://www.ca.kayak.com/flights/" + citiesCodes.get(data.getsource().toLowerCase()) + "-" + citiesCodes.get(data.getdestination().toLowerCase() )+ "/" +
                    (new SimpleDateFormat("yyyy-MM-dd").format(date)) + "/" + data.getnoofpersons() + "adults?" + "sort=bestflight_a";
            url2 = "https://www.cheapoair.ca/air/listing?&d1="+ citiesCodes.get(data.getsource().toLowerCase())+"&r1="+citiesCodes.get(data.getdestination().toLowerCase() )+"&dt1="+
                    (new SimpleDateFormat("MM/dd/yyyy").format(date))+"&tripType=ONEWAYTRIP&cl="+data.getclasstype()+"&ad="+data.getnoofpersons()+"&se=0&ch=0&infs=0&infl=0";
        }
            else {
            url = "https://www.ca.kayak.com/flights/" +  citiesCodes.get(data.getsource().toLowerCase()) + "-" + citiesCodes.get(data.getdestination().toLowerCase() ) + "/" +
                    (new SimpleDateFormat("yyyy-MM-dd").format(date) ) + "/premium/" + data.getnoofpersons() + "adults?" + "sort=bestflight_a";
            url2 = "https://www.cheapoair.ca/air/listing?&d1=" +  citiesCodes.get(data.getsource().toLowerCase()) + "&r1=" + citiesCodes.get(data.getdestination().toLowerCase() ) + "&dt1=" +
                    (new SimpleDateFormat("MM/dd/yyyy").format(date)) + "&tripType=ONEWAYTRIP&cl=" + "PREMIUMECONOMY" + "&ad=" + data.getnoofpersons() + "&se=0&ch=0&infs=0&infl=0";

        }
        List<String> urls = new ArrayList<String>();
       urls.add(url);
        urls.add(url2);
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


        String pageText = webDriver.getPageSource();

        // Parse the HTML string with Jsoup
        Document document = Jsoup.parse(pageText);

        // Use the text() method to get the plain text
        String textContent = document.text().replaceAll("\\p{Zs}", " ");

       // System.out.println(pageText);
       // System.out.println(textContent);
        String filepath="";
         if(next_link2.contains("kayak")) {
             filepath = "src\\kayak.txt";
             findpatternusingregex(textContent);
         } else if (next_link2.contains("cheapoair")) {


             filepath = "src\\cheapoair.txt";
             findpatternusingregexcheapoair(textContent);
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

    public  void findpatternusingregex(String src) {
       Map<String, String> map=new HashMap<>();
       List<String> list=new ArrayList<>();
        String pricePattern = "C\\$\\s*(\\d+)\\s+/\\s+person";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(pricePattern);

        // Match the pattern against the input string
        Matcher matcher = pattern.matcher(src);

        // Extract and print the matched price
        while (matcher.find()) {
           list.add( matcher.group());
        }
       Collections.sort(list);
        System.out.println(list);
        map.put("price",list.get(0));
        map.put("website","Kayak");

        listofflight.add(map);

    }

    public  void findpatternusingregexcheapoair(String src) {
        Map<String, String> map=new HashMap<>();
        List<String> list=new ArrayList<>();
        String pricePattern = "C\\$(\\d+(\\.\\d+)?)\\sPrice per person";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(pricePattern);

        // Match the pattern against the input string
        Matcher matcher = pattern.matcher(src);

        // Extract and print the matched price
        while (matcher.find()) {
            list.add( matcher.group());
        }
        Collections.sort(list);
        System.out.println(list);
        map.put("price",list.get(0));
        map.put("website","cheapoair");

        listofflight.add(map);

    }


    public String wordCheckmain(String src)
    {
       if(!src.matches("[a-zA-Z_]+")){
            System.out.println("Invalid source");
            System.out.println("Enter name of source");

        }
        src = src.toLowerCase();
        String fileword = src + "\n";
        String source = citiesCodes.get(src);
        if(source==null) {
            source=wordCheck(src);
            fileword = source + "\n";
            System.out.println(fileword);
            source = citiesCodes.get(source);
try {
    Files.write(Paths.get("src/search_frequency.txt"), fileword.getBytes(), StandardOpenOption.APPEND);
}
catch (Exception e)
{
    System.out.println("unable to write in search frequency.txt file");
}
        }

        return fileword;
    }




    public  String wordCheck(String indexWord) {

        //using array list to store the resulting list
        ArrayList<String> resultsArray = new ArrayList<String>();


        //converting to lower-case to have uniform results
        indexWord = indexWord.toLowerCase();
        int minDistance = Integer.MAX_VALUE;

        //Parsed Array stored in Set
        Set<String> wordsArray = htmlParse();

        for(String word : wordsArray) {
            int distance = minDistance(indexWord, word);
            if(distance <= minDistance) {
                minDistance = distance;
            }
            //condition for being similar words is having edit distance of 2
            if(distance <=2) {
                resultsArray.add(word);
            }
        }

        //Printing list of similar words
        System.out.println("Did you mean " + indexWord + ":");
        System.out.println(resultsArray);
        String word = resultsArray.get(0);
        return word;
    }



    //Function to parse HTML file and store in an Array
    static Set<String> htmlParse (){
        //fetching document
        String url = "http://www.citymayors.com/gratis/canadian_cities.html";
        Document doc;
        //adding try block to check for exceptions
        try {
            doc = Jsoup.connect(url).get();
            String body = doc.body().text();
            //adding words in an array
            String[] str =  body.split("\\s+");
            Set<String> name = new HashSet<>();
            for (int i = 0; i < str.length; i++) {
                //deleting non alphanumeric characters
                name.add(str[i].replaceAll("[^\\w]", "").toLowerCase());
            }
            //System.out.println("Length of string is: "+ str.length);
            return name;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



    public static int minDistance(String firstWrd, String secondWrd) {

        //assigning length of both words to variables
        int x = firstWrd.length();
        int y = secondWrd.length();

        //assigning both lengths to an array
        int[][] editDist = new int[x + 1][y + 1];

        //Dynamic Programming algorithm
        for(int i = 0; i <= x; i++)
            editDist[i][0] = i;
        for(int i = 1; i <= y; i++)
            editDist[0][i] = i;

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                if(firstWrd.charAt(i) == secondWrd.charAt(j))
                    editDist[i + 1][j + 1] = editDist[i][j];
                else {
                    int a = editDist[i][j];
                    int b = editDist[i][j + 1];
                    int c = editDist[i + 1][j];
                    editDist[i + 1][j + 1] = a < b ? (a < c ? a : c) : (b < c ? b : c);
                    editDist[i + 1][j + 1]++;
                }
            }
        }
        return editDist[x][y];
    }
}
