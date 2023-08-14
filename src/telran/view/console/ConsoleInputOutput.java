package telran.view.console;

import java.io.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class ConsoleInputOutput {
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private PrintStream output = System.out;

	public String readString(String prompt) {
		output.println(prompt);
		try {
			String res = input.readLine();
			return res;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void write(String string) {
		output.print(string);
	}

	public void writeLine(String string) {
		write(string + "\n");
	}

	public <T> T readObject(String prompt, String errorPrompt, Function<String, T> mapper) {
		boolean running = false;
		T res = null;
		do {
			String resInput = readString(prompt);
			try {
				res = mapper.apply(resInput);

			} catch (Exception e) {
				writeLine(errorPrompt + ": " + e.getMessage());
				running = true;
			}

		} while (running);
		return res;
	}

	public int readInt(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Integer::parseInt);
	}

	public int readInt(String prompt, String errorPrompt, int min, int max) {
		return readObject(String.format("%s[%d - %d] ", prompt, min, max), errorPrompt,
				string -> {

			int res = Integer.parseInt(string);
			if (res < min) {
				throw new IllegalArgumentException("must be not less than " + min);
			}
			if (res > max) {
				throw new IllegalArgumentException("must be not greater than " + max);
			}
			return res;

		});
	}

	public long readLong(String prompt, String errorPrompt) {
		// TODO
		return 0;
	}

	public long readLong(String prompt, String errorPrompt, long min, long max) {
		// TODO
		return 0;
	}

	public String readString(String prompt, String errorPrompt, Predicate<String> predicate) {
		// TODO
		return "";
	}

	public String readString(String prompt, String errorPrompt, Set<String> options) {
		// TODO
		return "";
	}

	public LocalDate readDate(String prompt, String errorPrompt) {
		// TODO
		return null;
	}

	public LocalDate readDate(String prompt, String errorPrompt, LocalDate from, LocalDate to) {
		// TODO
		return null;
	}

	public double readDouble(String prompt, String errorPrompt) {
		// TODO
		return 0;
	}
}
