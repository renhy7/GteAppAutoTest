package TestScript;

import net.sf.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.Pages;
import redis.clients.jedis.Jedis;
import Logger.Dailylog;

public class NASignInAndSignOut extends SupperTestClass {

	private String mobile = "18618268747";

	@Test
	public void NALgoin() {

		try {
			Thread.sleep(3000);
			Pages pages = new Pages(driver);

			if (common.checkElementDisplays(driver,
					pages.StartupPage_OpenAccess, 5)) {
				pages.StartupPage_OpenAccess.click();
			}

			Dailylog.logInfo("判断是否是首次启动："
					+ (common.checkElementDisplays(driver,
							pages.Home_PersonInfo, 5) || common
							.checkElementDisplays(driver,
									pages.Login_LoginButton, 5)));

			if (!(common.checkElementDisplays(driver, pages.Home_PersonInfo, 5) || common
					.checkElementDisplays(driver, pages.Login_LoginButton, 5))) {
				common.moveToLeft(driver, 4);
				pages.StartUsingAPP.click();
			}

			pages.Home_PersonInfo.click();
			if (common.checkElementDisplays(driver,
					pages.LeftNavigation_PhoneNumber, 5)) {
				pages.LeftNavigation_More.click();
				pages.LeftNavigation_More_SignOut.click();
			} else {
				pages.LeftNavigation_PersonImage.click();
			}

			// 验证手机号码不能为空
			pages.Login_InputMobile.clear();
			pages.Login_InputValidateCode.clear();
			pages.Login_InputPassword.clear();
			pages.Login_LoginButton.click();
			common.assertToast("手机号码不能为空");

			// 获取短信验证码，没有输入图形验证码，提示：请输入图片中的字符！
			pages.Login_InputMobile.sendKeys(mobile);
			pages.Login_GetMessageCodeButton.click();
			common.assertToast("请输入图片中的字符!");

			// 输入错误的图形验证码，提示：请输入图片中正确的内容
			pages.Login_InputValidateCode.sendKeys("XXXXX");
			pages.Login_GetMessageCodeButton.click();
			common.assertToast("请输入图片中正确的内容");

			// 验证短信验证码不能为空
			pages.Login_InputValidateCode.clear();
			pages.Login_InputValidateCode.sendKeys(common
					.getPhotoAndMessageValidate("cmn:verify:" + mobile
							+ ":imgcode", 15));
			pages.Login_InputPassword.clear();
			pages.Login_LoginButton.click();
			common.assertToast("验证码不能为空");

			// 验证手机号和短信验证码是否匹配,提示：请输入正确的手机号和验证码
			pages.Login_InputPassword.sendKeys("123456");
			pages.Login_LoginButton.click();
			Dailylog.logInfo(common.getPhotoAndMessageValidate("cmn:verify:"
					+ mobile + ":code", 15));
			if (common.getPhotoAndMessageValidate("cmn:verify:" + mobile
					+ ":code", 15) == null) {
				common.assertToast("请输入正确的手机号和验证码");
			} else {
				common.assertToast("请输入正确的验证码");
			}

			pages.Login_InputMobile.clear();
			pages.Login_InputMobile.sendKeys(mobile);
			pages.Login_InputValidateCode.clear();
			pages.Login_InputValidateCode.sendKeys(common
					.getPhotoAndMessageValidate("cmn:verify:" + mobile
							+ ":imgcode", 15));
			String beforeStr = pages.Login_GetMessageCodeButton.getText();
			Dailylog.logInfo("beforeStr" + beforeStr);
			pages.Login_GetMessageCodeButton.click();
			// common.assertToast("验证码已发送，请注意查收");
			Thread.sleep(5000);
			String afterStr = pages.Login_GetMessageCodeButton.getText();
			Assert.assertFalse(beforeStr.equals(afterStr), "获取短信验证码失败！！！！");
			pages.Login_InputPassword.clear();
			pages.Login_InputPassword.sendKeys(common
					.getPhotoAndMessageValidate("cmn:verify:" + mobile
							+ ":code", 15));
			pages.Login_UserAgreementButton.click();
			pages.Login_LoginButton.click();
			common.assertToast("请接受用户协议");
			pages.Login_UserAgreementButton.click();
			pages.Login_LoginButton.click();
			// 新手引导页面，第一步
			if (common.checkElementDisplays(driver,
					pages.NoviceGuide_NextClick, 5)) {
				pages.NoviceGuide_NextClick.click();
			}
			// 新手引导页面，第二步
			if (common.checkElementDisplays(driver,
					pages.NoviceGuide_NextClick, 5)) {
				pages.NoviceGuide_NextClick.click();
			}
			// 新手引导页面，第三步
			if (common.checkElementDisplays(driver,
					pages.NoviceGuide_NextClick, 5)) {
				pages.NoviceGuide_NextClick.click();
			}

			Assert.assertTrue(common.checkElementDisplays(driver,
					pages.Home_PersonInfo, 10), "Login success……");

			// 注销登录
			if (common.checkElementDisplays(driver, pages.Home_PersonInfo, 5)) {
				pages.Home_PersonInfo.click();
			}
			pages.LeftNavigation_More.click();
			pages.LeftNavigation_More_SignOut.click();
			Assert.assertTrue(common.checkElementExists(driver,
					pages.Login_LoginButton, 5), "Sign out success……");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
