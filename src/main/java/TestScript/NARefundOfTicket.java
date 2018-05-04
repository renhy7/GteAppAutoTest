package TestScript;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pages.Pages;
import redis.clients.jedis.Jedis;
import CommonFunction.CommonFunction;
import CommonFunction.ScreenFailtureListener;
import Logger.Dailylog;


@Listeners(ScreenFailtureListener.class)
public class NARefundOfTicket extends SupperTestClass {
	private String mobile = "18618268747";
	private String defaultPassword = "12345";
	private String query = "022";
	private String deleteRoutesPlanssql = "DELETE FROM `service_number_plans` WHERE service_date = '"
			+ CommonFunction.getNowDateFormatyyyyMMdd()
			+ "' AND frequency_number = '" + query + "'";
	private String deleteUserOrders = "DELETE FROM  `service_orders` WHERE passenger_id = (SELECT id FROM passenger WHERE mobile = '"
			+ mobile + "')";
	private String lineName = "022中关村-西红门-黄村";
	private String originMoney;
	String deleteOrderssqlRecords = "DELETE FROM `service_order_records`"
			+ " WHERE  passenger_id = (SELECT id FROM passenger WHERE mobile = '18618268747')";
	@Test(alwaysRun = true)
	public void NARefundOfTicket(){
		try{
			Pages pages = new Pages(driver);
			Jedis redis = common.getRedisConnection();
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
			//必须进入首页才能取获取余额信息
			originMoney = common.getBalance(pages);
			Dailylog.logInfo("进入APP，查看用户所剩余额："+originMoney);
			//票价为0元的订单进行退款
			String insertIntoRoutesPlansZero = "INSERT INTO `service_number_plans` "
					+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
					+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
					+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
					+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ "',"
					+ "'23:30:00','16.8','','1','0','"
					+ CommonFunction.getNowTime()
					+ "','"
					+ CommonFunction.getNowTime()
					+ "','23:55:00',"
					+ "'1','0','0.00','"
					+ query
					+ "','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ " 23:30:00','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ " 23:55:00','1','1')";
			common.Delete(deleteUserOrders);
			common.Delete(deleteOrderssqlRecords);
			common.bookTicket(pages, deleteRoutesPlanssql, insertIntoRoutesPlansZero, query, 0, (float)0.00);
			pages.OrdersSuccess_LookOrder.click();
			pages.MyOrders_OrderName.click();
			pages.MyOrders_RefundTicket.click();
			if(common.checkElementDisplays(driver, pages.MyOrders_RefundTicket_Sure, 10)){
				pages.MyOrders_RefundTicket_Sure.click();
			}
			Thread.sleep(3000);
			pages.Home_Left.click();
			Thread.sleep(3000);
			common.myOrdersSwitchTabs(driver, pages, 3);
			Thread.sleep(5000);
			if(!pages.MyOrder_OrderStatus.getText().equals("已退款")){
				Assert.fail("退款状态不对，不对的值是："+pages.MyOrder_OrderStatus.getText());
			}
			pages.Home_Left.click();
			Thread.sleep(3000);
			String zeroRefundTicketMoney= common.getBalance(pages);
			if(!originMoney.equals(zeroRefundTicketMoney)){
				Assert.fail("退款完成后，原始的余额与退款后的余额不一致，原始余额："+originMoney+"&退款后的余额zeroMoney:"+zeroRefundTicketMoney);
			}
			common.Delete(deleteUserOrders);
			common.Delete(deleteOrderssqlRecords);
			
			//优惠券购买退票
			String insertIntoRoutesPlansCoupon = "INSERT INTO `service_number_plans` "
					+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
					+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
					+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
					+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ "',"
					+ "'23:30:00','16.8','','1','0','"
					+ CommonFunction.getNowTime()
					+ "','"
					+ CommonFunction.getNowTime()
					+ "','23:55:00',"
					+ "'1','0','6.88','"
					+ query
					+ "','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ " 23:30:00','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ " 23:55:00','1','1')";
			common.Delete(deleteUserOrders);
			common.Delete(deleteOrderssqlRecords);
			common.bookTicket(pages, deleteRoutesPlanssql, insertIntoRoutesPlansCoupon, query, 2, (float)10.88);
			pages.PaySuccess_ReturnHome.click();
			Thread.sleep(3000);
			String RefundTicketMoneyCouponBefore = common.getBalance(pages);
			if(! RefundTicketMoneyCouponBefore.equals(zeroRefundTicketMoney)){
				Assert.fail("优惠券购票完成后，余额与之前对比不一致……");
			}
			Thread.sleep(3000);
			pages.Home_PersonInfo.click();
			pages.LeftNavigation_MyOrders.click();
			pages.MyOrders_OrderName.click();
			pages.MyOrders_RefundTicket.click();
			if(common.checkElementDisplays(driver, pages.MyOrders_RefundTicket_Sure, 10)){
				pages.MyOrders_RefundTicket_Sure.click();
			}
			Thread.sleep(3000);
			pages.Home_Left.click();
			Thread.sleep(3000);
			common.myOrdersSwitchTabs(driver, pages, 3);
			Thread.sleep(5000);
			if(!pages.MyOrder_OrderStatus.getText().equals("已退款")){
				Assert.fail("退款状态不对，不对的值是："+pages.MyOrder_OrderStatus.getText());
			}
			pages.Home_Left.click();
			Thread.sleep(3000);
			pages.Home_PersonInfo.click();
			String RefundTicketMoneyCouponAfter = common.getBalance(pages);
			if(! RefundTicketMoneyCouponAfter.equals(RefundTicketMoneyCouponBefore)){
				Assert.fail("用优惠券购票，退款之后，余额不对……");
			}
			System.out.print("end…………");
			
			
			//余额支付退款
			String insertIntoRoutesPlansBalance = "INSERT INTO `service_number_plans` "
					+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
					+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
					+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
					+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ "',"
					+ "'23:30:00','16.8','','1','0','"
					+ CommonFunction.getNowTime()
					+ "','"
					+ CommonFunction.getNowTime()
					+ "','23:55:00',"
					+ "'1','0','1.28','"
					+ query
					+ "','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ " 23:30:00','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ " 23:55:00','1','1')";
			common.bookTicket(pages, deleteRoutesPlanssql, insertIntoRoutesPlansBalance, query, 1, (float)0.00);
			pages.PaySuccess_ReturnHome.click();
			String balanceRefundTicketMoneyBefore = common.getBalance(pages);
			if(!(Float.parseFloat(balanceRefundTicketMoneyBefore)+(float)(1.28) == Float.parseFloat(zeroRefundTicketMoney))){
				Assert.fail("用余额进行购票，购票完成后，钱包的余额对不上……");
			}
			Thread.sleep(3000);
			pages.Home_PersonInfo.click();
			pages.LeftNavigation_MyOrders.click();
			pages.MyOrders_OrderName.click();
			pages.MyOrders_RefundTicket.click();
			if(common.checkElementDisplays(driver, pages.MyOrders_RefundTicket_Sure, 10)){
				pages.MyOrders_RefundTicket_Sure.click();
			}
			Thread.sleep(3000);
			pages.Home_Left.click();
			Thread.sleep(3000);
			common.myOrdersSwitchTabs(driver, pages, 3);
			Thread.sleep(5000);
			if(!pages.MyOrder_OrderStatus.getText().equals("已退款")){
				Assert.fail("退款状态不对，不对的值是："+pages.MyOrder_OrderStatus.getText());
			}
			pages.Home_Left.click();
			Thread.sleep(3000);
			String balanceRefundTicketMoneyAfter = common.getBalance(pages);
			if(! balanceRefundTicketMoneyAfter.equals(zeroRefundTicketMoney)){
				Assert.fail("用余额购票，退款之后，余额不对……");
			}
			
			//微信支付退款
			String insertIntoRoutesPlansWechat = "INSERT INTO `service_number_plans` "
					+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
					+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
					+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
					+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ "',"
					+ "'23:30:00','16.8','','1','0','"
					+ CommonFunction.getNowTime()
					+ "','"
					+ CommonFunction.getNowTime()
					+ "','23:55:00',"
					+ "'1','0','1.28','"
					+ query
					+ "','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ " 23:30:00','"
					+ CommonFunction.getNowDateFormatyyyyMMdd()
					+ " 23:55:00','1','1')";
			common.bookTicket(pages, deleteRoutesPlanssql, insertIntoRoutesPlansWechat, query, 4, (float)0.00);
			pages.PaySuccess_ReturnHome.click();
			String WechatRefundTicketMoneyBefore = common.getBalance(pages);
			if(!(Float.parseFloat(WechatRefundTicketMoneyBefore)+(float)(1.28) == Float.parseFloat(zeroRefundTicketMoney))){
				Assert.fail("用微信进行购票，购票完成后，钱包的余额对不上……");
			}
			Thread.sleep(3000);
			pages.Home_PersonInfo.click();    
			pages.LeftNavigation_MyOrders.click();
			pages.MyOrders_OrderName.click();
			pages.MyOrders_RefundTicket.click();
			if(common.checkElementDisplays(driver, pages.MyOrders_RefundTicket_Sure, 10)){
				pages.MyOrders_RefundTicket_Sure.click();
			}
			Thread.sleep(3000);
			pages.Home_Left.click();
			Thread.sleep(3000);
			common.myOrdersSwitchTabs(driver, pages, 3);
			Thread.sleep(5000);
			if(!pages.MyOrder_OrderStatus.getText().equals("已退款")){
				Assert.fail("退款状态不对，不对的值是："+pages.MyOrder_OrderStatus.getText());
			}
			pages.Home_Left.click();
			Thread.sleep(3000);
			String WechatRefundTicketMoneyAfter = common.getBalance(pages);
			if(! balanceRefundTicketMoneyAfter.equals(zeroRefundTicketMoney)){
				Assert.fail("用微信购票，退款之后，余额不对……");
			}
			
		}catch(Exception e){
 			e.printStackTrace();
		}
	}

}
