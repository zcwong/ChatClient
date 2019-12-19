package application;


import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.Client;

public class MainController {

	@FXML
	public TextArea myMessage =new TextArea();
	@FXML
	public TextArea myReply = new TextArea();
	public Label instruction = new Label();
	public StringBuilder fieldContent = new StringBuilder(""); 



	
	
	public void getStatus(ActionEvent event) {
		
		String msgString = "STAT";
		System.out.println(msgString);
		//Client.sendOverConnection(msgString);
		Client.sendOverConnection(msgString);

	}
	

	
	public void getList(ActionEvent event) {
		
		String msgString = "LIST";
		System.out.println(msgString);
		//Client.sendOverConnection(msgString);
		Client.sendOverConnection(msgString);

	}
	
	
	public void quit(ActionEvent event) throws IOException {
		
		String msgString = "QUIT";
		System.out.println(msgString);
		//Client.sendOverConnection(msgString);
	//	myMessage.setText("OK thank you for using the chat service, goodbye. ");

		Client.sendOverConnection(msgString);
	//	Client.getSocket().close();



	}
	
	
	public void clear(ActionEvent event) {
		fieldContent.setLength(0);
		myMessage.setText(fieldContent.toString());
		
		

	}

	
	
	public void send(ActionEvent event) {
		
		
		

		String msgString = (String)myReply.getText();
		
		if(msgString.isEmpty()) {
			return;
			
		}
		
		

		if(msgString.contains("\n")==false) {
			
			char[] chars = msgString.toCharArray();

			if(chars[0]=='M' && chars[1]=='E' && chars[2]=='S' && chars[3]=='G' && chars[4]==' ' ) {
				Client.sendOverConnection(msgString);
			}else {
				Client.sendOverConnection("HAIL " + msgString);
			}
			myMessage.setText(msgString);
			

		}else {
			
			
			String lines[] = myReply.getText().split("\\n");
			for(int i = 0; i < lines.length; i++) {
				Client.sendOverConnection("HAIL " + lines[i]);
				//myMessage.setText(lines[i]);

			}
			
			
			
			
			
			
		}
			
			
		
		
		
	
		
		
		//System.out.println(msgString);
		

		//myMessage.setText(msgString);
		myReply.setText("");
		

	}
	
	public void pm(ActionEvent event) {
		
		System.out.println(Client.userList);
		
		List<String> choices = new ArrayList<>();
		for(int i = 0; i < Client.items.size(); i++) {
		 	String temp = Client.items.get(i);
		 	choices.add(temp);
        }
	
		

		ChoiceDialog<String> dialog = new ChoiceDialog<>("users", choices);
		dialog.setTitle("Private message");
		dialog.setHeaderText("Choose a user to send private message");
		dialog.setContentText("Choose your user:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			System.out.println(result);
		    myReply.setText("MESG " + result.get() + " <your message>" );
		}


		
		

	}
	
	
	
	

	

}