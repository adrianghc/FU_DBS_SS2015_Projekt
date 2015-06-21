import java.util.Scanner;


public class Frontend_import {
	
	/* public Backend backend;
	public static Backend_import backendImport;
	
	public Frontend_import(Backend backend) {
		this.backend = backend;
	}
	
	public Frontend_import() {
		this.backendImport = new Backend_import(backend);
	} */
	

	public static void intro() {
		
		System.out.println();
		System.out.println(
				
				"#######################################\n"
			+	"#                                     #\n"
			+ 	"#          IMDb-Filmdatenbank         #\n"
			+ 	"#              Datenimport            #\n"
			+ 	"#                                     #\n"
			+ 	"#######################################\n"
			
			);
		
		howToContinue();
		
	}
	
	public static void howToContinue() {
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print("1 drücken für Import über Java, 2 drücken für Import über SQL: ");
	    
	    try {
	    	int cont = scanner.nextInt();
	    	if (cont != 1 && cont != 2 && cont != 42) {
	    		System.out.println("Keine gültige Eingabe.");
	    		howToContinue();
	    	}
	    	else if (cont == 1) introConsole();
	    	else if (cont == 2) {}
	    	
	    	else if (cont == 42) { // Einträge der Datenbank löschen; für Testzwecke
	    		
	    		Scanner delete = new Scanner(System.in);
	    		System.out.println("Dadurch werden alle Einträge aus der Datenbank gelöscht. Fortfahren? 1 für JA, 0 für NEIN");
	    		
	    		try {
	    			int contDel = scanner.nextInt();
	    	    	if (contDel != 1) {
	    	    		System.out.println("Abgebrochen.");
		    	    	howToContinue();
	    	    	}
	    	    	else {
	    	    		System.out.println("Lösche Einträge...");
	    	    		//boolean success = backend.backendImport.clearTables();#
	    	    		boolean success = true;
	    	    		if (success) {
	    	    			System.out.println("Alle Einträge erfolgreich gelöscht.\n");
	    	    		}
	    	    		else {
	    	    			System.out.println("Ein Fehler ist aufgetreten, es wurden keine Einträge gelöscht. Möglicherweise ist die Datenbank zur Zeit nicht online oder es treten Verbindungsprobleme auf.\n");
	    	    		}
	    	    		intro();
	    	    	}
	    		}
	    		catch (java.util.InputMismatchException e) {
	    			System.out.println("Abgebrochen.");
	    	    	howToContinue();
	    		}
	    		
	    		delete.close();
	    		
	    	}
	    }
	    catch (java.util.InputMismatchException e) {
	    	System.out.println("Keine gültige Eingabe.");
	    	howToContinue();
	    }
		
	    scanner.close();
	    
	}
	
	public static void introConsole() {
		
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
		
	}
	
}
