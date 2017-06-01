package CinemaSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CommentSystem extends Application{
	private Stage preStage;
	private Connection connection;
	private Customer customer;
	private Button refresh;
	
	
	public CommentSystem(Stage preStage, Connection connection, Customer customer) {
		this.connection=connection;
		this.preStage=preStage;
		this.customer=customer;
	}
	
	public void alert_message(String header, String message, Stage stage){
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.initOwner(stage);
		alert.show();
	}
	public HBox upPane(){
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(15, 12, 15, 12));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color:#336699;");
		Label label=new Label("Films have seen");
/*		
		Button button=new Button("Top up");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Stage stage=new Stage();
				try {
					BorderPane borderPane=topup_pane(connection, stage);
					Scene scene=new Scene(borderPane);
					stage.setScene(scene);
					stage.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
*/		refresh=new Button("refresh");
		hBox.getChildren().add(label);
		hBox.getChildren().add(refresh);
//		hBox.getChildren().add(button);
		return hBox;
	}
	
	public BorderPane topup_pane(Connection connection, Stage nowStage)throws Exception{
		BorderPane borderPane=new BorderPane();
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(12, 15, 12, 15));
		Label label=new Label("Amount: ");
		TextField textField=new TextField();
		hBox.getChildren().add(label);
		hBox.getChildren().add(textField);
		borderPane.setCenter(hBox);
		
		HBox hBox2=new HBox();
		Button button=new Button("Confirm");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				int balance=Integer.parseInt(textField.getText());
				try {
					customer.topup(balance, connection);
					nowStage.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		hBox2.setAlignment(Pos.CENTER);
		hBox2.getChildren().add(button);
		borderPane.setBottom(hBox2);
		return borderPane;
	}
	
	public BorderPane commentPane(Connection connection, String film_name, Stage stage){
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
					comment=StringHelper.cut(comment, 70);
					customer.comment(connection, comment, film_name, score);
					stage.close();
					alert_message("Confirm", "Comment success", stage);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return borderPane;
	}
	
	public VBox vBox_of_infoPane(Stage nowStage, Connection connection)throws Exception{
		VBox vBox=new VBox();
		vBox.setPadding(new Insets(12, 15, 12, 15));
		vBox.setSpacing(20);
		PreparedStatement preparedStatement=connection.prepareStatement("select * from ticket where ticket_customer=?");
		preparedStatement.setString(1, customer.getCustomer_account());
		ResultSet resultSet=preparedStatement.executeQuery();
		Label fLabel=new Label("Film");
		Label cLabel=new Label("Cinema");
		Label tLabel=new Label("Date");
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(12, 15, 12, 15));
		hBox.setSpacing(20);
		hBox.getChildren().add(fLabel);
		hBox.getChildren().add(cLabel);
		hBox.getChildren().add(tLabel);
		vBox.getChildren().add(hBox);
		
		while(resultSet.next()){
			String film=resultSet.getString("ticket_film");
			String cinema=resultSet.getString("ticket_cinema");
			String date=resultSet.getString("ticket_time");
			int value=resultSet.getInt("ticket_value");
			String customer_name=resultSet.getString("ticket_customer");
			Label label1=new Label(film);
			Label label2=new Label(cinema);
			Label label3=new Label(date);
			HBox hBox2=new HBox();
			hBox2.setPadding(new Insets(12, 15, 12, 15));
			hBox2.setSpacing(20);
			hBox2.getChildren().add(label1);
			hBox2.getChildren().add(label2);
			hBox2.getChildren().add(label3);
			
			Button button=new Button("Comment");
			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Stage commentStage=new Stage();
					Scene commentScene=new Scene(commentPane(connection, film, commentStage), 500, 500);
					commentStage.setScene(commentScene);
					commentStage.initOwner(nowStage);
					commentStage.show();
				}
			});
			hBox2.getChildren().add(button);
			Button button2=new Button("refund");
			button2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Ticket ticket=new Ticket(film, value, customer_name, date, cinema);
					try {
						if(customer.refund(connection, ticket)){
							vBox.getChildren().removeAll(hBox2);
						}
						else{
							alert_message("Refund Fail", "Fail to refund due to time exceeding", nowStage);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			hBox2.getChildren().add(button2);
			vBox.getChildren().add(hBox2);
		}
		return vBox;
	}
	
	public BorderPane Info(Stage nowStage, Connection connection) throws Exception{
		BorderPane borderPane=new BorderPane();
		borderPane.setTop(upPane());
/*		GridPane gridPane=new GridPane();
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
			String cinema=resultSet.getString("ticket_cinema");
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
					Scene commentScene=new Scene(commentPane(connection, film, commentStage), 500, 500);
					commentStage.setScene(commentScene);
					commentStage.initOwner(nowStage);
					commentStage.show();
				}
			});
			gridPane.add(button, 5, row);
			row++;
		}
	*/
		ScrollPane scrollPane=new ScrollPane();
		scrollPane.setContent(vBox_of_infoPane(nowStage, connection));
		borderPane.setCenter(scrollPane);
		Button button=new Button("Change password");
		HBox hBox=new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().add(button);
		borderPane.setBottom(hBox);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				BorderPane borderPane=change_password_pane(nowStage);
				Scene scene=new Scene(borderPane, 400, 200);
				Stage stage=new Stage();
				stage.initOwner(nowStage);
				stage.setScene(scene);
				stage.show();
			}
		});
		Button button2=new Button("Top up");
		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Stage stage=new Stage();
				try {
					BorderPane borderPane=topup_pane(connection, stage);
					Scene scene=new Scene(borderPane);
					stage.setScene(scene);
					stage.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		hBox.getChildren().add(button2);
		Label label=new Label("Balance: ");
		PreparedStatement preparedStatement=connection.prepareStatement("select customer_balance from customer where customer_account=?");
		preparedStatement.setString(1, customer.getCustomer_account());
		ResultSet resultSet=preparedStatement.executeQuery();
		int balance=0;
		if(resultSet.next()){
			balance=resultSet.getInt("customer_balance");
		}
		Label label2=new Label(balance+"");
		hBox.getChildren().add(label);
		hBox.getChildren().add(label2);

		return borderPane;
	}
	
	public BorderPane change_password_pane(Stage stage){
		BorderPane borderPane=new BorderPane();
		Label label=new Label("Old Password: ");
		Label label2=new Label("New Password: ");
		TextField textField=new TextField();
		TextField textField2=new TextField();
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(12, 15, 12, 15));
		hBox.getChildren().add(label);
		hBox.getChildren().add(textField);
		HBox hBox2=new HBox();
		hBox2.setPadding(new Insets(12, 15, 12, 15));
		hBox2.getChildren().add(label2);
		hBox2.getChildren().add(textField2);
		VBox vBox=new VBox();
		vBox.setPadding(new Insets(12, 15, 12, 15));
		vBox.setSpacing(20);
		vBox.getChildren().add(hBox);
		vBox.getChildren().add(hBox2);
		borderPane.setCenter(vBox);
		
		Button button=new Button("Confirm");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				String new_password=textField2.getText();
				String old_password=textField.getText();
				if(old_password.equals(customer.getCustomer_pwd())){
					try {
						customer.setCustomer_pwd(new_password, connection);
						stage.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					alert_message("Fail to change password", "Wrong old password", stage);
				}
			}
		});
		HBox hBox3=new HBox();
		hBox3.setAlignment(Pos.CENTER);
		hBox3.getChildren().add(button);
		borderPane.setBottom(hBox3);
		return borderPane;
	}
	
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		Scene scene=new Scene(Info(primaryStage, connection), 700, 400);
		primaryStage.setScene(scene);
		primaryStage.initOwner(preStage);
		primaryStage.show();
		refresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				primaryStage.close();
				Scene scene;
				try {
					scene = new Scene(Info(primaryStage, connection), 700, 400);
					primaryStage.setScene(scene);
//					primaryStage.initOwner(preStage);
					primaryStage.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
}
