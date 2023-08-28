package telran.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorTcpServerAppl {
	static final int PORT=5000;
	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server is listening to port " + PORT);
		while(true) {
			Socket socket = serverSocket.accept();
			clientRun(socket);
		}

	}
	private static void clientRun(Socket socket) {
		try(BufferedReader reader =
				new BufferedReader
				(new InputStreamReader(socket.getInputStream()));
				PrintStream writer = new PrintStream(socket.getOutputStream())) {
			while(true) {
				String line = reader.readLine();
				if(line == null) {
					System.out.println("client closed normally connection");
					break;
				}
				String response = getResponse(line);
				writer.println(response);
			}
			
		} catch (Exception e) {
			System.out.println("client closed abnormally connection");
		}
		
	}
	private static String getResponse(String line) {
		// <operation type>#<operand 1>#<operand 2>
		//operation type := "add" | "minus" | "plus" | "multiply" | "divide"
		
		String response = "Wrong request structure, usage: <operation type>#<operand 1>#<operand 2>";
		String [] tokens = line.split("#");
		if(tokens.length == 3) {
			try {
				double[] operands = getOperands(tokens);
				response = switch(tokens[0]) {
				case "add" -> Double.toString(operands[0] + operands[1]);
				case "minus" -> Double.toString(operands[0] - operands[1]);
				case "multiply" -> Double.toString(operands[0] * operands[1]);
				case "divide" -> Double.toString(operands[0] / operands[1]);
				default -> "wrong request type";
				};
			} catch (Exception e) {
				response = e.getMessage();
			}
		}
		
		return response;
	}
	private static double[] getOperands(String[] tokens)throws Exception {
		try {
			double op1 = Double.parseDouble(tokens[1]);
			double op2 = Double.parseDouble(tokens[2]);
			return new double[] {op1, op2};
		} catch (NumberFormatException e) {
			throw new Exception ("all operands must be any numbers " + e.getMessage());
		}
	}

}
