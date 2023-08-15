package telran.view.console;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class TestConsoleInputOutput {
	ConsoleInputOutput inputOutput = new ConsoleInputOutput();

	@Test
	void testReadInt() {
		int res = inputOutput.readInt("Input int:", "This input isn't int");
		inputOutput.writeLine("The integer = " + res);		
	}
	
	@Test
	void testReadIntMinMax() {
		int res = inputOutput.readInt("Input int:", "This input isn't int in the interval", 10, 100);
		inputOutput.writeLine("The integer = " + res);		
	}
	
	@Test
	void testReadStringPredaicate() {
		String res = inputOutput.readString("Input e-mail:", "This input does not match to e-mail",				
				s -> s.matches("\\w+@\\w+\\..{2}"));
		inputOutput.writeLine("The e-mail = " + res);		
	}
	
	@Test
	void testReadStringSet() {
		Set<String> opt = new HashSet<>(Arrays.asList("aaa", "bbb", "ccc"));
		String res = inputOutput.readString("Input from the Set:", "This input does not match to the Set", opt);
		inputOutput.writeLine("Input from the Set = " + res);
	}

}
