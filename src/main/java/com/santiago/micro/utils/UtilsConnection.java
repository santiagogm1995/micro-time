package com.santiago.micro.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class UtilsConnection {
	
	public static String getContentFromRequest(String urlString) {
		StringBuffer result = null;
		try {
			
			URL url = new URL(urlString);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			 result = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				result.append(inputLine);
			}
			in.close();
			con.disconnect();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result.toString();
	}

}
