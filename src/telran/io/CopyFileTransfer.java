package telran.io;
import java.io.*;
public class CopyFileTransfer implements CopyFile {

	@Override
	public void copy(String pathToSource, String pathToDestination) {
		try(InputStream input = new FileInputStream(pathToSource);
				OutputStream output = new FileOutputStream(pathToDestination)) {
			input.transferTo(output);
		} catch(IOException e) {
			throw new RuntimeException(e.toString());
		}

	}

}
