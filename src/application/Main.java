package application;	
import application.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	
	
	//set up client object
	private static Client client = new Client("localhost",9000);
	public static MainController mainController;


	public static void main(String[] args) {
		launch(args);
	}
	

	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
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
	
		if(!client.start())
			return;

		
	}

		
}







		
	