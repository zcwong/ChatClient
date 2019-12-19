package application;


import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import application.Client;
import application.Client.ListenNameList;

public class MainController {

	@FXML
	public TextArea myMessage =new TextArea();
	@FXML
	public TextArea myReply = new TextArea();
;
	public StringBuilder fieldContent = new StringBuilder(""); 



	
	//button function to get status
	public void getStatus(ActionEvent event) {
		
		String msgString = "STAT";
		Client.sendOverConnection(msgString);

	}
	

	//button function to get list of users online
	public void getList(ActionEvent event) {
		
		String msgString = "LIST";
		Client.sendOverConnection(msgString);

	}
	
	
	//button function to quit
	public void quit(ActionEvent event) throws IOException {
		
		String msgString = "QUIT";

		Client.sendOverConnection(msgString);
		Client.getSocket().close();



	}
	
	
	//button function to clear chat area
	public void clear(ActionEvent event) {
		fieldContent.setLength(0);
		
		Platform.runLater(() ->{
			myMessage.setText(fieldContent.toString());

		});
		
		

	}
	
	
	
	//broadcast message and message string handling
	public void send(ActionEvent event) {
		//switch thread to javafx
		Platform.runLater(() ->{
			final String msgString = myReply.getText();
			if (msgString.isEmpty()) {
				return;
			}
			
			//handle \n in string received
			if(msgString.contains("\n")) {
				String lines[] = myReply.getText().split("\\n");
				for(int i = 0; i < lines.length; i++) {
					Client.sendOverConnection("HAIL " + lines[i]);

				}
			}else {
				Client.sendOverConnection("HAIL" +msgString);

			}
			myReply.setText("");
			
		});
				
	}
		

	//send private message 
	public void pm(ActionEvent event) {
		
		Client.sendOverConnection("NAME");

			
		
		List<String> choices = new ArrayList<>();
		String username = Client.name.substring(5);
		System.out.println(username);
		for(int i = 0; i < Client.items.size(); i++) {
			if(username.equals(Client.items.get(i))==false) {
				String temp = Client.items.get(i);
				choices.add(temp);
			}
				
		
			
		 	
        }
		
		
		
	
		 String user =new String();
		 String pm =new String();


		 //alert box to prompt for user to send pm
		ChoiceDialog<String> dialog = new ChoiceDialog<>("users", choices);
		dialog.setTitle("Private message");
		dialog.setHeaderText("Choose a user to send private message");
		dialog.setContentText("Choose your user:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    user = result.get();
		    //alert box to prompt for private message content
		    TextInputDialog dialog2 = new TextInputDialog("");
			dialog2.getDialogPane().setPrefSize(480, 320);
			dialog2.setTitle("Message");
			dialog2.setHeaderText("Enter message");
		

			// Traditional way to get the response value.
			Optional<String> result2 = dialog2.showAndWait();
			if (result.isPresent()){
			   pm =result2.get();
			}
		}
		
		
		//don't send message if no result get from dialogue box
		if(result.get().isEmpty()==false) {
			Client.sendOverConnection("MESG "+user+" "+pm);
			fieldContent.append("PM sent to " + user +": " + pm+"\n");

		}
		
	
		Platform.runLater(() ->{
			Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());

		});
	

	}
	
	
	
	

	

}