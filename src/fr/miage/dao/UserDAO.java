package fr.miage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import fr.miage.connexion.ConnexionBD;
import fr.miage.model.User;

public class UserDAO extends AbstractDAO<User>{

	private static Logger logger = Logger.getRootLogger();

	public User find(long id) {
		ResultSet res = null;
		String query = "select * from user WHERE idUser=" + id;
		Statement st = null;
		User user = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			if (res.next()) {
				user = getUser(res);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le find user", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du find user", e);
			}
		}

		return user;
	}

	/**
	 * Recupere l'user grace a son login qui est son mail.
	 * 
	 * @param mail
	 *            login de l'user
	 * @return l'user
	 */
	public User getByMail(String mail) {
		ResultSet res = null;
		String query = "select * from user WHERE mail='" + mail + "'";
		Statement st = null;
		User user = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			if (res.next()) {
				user = getUser(res);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le getByMail user", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du getByMail user", e);
			}
		}

		return user;
	}

	private User getUser(ResultSet res) {
		User user = new User();
		try {
			user.setIdUser(res.getLong("idUser"));
			user.setFirstName(res.getString("firstName"));
			user.setIdHomeAddress(res.getLong("idHomeAddress"));
			user.setIdCurrentAddress(res.getLong("idCurrentAddress"));
			user.setMail(res.getString("mail"));
			user.setName(res.getString("name"));
			user.setPhone(res.getString("phone"));
			user.setPwd(res.getString("pwd"));
		} catch (SQLException e) {
			logger.error("Erreur dans le getUser", e);
		}
		return user;
	}

	public long create(User user) {
		long res = 0;
		// TODO : Create and handle exception
		// user.setPwd(Cryptography.getInstance().encryptWithAES(user.getPwd()));

		String query = "insert into user(mail, pwd) values ('" + user.getMail()
				+ "', '" + user.getPwd() + "')";
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch (MySQLIntegrityConstraintViolationException e) {
			logger.error("Erreur dans le create user", e);
			return -1;
		} catch (SQLException ex) {
			logger.error("Erreur dans le create user", ex);
			return res;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du create user", e);
			}
		}
	}

	public int delete(long id) {
		int res = 0;
		String query = "delete from user where idUser=" + id;
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query);
			return res;
		} catch (SQLException ex) {
			logger.error("Erreur dans le delete user", ex);
			return res;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("erreur dans le finally du delete user", e);
			}
		}
	}

	public int update(User user) {
		int res = 0;
		StringBuilder query = new StringBuilder();
		query.append("update user set name='").append( user.getName()).append("', firstName='");
		query.append(user.getFirstName()).append("', phone='").append(user.getPhone()).append("', idHomeAddress=");
		query.append(user.getIdHomeAddress()).append(", idCurrentAddress=").append(user.getIdCurrentAddress());
		
		if(user.getPwd() != null){
			query.append(", pwd='").append(user.getPwd()).append("'");
		}
		query.append(" where idUser=").append(user.getIdUser());
		Statement st = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeUpdate(query.toString());
			return res;
		} catch (SQLException ex) {
			logger.error("Erreur dans l'update user", ex);
			return res;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du update user", e);
			}
		}
	}

	public List<User> getAllUserByEvent(long idEvent) {
		ResultSet res = null;
		String query = "select * from invitation i inner join user u on i.idUser = u.idUser WHERE idEvent="
				+ idEvent;
		Statement st = null;
		List<User> listeUser = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query);
			listeUser = new ArrayList<User>();
			while (res.next()) {
				User user = getUser(res);
				user.setMail(null);
				user.setPwd(null);
				listeUser.add(user);
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le getAlluserByEvent", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du getAllUserByevent", e);
			}
		}

		return listeUser;
	}

	public List<User> getByMultiCriteria(String name, String firstName,
			String phone) {
		ResultSet res = null;
		StringBuilder query = new StringBuilder();
		query.append("select * from user WHERE ");
		List<String> listeCriteres = new ArrayList<String>();
		if (name != null) {
			listeCriteres.add("name='" + name + "'");
		}
		if (firstName != null) {
			listeCriteres.add("firstName='" + firstName + "'");
		}
		if (phone != null) {
			listeCriteres.add("phone='" + phone + "'");
		}

		if (!listeCriteres.isEmpty()) {
			query.append(listeCriteres.get(0));
			for (int i = 1; i < listeCriteres.size(); i++) {
				query.append("AND ").append(listeCriteres.get(i));
			}

			Statement st = null;
			List<User> listeUser = new ArrayList<User>();
			User user = null;
			try {
				st = ConnexionBD.getInstance().getConn().createStatement();
				res = st.executeQuery(query.toString());
				if (res.next()) {
					user = getUser(res);
					user.setIdCurrentAddress(0);
					user.setIdHomeAddress(0);
					user.setMail(null);
					user.setPwd(null);
					listeUser.add(user);
				} else {
					return null;
				}
			} catch (SQLException ex) {
				logger.error("Erreur dans le getByMultiCriteria user", ex);
			} finally {
				try {
					if (res != null) {
						res.close();
					}
					if (st != null) {
						st.close();
					}
				} catch (SQLException e) {
					logger.error("Erreur dans le finally du getByMultiCriteria user", e);
				}
			}

			return listeUser;

		}

		return null;
	}

	public List<User> getByUnknownCriteria(String unknown) {
		ResultSet res = null;
		StringBuilder query = new StringBuilder();
		query.append("select * from user WHERE name='" + unknown
				+ "' OR firstName='" + unknown + "' OR phone='" + unknown + "'");

		Statement st = null;
		List<User> listeUser = new ArrayList<User>();
		User user = null;
		try {
			st = ConnexionBD.getInstance().getConn().createStatement();
			res = st.executeQuery(query.toString());
			if (res.next()) {
				user = getUser(res);
				user.setIdCurrentAddress(0);
				user.setIdHomeAddress(0);
				user.setMail(null);
				user.setPwd(null);
				listeUser.add(user);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			logger.error("Erreur dans le getByUnknownCriteria", ex);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				logger.error("Erreur dans le finally du getByUnknownCriteria", e);
			}
		}

		return listeUser;

	}
}
