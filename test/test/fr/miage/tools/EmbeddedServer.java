package test.fr.miage.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.ibatis.common.jdbc.ScriptRunner;

public class EmbeddedServer {
	
	private static Server server;
	
	public static final String TEST_CONTEXT = "/";
	public static final int TEST_PORT = 8181;
	protected static final String SERVER_PATH = "http://localhost:8181/";
	
	@BeforeClass
	public static void start() {
		server = new Server(TEST_PORT);
	
		WebAppContext context = new WebAppContext();
		context.setDescriptor("WebContent/WEB-INF/web.xml");
		context.setResourceBase("src/");
		context.setContextPath(TEST_CONTEXT);
		context.setParentLoaderPriority(false);
		context.setClassLoader(EmbeddedServer.class.getClassLoader());		
		server.setHandler(context);		
		try {
			server.start();
			resetDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void resetDatabase() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
		String aSQLScriptFilePath = "scripts/cleanDatabase.sql";
		Properties prop = new Properties();
		InputStream inputStream  = new FileInputStream("properties/pathworld_local.properties");
		prop.load(inputStream);
		String url= prop.getProperty("url");
		String dbName = prop.getProperty("dbName");
		String driver = prop.getProperty("driver");
		String userName = prop.getProperty("user");
		String password = prop.getProperty("pwd");
		Class.forName(driver).newInstance();
		Connection con = DriverManager.getConnection(url+dbName, userName, password);
 
		try {
			// Initialize object for ScripRunner
			ScriptRunner sr = new ScriptRunner(con, false, false);
 
			// Give the input file to Reader
			Reader reader = new BufferedReader(
                               new FileReader(aSQLScriptFilePath));
 
			// Exctute script
			sr.runScript(reader);
 
		} catch (Exception e) {
			System.err.println("Failed to Execute" + aSQLScriptFilePath
					+ " The error is " + e.getMessage());
		}
	}

	@AfterClass
	public static void stop() throws Exception {
		if (server != null) {
			server.stop();
			server.join();
			server.destroy();
			server = null;
		}	
	}
}