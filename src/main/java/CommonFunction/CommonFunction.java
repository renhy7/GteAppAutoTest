package CommonFunction;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import pages.Pages;
import redis.clients.jedis.Jedis;
import sun.misc.BASE64Decoder;
import Logger.Dailylog;
import TestData.PropsUtils;

public class CommonFunction {
	private static String adbDeviceNumber = "";
	private static String platform = "Android";
	private static String apppkg = "com.gantang.gantang";
	private static String appActivity = "com.gantang.gantang.activitys.WelComeActivity";
	private static String AppiumServerIP = "http://127.0.0.1:4723/wd/hub";
	private static String time = "3000";
	private static AndroidDriver driver;
	private static String platformVersion = "6.1";
	private static String redisHost = "60.205.150.51";
	private static int redisPort = 6379;
	private static String redisPassword = "Gte321654";
	private static Jedis redis;
	private static String mobile = "18618268747";
	private static String url = "https://weixin.gtcx.top/ebus/app/ph/getImgInputValidateCode";
	private static Connection connection;
	private static PreparedStatement ps;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// StartAppiumService();
		// driver = getAndroidDriver();
		// moveToLeft(driver,4);
		// Pages pages = new Pages(driver);
		// pages.StartUsingAPP.click();

		// driver.findElementById("com.gantang.gantang:id/iv_left").click();
		//
		// driver.findElementById("com.gantang.gantang:id/iv_head").click();
		// String insertsql = "INSERT INTO `service_number_plans` "
		// +
		// "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
		// +
		// " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
		// +
		// "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
		// +
		// " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"+getNowDateFormatyyyyMMdd()+"',"
		// +
		// "'18:30:00','16.8','','1','0','"+getNowTime()+"','"+getNowTime()+"','20:15:00',"
		// +
		// "'1','0','0.01','022','"+getNowDateFormatyyyyMMdd()+" 18:30:00','"+getNowDateFormatyyyyMMdd()+" 20:15:00','1','1')";
		// insertInto(insertsql);
		// String deletesql =
		// "DELETE FROM `service_number_plans` WHERE service_date = '"+getNowDateFormatyyyyMMdd()+"' AND frequency_number = '022'";
		// Delete(deletesql);

