package com.nosliw.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;

public class HAPUtilityIO {

	public static String readURIContent(URI uri) {
		StringBuffer out = new StringBuffer();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(uri.toURL().openStream()))) {
		    String line;
		    while ((line = reader.readLine()) != null) {
		    	out.append(line);
		    	out.append(System.lineSeparator());
		    }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();
	}
	
}
