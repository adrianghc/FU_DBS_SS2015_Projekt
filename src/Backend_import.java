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
			PreparedStatement st1 = conn.prepareStatement("CREATE TABLE Import("
															+ "temp_id CHAR(9)"
															+ "temp_name VARCHAR(250),"
															+ "temp_year VARCHAR(10),"
															+ "temp_rating VARCHAR(5),"
															+ "temp_votes VARCHAR(10),"
															+ "temp_runtime VARCHAR(10),"
															+ "temp_directors VARCHAR(100),"
															+ "temp_actors VARCHAR(100),"
															+ "temp_genres VARCHAR(100)"
															+ ");");
			st1.executeQuery();
			
			PreparedStatement st2 = conn.prepareStatement("\\COPY Import FROM '" + csvFile + "' (DELIMITER E'\t');");
			st2.executeQuery();
			
			PreparedStatement st3 = conn.prepareStatement("INSERT INTO movies (SELECT"
															+ "CAST(substring(temp_id from 3 for 7) AS INT),"
															+ "temp_name, CAST(substring(temp_year from 1 for 4) AS INT),"
															+ "CASE WHEN temp_rating = 'NA' OR temp_rating = '-' "
																+ "THEN 0 "
																+ "ELSE CAST(temp_rating as NUMERIC) "
																+ "END, "
															+ "CASE WHEN temp_votes = 'NA' OR temp_votes = '-' "
																+ "THEN 0 "
																+ "ELSE CAST(temp_votes AS INT) "
																+ "END, "
															+ "CASE WHEN temp_runtime = 'NA' OR temp_runtime = '-' "
																+ "THEN 0 "
																+ "ELSE CAST(substring(temp_runtime from 1 for length(temp_runtime) - 6) AS INT) "
																+ "END "
															+ "FROM Import);");
			st3.executeQuery();
			
			PreparedStatement st4 = conn.prepareStatement("INSERT INTO directors(SELECT "
																+ "CAST(substring(temp_id from 3 for 7) AS INT),"
																+ "CASE WHEN temp_directors != '[''NA'']' AND temp_directors != 'NA' "
																	+ "THEN regexp_split_to_table(temp_directors,'\\|')"
																	+ "ELSE '-'  "
																	+ "END "
																+ "FROM Import "
																+ "GROUP BY temp_id, regexp_split_to_table(temp_directors,'\\|'),temp_directors");
			st4.executeQuery();
			
			PreparedStatement st5 = conn.prepareStatement("INSERT INTO directors(SELECT "
																+ "CAST(substring(temp_id from 3 for 7) AS INT),"
																+ "CASE WHEN temp_actors != '[''NA'']' AND temp_actors != 'NA' "
																	+ "THEN regexp_split_to_table(temp_actors,'\\|')"
																	+ "ELSE '-'  "
																	+ "END "
																+ "FROM Import "
																+ "GROUP BY temp_id, regexp_split_to_table(temp_actors,'\\|'),temp_actors");
			st5.executeQuery();
			
			PreparedStatement st6 = conn.prepareStatement("INSERT INTO directors(SELECT "
																+ "CAST(substring(temp_id from 3 for 7) AS INT),"
																+ "CASE WHEN temp_genres != '[''NA'']' AND temp_genres != 'NA' "
																	+ "THEN regexp_split_to_table(temp_genres,'\\|')"
																	+ "ELSE '-'  "
																	+ "END "
																+ "FROM Import "
																+ "GROUP BY temp_id, regexp_split_to_table(temp_genres,'\\|'),temp_genres");
			st6.executeQuery();
			
			PreparedStatement st7 = conn.prepareStatement("DELETE FROM directors WHERE name = '-';");
			st7.executeQuery();
			
			PreparedStatement st8 = conn.prepareStatement("DELETE FROM actors WHERE name = '-';");
			st8.executeQuery();
			
			PreparedStatement st9 = conn.prepareStatement("DELETE FROM genres WHERE name = '-';");
			st9.executeQuery();
		}
		catch (SQLException e) {
			throw e;
		}
		
	}
	
}
