package CinemaSystem;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	public void alert_message(String header, String message, Stage stage){
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.initOwner(stage);
		alert.show();
	}
	
	public BorderPane purchasePane(Connection connection, Film film, Stage stage) throws Exception{
		BorderPane borderPane=new BorderPane();
		borderPane.setCenter(middlePane(connection, stage));
		
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(15, 12, 15, 12));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color:#336699;");
		Label label=new Label("Buy the tickets for "+film.getFilm_name());
		hBox.getChildren().add(label);
		borderPane.setTop(hBox);
		
		ScrollPane scrollPane=new ScrollPane();
		GridPane gridPane=new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(20);
		gridPane.setAlignment(Pos.CENTER);
		PreparedStatement preparedStatement=connection.prepareStatement("select * from timetable where time_film=?");
		preparedStatement.setString(1, film.getFilm_name());
		ResultSet resultSet=preparedStatement.executeQuery();
		
		Label clabel=new Label("Cinema");
		Label tlabel=new Label("Date");
		Label slabel=new Label("Seats Remain");
		Label pabel=new Label("Price");
		gridPane.add(clabel, 2, 0);
		gridPane.add(tlabel, 3, 0);
		gridPane.add(slabel, 4, 0);
		gridPane.add(pabel, 5, 0);
		
		int row=1;
		
		while(resultSet.next()){
			String date=resultSet.getString("time_value");
			String cinema_name=resultSet.getString("cinema");
			int remain_seats=resultSet.getInt("seats");
			int price=resultSet.getInt("time_film_price");
			Label label1=new Label(cinema_name);
			Label label2=new Label(date);
			Label label3=new Label(String.valueOf(remain_seats));
			Label label4=new Label(String.valueOf(price));
			gridPane.add(label1, 2, row);
			gridPane.add(label2, 3, row);
			gridPane.add(label3, 4, row);
			gridPane.add(label4, 5, row);
			
			Button buy_button=new Button("Buy");
			Button delete_button=new Button("Delete");
			gridPane.add(buy_button, 6, row);
			gridPane.add(delete_button, 7, row);
			if(!customer.getCustomer_account().equals("administrator"))
				delete_button.setVisible(false);
			buy_button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Ticket ticket=new Ticket(film.getFilm_name(), price, customer.getCustomer_account(), date, cinema_name);
					// TODO Auto-generated method stub
					try {
						if(customer.purchase(ticket, connection)){
							alert_message("Confirm", "Purchasing successfully", stage);
						}
						else {
							alert_message("Confirm", "There is no empty seat", stage);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			});
			
			row++;
		}
		scrollPane.setContent(gridPane);
		borderPane.setCenter(scrollPane);
		
		return borderPane;
	}
	
	public ScrollPane middlePane(Connection connection, Stage stage) throws Exception{
		GridPane gridPane=new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		ScrollPane scrollPane=new ScrollPane();
		
		ArrayList<Film> films=getAllFilm(connection);
		
		int row=0;
		for(Film film: films){
			InputStream graph=new FileInputStream("C:\\study\\javatest\\CinemaSystem\\src\\CinemaSystem\\"+film.getFilm_name()+".jpg");
			ImageView image=new ImageView(new Image(graph));
			gridPane.add(image, 0, row);
			Text text=new Text();
			text.setText(film.getFilm_abstract());
			gridPane.add(text, 1, row);
			Button button1=new Button("See comments");
			Button button2=new Button("Buy the tickets");
			Button button3=new Button("Delete");
			
			button2.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Stage purchase_stage=new Stage();
					try {
						Scene purchase_scene=new Scene(purchasePane(connection, film, purchase_stage), 500, 700);
						purchase_stage.setScene(purchase_scene);
						purchase_stage.initOwner(stage);
						purchase_stage.show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
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
	
	
	
	public HBox upPane(Stage preStage, Connection connection){
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(15, 12, 15, 12));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color:#336699;");
		Button button=new Button("Personal Info");
		button.setPrefSize(100, 20);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				CommentSystem commentSystem=new CommentSystem(preStage, connection, customer);
				Stage stage=new Stage();
				stage.initOwner(preStage);
				try {
					commentSystem.start(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		
		
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
		borderPane.setCenter(middlePane(connection, stage));
		borderPane.setTop(upPane(stage, connection));
		
		Scene scene=new Scene(borderPane, 1000, 1000);
		stage.setScene(scene);
		stage.show();
		
	}
/*
	public static void main(String[] args) {
		launch(args);
	}
*/
}
