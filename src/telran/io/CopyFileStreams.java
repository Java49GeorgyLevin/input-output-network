package telran.io;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class CopyFileStreams implements CopyFile {
	private int bufferLength;
	public CopyFileStreams(int bufferLength) {
		super();
		this.bufferLength = bufferLength;
	}


	@Override
	public void copy(String pathToSource, String pathToDestination) throws Exception{
		try (InputStream input = new FileInputStream(pathToSource);
				OutputStream output = new FileOutputStream(pathToDestination)) {			
			byte[] buf = new byte[bufferLength];
			int length;
			while((length = input.read(buf)) > 0) {
				output.write(buf, 0, length);
			}
			Path path = Path.of(pathToDestination);
			long size = Files.size(path);
			System.out.printf("output file %s - %s b\n", path, size);			
		} 

	}

}
