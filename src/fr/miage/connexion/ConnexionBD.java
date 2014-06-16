package fr.miage.connexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;


public final class ConnexionBD {
	private Connection conn;
	private static final ConnexionBD db;
	private Properties prop = new Properties();
	private Logger logger = Logger.getRootLogger();
	
	//static block initialization for exception handling
    static{
        try{
        	db = new ConnexionBD();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }
	
	private ConnexionBD() {
		try{
			//load a properties file
			InputStream inputStream  = getClass().getClassLoader().getResourceAsStream("pathworld_local.properties");
			prop.load(inputStream);
			String url= prop.getProperty("url");
			String dbName = prop.getProperty("dbName");
			String driver = prop.getProperty("driver");
			String userName = prop.getProperty("user");
			String password = prop.getProperty("pwd");
			Class.forName(driver).newInstance();
			this.setConn((Connection) DriverManager.getConnection(url+dbName,userName,password));
		} catch (IOException e) {
			logger.error("Error during DB conection"+e);
		} catch (InstantiationException e) {
			logger.error("Error during DB conection"+e);
		} catch (IllegalAccessException e) {
			logger.error("Error during DB conection"+e);
		} catch (ClassNotFoundException e) {
			logger.error("Error during DB conection"+e);
		} catch (SQLException e) {
			logger.error("Error during DB conection"+e);
		}
	}
	
	private void setConn(Connection connection) {
		this.conn = connection;
	}

	public static ConnexionBD getInstance(){
		return db;
	}

	public Connection getConn() {
		return conn;
	}
}
