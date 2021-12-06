package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Voyage {
	
	
	
	public Voyage(int id, Date date, Time startTime, Time endTime, int roundId, String round, int driverId,
			String driver, int autobusId, String autobus) {
		super();
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roundId = roundId;
		this.round = round;
		this.driverId = driverId;
		this.driver = driver;
		this.autobusId = autobusId;
		this.autobus = autobus;
	}
	
	
	private int id;
	private Date date;
	private Time startTime;
	private Time endTime;
	
	private int roundId;
	private String round;
	
	private int driverId;
	private String driver;
	
	private int autobusId;
	private String autobus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	public int getRoundId() {
		return roundId;
	}
	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public int getAutobusId() {
		return autobusId;
	}
	public void setAutobusId(int autobusId) {
		this.autobusId = autobusId;
	}
	public String getAutobus() {
		return autobus;
	}
	public void setAutobus(String autobus) {
		this.autobus = autobus;
	}
	
	
	
}
