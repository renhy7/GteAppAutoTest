package TestScript;

import io.appium.java_client.android.AndroidDriver;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import CommonFunction.CommonFunction;

public abstract class SupperTestClass {
	public static AndroidDriver driver;
	public CommonFunction common;
	public String mobile;
	public String defaultPassword;

	@BeforeClass
	@Parameters({"mobile","defaultPassword"})
	public void setupParameters(String mobile, String defaultPassword){
		this.mobile = mobile;
		this.defaultPassword = defaultPassword;
		
	}
	public void SetupStartService() throws IOException, InterruptedException {
		common.StartAppiumService();
		driver = common.getAndroidDriver();
		Thread.sleep(6000);
	}

	@AfterClass
	public void closeAllServices() throws IOException, InterruptedException {

		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		
	}
}
