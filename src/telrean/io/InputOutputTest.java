package telrean.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FilePermission;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputOutputTest {

	private static final Path FILE_PATH = Path.of("/Users/User/Documents/abc.txt");
	private static final String NOT_EXIST = "doesn't exist";
	private static final String NO_DIRECTORY = "no directory";

	@BeforeEach
	void setUp() throws Exception {
		Files.createFile(FILE_PATH);
	}
	@AfterEach
	void tearDown() throws Exception {
		Files.delete(FILE_PATH);
	}

	@Test
	void pathTests() {
		Path pathParent = Path.of("../..");
		System.out.println(pathParent.toAbsolutePath().normalize().getName(4));
	}

	@Test
	void displayDirContentTest() throws IOException {
		displayDirContent(Path.of(".."), 3);
		assertThrowsExactly(IllegalArgumentException.class,
				()->displayDirContent(Path.of("/Users/User/Doc"), 1), NOT_EXIST);
		
		assertThrowsExactly(IllegalArgumentException.class,
				()->displayDirContent(FILE_PATH, 1), NO_DIRECTORY);
		
	}

	private void displayDirContent(Path dirPath, int maxDepth) throws IOException {
		// Display content directory by using method walk of the class Files
//		simple directory name>
//       <simple file name> - file
//       <subdirectory name> - dir
//          <simple file name> - file
//          <subdirectory name> - dir
//                  
//             <simple file name> - file

		dirPath = dirPath.toAbsolutePath().normalize();
		if(!Files.exists(dirPath)) {
			throw new IllegalArgumentException("doesn't exist");
		}
		if (!Files.isDirectory(dirPath)) {
			throw new IllegalArgumentException("no directory");
		}
		int count = dirPath.getNameCount();
		try {
			Files.walk(dirPath, maxDepth)
					.forEach(p -> System.out.printf("%s%s - %s\n", " ".repeat(p.getNameCount() - count),
							p.getName(p.getNameCount() - 1), Files.isDirectory(p) ? "dir" : "file"));
		} catch (Exception e) {
			
		}

	}
}
