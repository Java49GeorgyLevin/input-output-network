package telran.performance;

import telran.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyFilePerformanceApp {

	public static void main(String[] args) throws Exception{
		String pathName = args[0];
		Path pathToSource = Path.of(args[0]);
		int nRuns = 1;
		
		int[] array = {10_000, 100_000, 1_000_000, 100_000_000};
		
		try {
			long size = Files.size(pathToSource);
			System.out.printf("input file %s - %s b\n", pathName, size);
			
			for(int i = 0;i < array.length;i++) {
				int bufI = array[i];
				CopyFile copyFile = new CopyFileStreams(bufI);			
				PerformanceTest performanceTest = new CopyPerformanceTest("T" + bufI, nRuns, pathName, bufI + pathName, copyFile);
				performanceTest.run();
			
			}
		
		
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
