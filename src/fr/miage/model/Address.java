package fr.miage.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Address {
	private long idAddress;
	private String place;
	private String location;
	private double latitude;
	private double longitude;
	/**
	 * @return the idAdress
	 */
	public long getIdAddress() {
		return idAddress;
	}
	/**
	 * @param idAdress the idAdress to set
	 */
	public void setIdAddress(long idAddress) {
		this.idAddress = idAddress;
	}
	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}
	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the lattitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param lattitude the lattitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}
