package CinemaSystem;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PurchaseSystem extends Application {
	private Customer customer;
	public void setCustomer(Customer customer){
		this.customer=customer;
	}
	public ArrayList<Film> getAllFilm(Connection connection) throws Exception{
		ArrayList<Film> films=new ArrayList<Film>();
		Statement statement=connection.createStatement();
		String command="select * from film";
		ResultSet resultSet=statement.executeQuery(command);
		while(resultSet.next())
			films.add(new Film(resultSet.getString("film_name"), resultSet.getString("film_abstract")));
		
		return films;
	}
	
	public ScrollPane middlePane(Connection connection) throws Exception{
		GridPane gridPane=new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		ScrollPane scrollPane=new ScrollPane();
		
		ArrayList<Film> films=getAllFilm(connection);
		
		int row=0;
		for(Film film: films){
			InputStream graph=new FileInputStream("D:\\javatest\\Cinema\\src\\CinemaSystem\\"+film.getFilm_name()+".jpg");
			ImageView image=new ImageView(new Image(graph));
			gridPane.add(image, 0, row);
			Text text=new Text();
			text.setText(film.getFilm_abstract());
			gridPane.add(text, 1, row);
			Button button1=new Button("See comments");
			Button button2=new Button("Buy the tickets");
			Button button3=new Button("Delete");
			gridPane.add(button1, 2, row);
			gridPane.add(button2, 3, row);
			gridPane.add(button3, 4, row);
			if(!customer.getCustomer_account().equals("administrator"))
				button3.setVisible(false);
			row++;
		}
		
//		scrollPane.setVmax(440);
//		scrollPane.setPrefSize(115, 150);
		scrollPane.setContent(gridPane);
		return scrollPane;
	}
	
	
	
	public HBox upPane(){
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(15, 12, 15, 12));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color:#336699;");
		Button button=new Button("Personal Info");
		button.setPrefSize(100, 20);
		
		
		
		hBox.getChildren().add(button);
		return hBox;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Connection connection;
		Class.forName("com.mysql.jdbc.Driver");
		connection=DriverManager.getConnection("jdbc:MySql:///cinema", "root", "pptzz25228");
		// TODO Auto-generated method stub
		BorderPane borderPane=new BorderPane();
		borderPane.setCenter(middlePane(connection));
		borderPane.setTop(upPane());
		
		Scene scene=new Scene(borderPane, 1000, 1000);
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
