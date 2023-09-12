package com.masai.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.masai.Model.ApiEntry;
import com.masai.Model.ApiResponse;

@RestController
@RequestMapping("/api")
class ApiController {

    private final String publicApiUrl = "https://api.publicapis.org/entries";
    private final List<ApiEntry> entries = new ArrayList<>();
    private int nextEntryId = 1;

    @GetMapping("/listByCategory")
    public List<ApiEntry> listEntriesByCategory(@RequestParam String category) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity(publicApiUrl, ApiResponse.class);
        ApiResponse apiResponse = responseEntity.getBody();

        if (apiResponse != null && apiResponse.getEntries() != null) {
            return apiResponse.getEntries().stream()
                    .filter(entry -> entry.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>(); 
        }
    }

    @PostMapping("/saveEntry")
    public ApiEntry saveNewEntry(@RequestBody ApiEntry newEntry) {
        newEntry.setId(nextEntryId++);
        entries.add(newEntry);
        return newEntry;
    }
}
