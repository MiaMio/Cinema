package CinemaSystem;

public class Ticket {
	private String ticket_film;
	private int ticket_value;
	private String ticket_customer;
	private String ticket_time;
	private String ticket_cinema;
	
	public Ticket(String ticket_film, int ticket_value, String ticket_customer, String ticket_time, String ticket_cinema) {
		super();
		this.ticket_film = ticket_film;
		this.ticket_value = ticket_value;
		this.ticket_customer = ticket_customer;
		this.ticket_time = ticket_time;
		this.ticket_cinema=ticket_cinema;
	}

	@Override
	public String toString() {
		return "Ticket [ticket_film=" + ticket_film + ", ticket_value=" + ticket_value + ", ticket_customer="
				+ ticket_customer + ", ticket_time=" + ticket_time + ", ticket_cinema=" + ticket_cinema + "]";
	}
	public String getTicket_film() {
		return ticket_film;
	}
	public void setTicket_film(String ticket_film) {
		this.ticket_film = ticket_film;
	}
	public int getTicket_value() {
		return ticket_value;
	}
	public void setTicket_value(int ticket_value) {
		this.ticket_value = ticket_value;
	}
	public String getTicket_customer() {
		return ticket_customer;
	}
	public void setTicket_customer(String ticket_customer) {
		this.ticket_customer = ticket_customer;
	}
	public String getTicket_time() {
		return ticket_time;
	}
	public void setTicket_time(String ticket_time) {
		this.ticket_time = ticket_time;
	}
	public String getTicket_cinema() {
		return ticket_cinema;
	}
	public void setTicket_cinema(String ticket_cinema) {
		this.ticket_cinema = ticket_cinema;
	}	
}
