package TestScript;

import java.time.Duration;

import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.Pages;

public class NABuyTicket extends SupperTestClass {
	private String mobile = "18618268747";
	private String defaultPassword = "12345";
	private String query = "022";

	@Test
	public void NABuyTicket() {

		// 创建数据，给线路创建计划
		String insertIntoRoutesPlanssql = "INSERT INTO `service_number_plans` "
				+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
				+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
				+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
				+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
				+ common.getNowDateFormatyyyyMMdd() + "',"
				+ "'21:30:00','16.8','','1','0','" + common.getNowTime()
				+ "','" + common.getNowTime() + "','22:15:00',"
				+ "'1','0','0.01','" + query + "','"
				+ common.getNowDateFormatyyyyMMdd() + " 21:30:00','"
				+ common.getNowDateFormatyyyyMMdd() + " 22:15:00','1','1')";
		// 删除已经存在的计划
		String deleteRoutesPlanssql = "DELETE FROM `service_number_plans` WHERE service_date = '"
				+ common.getNowDateFormatyyyyMMdd()
				+ "' AND frequency_number = '" + query + "'";
		// 删除用户订单
		String deleteUserOrders = "DELETE FROM  `service_orders` WHERE passenger_id = (SELECT id FROM passenger WHERE mobile = '"
				+ mobile + "')";

		common.Delete(deleteUserOrders);
		common.Delete(deleteRoutesPlanssql); // 先删除线路计划
		common.insertInto(insertIntoRoutesPlanssql); // 再进行插入线路计划

		try {
			Pages pages = new Pages(driver);
			common.Login(driver, pages, mobile, defaultPassword);
			if (!common.checkElementDisplays(driver, pages.Home_Title, 5)) {
				pages.Home_PersonInfo.click();
			}
			pages.Home_AreaQuery.click();
			pages.AreaQuery_QueryInput.clear();
			pages.AreaQuery_QueryInput.sendKeys(query);
			pages.AreaQuery_QueryButton.click();
			pages.AreaQuery_RouteName.click();
			pages.RouteQuery_ChooseSeatButton.click();
			if (common.checkElementDisplays(driver,
					pages.RouteQuery_ConfirmStationButton, 5)) {
				pages.RouteQuery_ConfirmStationButton.click();
			}
			WebElement BookSeat_SelectData = driver.findElement(By
					.xpath("//android.widget.TextView[@text=\""
							+ common.getNowDateFormatMM() + "\"]"));
			if (common.checkElementDisplays(driver, BookSeat_SelectData, 5)) {
				BookSeat_SelectData.click();
			}

			pages.BookSeat_SelectSeat.click();
			pages.BookSeat_ConfirmSeat.click();
			pages.BookSeat_ConfirmSeat.click();
			pages.Pay_ConfirmPayButton.click();
			Thread.sleep(2000);
			if (common.checkElementDisplays(driver,
					pages.WeChat_TitleLoginWeChat, 10)) {
				common.loginWechat(driver, pages, "18618268747", "test123"); // 随便写的微信号，需要测试的时候，自己手动需要修改
			}
			Thread.sleep(10000);
			pages.Pay_NowPayButton.click();

			// 第一个密码的坐标系数：1
			Double a1 = 120.8 / 720;
			Double b1 = 873.2 / 1280;
			// 第二个密码的坐标系统:7
			Double a2 = 120.8 / 720;
			Double b2 = 1092.0 / 1280;
			// 第三个密码的坐标系数:5
			Double a3 = 351.5 / 720;
			Double b3 = 981.2 / 1280;
			// 第四个密码的坐标系数：1
			Double a4 = 120.8 / 720;
			Double b4 = 873.2 / 1280;
			// 第五个密码的坐标系数：6
			Double a5 = 603.2 / 720;
			Double b5 = 981.2 / 1280;
			// 第六个密码的坐标系数：5
			Double a6 = 351.5 / 720;
			Double b6 = 981.2 / 1280;

			int width = driver.manage().window().getSize().width;
			int height = driver.manage().window().getSize().height;
			Duration duration = Duration.ofSeconds(1);
			Thread.sleep(2000);
			new TouchAction(driver)
					.press((int) (a1 * width), (int) (b1 * height))
					.waitAction(duration).release().perform();
			Thread.sleep(2000);
			new TouchAction(driver)
					.press((int) (a2 * width), (int) (b2 * height))
					.waitAction(duration).release().perform();
			Thread.sleep(2000);
			new TouchAction(driver)
					.press((int) (a3 * width), (int) (b3 * height))
					.waitAction(duration).release().perform();
			Thread.sleep(2000);
			new TouchAction(driver)
					.press((int) (a4 * width), (int) (b4 * height))
					.waitAction(duration).release().perform();
			Thread.sleep(2000);
			new TouchAction(driver)
					.press((int) (a5 * width), (int) (b5 * height))
					.waitAction(duration).release().perform();
			Thread.sleep(2000);
			new TouchAction(driver)
					.press((int) (a6 * width), (int) (b6 * height))
					.waitAction(duration).release().perform();
			Thread.sleep(2000);
			if (common.checkElementExists(driver, pages.Pay_GoBackAppButton, 5)) {
				pages.Pay_GoBackAppButton.click();
			} else {
				Assert.assertTrue(false, "订单支付失败！！！！！！");
			}
			if (common
					.checkElementDisplays(driver, pages.Pay_JudeGoBackAPP, 10)) {
				Assert.assertTrue(true, "返回商家成功！！！！！！！");
			} else {
				Assert.assertTrue(false, "返回商家失败，且没有跳转订单支付成功页面！！！！");
			}
			if (common.checkElementDisplays(driver,
					pages.PaySuccess_ContinueBus, 10)) {
				Assert.assertTrue(true, "继续乘车菜单加载成功！！！");
			} else {
				Assert.assertTrue(false, "继续乘车菜单加载失败……");
			}
			if (common.checkElementDisplays(driver,
					pages.PaySuccess_ViewOrders, 10)) {
				Assert.assertTrue(true, "查看订单菜单加载成功！！！");
			} else {
				Assert.assertTrue(false, "查看订单菜单加载失败……");
			}
			if (common.checkElementDisplays(driver, pages.PaySuccess_JoinChat,
					10)) {
				Assert.assertTrue(true, "加入群聊菜单加载成功！！！");
			} else {
				Assert.assertTrue(false, "加入群聊菜单加载失败……");
			}
			if (common.checkElementDisplays(driver,
					pages.PaySuccess_ShareRedPacket, 10)) {
				Assert.assertTrue(true, "分享有礼菜单加载成功！！！");
			} else {
				Assert.assertTrue(true, "分享有礼菜单加载失败……");
			}

			pages.PaySuccess_ViewOrders.click();

			if (common.checkElementDisplays(driver, pages.MyOrder_OrderStatus,
					10)) {
				Assert.assertTrue(
						pages.MyOrder_OrderStatus.getText().equals("已支付"),
						"支付成功后订单状态显示正确！！！");
			} else {
				Assert.assertTrue(false, "订单支付成功后，我的订单页面，在待乘车tab页，没有订单生成……");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
