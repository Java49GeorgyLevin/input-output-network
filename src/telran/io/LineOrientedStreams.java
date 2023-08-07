package telran.io;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;
public class LineOrientedStreams {
	private static final int N_PRINTS = 10_000_000;
	String hello = "Hello";
@Test
@Disabled
void smallFilePrintStream() throws IOException{
	PrintStream printStream = new PrintStream("printStream.txt") ;
	printStream.println("Hello World");
	printStream.close();
}
@Test
@Disabled
void smallFilePrintWriter() throws IOException{
	PrintWriter printWriter = new PrintWriter("printWriter.txt") ;
	printWriter.println("Hello World");
	printWriter.close();
}
@Test
@Disabled
void performancePrintStream() throws IOException{
	try(PrintStream stream = new PrintStream("bigFile")) {
	IntStream.range(0, N_PRINTS).forEach(i -> stream.println(hello));
	}
}
@Test
@Disabled
void performancePrintWriter() throws IOException{
	try(PrintWriter stream = new PrintWriter("bigFile")) {
	IntStream.range(0, N_PRINTS).forEach(i -> stream.println(hello));
	}
}
@Test
void sumNumberLines() throws IOException {
	int res = 0;
	
//	try(BufferedReader reader =
//			new BufferedReader(new FileReader("numbers.txt"))){
//		String line = null;
//		
//		while((line = reader.readLine()) != null) {
//			res += Integer.parseInt(line);
//		}
//	}
//	try(BufferedReader reader =
//			new BufferedReader(new FileReader("numbers.txt"))){
//		res = reader.lines().mapToInt(Integer::parseInt).sum();
//	}
	res = Files.lines(Path.of("numbers.txt")).mapToInt(Integer::parseInt).sum();
	assertEquals(35, res);
}
}
