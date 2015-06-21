import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.zip.DataFormatException;


public class Frontend {
	
	public static Backend backend;
	public static boolean connSuccess;
	
	public static void main(String[] args) {
		
		try {
			backend = new Backend(); // Startet eine Backend-Instanz mit Verbindung zur Datenbank
			connSuccess = true;
		}
		catch (Exception e) {
			connSuccess = false;
		}
		
		startScreen();
	    
	}
	
	
	public static void startScreen() {
		
		System.out.println();
		System.out.println(
				
				"#######################################\n"
			+	"#                                     #\n"
			+ 	"# Freie Universität Berlin, SoSe 2015 #\n"
			+ 	"#           Datenbanksysteme          #\n"
			+ 	"#     Projekt: 2. Iteration KW 26     #\n"
			+ 	"#                                     #\n"
			+ 	"#          IMDb-Filmdatenbank         #\n"
			+ 	"#                                     #\n"
			+ 	"#######################################\n"
			
			);
	
		if (!connSuccess) {
			System.out.println("Fehler bei der Verbindung zur Datenbank. Möglicherweise ist die Datenbank nicht online oder es gibt Verbindungsprobleme. Dieses Programm wird beendet. Drücken Sie Enter zum Beenden...");
			try {
				System.in.read();
			} 
			catch (Exception f) { }
			System.exit(0);
		}
		
		howToContinue();
		
	}
	
	public static void howToContinue() {
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print("1 drücken für Datenimport, 2 drücken für Datenausgabe: ");
	    
	    try {
	    	int cont = scanner.nextInt();
	    	if (cont != 1 && cont != 2) {
	    		System.out.println("Keine gültige Eingabe.");
	    		howToContinue();
	    	}
	    	else if (cont == 1) import_intro();
	    	else if (cont == 2) queries_intro();
	    }
	    catch (java.util.InputMismatchException e) {
	    	System.out.println("Keine gültige Eingabe.");
	    	howToContinue();
	    }
	    finally {
	    	scanner.close();
	    }
		
	}
	

	public static void import_intro() {
		
		System.out.println();
		System.out.println(
				
				"#######################################\n"
			+	"#                                     #\n"
			+ 	"#          IMDb-Filmdatenbank         #\n"
			+ 	"#              Datenimport            #\n"
			+ 	"#                                     #\n"
			+ 	"#######################################\n"
			
			);
		
		import_howToContinue();
		
	}
	
	public static void import_howToContinue() {
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print("0 drücken für Zurück, 1 drücken für Import über Java, 2 drücken für Import über SQL: ");
	    
	    try {
	    	int cont = scanner.nextInt();
	    	if (cont != 0 && cont != 1 && cont != 2 && cont != 42) {
	    		System.out.println("Keine gültige Eingabe.");
	    		import_howToContinue();
	    	}
	    	else if (cont == 0) startScreen();
	    	else if (cont == 1) import_java_intro();
	    	else if (cont == 2) {
	    		System.out.println("Noch nicht verfügbar.");
	    		import_howToContinue();
	    		//import_sql_intro();
	    	}
	    	
	    	else if (cont == 42) { // Einträge der Datenbank löschen; für Testzwecke
	    		
	    		Scanner delete = new Scanner(System.in);
	    		System.out.print("Dadurch werden alle Einträge aus der Datenbank gelöscht. Fortfahren? 1 für JA, 0 für NEIN ");
	    		
	    		try {
	    			int contDel = scanner.nextInt();
	    	    	if (contDel != 1) {
	    	    		System.out.println("Abgebrochen.");
		    	    	import_howToContinue();
	    	    	}
	    	    	else {
	    	    		System.out.println("Lösche Einträge...");
	    	    		try{
	    	    			backend.backendImport.clearTables();
	    	    			System.out.println("Alle Einträge erfolgreich gelöscht.\n");
	    	    		}
	    	    		catch (SQLException e) {
	    	    			System.out.println("Ein Fehler ist aufgetreten, es wurden keine Einträge gelöscht. Möglicherweise ist die Datenbank zur Zeit nicht online oder es treten Verbindungsprobleme auf.\n");
	    	    		}
	    	    		import_intro();
	    	    	}
	    		}
	    		catch (java.util.InputMismatchException e) {
	    			System.out.println("Abgebrochen.");
	    	    	import_howToContinue();
	    		}
	    		finally {
	    			delete.close();
	    		}
	    		
	    	}
	    }
	    catch (java.util.InputMismatchException e) {
	    	System.out.println("Keine gültige Eingabe.");
	    	import_howToContinue();
	    }
	    finally {
	    	scanner.close();
	    }
	    
	}
	
