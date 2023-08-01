package telran.performance;

public abstract class PerformanceTest {
	private String testName;
	private int nRuns;
	public PerformanceTest(String testName, int nRuns) {
		super();
		this.testName = testName;
		this.nRuns = nRuns;
	}
	protected abstract void runTest();
	
	void run() {
		long start = System.currentTimeMillis();
		for(int i = 0;i < nRuns;i++) {
			runTest();
		}
		displayInfo(start, System.currentTimeMillis());
		
	}
	
	private void displayInfo(long start, long finish) {
		float time = (finish - start) / 1000;
		System.out.printf("test %s; Number of the runs: %s; Running time: %s, sec\n",
				testName, nRuns, time);		
	}

}
