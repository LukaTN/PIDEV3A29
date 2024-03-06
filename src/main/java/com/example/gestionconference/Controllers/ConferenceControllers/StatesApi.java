package com.example.gestionconference.Controllers.ConferenceControllers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StatesApi {
    public static List<String> getbyCountry(String country) {
        List<String> States = new ArrayList<>();
        try {
            String url = "https://countriesnow.space/api/v0.1/countries/states";

            String postData = "{\"country\": \"" + country + "\"}";

            String response = sendPostRequest(url, postData);
            JSONObject jsonResponse = new JSONObject(response);
            if (!jsonResponse.getBoolean("error")) {
                JSONObject data = jsonResponse.getJSONObject("data");
                JSONArray states = data.getJSONArray("states");

                System.out.println("States in " + data.getString("name") + ":");
                for (int i = 0; i < states.length(); i++) {
                    JSONObject state = states.getJSONObject(i);

                    States.add(state.getString("name"));
                }
                return States;
            } else {
                System.out.println("Error: " + jsonResponse.getString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return States;
    }

    public static String sendPostRequest(String urlString, String postData) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.writeBytes(postData);
            outputStream.flush();
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return response.toString();
    }
}
