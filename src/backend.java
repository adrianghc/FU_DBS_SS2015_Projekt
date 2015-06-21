import java.sql.Connection;
import java.sql.DriverManager;


public class Backend {
	
	private Connection conn;
	public Backend_import backendImport;
	public Backend_queries backendQueries;

	public Backend() throws Exception {
		
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:15432/myapp?user=myapp&password=dbpass";
			this.conn = DriverManager.getConnection(url);
			
			backendImport = new Backend_import(this.conn);
			backendQueries = new Backend_queries(this.conn);
		} 
		catch (Exception e) {
			throw new Exception();
		}
		
	}
	
}
