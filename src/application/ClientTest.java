package application;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;

import application.Client;


import org.junit.Test;

public class ClientTest {
	
	private static Client testClient;
	private static Socket socket;
	private BufferedReader sInput;		// to read from the socket
	public static PrintWriter sOutput;
	

	  
	  @Before
	    public void initialiseServer(){
		  try {
			  Client testClient = new Client("localhost",9000);
			  if(!testClient.start())
					return;
			  
			  
				
			  
			  System.out.println("connected");
		  }
			// exception handler if it failed
			catch(Exception ec) {
				
				System.out.println("Error connectiong to server:" + ec + "\n");
			
			}
		  
		 

			
		}
		  
		  
		 
			

	    
	    
	  
	  
	    
		
	 

	@Test
	public void test() {
		System.out.println("test");

		testClient.sendOverConnection("IDEN wong");
		
		
		
	}

}