	public static void import_java_intro() {
		
		System.out.println();
		System.out.println(
				
				"#######################################\n"
			+	"#                                     #\n"
			+ 	"#          IMDb-Filmdatenbank         #\n"
			+ 	"#              Datenimport            #\n"
			+ 	"#                 Java                #\n"
			+ 	"#                                     #\n"
			+ 	"#######################################\n"
			
			);
		
		import_java_howToContinue();
		
	}
	
	public static void import_java_howToContinue() {
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print("Geben Sie den Pfad zur CSV-Datei mit den zu importierenden Daten an (0 für Zurück): ");
	    
	    try {
	    	String csvFile = scanner.nextLine();
	    	if (csvFile.equals("0")) import_intro();
	    	
	    	try {
	    		backend.backendImport.importJava(csvFile);
	    	}
	    	catch (DataFormatException e) {
	    		System.out.println("Es konnten Daten importiert werden, aber einige Einträge waren fehlerhaft und konnte nicht gelesen werden.");
	    	}
	    	catch (FileNotFoundException e) {
	    		System.out.println("Datei nicht gefunden oder ungültige Pfadangabe.");
	    		import_java_howToContinue();
	    	}
	    	catch (IOException e) {
	    		System.out.println("Fehler beim Lesen der Datei.");
	    		import_java_intro();
	    	}
	    	catch (SQLException e) {
	    		System.out.println(e.getMessage() + " Einträge konnten nicht übernommen werden.");
	    	}
	    	
	    	System.out.println("Daten wurden erfolgreich importiert.");
	    	startScreen();
	    	
	    }
	    catch (java.util.InputMismatchException e) {
	    	System.out.println("Keine gültige Eingabe.");
	    	import_java_howToContinue();
	    }
	    finally {
	    	scanner.close();
	    }
		
	}
	
	public static void import_sql_intro() {
		
		System.out.println();
		System.out.println(
				
				"#######################################\n"
			+	"#                                     #\n"
			+ 	"#          IMDb-Filmdatenbank         #\n"
			+ 	"#              Datenimport            #\n"
			+ 	"#                  SQL                #\n"
			+ 	"#                                     #\n"
			+ 	"#######################################\n"
			
			);
		
		import_sql_howToContinue();
		
	}
	
	public static void import_sql_howToContinue() {
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print("Geben Sie den Pfad zur CSV-Datei mit den zu importierenden Daten an (0 für Zurück): ");
	    
	    try {
	    	String csvFile = scanner.nextLine();
	    	if (csvFile.equals("0")) import_intro();
	    	
	    	try {
	    		backend.backendImport.importSQL(csvFile);
	    	}
	    	catch (DataFormatException e) {
	    		System.out.println("Es konnten Daten importiert werden, aber einige Einträge waren fehlerhaft und konnte nicht gelesen werden.");
	    	}
	    	catch (FileNotFoundException e) {
	    		System.out.println("Datei nicht gefunden oder ungültige Pfadangabe.");
	    		import_java_howToContinue();
	    	}
	    	catch (IOException e) {
	    		System.out.println("Fehler beim Lesen der Datei.");
	    		import_java_intro();
	    	}
	    	catch (SQLException e) {
	    		System.out.println(e.getMessage() + " Einträge konnten nicht übernommen werden.");
	    	}
	    	
	    	System.out.println("Daten wurden erfolgreich importiert.");
	    	startScreen();
	    	
	    }
	    catch (java.util.InputMismatchException e) {
	    	System.out.println("Keine gültige Eingabe.");
	    	import_java_howToContinue();
	    }
	    finally {
	    	scanner.close();
	    }
		
	}
	
	
	public static void queries_intro() {
		
		System.out.println();
		System.out.println(
				
				"#######################################\n"
			+	"#                                     #\n"
			+ 	"#          IMDb-Filmdatenbank         #\n"
			+ 	"#             Datenausgabe            #\n"
			+ 	"#                                     #\n"
			+ 	"#######################################\n"
			
			);
		
		queries_howToContinue();
		
	}
	
