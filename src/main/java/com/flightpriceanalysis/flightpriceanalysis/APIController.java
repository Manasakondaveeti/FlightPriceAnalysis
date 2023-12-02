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
    public ResponseEntity<String> getData(@PathVariable("data") String data) {
        System.out.println("api entered");

        SpellChecking spellcheck =new SpellChecking();
        spellcheck.initialLoadofWordcheck();
        String dataoutput = spellcheck.wordCheckmain(data);
        System.out.println("data sent to ui" + dataoutput);
        try {
            String jsonData = objectMapper.writeValueAsString(dataoutput);
            return ResponseEntity.ok(jsonData);
        } catch (Exception e) {

            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/api/submissionform/submit")

    public ResponseEntity<?> onformSubmission(@RequestBody FormData data) {
        System.out.println(data.getsource());
        System.out.println(data.getdestination() + data.getnoofpersons() + data.getclasstype() + data.getdate());

        try {
            // Check if the request is in the cache and within the time window
            if (cache.containsKey(data.toString()) && AvoidCrawling5Minutes.isWithinTimeWindow(cache.get(data.toString()).getTimestamp())) {
                String resp = cache.get(data.toString()).getResponse();
                System.out.println(resp);
                SearchFrequency.searcheachlocationfreq();
                return ResponseEntity.ok(resp);
            } else {


                List<Map<String, String>> list = webcrawl.getflightsCheap(data);
                System.out.println(list);
                String jsonResponse = objectMapper.writeValueAsString(list);
                cache.put(data.toString(), new CacheEntry(jsonResponse, System.currentTimeMillis()));
                SearchFrequency.searcheachlocationfreq();
                return ResponseEntity.ok(jsonResponse);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            // e.printStackTrace();

             return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);


        }

    }

}