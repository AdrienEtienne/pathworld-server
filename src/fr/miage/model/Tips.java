package fr.miage.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class Tips {
	private String titre;
	private String details;
	private long idTips;
	private long idAdresse;
	private long idCreateur;
	private long idEvent;
	private String date;
	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}
	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}
	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * @return the idTips
	 */
	public long getIdTips() {
		return idTips;
	}
	/**
	 * @param idTips the idTips to set
	 */
	public void setIdTips(long idTips) {
		this.idTips = idTips;
	}
	/**
	 * @return the idAdresse
	 */
	public long getIdAdresse() {
		return idAdresse;
	}
	/**
	 * @param idAdresse the idAdresse to set
	 */
	public void setIdAdresse(long idAdresse) {
		this.idAdresse = idAdresse;
	}
	/**
	 * @return the idCreateur
	 */
	public long getIdCreateur() {
		return idCreateur;
	}
	/**
	 * @param idCreateur the idCreateur to set
	 */
	public void setIdCreateur(long idCreateur) {
		this.idCreateur = idCreateur;
	}
	/**
	 * @return the idEvent
	 */
	public long getIdEvent() {
		return idEvent;
	}
	/**
	 * @param idEvent the idEvent to set
	 */
	public void setIdEvent(long idEvent) {
		this.idEvent = idEvent;
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
	
}
