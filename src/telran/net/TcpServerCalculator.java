package telran.net;

import java.net.*;
import java.io.*;

public class TcpServerCalculator {
static final int PORT = 5500;
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(PORT);
		while(true) {
			Socket socket = serverSocket.accept();
			clientRun(socket);
		}		
		
	}
	private static void clientRun(Socket socket) {
		
		try(
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream writer = new PrintStream(socket.getOutputStream())) {
			while(true) {
				String line = reader.readLine();
				if(line == null) {
					System.out.println("Client closed normally connection");
					break;
				}
				String response = getResponse(line);
				writer.println(response);
			}
			
		} catch (Exception e) {
			System.out.println("Client closed abnormally connection");
		}		
		
	}
	private static String getResponse(String line) {
		String response = "Wrong request structure, usage: <a operation: +; -; /; *>#<float>#<float>";
		
		String[] tokens = line.split("#");		
		if(tokens.length == 3) {
			Float first = Float.valueOf(tokens[1]);
			Float second = Float.valueOf(tokens[2]);
			response = switch(tokens[0]) {			
				case "+" -> Float.toString(first + second);
				case "-" -> Float.toString(first - second);
				case "*" -> Float.toString(first * second);
				case "/" -> Float.toString(first / second);				
				default -> "Wrong request type";
			
			};
		}
		
		return response;
	}
	

}


//HW #40 To write TcpCalculatorServer (performing 4 arithmetic operations +; -; /; *) 
//and TcpCalculatorClient sending requests like +#2.5#2.5