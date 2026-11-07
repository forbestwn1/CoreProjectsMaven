package com.nosliw.core.utility.rest;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collections;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class HAPRestTestMain {

	public static void main1(String[] args) {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add((request, body, execution) -> {
			request.getHeaders().setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			request.getHeaders().set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
			request.getHeaders().set("Connection", "keep-alive");
			request.getHeaders().set("Accept-Encoding", "gzip, deflate");
			if (body != null) {
				request.getHeaders().setContentLength(body.length);
			}
			return execution.execute(request, body);
		});
		
		String responsStr = restTemplate.postForObject("http://www.lastonemile.ca:8080/nosliw/core/gateway", "{}", String.class);
//		String responsStr = restTemplate.postForObject("http://lastonemile.ca:8085/nosliw/datasource/instance/profile/search", "{}", String.class);
		System.out.println(responsStr);

	}

	public static void main(String[] args) {
        try (Socket socket = new Socket()) {
            socket.connect(
                new InetSocketAddress("lastonemile.ca", 8080),
                10000
            );

            System.out.println("Connection successful!");
            System.out.println(socket.getRemoteSocketAddress());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
}