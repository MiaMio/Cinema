package CinemaSystem;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class Customer {
	private String customer_account;
	private String customer_pwd;
	public Customer(String customer_account, String customer_pwd) {
		super();
		this.customer_account = customer_account;
		this.customer_pwd = customer_pwd;
	}
	@Override
	public String toString() {
		return "Customer [customer_account=" + customer_account + ", customer_pwd=" + customer_pwd + "]";
	}
	public String getCustomer_account() {
		return customer_account;
	}
	public void setCustomer_account(String customer_account) {
		this.customer_account = customer_account;
	}
	public String getCustomer_pwd() {
		return customer_pwd;
	}
	public void setCustomer_pwd(String customer_pwd) {
		this.customer_pwd = customer_pwd;
	}
	public boolean purchase(Ticket ticket, Connection connection) throws SQLException{
		PreparedStatement preparedStatement=connection.prepareStatement("select seats from timetable where time_value=? and time_film=? and cinema=?");
		preparedStatement.setString(1, ticket.getTicket_time());
		preparedStatement.setString(2, ticket.getTicket_film());
		preparedStatement.setString(3, ticket.getTicket_cinema());
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next() && resultSet.getInt("seats")>0){
			PreparedStatement preparedStatement2=connection.prepareStatement("insert into ticket(ticket_film, ticket_value, ticket_customer, ticket_time, ticket_cinema) values(?, ?, ?, ?, ?)");
			preparedStatement2.setString(1, ticket.getTicket_film());
			preparedStatement2.setInt(2, ticket.getTicket_value());
			preparedStatement2.setString(3, ticket.getTicket_customer());
			preparedStatement2.setString(4, ticket.getTicket_time());
			preparedStatement2.setString(5, ticket.getTicket_cinema());
			preparedStatement2.executeUpdate();
			System.out.println("purchase success");
			int new_seats=resultSet.getInt("seats")-1;
			PreparedStatement preparedStatement3=connection.prepareStatement("update timetable set seats=? where time_value=? and time_film=? and cinema=?");
			preparedStatement3.setInt(1, new_seats);
			preparedStatement3.setString(2, ticket.getTicket_time());
			preparedStatement3.setString(3, ticket.getTicket_film());
			preparedStatement3.setString(4, ticket.getTicket_cinema());
			preparedStatement3.executeUpdate();
			return true;
		}
		else{
			System.out.println("purchase fail");
			return false;
		}
	}
	public boolean signup(Connection connection)throws Exception{
		PreparedStatement preparedStatement=connection.prepareStatement("select id from customer where customer_account=?");
		preparedStatement.setString(1, customer_account);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()){
			System.out.println("Account exists!");
			return false;
		}
		PreparedStatement preparedStatement2=connection.prepareStatement("insert into customer(customer_account, customer_pwd) values(?, ?)");
		preparedStatement2.setString(1, customer_account);
		preparedStatement2.setString(2, customer_pwd);
		preparedStatement2.executeUpdate();
		System.out.println("sign up success");
		return true;
	}
	
	public boolean login(Connection connection)throws Exception{
		PreparedStatement preparedStatement=connection.prepareStatement("select * from customer where customer_account=? and customer_pwd=?");
		preparedStatement.setString(1, customer_account);
		preparedStatement.setString(2, customer_pwd);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()){
			System.out.println("Login success!");
			return true;
		}
		System.out.println("Login fail for wrong account or wrong password.");
		return false;
	}
	
	public void comment(Connection connection, String comment, String film_name, int score)throws Exception{
		Date date=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String now_date=simpleDateFormat.format(date);
		PreparedStatement preparedStatement=connection.prepareStatement("insert into comments(comment_film, comment_customer, comment_content, comment_time, comment_score)"
				+ "values(?, ?, ?, ?, ?)");
		preparedStatement.setString(1, film_name);
		preparedStatement.setString(2, customer_account);
		preparedStatement.setString(3, comment);
		preparedStatement.setString(4, now_date);
		preparedStatement.setInt(5, score);
		preparedStatement.executeUpdate();
		System.out.println("comment success");
		
	}
}
