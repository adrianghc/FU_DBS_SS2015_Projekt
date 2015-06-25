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
	
	public ResultSet querySQL(String SQLstatement) throws SQLException {
		
		try {
			PreparedStatement st = conn.prepareStatement(SQLstatement);
			ResultSet rs = st.executeQuery();
			return rs;
		}
		catch (SQLException e) {
			throw e;
		}
		
	}
	
	public ResultSet queryGranulated(String fromParse, String selectParse, String whereParse, String groupParse, String orderParse, String limitParse) throws SQLException {
		
		try {
			String statement = "SELECT " + selectParse + " FROM " + fromParse + " WHERE " + whereParse;
			if (!groupParse.equals("")) {
				statement = statement + " GROUP BY " + groupParse;
			}
			if (!orderParse.equals("")) {
				statement = statement + " ORDER BY " + orderParse;
			}
			if (!limitParse.equals("")) {
				statement = statement + " LIMIT " + limitParse;
			}
			PreparedStatement st = conn.prepareStatement(statement);
			ResultSet rs = st.executeQuery();
			return rs;
		}
		catch (SQLException e) {
			throw e;
		}
		
	}

}
