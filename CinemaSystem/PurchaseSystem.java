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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PurchaseSystem extends Application {
	private Customer customer;
	private Button refresh_purchase_system=new Button("refresh");
	private Button refresh_info_pane=new Button("refresh");
	private Button purchase_add_button=new Button("add");
	public void setCustomer(Customer customer){
		this.customer=customer;
	}
	public BorderPane infoPane(Connection connection, String film_name) throws SQLException{
		BorderPane borderPane=new BorderPane();
		ScrollPane scrollPane=new ScrollPane();
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(15, 12, 15, 12));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color:#336699;");
		Label label=new Label("Comments of "+film_name);
		
		hBox.getChildren().add(label);
		borderPane.setTop(hBox);
		
		GridPane gridPane=new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(20);
		PreparedStatement preparedStatement=connection.prepareStatement("select * from comments where comment_film=?");
		preparedStatement.setString(1, film_name);
		ResultSet resultSet=preparedStatement.executeQuery();
		int row=1;
		while(resultSet.next()){
			int score=resultSet.getInt("comment_score");
			String customer_name=resultSet.getString("comment_customer");
			String time=resultSet.getString("comment_time");
			String content=resultSet.getString("comment_content");
			String info=customer_name+" "+time+" Score: "+score;
			Text text=new Text(info);
			Text text2=new Text(content);
			gridPane.add(text, 2, row++);
			gridPane.add(text2, 2, row++);
			row++;
		}
		scrollPane.setContent(gridPane);
		borderPane.setCenter(scrollPane);
		
		return borderPane;
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
	
	
	public VBox middle_of_purchasePane(Connection connection, Film film, Stage stage) throws Exception{
		VBox vBox=new VBox();
		vBox.setPadding(new Insets(12, 15, 12, 15));
		vBox.setSpacing(20);
		HBox hBox=new HBox();
		hBox.setSpacing(40);
		hBox.setPadding(new Insets(12, 15, 12, 15));
		Label label1=new Label("Cinema");
		Label label2=new Label("    Date");
		Label label3=new Label("Seats Remain");
		Label label4=new Label("Price");
		hBox.getChildren().add(label1);
		hBox.getChildren().add(label2);
		hBox.getChildren().add(label3);
		hBox.getChildren().add(label4);
		vBox.getChildren().add(hBox);
		PreparedStatement preparedStatement=connection.prepareStatement("select * from timetable where time_film=?");
		preparedStatement.setString(1, film.getFilm_name());
		ResultSet resultSet=preparedStatement.executeQuery();
		
		while(resultSet.next()){
			String date=resultSet.getString("time_value");
			String cinema_name=resultSet.getString("cinema");
			int remain_seats=resultSet.getInt("seats");
			int price=resultSet.getInt("time_film_price");
			System.out.println("seats: "+remain_seats+" price: "+price);
			if(!StringHelper.ifExceed(StringHelper.getDate(), date)){
				Label cLabel=new Label(cinema_name);
				Label dLabel=new Label(date);
				Label rLabel=new Label(String.valueOf(remain_seats));
				Label pLabel=new Label(String.valueOf(price));
				
				
				Button delete_button=new Button("Delete");
				Button buy_button=new Button("Buy");
				HBox hBox2=new HBox();
				hBox2.setSpacing(40);
				hBox2.setPadding(new Insets(12, 15, 12, 15));
				hBox2.getChildren().addAll(cLabel, dLabel, rLabel, pLabel, delete_button, buy_button);
				
				vBox.getChildren().add(hBox2);
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
								stage.close();
							}
							else {
								alert_message("Confirm", "There is no empty seat", stage);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				});
				delete_button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						Ticket ticket=new Ticket(film.getFilm_name(), price, customer.getCustomer_account(), date, cinema_name);
						try {
							customer.delete(connection, ticket);
							vBox.getChildren().removeAll(hBox2);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		}
		
		return vBox;
	}
	
	public BorderPane purchasePane(Connection connection, Film film, Stage stage) throws Exception{
		BorderPane borderPane=new BorderPane();
//		borderPane.setCenter(middlePane(connection, stage));
		
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(15, 12, 15, 12));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color:#336699;");
		Label label=new Label("Buy the tickets for "+film.getFilm_name());
		hBox.getChildren().add(label);
		borderPane.setTop(hBox);
		
		ScrollPane scrollPane=new ScrollPane();
		VBox vBox=middle_of_purchasePane(connection, film, stage);
//		GridPane gridPane=new GridPane();
//		gridPane.setHgap(20);
//		gridPane.setVgap(20);
//		gridPane.setAlignment(Pos.CENTER);
/*		
		PreparedStatement preparedStatement=connection.prepareStatement("select * from timetable where time_film=?");
		preparedStatement.setString(1, film.getFilm_name());
		ResultSet resultSet=preparedStatement.executeQuery();
		
		Label clabel=new Label("     Cinema");
		Label tlabel=new Label("     Date");
		Label slabel=new Label("     Seats Remain");
		Label pabel=new Label("   Price");
		gridPane.add(clabel, 0, 0);
		gridPane.add(tlabel, 1, 0);
		gridPane.add(slabel, 2, 0);
		gridPane.add(pabel, 3, 0);
		
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
			
			
			Button delete_button=new Button("Delete");
			Button buy_button=new Button("Buy");
			HBox hBox2=new HBox();
			HBox hBox3=new HBox();
			HBox hBox4=new HBox();
			HBox hBox5=new HBox();
			hBox2.setPadding(new Insets(12, 15, 12, 15));
			hBox2.setSpacing(10);
			hBox3.setPadding(new Insets(12, 15, 12, 15));
			hBox3.setSpacing(10);
			hBox4.setPadding(new Insets(12, 15, 12, 15));
			hBox4.setSpacing(10);
			hBox5.setPadding(new Insets(12, 15, 12, 15));
			hBox5.setSpacing(10);
			hBox2.getChildren().add(label1);
			hBox3.getChildren().add(label2);
			hBox4.getChildren().add(label3);
			hBox5.getChildren().add(label4);
			hBox5.getChildren().add(delete_button);
			hBox5.getChildren().add(buy_button);
			gridPane.add(hBox2, 0, row);
			gridPane.add(hBox3, 1, row);
			gridPane.add(hBox4, 2, row);
			gridPane.add(hBox5, 3, row);
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
							stage.close();
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
			delete_button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Ticket ticket=new Ticket(film.getFilm_name(), price, customer.getCustomer_account(), date, cinema_name);
					try {
						customer.delete(connection, ticket);
						gridPane.getChildren().removeAll(hBox2, hBox3, hBox4, hBox5);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			row++;
		}
*/		
		HBox hBox2=new HBox();
		Button add_button=new Button("Add");
		hBox2.setAlignment(Pos.CENTER);
		hBox2.getChildren().add(add_button);
		hBox2.getChildren().add(refresh_purchase_system);
		if(!customer.getCustomer_account().equals("administrator"))
			add_button.setVisible(false);
		add_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Stage add_stage=new Stage();
				GridPane add_pane=addPane(connection, add_stage, film, vBox);
				Scene add_scene=new Scene(add_pane, 700, 150);
				add_stage.setScene(add_scene);
				add_stage.show();
			}
		});
		
		borderPane.setBottom(hBox2);
		scrollPane.setContent(vBox);
		borderPane.setCenter(scrollPane);
		
		return borderPane;
	}
	
	public GridPane addPane(Connection connection, Stage stage, Film film, VBox vBox){
		GridPane addPane=new GridPane();
		addPane.setPadding(new Insets(10));
		Label label2=new Label("Time(MM-dd HH:mm)");
		Label label3=new Label("Price");
		Label label4=new Label("Seats");
		Label label5=new Label("Cinema");
		TextField textField2=new TextField();
		TextField textField3=new TextField();
		TextField textField4=new TextField();
		TextField textField5=new TextField();
		addPane.setHgap(10);
		addPane.setVgap(10);
		addPane.add(label2, 3, 1);
		addPane.add(label3, 4, 1);
		addPane.add(label4, 5, 1);
		addPane.add(label5, 6, 1);
		addPane.add(textField2, 3, 2);
		addPane.add(textField3, 4, 2);
		addPane.add(textField4, 5, 2);
		addPane.add(textField5, 6, 2);
		Button button=new Button("Confirm");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Ticket ticket=new Ticket(film.getFilm_name(), Integer.parseInt(textField3.getText()), "administrator", textField2.getText(), textField5.getText());
				int seats=Integer.parseInt(textField4.getText());
				try {
					customer.add(connection, ticket, seats);
					Label cLabel=new Label(textField5.getText());
					Label dLabel=new Label(textField2.getText());
					Label sLabel=new Label(seats+"");
					Label pLabel=new Label(textField4.getText());
					
					Button delete_button=new Button("Delete");
					Button buy_button=new Button("Buy");
					HBox hBox2=new HBox();
					hBox2.setSpacing(40);
					hBox2.setPadding(new Insets(12, 15, 12, 15));
					hBox2.getChildren().addAll(cLabel, dLabel, sLabel, pLabel, delete_button, buy_button);
					
					vBox.getChildren().add(hBox2);
					if(!customer.getCustomer_account().equals("administrator"))
						delete_button.setVisible(false);
					buy_button.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							Ticket ticket=new Ticket(film.getFilm_name(), Integer.parseInt(textField3.getText()), customer.getCustomer_account(), textField2.getText(), textField5.getText());
							// TODO Auto-generated method stub
							try {
								if(customer.purchase(ticket, connection)){
									alert_message("Confirm", "Purchasing successfully", stage);
									stage.close();
								}
								else {
									alert_message("Confirm", "Puchasing fail due to no seats or insufficient balance", stage);
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
					});
					delete_button.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							Ticket ticket=new Ticket(film.getFilm_name(), Integer.parseInt(textField3.getText()), customer.getCustomer_account(), textField2.getText(), textField5.getText());
							try {
								customer.delete(connection, ticket);
								vBox.getChildren().removeAll(hBox2);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});		
							
					stage.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		HBox hBox=new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().add(button);
		addPane.add(hBox, 5, 3);
		
		return addPane;
	}
	
	public HBox hbox_of_vbox_of_middlePane(Connection connection, Stage stage, Film film, VBox vBox) throws Exception{
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(12, 15, 12, 15));
		hBox.setSpacing(20);
		InputStream graph=new FileInputStream("C:\\study\\javatest\\CinemaSystem\\src\\CinemaSystem\\"+film.getFilm_name()+".jpg");
		ImageView image=new ImageView(new Image(graph));
		hBox.getChildren().add(image);
		Text text=new Text();
		text.setText(film.getFilm_abstract());
		hBox.getChildren().add(text);
		Button button1=new Button("See comments");
		Button button2=new Button("Buy the tickets");
		Button button3=new Button("Delete");
