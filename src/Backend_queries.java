import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Backend_queries {
	
	private Connection conn;
	
	public Backend_queries(Connection conn) {
		this.conn = conn;
	}
	
	
	public ResultSet queryAll(String tableName) throws SQLException {
		
		try {
			PreparedStatement st = conn.prepareStatement("SELECT * FROM " + tableName + ";");
			ResultSet rs = st.executeQuery();
			return rs;
		}
		catch (SQLException e) {
			throw e;
		}
		
	}
	
	public ResultSet queryGranulated(String fromParse, String selectParse, String whereParse) throws SQLException {
		
		try {
			PreparedStatement st = conn.prepareStatement("SELECT " + selectParse + " FROM " + fromParse + " WHERE " + whereParse);
			ResultSet rs = st.executeQuery();
			return rs;
		}
		catch (SQLException e) {
			throw e;
		}
		
	}

}