	public static void queries_howToContinue() {
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print("0 drücken für Zurück, 1 drücken für Konsolenanwendung, 2 drücken für Web-Anwendung: ");
	    
	    try {
	    	int cont = scanner.nextInt();
	    	if (cont != 0 && cont != 1 && cont != 2) {
	    		System.out.println("Keine gültige Eingabe.");
	    		queries_howToContinue();
	    	}
	    	else if (cont == 0) startScreen();
	    	else if (cont == 1) queries_console_intro();
	    	else if (cont == 2) {
	    		System.out.println("Noch nicht verfügbar.");
	    		queries_howToContinue();
	    	}
	    }
	    catch (java.util.InputMismatchException e) {
	    	System.out.println("Keine gültige Eingabe.");
	    	queries_howToContinue();
	    }
	    finally {
	    	scanner.close();
	    }
	    
	}
	
	public static void queries_console_intro() {
		
		System.out.println();
		System.out.println(
				
				"#######################################\n"
			+	"#                                     #\n"
			+ 	"#          IMDb-Filmdatenbank         #\n"
			+ 	"#             Datenausgabe            #\n"
			+	"#           Konsolen-Version          #\n"
			+ 	"#                                     #\n"
			+ 	"#######################################\n"
			
			);
		
		queries_console_howToContinue();
		
	}
	
	public static void queries_console_howToContinue() {
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print("0 drücken für Zurück, 1 drücken, um alle Einträge auszugeben, 2 drücken für granulierte Abfragen: ");
	    
	    try {
	    	int cont = scanner.nextInt();
	    	if (cont != 0 && cont != 1 && cont != 2) {
	    		System.out.println("Keine gültige Eingabe.");
	    		queries_console_howToContinue();
	    	}
	    	else if (cont == 0) queries_intro();
	    	else if (cont == 1) queries_console_all();
	    	else if (cont == 2) queries_console_granulated();
	    	
	    }
	    catch (java.util.InputMismatchException e) {
	    	System.out.println("Keine gültige Eingabe.");
	    	queries_console_howToContinue();
	    }
	    finally {
	    	scanner.close();
	    }
		
	}
	
