package telran.performance;

import telran.io.CopyFile;

public class CopyPerformanceTest extends PerformanceTest {

String pathToSource;
String pathToDestination;
CopyFile copyFile;
public CopyPerformanceTest(String testName, int nRuns, String pathToSource, String pathToDestination,
		CopyFile copyFile) {
	super(testName, nRuns);
	this.pathToSource = pathToSource;
	this.pathToDestination = pathToDestination;
	this.copyFile = copyFile;
}
	@Override
	protected void runTest() {
		copyFile.copy(pathToSource, pathToDestination);

	}

}
