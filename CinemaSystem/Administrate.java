package CinemaSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Administrate {
	public static void delete(String film_name, Connection connection)throws Exception{
		PreparedStatement preparedStatement=connection.prepareStatement("delete from film where film_name=?");
		preparedStatement.setString(1, film_name);
		preparedStatement.executeUpdate();
	}
	public static void add(Film film, Connection connection)throws Exception{
		PreparedStatement preparedStatement=connection.prepareStatement("insert into film(film_name, film_abstract)"
				+ "values(?, ?)");
		preparedStatement.setString(1, film.getFilm_name());
		preparedStatement.setString(2, film.getFilm_abstract());
		preparedStatement.executeUpdate();
	}
	public static BorderPane add_pane(Connection connection, Stage stage){
		BorderPane borderPane=new BorderPane();
		TextArea textArea=new TextArea();
		borderPane.setCenter(textArea);
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(15, 12, 15, 12));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color:#336699;");
		Label label=new Label("Film Name");
		hBox.getChildren().add(label);
		TextField textField=new TextField();
		hBox.getChildren().add(textField);
		
		
		Button button=new Button("Confirm and add");
		
		HBox hBox2=new HBox();
		hBox2.setAlignment(Pos.CENTER);
		hBox2.getChildren().add(button);
		borderPane.setTop(hBox);
		borderPane.setBottom(hBox2);
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				String film_name=textField.getText();
				String film_abstract=textArea.getText();
				Film film=new Film(film_name, film_abstract);
				try {
					add(film, connection);
					stage.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return borderPane;
	}
}
