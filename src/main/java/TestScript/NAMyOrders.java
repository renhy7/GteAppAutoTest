package TestScript;

import io.appium.java_client.TouchAction;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pages.Pages;
import redis.clients.jedis.Jedis;
import CommonFunction.ScreenFailtureListener;


@Listeners(ScreenFailtureListener.class)
public class NAMyOrders extends SupperTestClass {
	   
	
	@Test(alwaysRun = true)
	public void NAMyOrders(){
		try{
			Pages pages = new Pages(driver);
			Jedis redis = common.getRedisConnection();
			redis.select(15);
			redis.set("cmn:verify:18618268747:code", "12345");
			pages.Home_PersonInfo.click();
			if(common.checkElementDisplays(driver, pages.LeftNavigation_PhoneNumber, 10)){
				pages.LeftNavigation_More.click();
				pages.LeftNavigation_More_SignOut.click();
				common.Login(driver, pages, mobile, defaultPassword);
			}else{
				pages.LeftNavigation_PersonImage.click();
				common.Login(driver, pages, mobile, defaultPassword);
			}
			pages.Home_PersonInfo.click();
			pages.LeftNavigation_MyOrders.click();
			if(pages.MyOrders_ToRide.getText().equals("022中关村-西红门-黄村")){
				Assert.assertTrue(true, "购买的车票在待乘车页面……");
			}else{
				Assert.fail("我的订单，待乘车页面，校验订单失败……");
			}
			//我的订单-->待支付
			Double a1 = 360.5 / 720;
			Double b1 = 188.8 / 1280;
			
			//我的订单-->全部订单
			Double a2 = 600.2 / 720;
			Double b2 = 188.8 / 1280;
			
			int width = driver.manage().window().getSize().width;
			int height = driver.manage().window().getSize().height;
			
			Duration duration = Duration.ofSeconds(3);
			new TouchAction(driver).press((int)(a1*width), (int)(b1*height)).waitAction(duration).release().perform();
			
			if(common.checkElementDisplays(driver, pages.MyOrders_GoBook, 10)){
				pages.MyOrders_GoBook.click();
				pages.Home_AreaQuery.click();
				Double a3 = 378.5 / 720;
				Double b3 = 664.5 /1280;
				new TouchAction(driver).press((int)(a3*width), (int)(b3*height)).waitAction(duration).release().perform();
				pages.RouteQuery_ChooseSeatButton.click();
				
			}
			
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
