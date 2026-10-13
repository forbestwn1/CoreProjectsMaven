package com.nosliw.datasource.utility.toronto.community;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.nosliw.common.serialization.HAPUtilityJson;

public class Utility {

    public static final String COMMUNITY_CENTER_LIST_RESOURCE_PATH = "/com/nosliw/datasource/utility/toronto/community/communitylist.json";
    public static final Path COMMUNITY_CENTER_CODETABLE_PATH = Paths.get("C:\\Mywork\\Library\\codetable\\toronto_community.json");
    public static final Path PROGRAM_CODETABLE_PATH = Paths.get("C:\\Mywork\\Library\\codetable\\toronto_program_dropin.json");

    
    public static String httpRead(String urlString) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("Accept", "application/json");

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new Exception();
            }

            String responseBody;
            try (InputStream inputStream = connection.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                responseBody = HAPUtilityJson.formatJson(builder.toString());
            }
            return responseBody;

        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    	return null;
    }
    
}
