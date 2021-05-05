package com.kanna.football;

import java.io.IOException;
import java.util.ArrayList;

public class FootBallAPI {

	static {

		try {
			GetProperties.loadProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String as[]) throws IOException {
		
		StringBuffer data = new StringBuffer();
		Utility ut = new Utility();
		//String url = GetProperties.getValue("TEAM_URL").trim();
		String url = "http://api.football-data.org/v2/competitions/PL/scorers?limit=100";
		data = ut.getConnection(url);
	    ut.writeAsCSV(data);
	    
	    System.out.println("--------Job completed-----------");
		
	}

	public static void printData(ArrayList<String> list) {

		System.out.println("Data from Football-API:");
		for (String str : list) {
			System.out.println(str);
		}

	}
}
