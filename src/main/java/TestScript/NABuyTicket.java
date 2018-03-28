package TestScript;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import pages.Pages;

public class NABuyTicket extends SupperTestClass {
	private String mobile = "18618268747";
	private String defaultPassword = "12345";
	private String query = "022";
	@Test
	public void  NABuyTicket(){
		
		
		
		try{
			Pages pages = new Pages(driver);
			common.Login(driver, pages, mobile, defaultPassword);
			if(!common.checkElementDisplays(driver, pages.Home_Title, 5)){
				pages.Home_PersonInfo.click();
			}
			pages.Home_AreaQuery.click();
			pages.AreaQuery_QueryInput.clear();
			pages.AreaQuery_QueryInput.sendKeys(query);
			pages.AreaQuery_QueryButton.click();
			pages.AreaQuery_RouteName.click();
			pages.RouteQuery_ChooseSeatButton.click();
			if(common.checkElementDisplays(driver, pages.RouteQuery_ConfirmStationButton, 5)){
				pages.RouteQuery_ConfirmStationButton.click();
			}
			WebElement BookSeat_SelectData = driver.findElement(By.xpath("//android.widget.TextView[@text=\""+common.getNowDateDay()+"\"]"));
			if(common.checkElementDisplays(driver, BookSeat_SelectData, 5)){
				BookSeat_SelectData.click();
			}
			
			pages.BookSeat_SelectSeat.click();
			pages.BookSeat_ConfirmSeat.click();
			pages.BookSeat_ConfirmSeat.click();
			pages.Pay_ConfirmPayButton.click();
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
