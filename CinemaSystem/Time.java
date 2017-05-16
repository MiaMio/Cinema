package CinemaSystem;

public class Time {
	private String time_value;
	private String time_film;
	private int seats;
	private Cinema cinema;
	private int time_film_price;
	public Time(String time_value, String time_film, int seats, Cinema cinema, int time_film_price) {
		super();
		this.time_value = time_value;
		this.time_film = time_film;
		this.seats = seats;
		this.cinema = cinema;
		this.time_film_price=time_film_price;
	}
	public String getTime_value() {
		return time_value;
	}
	public int getTime_film_price() {
		return time_film_price;
	}
	public void setTime_film_price(int time_film_price) {
		this.time_film_price = time_film_price;
	}
	public void setTime_value(String time_value) {
		this.time_value = time_value;
	}
	public String getTime_film() {
		return time_film;
	}
	public void setTime_film(String time_film) {
		this.time_film = time_film;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public Cinema getCinema() {
		return cinema;
	}
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	@Override
	public String toString() {
		return "Time [time_value=" + time_value + ", time_film=" + time_film + ", seats=" + seats
				+ ", cinema=" + cinema + "]";
	}
	
}
