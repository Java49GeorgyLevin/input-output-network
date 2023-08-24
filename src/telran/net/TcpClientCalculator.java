package telran.net;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.function.Predicate;

import telran.view.*;

public class TcpClientCalculator {
	static final String HOST = "localhost";
	static final int PORT = 5500;

	public static void main(String[] args) throws Exception{
		try(
				Socket socket = new Socket(HOST, PORT);
				PrintStream writer = new PrintStream(socket.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))				
				){
			InputOutput io = new ConsoleInputOutput();
						
			Menu menu = new Menu("TCP client calculator", Item.of("To do calculations", io1 -> {
						Set<String> operations = new HashSet<>(Arrays.asList("+", "-", "*", "/"));
						String operationsType = io1.readString("Enter a operation type " + operations, "The operation is wrong", operations);
						Predicate<String> predicateStr = s -> s.matches("[0-9]+\\.?[0-9]*");
						String firstFloat = io1.readString("Enter a first float", "This is not float", predicateStr);
						String secondFloat = io1.readString("Enter a second float", "This is not float", predicateStr);
						writer.println(String.format("%s#%s#%s", operationsType, firstFloat, secondFloat));
						try {
							String response = reader.readLine();
							io1.writeLine(response);
							
						} catch (IOException e) {
							throw new RuntimeException(e.toString());
						}
						
					}),
					Item.ofExit());
					menu.perform(io);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
