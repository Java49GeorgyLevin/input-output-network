package telran.performance;
import telran.io.CopyFile;

public class CopyPerformanceTest extends PerformanceTest {
	private String pathToSource;
	private String pathToDestination;
	private CopyFile copyFile;
	
	

	public CopyPerformanceTest(String testName, int nRuns, String pathToSource, String pathToDestination,
			CopyFile copyFile) {
		super(testName, nRuns);
		this.pathToSource = pathToSource;
		this.pathToDestination = pathToDestination;
		this.copyFile = copyFile;
	}



	@Override
	protected void runTest() {
		try {
		copyFile.copy(pathToSource, pathToDestination);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
