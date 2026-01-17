package com.project1.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class AiApiController {
    
    public String callGeminiAPI(String prompt){

        String apiKey = "AIzaSyDtQjDQxQ6VpDTCbg-YRArMgkGR0q-irDs"; 
        StringBuilder response = new StringBuilder();

        try{
            URL url = new URL(
                "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key="
                + apiKey
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // JSON request body
            JSONObject jsonRequest = new JSONObject()
                .put("contents", List.of(
                    Map.of(
                        "role", "user",
                        "parts", List.of(
                            Map.of("text", prompt + " Give this in 3 to 4 lines.")
                        )
                    )
                ));

            // Send request
            OutputStream os = conn.getOutputStream();
            os.write(jsonRequest.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            // Read response
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300)
                            ? conn.getInputStream()
                            : conn.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

        } catch(IOException e){
            return "⚠ Unable to connect to Gemini API.";
        }

        JSONObject jsonResponse = new JSONObject(response.toString());

        // If API returned an error — return the error message silently
        if (!jsonResponse.has("candidates")) {
            return "⚠ AI could not generate a response.";
        }

        // Extract the text
        return jsonResponse
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text");
    }
}
