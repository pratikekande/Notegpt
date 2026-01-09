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
        String apiKey = "";

        StringBuilder response = new StringBuilder();
        try{
            URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-latest:generateContent?key=" + apiKey);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // Build the JSON request body
            JSONObject jsonRequest = new JSONObject().put("contents", List.of(Map.of("parts",
                List.of(Map.of("text", prompt + "Give this in 3 to 4 lines")))));

                // Send the JSON request
                OutputStream os = conn.getOutputStream();
                byte[] input = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);

                // Read the response
                int responseCode = conn.getResponseCode();
                InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                response = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    response.append(line);
                }
        } catch(IOException e){
            e.printStackTrace();
        }

        JSONObject jsonResponse = new JSONObject(response.toString());

        return jsonResponse.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");
    }
}
