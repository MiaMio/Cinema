package CinemaSystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Cinema {
	private String cinema_name;
	private String location;
	public Cinema(String cinema_name, String location) {
		super();
		this.cinema_name = cinema_name;
		this.location = location;
	}
	public String getCinema_name() {
		return cinema_name;
	}
	public void setCinema_name(String cinema_name) {
		this.cinema_name = cinema_name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString(){
		return "Cinema [cinema_name=" + cinema_name + ", location=" + location + "]";
	}
	public void printAvalidFilm(Connection connection)throws Exception{
		System.out.println("The films that can be selected");
		Statement statement=connection.createStatement();
		ResultSet resultSet=statement.executeQuery("select * from film");
		while(resultSet.next()){
			int film_id=resultSet.getInt("film_id");
			String film_name=resultSet.getString("film_name");
			System.out.println(film_id+"  "+film_name);
		}
	}
}
