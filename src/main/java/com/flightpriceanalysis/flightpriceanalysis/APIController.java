package com.flightpriceanalysis.flightpriceanalysis;



import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class APIController {
    ObjectMapper objectMapper = new ObjectMapper();
    Service service=new Service();
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/api/wordcheck/{data}")
    public ResponseEntity<String> getData(@PathVariable("data") String data) {
        System.out.println("api entered");

        String dataoutput = service.wordCheckmain(data);
        System.out.println("data sent to ui"+dataoutput);
        try {
            String jsonData = objectMapper.writeValueAsString(dataoutput);
            return ResponseEntity.ok(jsonData);
        }
        catch(Exception e) {

            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/api/submissionform/submit")
    public ResponseEntity<String> onformSubmission(@RequestBody FormData data ) {
        System.out.println(data.getsource());
        System.out.println(data.getdestination()+data.getnoofpersons()+data.getclasstype()+data.getdate());
        try {
            List<Map<String,String>> list=service.getflightsCheap(data);
            System.out.println(list);
            String jsonResponse = objectMapper.writeValueAsString(list);
            System.out.println(jsonResponse);
            return ResponseEntity.ok(jsonResponse);
        }
        catch(Exception e) {

            return ResponseEntity.ok(e.getMessage());
        }


    }

}