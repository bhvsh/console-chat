package tcpchat;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.out;

public class TcpChat {
	public static void tcpServer() throws IOException{
		ServerSocket ss=new ServerSocket(8000);
			out.println("Server waiting for connection request ...");
			Socket s=ss.accept();
			
			out.println("Connected from " + s.getInetAddress() + "\nWait for the client message.\n");
			
			Scanner stdin=new Scanner(System.in);

			Scanner msgIn=new Scanner(s.getInputStream());
			PrintWriter msgOut=new PrintWriter(s.getOutputStream(),true);

			try {
				while(true) {
					String clientMsg=msgIn.nextLine();
					out.println("CLIENT: " + clientMsg);
					if(clientMsg.contentEquals("close")) break;
					
					out.print("toClient: ");
					String serverMsg=stdin.nextLine();
					msgOut.println(serverMsg);
					if(clientMsg.contentEquals("close")) break;
				}
			}
			catch(Exception e) {}

			s.close();
			ss.close();
			msgIn.close();
			stdin.close();
			out.println("\nConnection closed!");
	}
	
	public static void tcpClient(String ipAddr) throws IOException{
		Socket s=new Socket(ipAddr,8000);
		out.println("Connected to server!");
		
		Scanner stdin=new Scanner(System.in);

		Scanner msgIn=new Scanner(s.getInputStream());
		PrintWriter msgOut=new PrintWriter(s.getOutputStream(),true);

		try {
			while(true) {
				out.print("\ntoServer: ");
				String clientMsg=stdin.nextLine();
				msgOut.println(clientMsg);
				if(clientMsg.contentEquals("close")) break;
			
				String serverMsg=msgIn.nextLine();
				out.print("SERVER: " + serverMsg);
				if(serverMsg.contentEquals("close")) break;
			}
		}
		catch(Exception e) {}
		
		s.close();
		msgIn.close();
		stdin.close();
		out.println("\n\nConnection closed.");
				
	}
	
	public static void main(String[] args) throws IOException {
		Scanner ip=new Scanner(System.in);
		out.print("Enter your role:\n\t1. Server\n\t2. Client\n>");
		switch(ip.nextInt())
		{
			case 1: out.println("Running server at port 8000");
					tcpServer();
					break;
			case 2: out.print("Enter the IP address: ");
					tcpClient(ip.next());
					break;
			default: out.print("Invalid input! Please try again.");	
		}
		ip.close();
	}
}
