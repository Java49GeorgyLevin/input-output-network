package telran.io;
import java.io.*;

public class TextCommentsSeparation {

	public static void main(String[] args) {
		// args = {text_and_comments.txt, text.txt, comments.txt}
		int len = args.length;
		if(len < 1) {
			new RuntimeException("There is no resource file.");
		};
		if(len < 2) {
			new RuntimeException("There is no path to text.");
		};
		if(len < 3) {
			new RuntimeException("There is no path to comments.");
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