		// int width = driver.manage().window().getSize().width;
		// int height = driver.manage().window().getSize().height;
		// Dailylog.logInfo("width:" + width + "height:" + height);
		String hotAreaJedis = getRedisResponseValue(
				"city:city_area_index_chirld:1:0", 15);
		Dailylog.logInfo("hotAreastr:" + hotAreaJedis);
		JSONArray hotAreaJson = (JSONArray) JSONArray.fromObject(hotAreaJedis);
		String[] hotAreaName = new String[hotAreaJson.size()];
		for (int i = 0; i < hotAreaJson.size(); i++) {
			JSONObject obj = (JSONObject) hotAreaJson.get(i);
			hotAreaName[i] = obj.getString("text");
		}
		for (String str : hotAreaName) {
			Dailylog.logInfo("hotAreaName:" + str);
		}

	}

	/**
	 * @author renhaiyang
	 * @param
	 * @Usage get android device number
	 * 
	 */
	public static String getAndroidDeviceNumber() {

		BufferedReader br = null;
		try {
			Process process = Runtime.getRuntime().exec("adb devices");
			br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			Dailylog.logInfo("exe cmd response device munber:" + sb);
			adbDeviceNumber = sb.toString().split(" ")[4].split("	")[0]
					.toString();
			Dailylog.logInfo("adbDeviceNumber:" + adbDeviceNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return adbDeviceNumber;
	}

	/**
	 * @author renhaiyang
	 * @param
	 * @Usage 启动appium服务
	 * 
	 */

	public static void StartAppiumService() {

		String cmd = "cmd /c start appium -a 127.0.0.1 -p 4723 --platform-name "
				+ platform
				+ "  --platform-version 6.1 --device-name "
				+ getAndroidDeviceNumber()
				+ " --app-activity com.gantang.gantang.activitys.WelComeActivity --app-pkg "
				+ apppkg + " --automation-name Appium --log-no-color";
		Run(cmd);
		try {
			Thread.sleep(6000);
			Dailylog.logInfo("开启appium服务成功！！！！");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void runCmd(String str) {
		Runtime runtime = Runtime.getRuntime();

		try {
			runtime.exec(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void Run(final String cmd) {
		new Thread(new Runnable() {
			public void run() {
				runCmd(cmd);
			}
		}).start();
	}

	/**
	 * @author renhaiyang
	 * @param
	 * @Usage 获取Android Driver
	 */
	public static AndroidDriver getAndroidDriver() {

		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "apps");
		File app = new File(appDir, "gantangClient.apk");

		Dailylog.logInfo("获取Android Driver ！！！！！");
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability(CapabilityType.BROWSER_NAME, ""); // add

		capabilities.setCapability("platformName", "Android"); // add

		capabilities.setCapability("deviceName", getAndroidDeviceNumber());

		capabilities.setCapability("platformVersion", platformVersion);

		capabilities.setCapability("noSign", true);

		capabilities.setCapability("app", app.getAbsolutePath());

		capabilities.setCapability("noReset", true);

		capabilities.setCapability("unicodeKeyboard", "True");

		capabilities.setCapability("resetKeyboard", "True");

		capabilities.setCapability("udid", getAndroidDeviceNumber());

		capabilities.setCapability("appPackage", "com.gantang.gantang"); // add

		capabilities.setCapability("appActivity", appActivity);

		capabilities.setCapability("appWaitActivity",
				"com.gantang.gantang.activitys.WelComeActivity"); // add

		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
				AutomationName.ANDROID_UIAUTOMATOR2); // add

		try {
			driver = new AndroidDriver(new URL(AppiumServerIP), capabilities);
			Thread.sleep(Integer.parseInt(time));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	/**
	 * @author renhaiyang
	 * @param
	 * @Usage 发送post请求
	 * 
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @author renhaiyang
	 * @param driver
	 * @param element
	 * @param timeoutInSeconds
	 * @usage 检查元素是否展示出来
	 * @return
	 */
	public static boolean checkElementDisplays(AndroidDriver driver,
			WebElement element, int timeoutInSeconds) {
		try {
			driver.manage().timeouts()
					.implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		} finally {
			driver.manage()
					.timeouts()
					.implicitlyWait(PropsUtils.getDefaultTimeout(),
							TimeUnit.SECONDS);
		}
	}

	public static boolean checkElementExists(AndroidDriver driver,
			WebElement element, int timeoutInSeconds) {
		try {
			driver.manage().timeouts()
					.implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} finally {
			driver.manage()
					.timeouts()
					.implicitlyWait(PropsUtils.getDefaultTimeout(),
							TimeUnit.SECONDS);
		}
	}

	/**
	 * @author renhaiyang
	 * @param imgStr
	 * @Usage base64字符串转化成图片
	 * @return
	 */

	public static boolean GenerateImage(String imgStr) {
		// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) {// 图像数据为空
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = "D:\\new.jpg";// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @author renhaiyang
	 * @Usage 获取redis 链接
	 * @return
	 */

	public static Jedis getRedisConnection() {
		redis = new Jedis(redisHost, redisPort);
		redis.auth(redisPassword);
		return redis;
	}

	public static void assertToast(String message) {
		final WebDriverWait wait = new WebDriverWait(driver, 10);
		Assert.assertNotNull(wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath(".//*[contains(@text,'"
						+ message + "')]"))));
	}

	/**
	 * @author renhaiyang
	 * @Usage 获取Android driver
	 * @return driver
	 */
	public static AndroidDriver getAndroidWeiXinDriver() {

		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "apps");
		File chromedriver = new File(appDir, "chromedriver.exe");

		DesiredCapabilities caps = new DesiredCapabilities();
		// 表示我们的设备名字，在安卓下这个参数必须有，但是值可以随便写
		caps.setCapability(MobileCapabilityType.DEVICE_NAME,
				getAndroidDeviceNumber());// “devicesName”
		// 表示appium服务的session过期时间，单位是秒，默认是60秒
		caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);
		// 表示在安装的时候不对应用进行重签名操作，因为有的应用在重签名之后就无法正常使用
		caps.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
		// 下面两项是用来使用appium自带的unicode输入法，来隐藏键盘并且支持中文输入
		caps.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
		caps.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);

		caps.setCapability(MobileCapabilityType.UDID, getAndroidDeviceNumber());
		// File chromedriver=new File("X:/不要照抄，这里写你自己的路径/chromedriver2.20.exe");
		caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
				"com.tencent.mm");
		caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
				"com.tencent.mm.ui.LauncherUI");
		caps.setCapability(MobileCapabilityType.NO_RESET, true);
		caps.setCapability(
				AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS,
				true);
		caps.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
				chromedriver.getAbsolutePath());
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("androidProcess", "com.tencent.mm:tools");
		caps.setCapability(ChromeOptions.CAPABILITY, options);
		try {
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
					caps);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver;
	}

	/**
	 * @author renhaiyang
	 * @Usage 获取从redis里的取值
	 * @param str
	 * @param index
	 * @return
	 */

	public static String getRedisResponseValue(String str, int index) {
		Jedis redis = new Jedis();
		redis = getRedisConnection();
		redis.select(index);
		String result = redis.get(str);
		return result;
	}

	/**
	 * @author renhaiyang
	 * @Usage 左移动页面
	 * @param driver
	 * @param pageNumber
	 */

	public static void moveToLeft(AndroidDriver driver, int pageNumber) {
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		Duration duration = Duration.ofSeconds(1);
		Dailylog.logInfo("width:" + width + "\n height:" + height);

		driver.findElement(By.className("android.widget.ImageView"))
				.getLocation();
		for (int i = 1; i <= pageNumber; i++) {
			new TouchAction(driver).press(width - 10, height / 2)
					.waitAction(duration).moveTo(width / 4, height / 2)
					.release().perform();
		}

	}

	/**
	 * @author renhaiyang
	 * @usage 用户登录APP
	 * @param driver
	 * @param pages
	 * @param mobile
	 * @param defaultPassword
	 */
	public static void Login(AndroidDriver driver, Pages pages, String mobile,
			String defaultPassword) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pages.Home_PersonInfo.click();

		if (checkElementDisplays(driver, pages.LeftNavigation_PhoneNumber, 5)) {
			return;
		} else {
			pages.LeftNavigation_PersonImage.click();
			pages.Login_InputMobile.clear();
			pages.Login_InputMobile.sendKeys(mobile);
			pages.Login_InputValidateCode.clear();
			pages.Login_InputValidateCode.sendKeys(getRedisResponseValue(
					"cmn:verify:" + mobile + ":imgcode", 15));
			pages.Login_InputPassword.clear();
			pages.Login_InputPassword.sendKeys(defaultPassword);
			pages.Login_LoginButton.click();
		}
	}

	/**
	 * @author renhaiyang
	 * @usage 获取当前日期的day
	 * 
	 * @return
	 */

	public static int getNowDateFormatMM() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @author renhaiyang
	 * @Usage 获取当前日期的格式 :yyyy-MM-dd
	 * 
	 * @return
	 */

	public static String getNowDateFormatyyyyMMdd() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * @author renhaiyang
	 * @Usage 获取当前时间格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */

	public static String getNowTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * @author renhaiyang
	 * @usage 获取MySQL链接
	 * @return
	 */
	public static Connection getMysqlConnection() {
		Properties properties = new Properties();

		Connection connection = null;
		try {
			FileInputStream in = new FileInputStream("Conf.properties");
			properties.load(in);
			String mysqldriver = properties.getProperty("mysqldriver");
			String mysqlurl = properties.getProperty("mysqlurl");
			String mysqluser = properties.getProperty("mysqluser");
			String mysqlpassword = properties.getProperty("mysqlpassword");
			Class.forName(mysqldriver);
			connection = DriverManager.getConnection(mysqlurl, mysqluser,
					mysqlpassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * @author renhaiyang
	 * @usage 插入数据
	 * @param sql
	 */

	public static void insertInto(String sql) {
		connection = getMysqlConnection();
		try {
			ps = connection.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @author renhaiyang
	 * @Usage 删除数据
	 * 
	 * @param sql
	 */
	public static void Delete(String sql) {
		connection = getMysqlConnection();
		try {
			ps = connection.prepareStatement(sql);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void loginWechat(AndroidDriver driver, Pages pages,
			String mobile, String password) {
		pages.WeChat_Username.clear();
		pages.WeChat_Username.sendKeys(mobile);
		pages.WeChat_Password.clear();
		pages.WeChat_Password.sendKeys(password);
		if (checkElementDisplays(driver, pages.WeChat_Login, 10)) {
			pages.WeChat_Login.click();
		} else {
			Assert.assertTrue(false, "微信登录失败……");
		}
	}

}
