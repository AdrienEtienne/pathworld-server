package fr.miage.dao;

import java.sql.PreparedStatement;
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
import fr.miage.model.Tips;

public class TipsDAO extends AbstractDAO<Tips> {

	private static Logger logger = Logger.getRootLogger();
	
	@Override
	public int delete(long id) {
		int res = 0;
		String query = "delete from tips where idTips=" + id;
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch (SQLException ex) {
			logger.error("Erreur dans le delete tips", ex);
			return res;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du delete tips", e);
			}
		}
	}

	@Override
	public int update(Tips tips) {
		int res = 0;
		String query = "update tips set titre='" + tips.getTitre()
				+ "', details='" + tips.getDetails() + "' where idTips=" + tips.getIdTips();
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch (SQLException ex) {
			logger.error("Erreur dans le update tips", ex);
			return -1;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du update tips", e);
			}
		}
	}

	@Override
	public long create(Tips tips) {
		long res = 0;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String query = "insert into tips(idUser, idAdresse, idEvent, titre, details, date) values ("+tips.getIdCreateur()+", "+tips.getIdAdresse()+", "+tips.getIdEvent()+", '"+tips.getTitre()+"', '"+tips.getDetails()+"', '"+format.format(date)+"')";
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
		} catch (MySQLIntegrityConstraintViolationException e) {
			logger.error("Erreur dans le create tips", e);
			return -1;
		} catch (SQLException ex) {
			logger.error("Erreur dans le create tips", ex);
			return res;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du create tips", e);
			}
		}
	}

	@Override
	public Tips find(long id) {
		ResultSet res = null;
		String query = "select * from tips WHERE idTips=" + id;
		Statement st = null;
		Tips tips = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			if (res.next()) {
				tips = getTips(res);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le find tips", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du find tips", e);
			}
		}

		return tips;
	}
	
	public List<Tips> findByCriteria(long idEvent){
		ResultSet res = null;
		String query = "select * from tips WHERE idEvent=" + idEvent;
		Statement st = null;
		List<Tips> listeTips = new ArrayList<Tips>();
		
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			while(res.next()){
				Tips tips = getTips(res);
				listeTips.add(tips);
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le findbycriteria de tips", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du findbycriteria de tips", e);
			}
		}

		return listeTips;
	}

	private Tips getTips(ResultSet res) {
		Tips tips = new Tips();
		try {
			tips.setIdTips(res.getLong("idTips"));
			tips.setIdCreateur(res.getLong("idUser"));
			tips.setIdAdresse(res.getLong("idAdresse"));
			tips.setIdEvent(res.getLong("idEvent"));
			tips.setTitre(res.getString("titre"));
			tips.setDetails(res.getString("details"));
			tips.setDate(res.getString("date"));
		} catch (SQLException e) {
			logger.error("Erreur dans le getTips", e);
		}
		return tips;
	}

}
