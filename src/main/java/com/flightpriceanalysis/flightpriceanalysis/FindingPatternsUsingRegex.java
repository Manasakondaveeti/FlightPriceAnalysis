package com.flightpriceanalysis.flightpriceanalysis;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindingPatternsUsingRegex {



    public  void findpatternusingregexkayak(String src) throws Exception{
        Map<String, String> map=new HashMap<>();
        List<String> list=new ArrayList<>();
        String pricePattern = "C\\$\\s*(\\d+)\\s";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(pricePattern);

        // Match the pattern against the input string
        Matcher matcher = pattern.matcher(src);

        // Extract and print the matched price
        while (matcher.find()) {
            list.add( matcher.group());
        }
        if(list.isEmpty())throw new Exception("Unable to find values matching with pattern declared in kayak website");
        Collections.sort(list);
        System.out.println(list);
        map.put("price",list.get(0));
        map.put("website","Kayak");

        WebCrawling.listofflight.add(map);

    }

    public  void findpatternusingregexcheapoair(String src) throws Exception{
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
        if(list.isEmpty())throw new Exception("Unable to find values matching with pattern declared in cheapoair website");

        Collections.sort(list);
        System.out.println(list);
        map.put("price",list.get(0));
        map.put("website","cheapoair");

        WebCrawling.listofflight.add(map);

    }

    public  void findpatternusingregexcheapflights(String src) throws Exception{
        Map<String, String> map=new HashMap<>();
        List<String> list=new ArrayList<>();
        String pricePattern = "C\\$\\s*(\\d+)\\s";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(pricePattern);

        // Match the pattern against the input string
        Matcher matcher = pattern.matcher(src);

        // Extract and print the matched price
        while (matcher.find()) {
            list.add( matcher.group());
        }
        if(list.isEmpty())throw new Exception("Unable to find values matching with pattern declared in cheapflights website");

        Collections.sort(list);
        System.out.println(list);
        map.put("price",list.get(0));
        map.put("website","cheapflights");

        WebCrawling.listofflight.add(map);

    }









}
