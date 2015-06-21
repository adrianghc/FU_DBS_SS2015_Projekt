import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.zip.DataFormatException;


public class Backend_import {
	
	private Connection conn;
	
	public Backend_import(Connection conn) {
		this.conn = conn;
	}
	
	
	public void clearTables() throws SQLException {
		
		try {
			PreparedStatement st = conn.prepareStatement("TRUNCATE movies, directors, actors, genres;");
			st.executeQuery();
			st.close();
		}
		catch (SQLException e) {
			if (e.getErrorCode() != 0) throw e;
		}
	}
	
	public void importJava(String csvFile) throws DataFormatException, FileNotFoundException, IOException, SQLException {
		
		BufferedReader br = null;
    	String line = "";
    	int fails = 0;
    	int readFails = 0;
     
    	try {
    		br = new BufferedReader(new FileReader(csvFile)); // Lese Datei ein
    		while ((line = br.readLine()) != null) { // Parsen
    			
    			String[] resultLine = line.split("\t");
    			
    			int movie_ID = 0;
    			String movie_name = "";
    			int movie_year = 0;
    			float movie_rating = 0;
    			int movie_votes = 0;
    			int movie_runtime = 0;
    			
    			String[] directors_name;
    			String[] actors_name;
    			String[] genres_name;
    			
    			// Umwandlung der Daten
    			try {
        			movie_ID = Integer.parseInt(resultLine[0].substring(2));
        			movie_name = resultLine[1];
        			try {
        				movie_year = Integer.parseInt(resultLine[2]);
        			}
        			catch (java.lang.NumberFormatException e) { // Fange "Unreinheiten" auf (z.B. "2015 Video"), extrahiere nur Zahl am Anfang
        				try {
        					movie_year = Integer.parseInt(resultLine[2].substring(0,4));
        				}
        				catch (java.lang.NumberFormatException f) { } // Fange sonstige "Unreinheiten" auf
        			}
        			if (!resultLine[3].equals("NA") && !resultLine[3].equals("-")) {
        				movie_rating = Float.parseFloat(resultLine[3]);
        			}
        			if (!resultLine[4].equals("NA") && !resultLine[4].equals("-")) {
        				movie_votes = Integer.parseInt(resultLine[4]);
        			}
        			if (!resultLine[5].equals("NA") && !resultLine[5].equals("-")) {
        				movie_runtime = Integer.parseInt(resultLine[5].substring(0,resultLine[5].length() - 6));
        			}
        			
        			directors_name = resultLine[6].split("\\|");
        			actors_name = resultLine[7].split("\\|");
        			genres_name = resultLine[8].split("\\|");
    			}
    			catch (Exception e) {
    				readFails++;
    				continue;
    			}
    			
    			// Eintragen in 'movies'
    			try {
    				PreparedStatement st = conn.prepareStatement("INSERT INTO movies VALUES (?, ?, ?, ?, ?, ?);");
    				st.setInt(1, movie_ID);
    				st.setString(2, movie_name);
    				st.setInt(3, movie_year);
    				st.setFloat(4, movie_rating);
    				st.setInt(5, movie_votes);
    				st.setInt(6, movie_runtime);
    				st.executeQuery();
    				st.close();
    			}
    			catch (SQLException e) {
    				if (e.getErrorCode() != 0) fails++;
    			}
    			
    			// Eintragen in 'directors'
    			for (int i = 0; i < directors_name.length; i++) {
    				
    				if (!directors_name[i].equals("['NA']")) {
    					try {
            				PreparedStatement st = conn.prepareStatement("INSERT INTO directors VALUES (?, ?);");
            				st.setInt(1, movie_ID);
            				st.setString(2, directors_name[i]);
            				st.executeQuery();
            				st.close();
            			}
            			catch (SQLException e) {
            				if (e.getErrorCode() != 0) fails++;
            			}
    				}
    				
    			}
    			
    			// Eintragen in 'actors'
    			for (int i = 0; i < actors_name.length; i++) {
    				
    				if (!actors_name[i].equals("['NA']")) {
    					try {
            				PreparedStatement st = conn.prepareStatement("INSERT INTO actors VALUES (?, ?);");
            				st.setInt(1, movie_ID);
            				st.setString(2, actors_name[i]);
            				st.executeQuery();
            				st.close();
            			}
            			catch (SQLException e) {
            				if (e.getErrorCode() != 0) fails++;
            			}
    				}

    			}
    			
    			// Eintragen in 'genres'
    			for (int i = 0; i < genres_name.length; i++) {
    				
    				if (!genres_name[i].equals("['NA']")) {
	    				try {
	        				PreparedStatement st = conn.prepareStatement("INSERT INTO genres VALUES (?, ?);");
	        				st.setInt(1, movie_ID);
	        				st.setString(2, genres_name[i]);
	        				st.executeQuery();
	        				st.close();
	        			}
	        			catch (SQLException e) {
	        				if (e.getErrorCode() != 0) fails++;
	        			}
    				}
    			}
    			
    		}
     
    	} 
    	catch (FileNotFoundException e) {
    		throw new FileNotFoundException();
    	} 
    	catch (IOException e) {
    		throw new IOException();
    	} 
    	finally {
    		if (fails > 0) throw new SQLException(Integer.toString(fails));
    		if (readFails > 0) throw new DataFormatException();
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
		
	}
	
	public void importSQL(String csvFile) throws DataFormatException, FileNotFoundException, IOException, SQLException {
		
		try {
			PreparedStatement st = conn.prepareStatement("");
			st.executeQuery();
		}
		catch (SQLException e) {
			throw e;
		}
		
	}
	
}
