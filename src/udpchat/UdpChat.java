package udpchat;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.out;

public class UdpChat {
	public static void udpServer() throws IOException{
		byte data[]= new byte[1024];
		DatagramSocket s=new DatagramSocket(8000);

		Scanner stdin=new Scanner(System.in);
		
		try {
			while(true) {
				DatagramPacket packet=new DatagramPacket(data,data.length);
				s.receive(packet);

				String clientMsg=new String(packet.getData(),0,packet.getLength());
				out.print("CLIENT: " + clientMsg);
				if(clientMsg.contentEquals("close")) break;
				
				out.print("\ntoClient: ");
				String serverMsg=stdin.nextLine();

				packet=new DatagramPacket(serverMsg.getBytes(),serverMsg.getBytes().length,packet.getAddress(),packet.getPort());
				s.send(packet);

				if(serverMsg.contentEquals("close")) break;
			}
		}
		catch(Exception e) {}
		
		s.close();
		stdin.close();
		out.println("\n\nConnection closed.");
	}
	
	public static void udpClient(String ipAddr) throws IOException{
		byte data[]= new byte[1024];
		DatagramSocket s=new DatagramSocket();

		InetAddress ipAddrm=InetAddress.getByName(ipAddr);
		Scanner stdin=new Scanner(System.in);
		
		try {
			while(true) {
				out.print("\ntoServer: ");
				String clientMsg=stdin.nextLine();
				
				DatagramPacket packet=new DatagramPacket(clientMsg.getBytes(),clientMsg.getBytes().length,ipAddrm,8000);
				s.send(packet);
				
				if(clientMsg.contentEquals("close")) break;
				
				packet=new DatagramPacket(data,data.length);
				s.receive(packet);
				
				String serverMsg=new String(packet.getData(),0,packet.getLength());
				out.print("SERVER: " + serverMsg);
				if(serverMsg.contentEquals("close")) break;
			}
		}
		catch(Exception e) {}
		
		s.close();
		stdin.close();
		out.println("\nConnection closed.");
				
	}
	
	public static void main(String[] args) throws IOException {
		Scanner ip=new Scanner(System.in);
		out.print("Enter your role:\n\t1. Server\n\t2. Client\n>");
		switch(ip.nextInt())
		{
			case 1: out.print("Enter the IP address: ");
					udpClient(ip.next()); //'client' is 'server'
					break;
			case 2: out.println("Running server at port 8000\n");
					udpServer();  //'server' is 'client'
					break;
			default: out.print("Invalid input! Please try again.");	
		}
		ip.close();
	}
}
