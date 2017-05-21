package CinemaSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CommentSystem extends Application{
	private Stage preStage;
	private Connection connection;
	private Customer customer;
	public CommentSystem(Stage preStage, Connection connection, Customer customer) {
		this.connection=connection;
		this.preStage=preStage;
		this.customer=customer;
	}
	public HBox upPane(){
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(15, 12, 15, 12));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color:#336699;");
		Label label=new Label("Films have seen");
		
		hBox.getChildren().add(label);
		return hBox;
	}
	
	public BorderPane commentPane(Connection connection, String film_name){
		BorderPane borderPane=new BorderPane();
		TextArea textArea=new TextArea();
		borderPane.setCenter(textArea);
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(15, 12, 15, 12));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color:#336699;");
		Label label=new Label("Select your score");
		hBox.getChildren().add(label);
		ChoiceBox<Integer> choiceBox=new ChoiceBox<Integer>(FXCollections.observableArrayList(1, 2, 3, 4, 5));
		hBox.getChildren().add(choiceBox);
		borderPane.setTop(hBox);
		
		Button button=new Button("Confirm and Comment");
		button.setAlignment(Pos.CENTER);
		borderPane.setBottom(button);
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				String comment=textArea.getText();
				int score=choiceBox.getValue();
				try {
					customer.comment(connection, comment, film_name, score);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		
		
		return borderPane;
	}
	
	public BorderPane Info(Stage nowStage, Connection connection) throws Exception{
		BorderPane borderPane=new BorderPane();
		borderPane.setTop(upPane());
		GridPane gridPane=new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(20);
		PreparedStatement preparedStatement=connection.prepareStatement("select * from ticket where ticket_customer=?");
		preparedStatement.setString(1, customer.getCustomer_account());
		ResultSet resultSet=preparedStatement.executeQuery();
		Label fLabel=new Label("Film");
		Label cLabel=new Label("Cinema");
		Label tLabel=new Label("Date");
		gridPane.add(fLabel, 2, 0);
		gridPane.add(cLabel, 3, 0);
		gridPane.add(tLabel, 4, 0);
		int row=1;
		while(resultSet.next()){
			String film=resultSet.getString("ticket_film");
			String cinema=resultSet.getString("ticket_film");
			String date=resultSet.getString("ticket_time");
			Label label1=new Label(film);
			Label label2=new Label(cinema);
			Label label3=new Label(date);
			gridPane.add(label1, 2, row);
			gridPane.add(label2, 3, row);
			gridPane.add(label3, 4, row);
			
			Button button=new Button("Comment");
			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Stage commentStage=new Stage();
					Scene commentScene=new Scene(commentPane(connection, film), 500, 500);
					commentStage.setScene(commentScene);
					commentStage.initOwner(nowStage);
					commentStage.show();
				}
			});
			gridPane.add(button, 5, row);
			row++;
		}
		borderPane.setCenter(gridPane);
		return borderPane;
	}
	
	
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		Scene scene=new Scene(Info(primaryStage, connection), 700, 400);
		primaryStage.setScene(scene);
		primaryStage.initOwner(preStage);
		primaryStage.show();
	}
}
