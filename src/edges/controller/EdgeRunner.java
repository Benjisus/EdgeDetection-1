package edges.controller;

public class EdgeRunner
{

	public static void main(String[] args) 
	{
		EdgeDetector detector = new EdgeDetector(null);
		detector.setUseGradient(true);
		detector.setThreshold(300);
		detector.start();
	}

}
