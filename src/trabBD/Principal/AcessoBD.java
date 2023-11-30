package trabBD.Principal;

public class AcessoBD {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb://localhost:3307/mercado?allowMultiQueries=true";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "alunofatec";
	
//===========================================================================================	
	public static final String getJdbcDriver() {
		return JDBC_DRIVER;
	}
	public static final String getJdbcUrl() {
		return JDBC_URL;
	}
	public static final String getJdbcUser() {
		return JDBC_USER;
	}
	public static final String getJdbcPass() {
		return JDBC_PASS;
	}
}
