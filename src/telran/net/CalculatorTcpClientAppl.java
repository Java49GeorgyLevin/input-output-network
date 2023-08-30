package telran.net;

import java.util.Arrays;
import java.util.HashSet;
import telran.view.*;


public class CalculatorTcpClientAppl {
	static final String HOST="localhost";
	static final int PORT=5000;
		public static void main(String[] args) throws Exception{
			try(TcpHandler tcpHandler = new TcpHandler(HOST, PORT)){
				 InputOutput io = new ConsoleInputOutput();
				 Menu menu = new Menu("Calculator Application", Item.of("send request", io1 -> {
					 HashSet<String> requests = new HashSet<>(Arrays.asList("add", "minus",
							 "multiply", "divide"));
					 String requestType = io1.readString("Enter operation type " + requests, "Wrong operation", requests);
					 double op1 = io1.readDouble("Enter first number", "Wrong number");
					 double op2 = io1.readDouble("Enter second number", "Wrong number");
					 double responseData = tcpHandler.send(requestType, new Double[] {op1, op2});
					 io1.writeLine("Response data: " + responseData);
				 }), Item.ofExit());
				 menu.perform(io);
			}

		}
}
