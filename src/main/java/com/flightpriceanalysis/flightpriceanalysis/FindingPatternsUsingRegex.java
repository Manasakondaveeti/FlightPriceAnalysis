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

        Map<String, String> map=new HashMap<>();
        List<String> list=new ArrayList<>();

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(patternString);

        // Match the pattern against the input string
        Matcher matcher = pattern.matcher(src);

        // Extract and print the matched price
        while (matcher.find()) {
            list.add( matcher.group());
        }
        if(list.isEmpty())throw new Exception("Unable to find values matching with pattern declared in " + website + " website");

        Collections.sort(list);
        System.out.println(list);
        map.put("price",list.get(0));
        map.put("website",website);

        WebCrawling.listofflight.add(map);

    }

}