	public static void queries_console_all() {
		
		// Tabelle 'movies'
		try {
			ResultSet rs = backend.backendQueries.queryAll("movies");
			
			System.out.println("\nTabelle 'movies':\n");
			
			// // // Beginn Tabellenkopf
			
			/*
			 * ID: 7 Zeichen
			 * name: 80 Zeichen
			 * year: 4 Zeichen
			 * rating: 6 Zeichen
			 * votes: 10 Zeichen
			 * runtime: 7 Zeichen
			 */
			
			//
			System.out.print("| ID");
			for (int i = 1; i <= 6; i++) {
				System.out.print(" ");
			}
			System.out.print("| name");
			for (int i = 1; i <= 75; i++) {
				System.out.print(" ");
			}
			System.out.print("| year | rating | votes");
			for (int i = 1; i <= 6; i++) {
				System.out.print(" ");
			}
			System.out.print("| runtime |\n");
			
			//
			for (int i = 1; i <= 1+(7+2)+1+(80+2)+1+(4+2)+1+(6+2)+(10+2)+(7+2)+1; i++) {
				System.out.print("-");
			}
			System.out.print("\n");
			
			// // // Beginne Ausgabe der Einträge
			while (rs.next()) {
				
				System.out.print("| ");
				
				int ID = rs.getInt("ID");
				System.out.print(ID);
				for (int i = 1; i <= 7 - Integer.toString(ID).length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" | ");
				
				String name = rs.getString("name");
				System.out.print(name);
				for (int i = 1; i <= 78 - name.length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" | ");
				
				int year = rs.getInt("year");
				System.out.print(year);
				for (int i = 1; i <= 4 - Integer.toString(year).length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" | ");
				
				float rating = rs.getFloat("rating");
				System.out.print(rating);
				for (int i = 1; i <= 6 - Float.toString(rating).length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" | ");
				
				int votes = rs.getInt("votes");
				System.out.print(votes);
				for (int i = 1; i <= 10 - Integer.toString(votes).length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" | ");
				
				int runtime = rs.getInt("runtime");
				System.out.print(runtime);
				for (int i = 1; i <= 7 - Integer.toString(runtime).length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" |\n");
				
			}
			System.out.println();
			rs.close();
			
		}
		catch(SQLException e) {
			System.out.println("Ein Fehler ist aufgetreten (" + e.getSQLState() + "). Möglicherweise ist die Datenbank zur Zeit nicht online oder es treten Verbindungsprobleme auf.\n");
			queries_console_howToContinue();
		}
		
		
		// Tabelle 'directors'
		try {
			ResultSet rs = backend.backendQueries.queryAll("directors");
			
			System.out.println("\nTabelle 'directors':\n");
			
			// // // Beginn Tabellenkopf
			
			/*
			 * ID: 8 Zeichen
			 * name: 50 Zeichen
			 */

			//
			System.out.print("| movie_ID | name");
			for (int i = 1; i <= 45; i++) {
				System.out.print(" ");
			}
			System.out.print("|\n");
			
			//
			for (int i = 1; i <= 1+(8+2)+1+(48+2)+1; i++) {
				System.out.print("-");
			}
			System.out.print("\n");
			
			// // // Beginne Ausgabe der Einträge
			while (rs.next()) {
				
				System.out.print("| ");
				
				int ID = rs.getInt("movie_ID");
				System.out.print(ID);
				for (int i = 1; i <= 8 - Integer.toString(ID).length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" | ");
				
				String name = rs.getString("name");
				System.out.print(name);
				for (int i = 1; i <= 48 - name.length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" |\n");
				
			}
			System.out.println();
			rs.close();
			
		}
		catch(SQLException e) {
			System.out.println("Ein Fehler ist aufgetreten (" + e.getSQLState() + "). Möglicherweise ist die Datenbank zur Zeit nicht online oder es treten Verbindungsprobleme auf.\n");
			queries_console_howToContinue();
		}
		
		
		// Tabelle 'actors'
		try {
			ResultSet rs = backend.backendQueries.queryAll("actors");
			
			System.out.println("\nTabelle 'actors':\n");
			
			// // // Beginn Tabellenkopf
			
			/*
			 * ID: 8 Zeichen
			 * name: 50 Zeichen
			 */

			//
			System.out.print("| movie_ID | name");
			for (int i = 1; i <= 45; i++) {
				System.out.print(" ");
			}
			System.out.print("|\n");
			
			//
			for (int i = 1; i <= 1+(8+2)+1+(48+2)+1; i++) {
				System.out.print("-");
			}
			System.out.print("\n");
			
			// // // Beginne Ausgabe der Einträge
			while (rs.next()) {
				
				System.out.print("| ");
				
				int ID = rs.getInt("movie_ID");
				System.out.print(ID);
				for (int i = 1; i <= 8 - Integer.toString(ID).length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" | ");
				
				String name = rs.getString("name");
				System.out.print(name);
				for (int i = 1; i <= 48 - name.length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" |\n");
				
			}
			System.out.println();
			rs.close();
			
		}
		catch(SQLException e) {
			System.out.println("Ein Fehler ist aufgetreten (" + e.getSQLState() + "). Möglicherweise ist die Datenbank zur Zeit nicht online oder es treten Verbindungsprobleme auf.\n");
			queries_console_howToContinue();
		}
		
		
		// Tabelle 'genres'
		try {
			ResultSet rs = backend.backendQueries.queryAll("genres");
			
			System.out.println("\nTabelle 'genres':\n");
			
			// // // Beginn Tabellenkopf
			
			/*
			 * ID: 8 Zeichen
			 * name: 20 Zeichen
			 */

			//
			System.out.print("| movie_ID | name");
			for (int i = 1; i <= 15; i++) {
				System.out.print(" ");
			}
			System.out.print("|\n");
			
			//
			for (int i = 1; i <= 1+(8+2)+1+(18+2)+1; i++) {
				System.out.print("-");
			}
			System.out.print("\n");
			
			// // // Beginne Ausgabe der Einträge
			while (rs.next()) {
				
				System.out.print("| ");
				
				int ID = rs.getInt("movie_ID");
				System.out.print(ID);
				for (int i = 1; i <= 8 - Integer.toString(ID).length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" | ");
				
				String name = rs.getString("name");
				System.out.print(name);
				for (int i = 1; i <= 18 - name.length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" |\n");
				
			}
			System.out.println();
			rs.close();
			
		}
		catch(SQLException e) {
			System.out.println("Ein Fehler ist aufgetreten (" + e.getSQLState() + "). Möglicherweise ist die Datenbank zur Zeit nicht online oder es treten Verbindungsprobleme auf.\n");
			queries_console_howToContinue();
		}
		
		queries_console_intro();
		
	}
	
