package telrean.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputOutputTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void pathTests() {
		Path pathParent = Path.of("./DIRS/DIR1");
		System.out.println(pathParent
				.toAbsolutePath()
				.normalize()				
				.getName(3)
				);
	}
	
	@Test
	void displayDirContentTest() {
		displayDirContent(Path.of("DIRS"), 3);
	}
	
	@Test
	void displayListDirContentTest() {
		displayListDirContent(Path.of("DIRS"), 4);
	}

	private void displayDirContent(Path dirPath, int maxDepth) {
		if(!Files.isDirectory(dirPath)) {
			throw new IllegalArgumentException();
		}
		try {
			Files.walk(dirPath, maxDepth)			
			.forEach(p ->  {
				int offset = p.getNameCount();				
				System.out.printf("%s %s - %s\n", 
						" ".repeat(offset * 4) , p.getFileName(), Files.isDirectory(p) ? "dir" : "file");
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("The path is not the path to a directory");
		}
	}
	
	
	private void displayListDirContent(Path dirPath, int maxDepth) {
		if(!Files.isDirectory(dirPath)) {
			throw new IllegalArgumentException();			
		}
		
		final int depth = --maxDepth;
		int offset = dirPath.getNameCount();
		printFiles(dirPath.getFileName(), offset, 'd');

		if(maxDepth > 0) {		
			try {
				Files.list(dirPath)
				.forEach(p -> { 
					if(!Files.isDirectory(p)) {
						printFiles(p.getFileName(), offset, 'f');						
					} else {
						displayListDirContent(p, depth);
						} 
					});
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void printFiles(Path fileName, int offset, Character dF) {
		System.out.printf("%s %s - %s\n", " ".repeat(offset * 2), fileName,
			dF.equals('d') ? "dir" : "file");	
	}

}
