package application;

import application.MainController;
import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



public class Client {
	

	public static String msgg;
	public static String username;
	private BufferedReader sInput;		// to read from the socket
	public static PrintWriter sOutput;		// to write on the socket

	public static String userList; 
	
	public static ArrayList<String> names = new ArrayList<String>();
	
	
	public static List<String> items;

	public static String returnMsg;


	private static Socket socket;					// socket object
	
	private String server;	
	private int port;	
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
    Client(String server, int port) {
		this.server = server;
		this.port = port;
	}
    
    public boolean start() {
		// try to connect to the server
		try {
			setSocket(new Socket(server, port));
		} 
		// exception handler if it failed
		catch(Exception ec) {
			
			Main.mainController.fieldContent.append("Error connectiong to server:" + ec + "\n");
			Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());
			//returnMsg=("Error connectiong to server:" + ec);

			return false;
		}
		Main.mainController.fieldContent.append("Connection accepted " + getSocket().getInetAddress() + ":" + getSocket().getPort()+ "\n");
		Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());

		//System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
			
		/* Creating both Data Stream */
		try
		{
			sInput  = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
			sOutput = new PrintWriter(getSocket().getOutputStream(), true);
			
		}
		catch (IOException eIO) {
			Main.mainController.fieldContent.append("Exception creating new Input/output Streams: " + eIO + "\n");
			Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());

			//System.out.println("Exception creating new Input/output Streams: " + eIO);
			return false;
		}
		
		
		Main.mainController.myMessage.setWrapText(true);
		Main.mainController.myReply.setWrapText(true);


    	new ListenNameList().start();
    	new ListenNameList().nameVeri();
		sendOverConnection(msgg);

		 //creates the Thread to listen from the server 
		new ListenFromServer().start();

		
		
		return true;
	}

    
	

	
	public synchronized static void sendOverConnection (String message){
		sOutput.println(message);
	}
	
	

	
	
	public static Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		Client.socket = socket;
	}



	


	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					// read the message form the input data stream

					String msg = sInput.readLine();
					System.out.println(msg);
					
					//char[] receivedChar = msg.toCharArray();
					
					
					

					
					// print the message
					Main.mainController.fieldContent.append(msg + "\n");

					
					
					Platform.runLater(() ->{
						Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());

					});

					


				//	System.out.println(msg);
				}
				catch(IOException e) {
					Main.mainController.fieldContent.append("Server has closed the connection: " + e );

					Main.mainController.myMessage.setText(Main.mainController.fieldContent.toString());

					break;
				}
			
			}
		}
	}
	
	
	
	class ListenNameList extends Thread {

		public void run() {
			
				try {
					// read the message form the input data stream
					userList = sInput.readLine();
					

					
					
					
					items = Arrays.asList(userList.split(",\\s*"));
					
					
					

					 

				
					
				}
				catch(IOException e) {
					Main.mainController.myMessage.setText("Server has closed the connection: " + e );

				
				}
			
			
		}
		
		
		
		public void nameVeri() {
			boolean flag = false;
			
			while(flag==false) {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Username");
				dialog.setHeaderText("Enter username");
				dialog.setContentText("Please enter your name:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
			

				if (result.isPresent()){		    
					username = result.get();		  		    
			 
				}
				
				
						
				
				 boolean nameFlag = false;
				 char[] chars = username.toCharArray();
				 
				 if (username.isEmpty()) {
					 nameFlag=true;
				 }
				 
				 if (username.length()>10) {
					 nameFlag=true;
				 }
				 
				 
				 
				 for  (int i=0; i<username.length(); i++) {
				    	
				    if (chars[i] == ' ') {
				    		nameFlag=true;
				    }
				    
				   
				    	
				 }
				 
				
				 System.out.println(Client.items);
				 
				 boolean sameName = false;
				 
				 for(int i = 0; i < items.size(); i++) {
					 	String temp = items.get(i);
					 	if(username.equals(temp)==true) {
					 		sameName=true;
					 		break;
					 	}
			        }

				

				
				 
				 
				 
				 if (nameFlag==true) {
					 Alert alert = new Alert(AlertType.ERROR);
			    		alert.setTitle("Error");
			    		alert.setHeaderText("Error in username");
			    		alert.setContentText("Username cannot be blank, contains space or special characters!");

			    		alert.showAndWait();
				 }else if(sameName==true){
					 Alert alert = new Alert(AlertType.ERROR);
			    		alert.setTitle("Error");
			    		alert.setHeaderText("Error in username");
			    		alert.setContentText("Username already used");

			    		alert.showAndWait();
				 }else {
					 
					 flag=true;
						msgg = "IDEN " + username;
			    		break;
				 }
				
			
			}
			
		
	}
		
		
	
	}


	

	
	
	
}

	
	