//		Button button4=new Button("Add");
		
		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				Stage info_stage=new Stage();
				try {
					Scene info_scene=new Scene(infoPane(connection, film.getFilm_name()), 500, 700);
					info_stage.initOwner(stage);
					info_stage.setScene(info_scene);
					info_stage.show();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
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
					refresh_purchase_system.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							Scene purchase_scene;
							try {
								purchase_stage.close();
								purchase_scene = new Scene(purchasePane(connection, film, purchase_stage), 500, 700);
								purchase_stage.setScene(purchase_scene);
								purchase_stage.show();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						};
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					Administrate.delete(film.getFilm_name(), connection);
					vBox.getChildren().removeAll(hBox);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		hBox.getChildren().add(button1);
		hBox.getChildren().add(button2);
		hBox.getChildren().add(button3);
		if(!customer.getCustomer_account().equals("administrator"))
			button3.setVisible(false);
		return hBox;
	}
	
	public VBox vBox_of_middlePane(Connection connection, Stage stage) throws Exception{
		VBox vBox=new VBox();
		vBox.setPadding(new Insets(12, 15, 12, 15));
		vBox.setSpacing(20);
		ArrayList<Film> films=getAllFilm(connection);
		
		for(Film film: films){
			vBox.getChildren().add(hbox_of_vbox_of_middlePane(connection, stage, film, vBox));
		}
		
		return vBox;
	}
	
	public ScrollPane middlePane(VBox vBox) throws Exception{
//		GridPane gridPane=new GridPane();
//		gridPane.setAlignment(Pos.CENTER);
//		gridPane.setHgap(10);
//		gridPane.setVgap(10);
		ScrollPane scrollPane=new ScrollPane();
		
//		ArrayList<Film> films=getAllFilm(connection);
/*		
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
//			Button button4=new Button("Add");
			
			button1.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					
					Stage info_stage=new Stage();
					try {
						Scene info_scene=new Scene(infoPane(connection, film.getFilm_name()), 500, 700);
						info_stage.initOwner(stage);
						info_stage.setScene(info_scene);
						info_stage.show();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
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
						refresh_purchase_system.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								Scene purchase_scene;
								try {
									purchase_stage.close();
									purchase_scene = new Scene(purchasePane(connection, film, purchase_stage), 500, 700);
									purchase_stage.setScene(purchase_scene);
									purchase_stage.show();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							};
						});
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			button3.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					try {
						Administrate.delete(film.getFilm_name(), connection);
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
*/		
//		scrollPane.setVmax(440);
//		scrollPane.setPrefSize(115, 150);
		scrollPane.setContent(vBox);
		return scrollPane;
	}
	
	
	
	public HBox upPane(Stage preStage, Connection connection, VBox vBox){
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(15, 12, 15, 12));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color:#336699;");
		Button button=new Button("Personal Info");
		Button button2=new Button("Add");
		button.setPrefSize(100, 20);
		if(!customer.getCustomer_account().equals("administrator"))
			button2.setVisible(false);
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
		
		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Stage add_stage=new Stage();
				Scene add_scene=new Scene(add_film_pane(connection, add_stage, vBox));
				add_stage.initOwner(preStage);
				add_stage.setScene(add_scene);
				add_stage.show();
			}
		});
		hBox.getChildren().add(button);
		hBox.getChildren().add(refresh_info_pane);
		hBox.getChildren().add(button2);
		return hBox;
	}
	
	public BorderPane add_film_pane(Connection connection, Stage stage, VBox vBox){
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
				System.out.println("reach");
				try {
					System.out.println("reach");
					Administrate.add(film, connection);
					HBox hBox=hbox_of_vbox_of_middlePane(connection, stage, film, vBox);
					vBox.getChildren().add(hBox);
					System.out.println("reach");
					stage.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return borderPane;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Connection connection;
		Class.forName("com.mysql.jdbc.Driver");
		connection=DriverManager.getConnection("jdbc:MySql:///cinema", "root", "pptzz25228");
		// TODO Auto-generated method stub
		BorderPane borderPane=new BorderPane();
		VBox vBox=vBox_of_middlePane(connection, stage);
		borderPane.setCenter(middlePane(vBox));
		borderPane.setTop(upPane(stage, connection, vBox));
		
		Scene scene=new Scene(borderPane, 1000, 1000);
		stage.setScene(scene);
		stage.show();
		refresh_info_pane.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				BorderPane borderPane=new BorderPane();
				try {
					borderPane.setCenter(middlePane(vBox));
					borderPane.setTop(upPane(stage, connection, vBox));
					
					Scene scene=new Scene(borderPane, 1000, 1000);
					stage.close();
					stage.setScene(scene);
					stage.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
	}
/*
	public static void main(String[] args) {
		launch(args);
	}
*/
}
