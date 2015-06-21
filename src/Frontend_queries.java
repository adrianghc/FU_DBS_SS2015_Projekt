import java.util.Scanner;


public class Frontend_queries {
	
	/* Backend backend;
	public static Backend_queries backendQueries;
	
	public Frontend_queries(Backend backend) {
		this.backend = backend;
	}

	public Frontend_queries() {
		this.backendQueries = new Backend_queries(backend);
	} */
	
	
	public static void intro() {
		
		System.out.println();
		System.out.println(
				
				"#######################################\n"
			+	"#                                     #\n"
			+ 	"#          IMDb-Filmdatenbank         #\n"
			+ 	"#             Datenausgabe            #\n"
			+ 	"#                                     #\n"
			+ 	"#######################################\n"
			
			);
		
		howToContinue();
		
	}
	
	public static void howToContinue() {
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print("1 drücken für Konsolenanwendung, 2 drücken für Web-Anwendung: ");
	    
	    try {
	    	int cont = scanner.nextInt();
	    	if (cont != 1 && cont != 2) {
	    		System.out.println("Keine gültige Eingabe.");
	    		howToContinue();
	    	}
	    	else if (cont == 1) introConsole();
	    	else if (cont == 2) {
	    		System.out.println("Noch nicht verfügbar.");
	    		howToContinue();
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
			+ 	"#             Datenausgabe            #\n"
			+	"#           Konsolen-Version          #\n"
			+ 	"#                                     #\n"
			+ 	"#######################################\n"
			
			);
		
		
		
	}
	
}
