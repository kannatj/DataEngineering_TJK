package com.kanna.football;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.opencsv.CSVWriter;

public class Utility {

	public StringBuffer getConnection(String url) throws IOException {

        StringBuffer data = new StringBuffer();
		
		System.out.println(url);
		
		//URL urlGetReq = new URL("http://api.football-data.org/v2/competitions/PL/scorers?limit=100");
		
		URL urlGetReq = new URL(null, "http://api.football-data.org/v2/competitions/PL/scorers?limit=300", new sun.net.www.protocol.https.Handler());
		
		HttpsURLConnection connection = (HttpsURLConnection) urlGetReq.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("X-Auth-Token", GetProperties.getValue("KEY"));
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoInput(true);
		
		int responsecode = connection.getResponseCode();
		System.out.println("Response code : "+responsecode);
		
		if (responsecode == 200) {
			System.out.println("Connection established.");
			data = getDataFromAPI(connection);
		}
		else {
			System.out.println("Connection not established.");
		}
		return data;
	}

	public static StringBuffer getDataFromAPI(HttpsURLConnection conn) throws IOException {
		//ArrayList<String> list = new ArrayList<String>();
		String readLine = null;

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer response = new StringBuffer();
		while ((readLine = in.readLine()) != null) {
			//list.add(readLine);
			response.append(readLine);
		}

		return response;
	}
	
	public static void writeAsCSV(StringBuffer strBuff) throws IOException {
		
		String formatCode = "yyyy_MM_dd"; // Example
		SimpleDateFormat formatter = new SimpleDateFormat(formatCode); // Time Formatter
		Date date = Calendar.getInstance().getTime();
		String time = formatter.format(date);
		
		System.out.println(time);
		
		System.out.println(strBuff.toString());
		
		JSONObject jsonObj = new JSONObject(strBuff.toString());
		JSONArray jsonArr = jsonObj.getJSONArray("scorers");
		int len = jsonArr.length();
		
		String compCode = jsonObj.getJSONObject("competition").getString("code");
		String lastUpdate = jsonObj.getJSONObject("competition").getString("lastUpdated");
		String lastUpdateDate = lastUpdate.substring(0,9).replace('-','_');
		
		//String outputPath = GetProperties.getValue("OUTPUT_DIR");
		String fullpath = "C:/Users/KANNA/Documents/Java_Runnable/TopScores/"+compCode+"TOP_"+lastUpdateDate+".csv";
		
		
		FileWriter outputfile = new FileWriter(fullpath);
		CSVWriter writer = new CSVWriter(outputfile, ',');

		
		String header[] = {"id", "name", "DOB", "nationality", "position", "team", "goals" };
		writer.writeNext(header);
		
		System.out.println(len);
		
		for (int i = 0; i < len; ++i) {
		      final JSONObject person = jsonArr.getJSONObject(i);
		      String arr[] = new String[7];
		      arr[0] = person.getJSONObject("player").get("id").toString();
			  arr[1] = person.getJSONObject("player").get("name").toString();
			  arr[2] = person.getJSONObject("player").get("dateOfBirth").toString();
			  arr[3] = person.getJSONObject("player").get("nationality").toString();
			  arr[4] = person.getJSONObject("player").get("position").toString();
			  arr[5] = person.getJSONObject("team").get("name").toString();
			  arr[6] = person.get("numberOfGoals").toString();
			  
			 //System.out.println(arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3]+" "+arr[4]+" "+arr[5]+" "+arr[6]);
			  writer.writeNext(arr);
		    }	
		
		writer.close();
	}
}
