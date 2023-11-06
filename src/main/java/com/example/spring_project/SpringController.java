package com.example.spring_project;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Getter
@RestController
public class SpringController {
    String email;
    String url;

    @PostMapping("/submit-data")
    public ResponseEntity<String> submitData(@RequestBody Map<String, String> data) {
        // Retrieve data from the frontend
        email = data.get("email");

        // Process the data as needed

        // Return a response
        return ResponseEntity.ok("Data submitted successfully");
    }
    @GetMapping("/images")
    public ResponseEntity<List<String>> getImageUrls() {
        List<String> imageUrls = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String imageUrl = "http://localhost:8080/wallpapers/wallpaper("+i+").jpg";
            imageUrls.add(imageUrl);
        }
        if (!imageUrls.isEmpty()) {
            return ResponseEntity.ok(imageUrls);
        } else {
            // Handle the case when no images are found.
            return ResponseEntity.notFound().build(); // or return an error response
        }

    }
    @PostMapping ("/selectedImage")
    public ResponseEntity<String> receiveSelectedImageUrl(@RequestBody Map<String, String> requestData) {
        url = requestData.get("selectedImageUrl");

        // You can now use the selectedImageUrl in your Java backend code.
        // Perform any necessary operations with it.

        return ResponseEntity.ok("Received selected image URL: " + url);
    }




}
