package fr.miage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import fr.miage.connexion.ConnexionBD;
import fr.miage.model.Event;

public class EventDAO extends AbstractDAO<Event>{

	private static Logger logger = Logger.getRootLogger();

	public Event find(long id) {
		ResultSet res = null;
		String query = "select * from event WHERE idEvent=" + id;
		Statement st = null;
		Event event = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			if (res.next()) {
				event = getEvent(res);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le find", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du find", e);
			}
		}

		return event;
	}

	
	public List<Event> getAllByUser(long idUser, String date) {
		ResultSet res = null;
		String query = "select e.idEvent, e.entitled, e.date, e.eventType, e.idAdress, e.idCreator from (invitation i join event e on  i.idEvent=e.idEvent) WHERE i.idUser="+idUser;
		if(date != null){
			query+=" AND e.date='"+date+"'";
		}
		Statement st = null;
		List<Event> listeEvent = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			listeEvent = new ArrayList<Event>();
			while(res.next()){
				Event event = getEvent(res);
				listeEvent.add(event);
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le getAllByUser", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du getAllbyUser", e);
			}
		}
		
		return listeEvent;
	}

	private Event getEvent(ResultSet res) {
		Event event = new Event();
		try {
			event.setIdEvent(res.getLong("idEvent"));
			event.setIntitule(res.getString("entitled"));
			event.setDate(res.getString("date"));
			event.setTypeEvent(res.getString("eventType"));
			event.setIdAdresse(res.getLong("idAdress"));
			event.setIdCreateur(res.getLong("idCreator"));
		} catch (SQLException e) {
			logger.error("Erreur dans le getEvent", e);
		}
		return event;
	}
	
	public long create(Event event) {
		long res = 0;
		StringBuilder query = new StringBuilder("insert into event(entitled, date, eventType, idAdress, idCreator) values ('");
		query.append(event.getIntitule()).append("', '").append(event.getDate()).append("', '");
		query.append(event.getTypeEvent()).append("', ").append(event.getIdAdresse()).append(", ");
		query.append(event.getIdCreateur() + ")");
		
		Statement st = null;
		try {
			PreparedStatement ps = ConnexionBD.getInstance().getConn().prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
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
		} catch (MySQLIntegrityConstraintViolationException e) {
			logger.error("Erreur dans le create event ", e);
			return -1;
		} catch (SQLException ex) {
			logger.error("Erreur dans le create event ", ex);
			return res;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du create event", e);
			}
		}
	}

	public int delete(long id) {
		int res = 0;
		String query = "delete from event where idEvent=" + id;
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch (SQLException ex) {
			logger.error("Erreur dans le delete", ex);
			return res;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally de delete event", e);
			}
		}
	}


	public int update(Event event) {
		int res = 0;
		String query = "update event set entitled='" + event.getIntitule()
				+ "', date='" + event.getDate() + "', eventType='"
				+ event.getTypeEvent() + "', idAdress="+event.getIdAdresse()+" where idEvent=" + event.getIdEvent();
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

}
