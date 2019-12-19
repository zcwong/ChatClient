package application;

import application.Main;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



public class Client {
	

	public static String name;
	public static String username;
	private BufferedReader sInput;		// to read from the socket
	public static PrintWriter sOutput;		// to write on the socket

	public static String userList; 
	
	
	
	public static List<String> items;

	


	private static Socket socket;					// socket object
	
	private String server;	
	private int port;	
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	public static Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		Client.socket = socket;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
	
	//client constructor
    Client(String server, int port) {
		this.server = server;
		this.port = port;
	}
    
    
    
    //set up server
    public boolean start() {
		// try to connect to the server
		try {
			setSocket(new Socket(server, port));
		} 
		// exception handler if it failed
		catch(Exception ec) {
			
			Main.mainController.fieldContent.append("Error connectiong to server:" + ec + "\n");
			Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());
			

			return false;
		}
		Main.mainController.fieldContent.append("Connection accepted " + getSocket().getInetAddress() + ":" + getSocket().getPort()+ "\n");
		Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());

		
			
		/* Creating both Data Stream */
		try
		{
			sInput  = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
			sOutput = new PrintWriter(getSocket().getOutputStream(), true);
			
		}
		catch (IOException eIO) {
			Main.mainController.fieldContent.append("Exception creating new Input/output Streams: " + eIO + "\n");
			Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());

			
			return false;
		}
		
		
		Main.mainController.myMessage.setWrapText(true);
		Main.mainController.myReply.setWrapText(true);

    	//listen and get name list from server
    	new ListenNameList().start();
    	
    	//verify if name is in correct format
    	//new ListenNameList().nameVeri();
    	
		sendOverConnection(name);

		//creates the Thread to listen from the server 
		new ListenFromServer().start();

		
		
		return true;
	}

    
	

	//send data to server
	public synchronized static void sendOverConnection (String message){
		sOutput.println(message);
	}
	
	

	
	
	


	

	//get data returned from server
	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					// read the message form the input data stream

					String msg = sInput.readLine();
					
					if(msg.contains("nAmEList")) {
						String list = msg.substring(8);
					//	System.out.println("list content before trim"+list);

						//split by \n character and store in list
						items = Arrays.asList(list.split(",\\s*"));
					//	System.out.println("after trim"+items);
				
					}else {

					// print the message
					Main.mainController.fieldContent.append(msg + "\n");

					
					//switch thread to javafx thread
					Platform.runLater(() ->{
						
						Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());
						
						//set text area auto scroll to bottom
						Main.mainController.myMessage.selectPositionCaret(Main.mainController.myMessage.getLength());
						Main.mainController.myMessage.deselect();

					});
					}
					


				
				}
				catch(IOException e) {
					Main.mainController.fieldContent.append("Server has closed the connection: " + e );

					Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());

					break;
				}
			
			}
		}
	}
	
	
	
	//get name list of user online
	class ListenNameList extends Thread {

		public void run() {
			
				try {
					// read the message form the input data stream
					userList = sInput.readLine();	
					//split by \n character and store in list
					items = Arrays.asList(userList.split(",\\s*"));
			
				}
				catch(IOException e) {
					Main.mainController.myMessage.setText("Server has closed the connection: " + e );

				
				}
			
			
		}
		
		
		//name format checking
		public void nameVeri() {
			boolean flag = false;
			
			while(flag==false) {
				//alert to prompt for user name input
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Username");
				dialog.setHeaderText("Enter username");
				dialog.setContentText("Please enter your name:");


				Optional<String> result = dialog.showAndWait();
			
				//assign result if result present
				if (result.isPresent()){		    
					username = result.get();		  		    
			 
				}
				
				
						
				
				 boolean nameFlag = false;
				 char[] chars = username.toCharArray();
				 
				 //make sure username not empty
				 if (username.isEmpty()) {
					 nameFlag=true;
				 }
				 
				 //make sure username length not more than 10 char
				 if (username.length()>10) {
					 nameFlag=true;
				 }
				 
				 
				 //make sure username does not contain space
				 for  (int i=0; i<username.length(); i++) {
				    	
				    if (chars[i] == ' ') {
				    		nameFlag=true;
				    }
				    
				   
				    	
				 }
				 
				
				 
				 boolean sameName = false;
				 
				 //make sure username does not already exist
				 for(int i = 0; i < items.size(); i++) {
					 	String temp = items.get(i);
					 	if(username.equals(temp)==true) {
					 		sameName=true;
					 		break;
					 	}
			        }

				

				
				 
				 
				
				 if (nameFlag==true) {
					 
					 	//alert if username format wrong
					 	Alert alert = new Alert(AlertType.ERROR);
			    		alert.setTitle("Error");
			    		alert.setHeaderText("Error in username");
			    		alert.setContentText("Username cannot be blank, contains space or special characters!");

			    		alert.showAndWait();
				 }else if(sameName==true){
					 
					 	//alert if username already exist
					 	Alert alert = new Alert(AlertType.ERROR);
			    		alert.setTitle("Error");
			    		alert.setHeaderText("Error in username");
			    		alert.setContentText("Username already used");

			    		alert.showAndWait();
				 }else {
					 	
					 	//send username to server if all correct
					 	flag=true;
						name = "IDEN " + username;
			    		break;
				 }
				
			
			}
					
	}		
		
	
}	
	
	
}

	
	

