package CinemaSystem;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginSystem extends Application {
	public void alert_message(String header, String message, Stage stage){
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Notify");
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.initOwner(stage);
		alert.show();
	}
	public GridPane new_pane(){
		GridPane gridPane=new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
//		gridPane.setPadding(new Insets(10, 10, 10, 10));
		Text sceneTitle=new Text("welcome");
		gridPane.add(sceneTitle, 0, 0);
		return gridPane;
	}
	public void start(Stage primaryStage)throws Exception{
		Connection connection;
		Class.forName("com.mysql.jdbc.Driver");
		connection=DriverManager.getConnection("jdbc:MySql:///cinema", "root", "pptzz25228");
		primaryStage.setTitle("Cinema System");
		GridPane gridPane=new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
//		gridPane.setPadding(new Insets(10, 10, 10, 10));
		Text sceneTitle=new Text("welcome");
		gridPane.add(sceneTitle, 0, 0);
		Label account=new Label("account: ");
		gridPane.add(account, 0, 1);
		TextField textField=new TextField();
		gridPane.add(textField, 1, 1);
		Label password=new Label("Password: ");
		gridPane.add(password, 0, 2);
		PasswordField passwordField=new PasswordField();
		gridPane.add(passwordField, 1, 2);
		Button button=new Button("Sign in");
//		HBox hBox=new HBox();
//		hBox.setAlignment(Pos.BOTTOM_RIGHT);
//		hBox.getChildren().add(button);
		gridPane.add(button, 1, 4);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0){
				String name=textField.getText();
				String password=passwordField.getText();
				Customer customer=new Customer(name, password);
//				System.out.println(customer.getCustomer_pwd());

				try {
					if(customer.login(connection)){
						System.out.println("Login success");
						Stage stage=new Stage();
						PurchaseSystem purchaseSystem=new PurchaseSystem();
						primaryStage.close();
						purchaseSystem.setCustomer(customer);
						purchaseSystem.start(stage);
					}
					else {
						Alert alert=new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Login fail");
						alert.setHeaderText("Login fail");
						alert.setContentText("Wrong account or wrong password");
						alert.initOwner(primaryStage);
						alert.show();
						System.out.println("Login fail");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// TODO Auto-generated method stub
//				alert_message("Fail to sign in", "wrong password or account", primaryStage);
//				primaryStage.close();
			}
		});
		
		Scene scene=new Scene(gridPane, 300, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args){
		launch(args);
	}
}
