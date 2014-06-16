package fr.miage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import fr.miage.connexion.ConnexionBD;
import fr.miage.model.Invitation;

public class InvitationDAO extends AbstractDAO<Invitation>{
	private static Logger logger = Logger.getRootLogger();
	
	public Invitation find(long idInvitation) {		
		ResultSet res = null;
		String query = "select * from invitation WHERE idInvitation="+idInvitation;
		Statement st = null;
		Invitation invit = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			if(res.next()){
				invit = getInvitation(res);
			}else{
				return null;
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le find invitation", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du find invitation", e);
			}
		}
		
		return invit;
	}
	
	public Invitation getById(long idUser, long idEvent) {		
		ResultSet res = null;
		String query = "select * from invitation WHERE idUser="+idUser+" AND idEvent="+idEvent;
		Statement st = null;
		Invitation invit = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			if(res.next()){
				invit = getInvitation(res);
			}else{
				return null;
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le getById Invitation", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du getById Invitation", e);
			}
		}
		
		return invit;
	}

	private Invitation getInvitation(ResultSet res) {
		Invitation invit = new Invitation();
		try {
			invit.setIdInvitation(res.getLong("idInvitation"));
			invit.setIdEvent(res.getLong("idEvent"));
			invit.setIdUser(res.getLong("idUser"));
			invit.setResponse(res.getInt("response"));
			invit.setDate(res.getString("date"));
			invit.setRole(res.getString("role"));
			invit.setGeolocalization(res.getInt("geolocalization"));
		} catch (SQLException e) {
			logger.error("Erreur dans le getInvitation", e);
		}
		return invit;
	}

	public List<Invitation> getAllInvitByUser(String idUser) {
		ResultSet res = null;
		String query = "select * from invitation WHERE idUser="+idUser;
		Statement st = null;
		List<Invitation> listeInvit = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			listeInvit = new ArrayList<Invitation>();
			while(res.next()){
				Invitation invit = getInvitation(res);
				listeInvit.add(invit);
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le getAllInvitByUser", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally de getAllInvitByUser", e);
			}
		}
		
		return listeInvit;
	}

	public long create(Invitation invit) {
		long res = 0;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String query = "insert into invitation(idEvent, idUser, response, role, date, geolocalization) values ("+invit.getIdEvent()+", "+invit.getIdUser()+", "+invit.getResponse()+", '"+invit.getRole()+"', '"+format.format(date)+"', 1)";
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch(MySQLIntegrityConstraintViolationException e){
			logger.error("Erreur dans le create invitation", e);
			return -1;
		} catch (SQLException ex) {
			logger.error("Erreur dans le create invitation", ex);
			return res;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du create invitation", e);
			}
		}
	}

	public int update(Invitation invit) {
		int res = 0;
		String query = "UPDATE invitation SET ";
		
		if(invit.getResponse() > 0){
			query+="response="+invit.getResponse();
			if(invit.getRole() != null){
				query+=", role='"+invit.getRole()+"'";
			}
		}else{
			query+="role='"+invit.getRole()+"'";
		}
		
		query += ", geolocalization="+invit.getGeolocalization()+" WHERE idEvent="+invit.getIdEvent()+" AND idUser="+invit.getIdUser();
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch (SQLException ex) {
			logger.error("Erreur dans l'update invitation", ex);
			return -1;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally del 'update invitation", e);
			}
		}
	}
	
	public int deleteInvitation(long idEvent, long idUser){
		int res = 0;
		String query = "delete from invitation where idEvent=" + idEvent+" AND idUser="+idUser;
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch (SQLException ex) {
			logger.error("Erreur dans le delete invitation", ex);
			return -1;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du delete invitation", e);
			}
		}
	}
	
	public int delete(long id){
		int res = 0;
		String query = "delete from invitation where idInvitation=" + id;
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch (SQLException ex) {
			logger.error("Erreur dans le delete invitation", ex);
			return -1;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du delete invitation", e);
			}
		}
	}
}
