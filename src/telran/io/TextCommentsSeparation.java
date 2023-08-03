package telran.io;
import java.io.*;

public class TextCommentsSeparation {

	public static void main(String[] args) {
		
		int len = args.length;
		String A = "There is no resource file.";
		String B = "There is no path to text.";
		String C = "There is no path to comments.";
		if(len < 1) {
			throw new RuntimeException(A + " " + B + " " + C);
		};
		if(len < 2) {
			throw new RuntimeException(B + " " + C);
		};
		if(len < 3) {
			throw new RuntimeException(C);
		}

		try(BufferedReader reader = new BufferedReader(new FileReader(args[0]));
				PrintWriter printText = new PrintWriter(args[1]);
				PrintWriter printComments = new PrintWriter(args[2])) {			
			reader.lines()			
			.forEach(s -> {
				if(s.contains("//")) {
					printComments.println(s);
				} else {
					printText.println(s);
				}			
			}
		);			

			
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}

	}

}
