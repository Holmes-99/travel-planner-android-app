package com.example.a1231279_1230239_courseproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {
    public static String getData(String urlString){
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
       reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
        }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

        }
    }

