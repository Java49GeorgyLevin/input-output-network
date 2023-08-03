package telran.io;

import java.io.*;

public class CopyFileStreams implements CopyFile {
private final int bufferLength;
	public CopyFileStreams(int bufferLength) {
	super();
	this.bufferLength = bufferLength;
}
	@Override
	public void copy(String pathToSource, String pathToDestination){
		try(FileInputStream input = new FileInputStream(pathToSource);
				FileOutputStream output = new FileOutputStream(pathToDestination)) {
			int length = 0;
			byte[] buffer = new byte[bufferLength];
			while((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			
		}catch (IOException e) {
			throw new RuntimeException(e.toString());
		}

	}

}
