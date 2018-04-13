package CommonFunction;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import Logger.Dailylog;
import TestScript.NAHomeHotArea;

public class ScreenFailtureListener extends TestListenerAdapter{

	@Override
	public void onTestFailure(ITestResult tr) {
		// TODO Auto-generated method stub
		Dailylog.logInfo("执行onTestFailure");
		ScreenShot.takeScreenshot(NAHomeHotArea.driver);
		super.onTestFailure(tr);
	}
	
	

}
