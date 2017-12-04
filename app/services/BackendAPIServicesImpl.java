package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import beans.APIRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BackendAPIServicesImpl {

	public static StringBuffer callAPI(APIRequest apiRequest, String url) {
		StringBuffer response = new StringBuffer();
		try {
			Gson ga = new GsonBuilder().disableHtmlEscaping().create();
			String jsonDataHost = ga.toJson(apiRequest);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");

			// For POST only - START
			con.setDoOutput(true);
			con.setRequestProperty("content-type", "application/json");
			OutputStream os = con.getOutputStream();

			os.write(jsonDataHost.getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

			} else {
				System.out.println("POST request not worked");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public static StringBuffer callGetAPI(String url) {
		StringBuffer response = new StringBuffer();
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

			} else {
				System.out.println("GET request not worked");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
	public static StringBuffer callGetAPIGET(String url) {
		StringBuffer response = new StringBuffer();
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			//System.out.println("URL:"+url);
			int responseCode = con.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

			} else {
				System.out.println("callGetAPIGET request not worked");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}
