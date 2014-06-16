package fr.miage.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class User {
	private String name;
	private String firstName;
	private String mail;
	private String pwd;
	private String phone;
	private long idHomeAddress;
	private long idCurrentAddress;
	private long idUser;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * @return the idHomeAddress
	 */
	public long getIdHomeAddress() {
		return idHomeAddress;
	}
	/**
	 * @param idHomeAddress the idHomeAddress to set
	 */
	public void setIdHomeAddress(long idHomeAddress) {
		this.idHomeAddress = idHomeAddress;
	}
	/**
	 * @return the idCurrentAddress
	 */
	public long getIdCurrentAddress() {
		return idCurrentAddress;
	}
	/**
	 * @param idCurrentAddress the idCurrentAddress to set
	 */
	public void setIdCurrentAddress(long idCurrentAddress) {
		this.idCurrentAddress = idCurrentAddress;
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
	
	
}
