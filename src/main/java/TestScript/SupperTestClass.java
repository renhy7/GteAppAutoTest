package TestScript;

import io.appium.java_client.android.AndroidDriver;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import CommonFunction.CommonFunction;
import Logger.Dailylog;

public abstract class SupperTestClass {
	public AndroidDriver driver;
	public CommonFunction common;

	@BeforeClass
	public void SetupStartService() throws IOException, InterruptedException {
		common.StartAppiumService();
		driver = common.getAndroidDriver();
	}

	@AfterClass
	public void closeAllServices() throws IOException, InterruptedException {

		Runtime.getRuntime().exec("taskkill /F /IM node.exe");

	}
}
