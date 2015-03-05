import java.net.*;
import java.io.*;
import java.util.*;


public class helloNetwork{
	public static void main(String[] args) throws IOException{
		String hostIP = null;
		Scanner inputReader = new Scanner(System.in);
		Scanner IPReader = new Scanner(System.in);
		int hostOrClient = 0;
		int portNumber = 4500;
		
		System.out.print("Would you like to be the host(1) or client(2): ");
		hostOrClient = inputReader.nextInt();
		
		if( hostOrClient == 1){
			// Do Host stuff
			try{
				SetupHost(portNumber);
			}
			catch(Exception e){
				System.out.println("Couldn't setup host: " + e);
			}
		}else if( hostOrClient == 2){
			// Do Client stuff
			if (args.length == 0){
				System.out.print("Please enter IP/URL: ");
				hostIP = IPReader.nextLine();
				
			}else{
				hostIP = args[0];
				System.out.println("Connecting to: " + hostIP);
			}
			
			try {
				SetupClient(hostIP, portNumber);
			}
			catch(Exception e){
				System.out.println("Couldn't setup client!");
			}
			
		}else{ System.out.println("Sorry that's not a valid entry");}
		inputReader.close();
	
	
	}
	
	private static void SetupClient(String hostIP, int portNum) throws Exception{
		
		try{
			Socket clientSocket = new Socket(hostIP, portNum);
			BufferedReader in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream()));
			System.out.println("Recieved data: ");
			
			while( !in.ready() ){}; // Wait for the stream to finish and be ready.
			System.out.println(in.readLine()); // print out one line of input.
			
			in.close();
			clientSocket.close();
			
		}catch(Exception e){
			System.out.println("Error setting up client: " + e);
		}
		
	}
	
	private static void SetupHost(int portNum){
		//int portNum = 4500; // Default port.
		String data = "foo bar";
		
		try{
			System.out.println("Attempting to bind to port...");
			ServerSocket servr = new ServerSocket(portNum);
			System.out.println("Port bound! waiting for client...");
			Socket listeningSocket = servr.accept();
			System.out.println("Connection established!");
			
			PrintWriter out = new PrintWriter(listeningSocket.getOutputStream(), true);
			System.out.println("Sending data...");
			out.print(data);
			out.close();
			listeningSocket.close();
			servr.close();
			
		}catch(Exception e){
			System.out.println("Networking error setting up server: " + e);
		}
		
	}
}


