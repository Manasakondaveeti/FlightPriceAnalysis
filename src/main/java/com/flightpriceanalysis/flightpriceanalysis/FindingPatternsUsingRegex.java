package com.flightpriceanalysis.flightpriceanalysis;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindingPatternsUsingRegex {

    public void findpatternusingregex(String src, String website) throws Exception{
        String patternString="";
        if(website.contains("kayak"))patternString="C\\$\\s*(\\d+)\\s";
        if(website.contains("cheapoair"))patternString="C\\$(\\d+(\\.\\d+)?)\\sPrice per person";
        if(website.contains("cheapflights"))patternString="C\\$\\s*(\\d+)\\s";

        Map<String, String> map=new TreeMap<>();
        //List<String> list=new ArrayList<>();
        TreeMap<Integer, String> priceMap = new TreeMap<>();
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(patternString);

        // Match the pattern against the input string
        Matcher matcher = pattern.matcher(src);

        // Extract and print the matched price
        while (matcher.find()) {
            String currentPriceStr = matcher.group();
            int currentPriceInt = Integer.parseInt(currentPriceStr.replaceAll("[^0-9]", ""));
            priceMap.put(currentPriceInt, currentPriceStr);
        }
        if(priceMap.isEmpty())throw new Exception("Unable to find values matching with pattern declared in " + website + " website");

        System.out.println(priceMap.firstEntry().getValue());
        map.put("price", priceMap.firstEntry().getValue());
        map.put("website", website);

        WebCrawling.listofflight.add(map);

    }

}