package application;
	
import application.Client;

import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Main extends Application {
	
	
	
	private static Client client = new Client("localhost",9000);
	public static MainController mainController;


	public static void main(String[] args) {
		launch(args);
	}
	

	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
		//Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
	    Parent root = loader.load();
	    mainController = (MainController)loader.getController();
	    
	    
	    
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(890);
		primaryStage.setMinHeight(510);
		
		
		

		
		primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		/*mainController.myReply.setOnKeyPressed(new EventHandler<KeyEvent>() {


			 public void handle(KeyEvent ke) {
		            if (ke.getCode() == KeyCode.ENTER) {
		            	Platform.runLater(() ->{
		        			final String msgString = Main.mainController.myReply.getText();
		        			if (msgString.isEmpty()) {
		        				return;
		        			}
		        			
		        			if(msgString.contains("\n")) {
		        				String lines[] = Main.mainController.myReply.getText().split("\\n");
		        				for(int i = 0; i < lines.length; i++) {
		        					Client.sendOverConnection("HAIL " + lines[i]);
		        					//myMessage.setText(lines[i]);

		        				}
		        			}else {
		        				Client.sendOverConnection("HAIL" +msgString);

		        			}
		        			Main.mainController.myReply.setText("");
		        			
		        		});
		        		
		            }
		        }
		    });	*/

		
		if(!client.start())
			return;
		
		
	
		

		

		
	}





	



	
		
}







		
	