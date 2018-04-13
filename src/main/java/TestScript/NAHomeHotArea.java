package TestScript;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pages.Pages;
import CommonFunction.CommonFunction;
import CommonFunction.ScreenFailtureListener;
import Logger.Dailylog;

@Listeners({ScreenFailtureListener.class})
public class NAHomeHotArea extends SupperTestClass {
	private String hotAreaJedis;
	private String query = "022";
	private String deleteRoutesPlanssql = "DELETE FROM `service_number_plans` WHERE service_date = '"
			+ CommonFunction.getNowDateFormatyyyyMMdd()
			+ "' AND frequency_number = '" + query + "'";
	private String insertIntoRoutesPlanssql = "INSERT INTO `service_number_plans` "
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
			+ CommonFunction.getNowDateFormatyyyyMMdd() + " 22:15:00','1','1')";

	@Test(alwaysRun = true)
	public void NAHomeHotArea() {
		Pages pages = new Pages(driver);

		hotAreaJedis = CommonFunction.getRedisResponseValue(
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
		// 判断是否有热门区域
		if (hotAreaName.length >= 1) {
			for (int j = 0; j < hotAreaName.length; j++) {
				// 判断热门区域是否在APP页面显示出来
				if (CommonFunction
						.checkElementDisplays(          
								driver,
								driver.findElementByXPath("//android.widget.TextView[@text=\""
										+ hotAreaName[j] + "\"]"), 10)) {
					Assert.assertTrue(true, "热门区域【" + hotAreaName[j]
							+ "】配置成功！！！");
				} else {
					Assert.fail("【" + hotAreaName[j]
							+ "】在APP首页无显示，设置失败，请查找原因……");
				}
			}
		} else {
			Dailylog.logInfo("NAHomeHotArea:-->" + "热门区域设置为0："
					+ hotAreaName.length);
		}
		CommonFunction.Delete(deleteRoutesPlanssql); // 先删除022线路计划
		CommonFunction.insertInto(insertIntoRoutesPlanssql); // 再进行插入022线路计划
		if (common.checkElementExists(driver, pages.HotArea_ZGCButton, 10)) {
			pages.HotArea_ZGCButton.click();
			if (common
					.checkElementDisplays(driver, pages.HotArea_ZGCResult, 10)) {
				Assert.assertTrue(true, "热门区域中关村搜索有数据……");
			} else {
				Assert.fail("热门区域搜索中关村，没有创建的022线路信息，请检查错误……");
			}
		} else {
			Assert.fail("中关村不是热门区域，请设置中关村为热门区域……");
		}
		pages.HotArea_QueryBack.click();
		if (common.checkElementDisplays(driver, pages.HotArea_HJLButton, 10)) {
			pages.HotArea_HJLButton.click();
			if (common
					.checkElementDisplays(driver, pages.HotArea_HJLResult, 10)) {
				Assert.assertTrue(true, "呼家楼热门区域无线路……");
			} else {
				Assert.fail("热门区域呼家楼站点有线路展示，实际情况是没有线路的……");
			}

		} else {
			Assert.fail("呼家楼不是热门区域，请设置呼家楼为热门区域……");
		}

	}

}
