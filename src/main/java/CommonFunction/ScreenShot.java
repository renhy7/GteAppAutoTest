package CommonFunction;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Reporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.appium.java_client.android.AndroidDriver;

public class ScreenShot {

	public static void takeScreenshot(String screenPath, AndroidDriver driver) {
		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(screenPath));
		} catch (IOException e) {
			System.out.println("Screen shot error: " + screenPath);
			Reporter.log("该错误可以查看截图：" + screenPath);
		}
	}

	public static void takeScreenshot(AndroidDriver driver) {
		Date date = new Date();
		String sj = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(date);

		String screenName = sj + ".jpg";
		File dir = new File("test-output/snapshot");
		if (!dir.exists())
			dir.mkdirs();
		String screenPath = dir.getAbsolutePath() + "/" + screenName;
		takeScreenshot(screenPath, driver);
	}

}
