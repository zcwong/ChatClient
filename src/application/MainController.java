package application;


import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.Client;

public class MainController {

	@FXML
	public TextArea myMessage =new TextArea();
	@FXML
	public TextArea myReply = new TextArea();
;
	public StringBuilder fieldContent = new StringBuilder(""); 



	
	
	public void getStatus(ActionEvent event) {
		
		String msgString = "STAT";
		//Client.sendOverConnection(msgString);
		Client.sendOverConnection(msgString);

	}
	

	
	public void getList(ActionEvent event) {
		
		String msgString = "LIST";
		//Client.sendOverConnection(msgString);
		Client.sendOverConnection(msgString);

	}
	
	
	public void quit(ActionEvent event) throws IOException {
		
		String msgString = "QUIT";
		//Client.sendOverConnection(msgString);
	//	myMessage.setText("OK thank you for using the chat service, goodbye. ");

		Client.sendOverConnection(msgString);
		Client.getSocket().close();



	}
	
	
	public void clear(ActionEvent event) {
		fieldContent.setLength(0);
		
		Platform.runLater(() ->{
			myMessage.setText(fieldContent.toString());

		});
		
		

	}
	
	
	public void test(ActionEvent event) {
		String temp = new String("HAIL ");

		//String msgString = myReply.getText();
		
		

	/*	Platform.runLater(() ->{
			final String msgString = myReply.getText();
			if (msgString.isEmpty()) {
				return;
			}else {
			Client.sendOverConnection(temp+msgString);
			}
			myReply.setText("");
		});*/
		
		
		Platform.runLater(() ->{
			final String msgString = myReply.getText();
			if (msgString.isEmpty()) {
				return;
			}
			
			if(msgString.contains("\n")) {
				String lines[] = myReply.getText().split("\\n");
				for(int i = 0; i < lines.length; i++) {
					Client.sendOverConnection("HAIL " + lines[i]);
					//myMessage.setText(lines[i]);

				}
			}else {
				Client.sendOverConnection("HAIL" +msgString);

			}
			myReply.setText("");
			
		});
		
		

			
			
			
			
			
		}
		
		
		
		//if(msgString.isEmpty())
		//	return;

		
		//
		
			
			

			
		
   

	
	

	
	

	
	public void pm(ActionEvent event) {
		
		System.out.println(Client.userList);
		
		List<String> choices = new ArrayList<>();
		for(int i = 0; i < Client.items.size(); i++) {
		 	String temp = Client.items.get(i);
		 	choices.add(temp);
        }
	
		 String user =new String();
		 String pm =new String();



		ChoiceDialog<String> dialog = new ChoiceDialog<>("users", choices);
		dialog.setTitle("Private message");
		dialog.setHeaderText("Choose a user to send private message");
		dialog.setContentText("Choose your user:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    user = result.get();
		}
		
		TextInputDialog dialog2 = new TextInputDialog("Enter message here");
		dialog2.setTitle("Message");
		dialog2.setHeaderText("Enter message");
	

		// Traditional way to get the response value.
		Optional<String> result2 = dialog2.showAndWait();
		if (result.isPresent()){
		   pm =result2.get();
		}

		Client.sendOverConnection("MESG "+user+" "+pm);
		
		fieldContent.append("PM sent to " + user +": " + pm+"\n");
		
		

		

		
		Platform.runLater(() ->{
			Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());

		});
		
	


		
		

	}
	
	
	
	

	

}