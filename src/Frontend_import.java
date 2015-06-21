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
	    System.out.print("1 dr�cken f�r Import �ber Java, 2 dr�cken f�r Import �ber SQL: ");
	    
	    try {
	    	int cont = scanner.nextInt();
	    	if (cont != 1 && cont != 2 && cont != 42) {
	    		System.out.println("Keine g�ltige Eingabe.");
	    		howToContinue();
	    	}
	    	else if (cont == 1) introConsole();
	    	else if (cont == 2) {}
	    	
	    	else if (cont == 42) { // Eintr�ge der Datenbank l�schen; f�r Testzwecke
	    		
	    		Scanner delete = new Scanner(System.in);
	    		System.out.println("Dadurch werden alle Eintr�ge aus der Datenbank gel�scht. Fortfahren? 1 f�r JA, 0 f�r NEIN");
	    		
	    		try {
	    			int contDel = scanner.nextInt();
	    	    	if (contDel != 1) {
	    	    		System.out.println("Abgebrochen.");
		    	    	howToContinue();
	    	    	}
	    	    	else {
	    	    		System.out.println("L�sche Eintr�ge...");
	    	    		//boolean success = backend.backendImport.clearTables();#
	    	    		boolean success = true;
	    	    		if (success) {
	    	    			System.out.println("Alle Eintr�ge erfolgreich gel�scht.\n");
	    	    		}
	    	    		else {
	    	    			System.out.println("Ein Fehler ist aufgetreten, es wurden keine Eintr�ge gel�scht. M�glicherweise ist die Datenbank zur Zeit nicht online oder es treten Verbindungsprobleme auf.\n");
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
	    	System.out.println("Keine g�ltige Eingabe.");
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