	public static void queries_console_granulated() {
		
		//String[] fromParsed = null;
		//String[] selectParsed = null;
		
		// Ausgabe der Tabellenstrukturen
		System.out.println("\nTabelle 'movies':\n");
		System.out.println(
				
				"| Name    | Datentyp    | Constraints |\n"
			+	"|---------|-------------|-------------|\n"
			+	"| ID      | INT         | PRIMARY KEY |\n"
			+	"| name    | varchar(80) | NOT NULL    |\n"
			+	"| year    | SMALLINT    | NOT NULL    |\n"
			+	"| rating  | DECIMAL     |             |\n"
			+	"| votes   | INT         | DEFAULT 0   |\n"
			+	"| runtime | SMALLINT    | NOT NULL    |\n"
			
			);
		
		System.out.println("\nTabelle 'directors':\n");
		System.out.println(
				
				"| Name     | Datentyp    | Constraints                       |\n"
			+	"|----------|-------------|-----------------------------------|\n"
			+	"| movie_ID | INT         | FOREIGN KEY REFERENCES movies(ID) |\n"
			+	"| name     | varchar(50) | NOT NULL                          |\n"
			+	"                                                              \n"
			+	" PRIMARY KEY (movie_ID, name)                                 \n"
			
			);
		
		System.out.println("\nTabelle 'actors':\n");
		System.out.println(
				
				"| Name     | Datentyp    | Constraints                       |\n"
			+	"|----------|-------------|-----------------------------------|\n"
			+	"| movie_ID | INT         | FOREIGN KEY REFERENCES movies(ID) |\n"
			+	"| name     | varchar(50) | NOT NULL                          |\n"
			+	"                                                              \n"
			+	" PRIMARY KEY (movie_ID, name)                                 \n"
			
			);
		
		System.out.println("\nTabelle 'genres':\n");
		System.out.println(
				
				"| Name     | Datentyp    | Constraints                       |\n"
			+	"|----------|-------------|-----------------------------------|\n"
			+	"| movie_ID | INT         | FOREIGN KEY REFERENCES movies(ID) |\n"
			+	"| name     | varchar(20) | NOT NULL                          |\n"
			+	"                                                              \n"
			+	" PRIMARY KEY (movie_ID, name)                                 \n"
			
			);
		
		
		// Einlesen
		String fromParse = "";
		String selectParse = "";
		String whereParse = "";
		
		Scanner from = new Scanner(System.in);
	    System.out.print("Aus welchen Tabellen soll ausgewählt werden? Namen bitte durch Komma abtrennen: ");
	    
	    // Tabellennamen
	    try {
	    	fromParse = from.nextLine();
	    	if (fromParse.equals("")) {
	    		System.out.println("Bitte mindestens einen Tabellennamen angeben.");
		    	queries_console_granulated();
	    	}
	    	System.out.println();
	    }
	    catch (java.util.InputMismatchException e) {
	    	System.out.println("Keine gültige Eingabe.");
	    	queries_console_granulated();
	    }
	    
	    
	    Scanner select = new Scanner(System.in);
	    System.out.print("Welche Spalten sollen ausgewählt werden? Namen bitte durch Komma abtrennen (* für alle): ");
	    
	    // Spaltennamen
	    try {
	    	selectParse = select.nextLine();
	    	if (selectParse.equals("")) {
	    		System.out.println("Bitte mindestens einen Spaltennamen angeben.");
		    	queries_console_granulated();
	    	}
	    	System.out.println();
	    }
	    catch (java.util.InputMismatchException e) {
	    	System.out.println("Keine gültige Eingabe.");
	    	queries_console_granulated();
	    }
	    
	    
	    Scanner where = new Scanner(System.in);
	    System.out.print("Bitte zusätzliche Bedingungen in SQL-Syntax angeben: WHERE ");
	    
	    // Spaltennamen
	    try {
	    	whereParse = where.nextLine();
	    	if (whereParse.equals("")) {
	    		whereParse = "1 = 1";
	    	}
	    	System.out.println();
	    }
	    catch (java.util.InputMismatchException e) {
	    	System.out.println("Keine gültige Eingabe.");
	    	queries_console_granulated();
	    }
		
	    try {
	    	
	    	ResultSet rs = backend.backendQueries.queryGranulated(fromParse, selectParse, whereParse);
	    	
	    	/*
	    	 * movies:
	    	 * 
			 * ID: 7 Zeichen
			 * name: 80 Zeichen
			 * year: 4 Zeichen
			 * rating: 6 Zeichen
			 * votes: 10 Zeichen
			 * runtime: 7 Zeichen
			 * 
			 * directors, actors:
			 * 
			 * movie_ID: 8 Zeichen
			 * name: 50 Zeichen
			 * 
			 * genres:
			 * 
			 * movie_ID: 8 Zeichen
			 * name: 20 Zeichen
			 * 
			 */
	    	
	    	// Ausgabe
		    ResultSetMetaData md = rs.getMetaData();
		    int colCount = md.getColumnCount();
		    String[] colOrder = new String[colCount];
		    
		    for (int i = 1; i <= colCount; i++) {
		    	if (md.getColumnName(i).equals("id")) {
		    		System.out.print("| ID      ");
		    		colOrder[i-1] = "ID";
		    	}
		    	else if (md.getColumnName(i).equals("name")) {
		    		System.out.print("| name                                                                         ");
		    		colOrder[i-1] = "name";
		    	}
		    	else if (md.getColumnName(i).equals("year")) {
		    		System.out.print("| year ");
		    		colOrder[i-1] = "year";
		    	}
		    	else if (md.getColumnName(i).equals("rating")) {
		    		System.out.print("| rating ");
		    		colOrder[i-1] = "rating";
		    	}
		    	else if (md.getColumnName(i).equals("votes")) {
		    		System.out.print("| votes      ");
		    		colOrder[i-1] = "votes";
		    	}
		    	else if (md.getColumnName(i).equals("runtime")) {
		    		System.out.print("| runtime ");
		    		colOrder[i-1] = "runtime";
		    	}
		    	else if (md.getColumnName(i).equals("movie_id")) {
		    		System.out.print("| movie_ID ");
		    		colOrder[i-1] = "movie_ID";
		    	}
		    }
	    	System.out.print("|\n");
		    
		    while (rs.next()) {
		    	for (int i = 1; i <= colCount; i++) {
		    		if (colOrder[i-1].equals("ID")) {
		    			Integer ID = new Integer(rs.getInt(i));
		    			System.out.print("| " + rs.getInt(i));
		    			for (int j = 1; j <= 8 - Integer.toString(ID).length(); j++) {
							System.out.print(" ");
						}
			    	}
			    	else if (colOrder[i-1].equals("name")) {
			    		String name = rs.getString(i);
		    			System.out.print("| " + name);
		    			for (int j = 1; j <= 81 - name.length(); j++) {
							System.out.print(" ");
						}
			    	}
			    	else if (colOrder[i-1].equals("year")) {
			    		System.out.print("| " + rs.getInt(i) + " ");
			    	}
			    	else if (colOrder[i-1].equals("rating")) {
			    		Float rating = new Float(rs.getFloat(i));
		    			System.out.print("| " + rs.getFloat(i));
		    			for (int j = 1; j <= 7 - Float.toString(rating).length(); j++) {
							System.out.print(" ");
						}
			    	}
			    	else if (colOrder[i-1].equals("votes")) {
			    		Integer votes = new Integer(rs.getInt(i));
		    			System.out.print("| " + rs.getInt(i));
		    			for (int j = 1; j <= 11 - Integer.toString(votes).length(); j++) {
							System.out.print(" ");
						}
			    	}
			    	else if (colOrder[i-1].equals("runtime")) {
			    		Integer runtime = new Integer(rs.getInt(i));
		    			System.out.print("| " + rs.getInt(i));
		    			for (int j = 1; j <= 8 - Integer.toString(runtime).length(); j++) {
							System.out.print(" ");
						}
			    	}
			    	else if (colOrder[i-1].equals("movie_ID")) {
			    		Integer ID = new Integer(rs.getInt(i));
		    			System.out.print("| " + rs.getInt(i));
		    			for (int j = 1; j <= 9 - Integer.toString(ID).length(); j++) {
							System.out.print(" ");
						}
			    	}
		    	}
		    	System.out.print("|\n");
		    }
		    
	    }
	    catch (SQLException e) {
	    	System.out.println("Die Eingabe war nicht gültig. Womöglich existieren die angegebenen Tabellen oder Spalten nicht oder es wurde keine gültige SQL-Syntax angegeben.");
	    }
		
	}

}
