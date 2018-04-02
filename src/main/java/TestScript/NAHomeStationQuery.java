package TestScript;

import org.testng.Assert;
import org.testng.annotations.Test;

import Logger.Dailylog;
import pages.Pages;

public class NAHomeStationQuery extends SupperTestClass {
	private String startStationName = "中关村地铁";
	private String endStationName = "黄村(公交站)";
	private String resultData = "022中关村-西红门-黄村";
	private String query = "022";
	private String startStationName_NO = "十八里店";
	private String endStationName_No = "丰台科技园地铁站";
	
	// 创建数据，给线路创建计划
	String insertIntoRoutesPlanssql = "INSERT INTO `service_number_plans` "
			+ "(`buses_id`, `routes_id`, `drivers_id`, `service_date`, `service_time`, `price`,"
			+ " `weixin`, `is_enable`, `is_complete`, `create_time`, `update_time`, `last_time`, "
			+ "`discounts`, `is_discount`, `present_price`, `frequency_number`, `service_date_time`,"
			+ " `last_date_time`, `create_id`, `part_dept_id`) VALUES('170','57','55','"
			+ common.getNowDateFormatyyyyMMdd() + "',"
			+ "'21:30:00','16.8','','1','0','" + common.getNowTime() + "','"
			+ common.getNowTime() + "','22:15:00'," + "'1','0','0.01','"
			+ query + "','" + common.getNowDateFormatyyyyMMdd()
			+ " 21:30:00','" + common.getNowDateFormatyyyyMMdd()
			+ " 22:15:00','1','1')";
	// 删除已经存在的计划
	String deleteRoutesPlanssql = "DELETE FROM `service_number_plans` WHERE service_date = '"
			+ common.getNowDateFormatyyyyMMdd()
			+ "' AND frequency_number = '"
			+ query + "'";

	@Test
	public void NAHomeStationQuery() {
		try {
			Thread.sleep(8000);
			Pages pages = new Pages(driver);
			common.Delete(deleteRoutesPlanssql); // 先删除线路计划
			common.insertInto(insertIntoRoutesPlanssql); // 再进行插入线路计划
			//首页进行站点搜索，搜索的有结果
			pages.Home_StartStationDelete.click();
			pages.Home_StartStationInput.sendKeys(startStationName);
			if(common.checkElementDisplays(driver, pages.Home_StartStationName, 10))
			pages.Home_StartStationName.click();

			pages.Home_EndStationDelete.click();
			pages.Home_EndStationInput.sendKeys(endStationName);
			if(common.checkElementDisplays(driver, pages.Home_EndStationName, 10))
			pages.Home_EndStationName.click();

			pages.Home_StationQueryButton.click();
			
			Thread.sleep(3000); //必须添加等待时间，不然报页面元素已过时，需要添加等待时间。
			if (common.checkElementDisplays(driver,
					pages.QueryResult_Title, 10)) {
				if (pages.QueryResult_ResultData.getText().equals(resultData)) {
					Assert.assertTrue(true, "查询结果正确……");
				} else {
					Assert.fail("查询数据错误……");
				}

			} else {
				Assert.fail("进入查询结果页失败……");
			}
			pages.QueryResult_Return.click();
			//首页进行站点搜索，搜索无结果
			pages.Home_StartStationDelete.click();
			pages.Home_StartStationInput.sendKeys(startStationName_NO);
			if(common.checkElementDisplays(driver, pages.Home_StartStationNameNo, 10))
			pages.Home_StartStationNameNo.click();
			
			pages.Home_EndStationDelete.click();
			pages.Home_EndStationInput.sendKeys(endStationName_No);
			if(common.checkElementDisplays(driver, pages.Home_EndStationNameNo, 10))
			pages.Home_EndStationNameNo.click();
			
			pages.Home_StationQueryButton.click();
			
			Thread.sleep(3000); //必须添加等待时间，不然报页面元素已过时，需要添加等待时间。
			if (common.checkElementDisplays(driver,
					pages.QueryResult_Title, 10)) {
				if (common.checkElementDisplays(driver,
						pages.RoutesCustom_ApplyForOpen, 10)
						&& common.checkElementDisplays(driver,
								pages.RoutesCustom_AreaQuery, 10)) {
					Assert.assertTrue(true, "查询结果正确……");
				} else {
					Assert.fail("查询数据错误……");
				}

			} else {
				Assert.fail("进入查询结果页失败……");
			}
			pages.QueryResult_Return.click();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
