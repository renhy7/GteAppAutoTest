package TestScript;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "apps/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		driver.get("http://www.baidu.com");
		
		String value = driver.findElement(By.id("su")).getAttribute("value");
		
		System.out.print(value);
		
		driver.findElement(By.id("kw")).clear();
		
		driver.findElement(By.id("kw")).sendKeys("java");
		
		driver.findElement(By.id("su")).click();
		
		
//		if(driver != null){
//			driver.quit();
//		}

	}

}
