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
				running = false;
				res = mapper.apply(resInput);

			} catch (Exception e) {
				writeLine(errorPrompt + ": " + e.getMessage());
				running = true;
			}

		} while (running);
		return res;
	}
	
	private <T> void minIllegalArgumentException(T min) {
		throw new IllegalArgumentException("must be not less than " + min);
	}

	private <T> void maxIllegalArgumentException(T max) {
		throw new IllegalArgumentException("must be not greater than " + max);
	}
	

	public int readInt(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Integer::parseInt);
	}

	public int readInt(String prompt, String errorPrompt, int min, int max) {
		return readObject(String.format("%s[%d - %d] ", prompt, min, max), errorPrompt,
				string -> {

			int res = Integer.parseInt(string);
			if (res < min) {
				minIllegalArgumentException(min);
			}
			if (res > max) {
				maxIllegalArgumentException(max);
			}
			return res;

		});
	}

	public long readLong(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Long::parseLong);
	}

	public long readLong(String prompt, String errorPrompt, long min, long max) {
		return readObject(String.format("%s[%d - %d]", prompt, min, max), errorPrompt,
				string -> {
					
				Long res = Long.parseLong(string);				
				if(res < min) {
					minIllegalArgumentException(min);
				}
				if(res > max) {
					maxIllegalArgumentException(max);
				}				
				return res;
		
				});
	}

	public String readString(String prompt, String errorPrompt, Predicate<String> predicate) {
			return readObject(String.format("%s: %s", prompt, predicate), errorPrompt, 
				string -> {		
				if(!predicate.test(string)) {
					throw new IllegalArgumentException("does not match the predicate");
				}
				return string;				
	});
	}

	public String readString(String prompt, String errorPrompt, Set<String> options) {
		return readString(String.format(prompt +" " + options.toString()), errorPrompt, options::contains);
	}

	public LocalDate readDate(String prompt, String errorPrompt) {		
		return readObject(prompt, errorPrompt, string -> LocalDate.parse(string));
	}

	public LocalDate readDate(String prompt, String errorPrompt, LocalDate from, LocalDate to) {
		return readObject(String.format("%s[%s - %s]", prompt, from, to), errorPrompt,
				string -> {
					LocalDate date = LocalDate.parse(string);
					
					if(date.isBefore(from)) {
						minIllegalArgumentException(from);
					}
					if(date.isAfter(to)) {
						maxIllegalArgumentException(to);
					}
					
					return date;
				});
	}

	public double readDouble(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Double::parseDouble);
	}
}
