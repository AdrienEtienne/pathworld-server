package fr.miage.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class Invitation {
	private long idInvitation;
	private long idEvent;
	private long idUser;
	private int response;
	private String role;
	private String date;
	private int geolocalization;
	/**
	 * @return the idEvent
	 */
	public long getIdEvent() {
		return idEvent;
	}
	/**
	 * @param l the idEvent to set
	 */
	public void setIdEvent(long l) {
		this.idEvent = l;
	}
	/**
	 * @return the idUser
	 */
	public long getIdUser() {
		return idUser;
	}
	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	/**
	 * @return the response
	 */
	public int getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(int response) {
		this.response = response;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the idInvitation
	 */
	public long getIdInvitation() {
		return idInvitation;
	}
	/**
	 * @param idInvitation the idInvitation to set
	 */
	public void setIdInvitation(long idInvitation) {
		this.idInvitation = idInvitation;
	}
	/**
	 * @return the geolocalization
	 */
	public int getGeolocalization() {
		return geolocalization;
	}
	/**
	 * @param geolocalization the geolocalization to set
	 */
	public void setGeolocalization(int geolocalization) {
		this.geolocalization = geolocalization;
	}
	
	
}
