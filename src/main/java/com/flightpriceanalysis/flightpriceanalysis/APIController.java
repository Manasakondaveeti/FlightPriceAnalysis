package com.flightpriceanalysis.flightpriceanalysis;



import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class APIController {
    ObjectMapper objectMapper = new ObjectMapper();

    WebCrawling webcrawl=new WebCrawling();

    private Map<String, CacheEntry> cache = new HashMap<>();

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/api/wordcheck/{data}")
    public ResponseEntity<?> getData(@PathVariable("data") String data) {
        System.out.println("api entered");

        SpellChecking spellcheck =new SpellChecking();
        spellcheck.initialLoadofWordcheck();

        try {
            String dataoutput = spellcheck.wordCheckmain(data);
            System.out.println("data sent to ui" + dataoutput);
            String jsonData = objectMapper.writeValueAsString(dataoutput);
            return ResponseEntity.ok(jsonData);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/submissionform/submit")

    public ResponseEntity<?> onformSubmission(@RequestBody FormData data) {

        System.out.println(data.getsource());
        System.out.println(data.getdestination() + data.getnoofpersons() + data.getclasstype() + data.getdate());

        try {
            data.validationofdata();
            // Check if the request is in the cache and within the time window
            //System.out.println("sdasdasd"+cache.containsKey(data.toString()));
           // System.out.println("sdjasdhsjad"+AvoidCrawling5Minutes.isWithinTimeWindow(cache.get(data.toString()).getTimestamp()));
            System.out.println(!data.getwebcrawl());
            if (cache.containsKey(data.toString()) && AvoidCrawling5Minutes.isWithinTimeWindow(cache.get(data.toString()).getTimestamp())) {


                ResponseEntity<String> resp = cache.get(data.toString()).getResponse();
                CacheEntry cacheResponse = new CacheEntry();
                cacheResponse.setFromCache(true);
                cacheResponse.setResponse(resp);


                System.out.println(resp);
                SearchFrequency.searcheachlocationfreq();
                return ResponseEntity.ok(cacheResponse);
            } else {


                List<Map<String, String>> list = webcrawl.getflightsCheap(data);
                System.out.println(list);
                String jsonResponse = objectMapper.writeValueAsString(list);
//
                CacheEntry newCacheEntry = new CacheEntry();
                newCacheEntry.setFromCache(false);
                newCacheEntry.setTimestamp(System.currentTimeMillis());
                newCacheEntry.setResponse(ResponseEntity.ok(jsonResponse));
                cache.put(data.toString(), newCacheEntry);
                //System.out.println("madhu"+cache.get(data.toString()));
                SearchFrequency.searcheachlocationfreq();
                return ResponseEntity.ok(newCacheEntry);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
             e.printStackTrace();

             return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);


        }

    }

    @GetMapping("/api/wordCompletion/{data}")
    public ResponseEntity<String> getAirportData(@PathVariable("data") String data) {
        System.out.println("api entered" + data);

        List<String> dataoutput = WordCompletion.wordCompletion(data);
        System.out.println("data sent to ui" + dataoutput);

        try {
            String jsonData = objectMapper.writeValueAsString(dataoutput);
            return ResponseEntity.ok(jsonData);
        } catch (Exception e) {

            return ResponseEntity.ok(e.getMessage());
        }
    }



}