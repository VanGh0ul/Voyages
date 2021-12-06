package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Trip {
	
	private static final String COL_ID = "id";
	private static final String COL_DATE = "trip_date";
	private static final String COL_START_TIME = "start_time";
	private static final String COL_END_TIME = "end_time";
	private static final String COL_ROUTE_ID = "route_id";
	private static final String COL_ROUTE = "route_name";
	private static final String COL_DRIVER_ID = "driver_id";
	private static final String COL_DRIVER = "driver_name";
	private static final String COL_AUTOBUS_ID = "autobus_id";
	private static final String COL_AUTOBUS = "autobus_name";

	private int id;
	private Date date;
	private Time startTime;
	private Time endTime;
	
	private int routeId;
	private String route;
	
	private int driverId;
	private String driver;
	
	private int autobusId;
	private String autobus;
	
	public static ObservableList<Trip> getTrips() throws Exception {
		
		ObservableList<Trip> result = FXCollections.observableArrayList();
		
		Connection conn = Utils.getConnection();
		
		try {
			
			ResultSet qResult = conn.createStatement().executeQuery(
				"select "
				+ "	t.id, "
				+ " t.trip_date, "
				+ " t.route_id, "
				+ " t.driver_id, "
				+ " t.autobus_id, "
				+ " t.start_time, "
				+ " t.end_time, "
				+ " r.name as route_name, "
				+ " concat_ws(' ', d.first_name, d.middle_name, d.last_name) as driver_name, "
				+ " concat_ws(' - ', a.brand, a.gov_id) as autobus_name "
				+ "from "
				+ "	trip t "
				+ "  inner join driver d on t.driver_id = d.id "
				+ "  inner join route r on t.route_id = r.id "
				+ "  inner join autobus a on t.autobus_id = a.id "
			);
			
			while (qResult.next()) {
				
				Trip trip = new Trip();
				
				trip.setId(qResult.getInt(COL_ID));
				trip.setDate(qResult.getDate(COL_DATE));
				trip.setRouteId(qResult.getInt(COL_ROUTE_ID));
				trip.setDriverId(qResult.getInt(COL_DRIVER_ID));
				trip.setAutobusId(qResult.getInt(COL_AUTOBUS_ID));
				trip.setStartTime(qResult.getTime(COL_START_TIME));
				trip.setEndTime(qResult.getTime(COL_END_TIME));
				trip.setRoute(qResult.getString(COL_ROUTE));
				trip.setDriver(qResult.getString(COL_DRIVER));
				trip.setAutobus(qResult.getString(COL_AUTOBUS));
				
				result.add(trip);
			}
			
		} finally {
			
			conn.close();
		}
		
		return result;
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
	
	public String getRoute() {
		return route;
	}
	
	public void setRoute(String route) {
		this.route = route;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getAutobus() {
		return autobus;
	}
	
	public void setAutobus(String autobus) {
		this.autobus = autobus;
	}
	
	public int getId() {
		return id;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public int getAutobusId() {
		return autobusId;
	}

	public void setAutobusId(int autobusId) {
		this.autobusId = autobusId;
	}
	
}
