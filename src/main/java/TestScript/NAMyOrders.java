package TestScript;

import io.appium.java_client.TouchAction;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.Pages;
import redis.clients.jedis.Jedis;
import CommonFunction.CommonFunction;
import CommonFunction.ScreenFailtureListener;
import Logger.Dailylog;

@Listeners(ScreenFailtureListener.class)
public class NAMyOrders extends SupperTestClass {
	private String mobile = "18618268747";
	private String defaultPassword = "12345";
	private String query = "022";
	private String deleteRoutesPlanssql = "DELETE FROM `service_number_plans` WHERE service_date = '"
			+ CommonFunction.getNowDateFormatyyyyMMdd()
			+ "' AND frequency_number = '" + query + "'";
	private String deleteUserOrders = "DELETE FROM  `service_orders` WHERE passenger_id = (SELECT id FROM passenger WHERE mobile = '"
			+ mobile + "')";
	private String lineName = "022中关村-西红门-黄村";

	@Test(alwaysRun = true)
	public void NAMyOrders() {
		try {
			Pages pages = new Pages(driver);
			Jedis redis = common.getRedisConnection();

			// 我的订单-->待支付
			double a1 = 360.5 / 720;
			double b1 = 188.8 / 1280;

			// 我的订单-->全部订单
			double a2 = 600.2 / 720;
			double b2 = 188.8 / 1280;

			int width = driver.manage().window().getSize().width;
			int height = driver.manage().window().getSize().height;

			Duration duration = Duration.ofSeconds(3);
			redis.select(15);
			redis.set("cmn:verify:18618268747:code", "12345");
			pages.Home_PersonInfo.click();
			if (common.checkElementDisplays(driver,
					pages.LeftNavigation_PhoneNumber, 10)) {
				pages.LeftNavigation_More.click();
				pages.LeftNavigation_More_SignOut.click();
				pages.Login_InputMobile.clear();
				pages.Login_InputMobile.sendKeys(mobile);    
				pages.Login_InputPassword.clear();
				pages.Login_InputPassword.sendKeys(defaultPassword);
				pages.Login_LoginButton.click();
			} else {
				pages.LeftNavigation_PersonImage.click();
				pages.Login_InputMobile.clear();
				pages.Login_InputMobile.sendKeys(mobile);
				pages.Login_InputPassword.clear();
				pages.Login_InputPassword.sendKeys(defaultPassword);
				pages.Login_LoginButton.click();
			}
			pages.Home_PersonInfo.click();
			pages.LeftNavigation_MyOrders.click();

			// 验证待乘车tab页面数据
			if (common.checkElementDisplays(driver, pages.MyOrders_GoBook, 10)) {
				pages.MyOrders_GoBook.click();
				pages.Home_AreaQuery.click();

				// 创建数据，给线路创建计划
				String insertIntoRoutesPlanssql = "INSERT INTO `service_number_plans` "
						+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
						+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
						+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
						+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
						+ CommonFunction.getNowDateFormatyyyyMMdd()
						+ "',"
						+ "'21:30:00','16.8','','1','0','"
						+ CommonFunction.getNowTime()
						+ "','"
						+ CommonFunction.getNowTime()
						+ "','22:15:00',"
						+ "'1','0','0.00','"
						+ query
						+ "','"
						+ CommonFunction.getNowDateFormatyyyyMMdd()
						+ " 21:30:00','"
						+ CommonFunction.getNowDateFormatyyyyMMdd()
						+ " 22:15:00','1','1')";
				bookTicket(pages, deleteRoutesPlanssql,
						insertIntoRoutesPlanssql);
				if (common.checkElementDisplays(driver,
						pages.OrdersSuccess_LookOrder, 10)) {
					pages.OrdersSuccess_LookOrder.click();
					Assert.assertTrue(pages.MyOrders_OrderName.getText()
							.equals(lineName), "待乘车页面订单验证成功……");
				} else {
					Assert.fail("支付完成后，跳转订单支付成功页面失败……");
				}
				// 跳转到全部订单页面
				new TouchAction(driver)
						.press((int) (a2 * width), (int) (b2 * height))
						.waitAction(duration).release().perform();
				// Thread.sleep(3000);
				if (pages.MyOrders_OrderName.getText().equals(lineName)) {
					Assert.assertTrue(true, "验证全部订单tab待乘车状态数据成功……");
				} else {
					Assert.fail("验证全部订单tab待乘车状态数据失败……");
				}
				common.Delete(deleteUserOrders);
			} else {
				common.Delete(deleteUserOrders);
				Thread.sleep(3000);
				common.pullToRefresh(driver);
				if (common.checkElementDisplays(driver, pages.MyOrders_GoBook,
						10)) {
					pages.MyOrders_GoBook.click();
					pages.Home_AreaQuery.click();

					// 创建数据，给线路创建计划
					String insertIntoRoutesPlanssql = "INSERT INTO `service_number_plans` "
							+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
							+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
							+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
							+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
							+ CommonFunction.getNowDateFormatyyyyMMdd()
							+ "',"
							+ "'21:30:00','16.8','','1','0','"
							+ CommonFunction.getNowTime()
							+ "','"
							+ CommonFunction.getNowTime()
							+ "','22:15:00',"
							+ "'1','0','0.00','"
							+ query
							+ "','"
							+ CommonFunction.getNowDateFormatyyyyMMdd()
							+ " 21:30:00','"
							+ CommonFunction.getNowDateFormatyyyyMMdd()
							+ " 22:15:00','1','1')";
					bookTicket(pages, deleteRoutesPlanssql,
							insertIntoRoutesPlanssql);
					if (common.checkElementDisplays(driver,
							pages.OrdersSuccess_LookOrder, 10)) {
						pages.OrdersSuccess_LookOrder.click();
						Assert.assertTrue(pages.MyOrders_OrderName.getText()
								.equals(lineName), "待乘车页面订单验证成功……");
					} else {
						Assert.fail("支付完成后，跳转订单支付成功页面失败……");
					}
					// 跳转到全部订单页面
					new TouchAction(driver)
							.press((int) (a2 * width), (int) (b2 * height))
							.waitAction(duration).release().perform();
					// Thread.sleep(3000);
					if (pages.MyOrders_OrderName.getText().equals(lineName)) {
						Assert.assertTrue(true, "验证全部订单tab待乘车状态数据成功……");
					} else {
						Assert.fail("验证全部订单tab待乘车状态数据失败……");
					}
				}
				common.Delete(deleteUserOrders);
			}

			// 进入待支付tab页面
			new TouchAction(driver)
					.press((int) (a1 * width), (int) (b1 * height))
					.waitAction(duration).release().perform();

			// 验证待支付订单tab页数据
			if (common.checkElementDisplays(driver, pages.MyOrders_GoBook, 10)) {
				pages.MyOrders_GoBook.click();
				pages.Home_AreaQuery.click();
				// 创建数据，给线路创建计划
				String insertIntoRoutesPlanssql = "INSERT INTO `service_number_plans` "
						+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
						+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
						+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
						+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
						+ CommonFunction.getNowDateFormatyyyyMMdd()
						+ "',"
						+ "'21:30:00','16.8','','1','0','"
						+ CommonFunction.getNowTime()
						+ "','"
						+ CommonFunction.getNowTime()
						+ "','22:15:00',"
						+ "'1','0','0.01','"
						+ query
						+ "','"
						+ CommonFunction.getNowDateFormatyyyyMMdd()
						+ " 21:30:00','"
						+ CommonFunction.getNowDateFormatyyyyMMdd()
						+ " 22:15:00','1','1')";
				bookTicket(pages, deleteRoutesPlanssql,
						insertIntoRoutesPlanssql);
				if (common.checkElementDisplays(driver,
						pages.CancelPay_ReturnPrevious, 10)) {
					pages.CancelPay_ReturnPrevious.click();
					pages.OrdersSuccess_LookOrder.click();
					if (pages.MyOrders_OrderName.getText().equals(lineName)
							&& pages.CancelPay_NowPay.getText().equals("立即支付")) {
						Assert.assertTrue(true, "待支付订单页面验证成功……");
					} else {
						Assert.fail("生成的待支付订单有问题……");
					}
				} else {
					Assert.fail("支付跳转微信页面失败……");
				}
				// 跳转到全部订单tab页
				new TouchAction(driver)
						.press((int) (a2 * width), (int) (b2 * height))
						.waitAction(duration).release().perform();
				// Thread.sleep(3000);
				if (pages.MyOrders_OrderName.getText().equals(lineName)) {
					Assert.assertTrue(true, "验证全部订单tab待支付状态数据成功……");
				} else {
					Assert.fail("验证全部订单tab待支付状态数据失败……");
				}

			} else {
				common.Delete(deleteUserOrders);
				common.pullToRefresh(driver);
				pages.MyOrders_GoBook.click();
				pages.Home_AreaQuery.click();
				// 创建数据，给线路创建计划
				String insertIntoRoutesPlanssql = "INSERT INTO `service_number_plans` "
						+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
						+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
						+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
						+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
						+ CommonFunction.getNowDateFormatyyyyMMdd()
						+ "',"
						+ "'21:30:00','16.8','','1','0','"
						+ CommonFunction.getNowTime()
						+ "','"
						+ CommonFunction.getNowTime()
						+ "','22:15:00',"
						+ "'1','0','0.01','"
						+ query
						+ "','"
						+ CommonFunction.getNowDateFormatyyyyMMdd()
						+ " 21:30:00','"
						+ CommonFunction.getNowDateFormatyyyyMMdd()
						+ " 22:15:00','1','1')";
				bookTicket(pages, deleteRoutesPlanssql,
						insertIntoRoutesPlanssql);
				if (common.checkElementDisplays(driver,
						pages.CancelPay_ReturnPrevious, 10)) {
					pages.CancelPay_ReturnPrevious.click();
					pages.OrdersSuccess_LookOrder.click();
					if (pages.MyOrders_OrderName.getText().equals(lineName)
							&& pages.CancelPay_NowPay.getText().equals("立即支付")) {
						Assert.assertTrue(true, "待支付订单页面验证成功……");
					} else {
						Assert.fail("生成的待支付订单有问题……");
					}
				} else {
					Assert.fail("支付跳转微信页面失败……");
				}
				// 跳转到全部订单tab页
				new TouchAction(driver)
						.press((int) (a2 * width), (int) (b2 * height))
						.waitAction(duration).release().perform();
				// Thread.sleep(3000);
				if (pages.MyOrders_OrderName.getText().equals(lineName)) {
					Assert.assertTrue(true, "验证全部订单tab待乘车状态数据成功……");
				} else {
					Assert.fail("验证全部订单tab待乘车状态数据失败……");
				}
			}
			for (int i = 1; i <= 7; i++) {
				Thread.sleep(50000); // 等待待支付订单，进入失效状态
				common.pullToRefresh(driver);
			}

			// 校验订单从待支付订单，转变失效状态
			if (!(pages.MyOrder_OrderStatus.getText().equals("已失效"))) {
				Assert.fail("订单从待支付订单到失效订单错误……");
			}
			common.Delete(deleteUserOrders);

			// 订单状态从待乘车状态到交易完成状态
			if (!common.checkElementDisplays(driver, pages.MyOrders_GoBook, 10)) {
				common.Delete(deleteUserOrders);
				common.pullToRefresh(driver);
			}
			pages.MyOrders_GoBook.click();
			pages.Home_AreaQuery.click();
			// 创建数据，给线路创建计划
			String insertIntoRoutesPlanssql = "INSERT INTO `service_number_plans` "
					+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
					+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
					+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
					+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ "',"
					+ "'21:30:00','16.8','','1','0','"
					+ CommonFunction.getNowTime()
					+ "','"
					+ CommonFunction.getNowTime()
					+ "','22:15:00',"
					+ "'1','0','0.00','"
					+ query
					+ "','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ " 21:30:00','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ " 22:15:00','1','1')";
			bookTicket(pages, deleteRoutesPlanssql, insertIntoRoutesPlanssql);
			if (common.checkElementDisplays(driver,
					pages.OrdersSuccess_LookOrder, 10)) {
				pages.OrdersSuccess_LookOrder.click();
				Assert.assertTrue(
						pages.MyOrders_OrderName.getText().equals(lineName),
						"待乘车页面订单验证成功……");
			} else {
				Assert.fail("支付完成后，跳转订单支付成功页面失败……");
			}
			// 跳转到全部订单页面
			new TouchAction(driver)
					.press((int) (a2 * width), (int) (b2 * height))
					.waitAction(duration).release().perform();
			// Thread.sleep(3000);
			if (pages.MyOrders_OrderName.getText().equals(lineName)) {
				Assert.assertTrue(true, "验证全部订单tab待乘车状态数据成功……");
			} else {
				Assert.fail("验证全部订单tab待乘车状态数据失败……");
			}
			String updateplanssql = "UPDATE `service_number_plans` "
					+ "SET service_time =DATE_FORMAT(SUBDATE(NOW(),INTERVAL 1 HOUR),'%H:%i'), "
					+ "last_time = DATE_FORMAT(SUBDATE(NOW(),INTERVAL 1 HOUR),'%H:%i') "
					+ " WHERE service_date =DATE_FORMAT(NOW(),'%Y-%m-%d')  AND frequency_number = '022';";

			common.insertInto(updateplanssql);
			common.pullToRefresh(driver);

			if (!pages.MyOrders_OrderType.getText().equals("交易完成")) {
				Assert.fail("订单从待乘车状态到交易完成状态失败……？");
			}
			common.Delete(deleteUserOrders);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bookTicket(Pages pages, String deleteRoutesPlanssql,
			String insertIntoRoutesPlanssql) {
		CommonFunction.Delete(deleteRoutesPlanssql); // 先删除线路计划
		CommonFunction.insertInto(insertIntoRoutesPlanssql); // 再进行插入线路计划
		pages.AreaQuery_QueryInput.clear();
		pages.AreaQuery_QueryInput.sendKeys(query);
		pages.AreaQuery_QueryButton.click();
		pages.AreaQuery_RouteName.click();
		pages.RouteQuery_ChooseSeatButton.click();
		if (CommonFunction.checkElementDisplays(driver,
				pages.RouteQuery_ConfirmStationButton, 5)) {
			pages.RouteQuery_ConfirmStationButton.click();
		}
		WebElement BookSeat_SelectData = driver.findElement(By
				.xpath("//android.widget.TextView[@text=\""
						+ CommonFunction.getNowDateFormatMM() + "\"]"));
		if (CommonFunction.checkElementDisplays(driver, BookSeat_SelectData, 5)) {
			BookSeat_SelectData.click();
		}
		pages.BookSeat_SelectSeat.click();
		pages.BookSeat_ConfirmSeat.click();
		pages.BookSeat_ConfirmSeat.click();
		pages.Pay_ConfirmPayButton.click();
	}

}
