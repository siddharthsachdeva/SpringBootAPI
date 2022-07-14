package com.sid.api.scheduler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HerokuServerAliveScheduler {
	
	private String url;
	
	private RestTemplate restTemplate = new RestTemplate();

	public HerokuServerAliveScheduler(@Value("${beezywebapiapp.service.url}") final String url) {
		super();
		this.url = url;
	}

	
/*	@Scheduled(fixedRate = 300000)
	public void keepItAlive(){
		
		String auth = "beezy:dc71d48b-bf04-40af-8e68-0e924c1f11bf";
		
		byte[] plainCredsBytes = auth.getBytes();
		String base64CredsBytes = Base64.getEncoder().encodeToString(plainCredsBytes);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64CredsBytes);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String response = responseEntity.getBody();
		System.out.println("Response is:"+response+"\nKeeping APIs alive: "+LocalDateTime.now());
		
		keepClientUIAlive();
	}*/


	private void keepClientUIAlive() {
	
		
	        String response = null;
	        try {
	            String finalURL = "https://beezy-admin.herokuapp.com";

	            URL url = new URL(finalURL);
	            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	            httpURLConnection.setRequestMethod("GET");
	            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
	            response = convertStreamToString(in);

	            System.out.println("Response:\n"+response.substring(0, 20)+"\nKeep Client UI alive: "+LocalDateTime.now());
	            
	        }catch(Exception e){
	            e.printStackTrace();
	        }
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
