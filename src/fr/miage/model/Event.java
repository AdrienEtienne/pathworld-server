package fr.miage.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class Event {
	private String intitule;
	private String date;
	private String typeEvent;
	private long idAdresse;
	private long idCreateur;
	private long idEvent;
	/**
	 * @return the intitule
	 */
	public String getIntitule() {
		return intitule;
	}
	/**
	 * @param intitule the intitule to set
	 */
	public void setIntitule(String intitule) {
		this.intitule = intitule;
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
	 * @return the typeEvent
	 */
	public String getTypeEvent() {
		return typeEvent;
	}
	/**
	 * @param typeEvent the typeEvent to set
	 */
	public void setTypeEvent(String typeEvent) {
		this.typeEvent = typeEvent;
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
	
}
