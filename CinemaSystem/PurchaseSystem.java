package CinemaSystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class PurchaseSystem {
	public static void main(String[] args) throws Exception {
		Customer customer=new Customer("jack", "jack123");
		Connection connection;
		
		
		Class.forName("com.mysql.jdbc.Driver");
		connection=DriverManager.getConnection("jdbc:MySql:///cinema", "root", "pptzz25228");
/*		Ticket ticket=new Ticket("avatar", 50, "jack", "2017-5-15", "yonghua");
		customer.purchase(ticket, connection);
*/
		
/*		customer.signup(connection);
		Customer customer2=new Customer("jack", "jack1234");
		customer2.login(connection);
*/
/*		Film avatar=new Film("avatar", "this is a suck film");
		avatar.printInfo(connection);
*/
		Cinema cinema=new Cinema("yonghua", "xuhui");
		cinema.printAvalidFilm(connection);
	}
}
