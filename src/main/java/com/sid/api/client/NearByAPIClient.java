package com.sid.api.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.sid.api.utility.Constants;

public class NearByAPIClient {
	
	
	
    public String getPlacesApiResult(String latitude, String longitude, String keyword){

        String response = null;
        try {
        	String location = latitude+","+longitude;
            String finalURL = Constants.API_URL +"location="+location+"&keyword="+keyword+"&rankby=distance"+"&types="+Constants.TYPES+"&key="+Constants.API_KEY;
            System.out.println(finalURL);
            URL url = new URL(finalURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            //read the response
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            response = convertStreamToString(in);

        }catch(Exception e){
            
        }

        return  response;
    }
    
    public String getPlacesApiResult(String latitude, String longitude){

        String response = null;
        try {
        	String location = latitude+","+longitude;
            String finalURL = Constants.API_URL +"location="+location+"&rankby=distance"+"&types="+Constants.TYPES+"&key="+Constants.API_KEY;
            System.out.println(finalURL);
            URL url = new URL(finalURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            //read the response
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            response = convertStreamToString(in);

        }catch(Exception e){
            
        }

        return  response;
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      
        return sb.toString();
    }

}
