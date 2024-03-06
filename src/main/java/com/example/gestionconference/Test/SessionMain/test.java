package com.example.gestionconference.Test.SessionMain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class test {

    public static void main(String[] args) {
        // Get the UID thread
        Thread thread = getUidThread();

        // Start the thread
        thread.start();
    }

    private static Thread getUidThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                String tester = "";
                while (true) {
                    try {
                        // Specify the URL of your ESP32 endpoint
                        URL url = new URL("http://192.168.231.134/rfid");

                        // Open a connection to the URL
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");

                        // Read the response
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        String test = response.toString();
                        String test2 = test.substring(test.indexOf(",") + 1);
                        if (!response.toString().isEmpty()) {
                            if (!tester.equals(test2)) {
                                System.out.println("Response from ESP32: " + response.toString());

                            }
                            tester = test2;
                        }

                        // Close the connection
                        conn.disconnect();

                        // Sleep for 1 second before making the next request
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
