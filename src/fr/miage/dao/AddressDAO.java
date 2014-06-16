package fr.miage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import fr.miage.connexion.ConnexionBD;
import fr.miage.model.Address;

public class AddressDAO extends AbstractDAO<Address>{
	
	private static Logger logger = Logger.getRootLogger();
	
	public Address getByPlaceAndCoord(String place, double latitude, double longitude){
		ResultSet res = null;
		String query = "select * from adress WHERE place='"+place+"' AND latitude="+latitude+" AND longitude="+longitude;
		Statement st = null;
		Address address = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			if(res.next()){
				address = getAddress(res);
			}else{
				return null;
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans getByPlaceAndCoord", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally de getByPlaceAndCoord", e);
			}
		}
		
		return address;
	}

	public Address find(long idAddress) {
		ResultSet res = null;
		String query = "select * from adress WHERE idAdress="+idAddress;
		Statement st = null;
		Address address = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			if(res.next()){
				address = getAddress(res);
			}else{
				return null;
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans find", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du find",e);
			}
		}
		
		return address;
	}

	private Address getAddress(ResultSet res) {
		Address address = new Address();
		try {
			address.setIdAddress(res.getLong("idAdress"));
			address.setPlace(res.getString("place"));
			address.setLocation(res.getString("location"));
			address.setLatitude(res.getDouble("latitude"));
			address.setLongitude(res.getDouble("longitude"));
		} catch (SQLException e) {
			logger.error("Erreur dans le getAddress", e);
		}
		return address;
	}

	public Address getAddressByUser(long idUser) {
		ResultSet res = null;
		String query = "select * from adress a inner join user u on a.idAdress = u.idHomeAddress WHERE u.idUser="+idUser;
		Statement st = null;
		Address address = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			if(res.next()){
				address = getAddress(res);
			}else{
				return null;
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le getAddressByUser", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally de getAddressByUser", e);
			}
		}
		
		return address;
	}

	public long create(Address address) {
		int res = 0;
		String query = "insert into adress(place, location, latitude, longitude) values ('"+address.getPlace()+"', '"+address.getLocation()+"', "+address.getLatitude()+", "+address.getLongitude()+")";
		Statement st = null;
		try {
			PreparedStatement ps = ConnexionBD.getInstance().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			res = ps.executeUpdate();
			if(res == 1){
				ResultSet resI = ps.getGeneratedKeys();
				long id = 0;
				if(resI.next()){
					id = resI.getInt(1);
				}
				return id;
			}else{
				return 0;
			}
		}catch(MySQLIntegrityConstraintViolationException e){
			logger.error("Erreur dans le create", e);
			return -1;
		} catch (SQLException ex) {
			logger.error("Erreur dans le create", ex);
			return res;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du create", e);
			}
		}
	}

	public int update(Address address) {
		int res = 0;
		String query = "update adress set place='" + address.getPlace()
				+ "', location='" + address.getLocation() + "', latitude="
				+ address.getLatitude() + ", longitude="+address.getLongitude()+" where idAdress=" + address.getIdAddress();
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch (SQLException ex) {
			logger.error("Erreur dans le update", ex);
			return -1;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally de l'update", e);
			}
		}
	}

	@Override
	public int delete(long id) {
		int res = 0;
		String query = "delete from adress where idAdress=" + id;
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch (SQLException ex) {
			logger.error("Erreur dans le delete", ex);
			return -1;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally de delete", e);
			}
		}
	}
}
