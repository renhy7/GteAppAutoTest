package pages;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Pages {
	
	public AndroidDriver driver;
	public Pages(AndroidDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	//启动页面的 “开启体验”按钮元素
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/button")
	public WebElement StartUsingAPP;
	
	// Login page
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/iv_left" )
	public WebElement Home_PersonInfo;
	
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/iv_head")
	public WebElement LeftNavigation_PersonImage;
	
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/edt_account")
	public WebElement Login_InputMobile;
	
	@FindBy(how = How.CLASS_NAME, using ="android.widget.Button")
	public WebElement StartupPage_OpenAccess;
	
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/edt_char")
	public WebElement Login_InputValidateCode;
	
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/get_code")
	public WebElement Login_GetMessageCodeButton;
	
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/edt_password")
	public WebElement Login_InputPassword;
	
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/login" )
	public WebElement Login_LoginButton;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/agree")
	public WebElement Login_UserAgreementButton;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/tv_set")
	public WebElement LeftNavigation_More;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/btn_exit")
	public WebElement LeftNavigation_More_SignOut;
	
	//新手引导页面的元素
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/start")
	public WebElement NoviceGuide_NextClick;
	
	//左侧导航栏昵称的元素
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/tv_phone")
	public WebElement LeftNavigation_PhoneNumber;
	
	//首页元素
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/title_name")
	public WebElement Home_Title;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/btn_area_query")
	public WebElement Home_AreaQuery;
	
	
	//全部区域查询页面
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/edt_station_name")
	public WebElement AreaQuery_QueryInput;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/iv_search")
	public WebElement AreaQuery_QueryButton;
	@FindBy(how = How.ID,using = "com.gantang.gantang:id/tv_area_name")
	public WebElement AreaQuery_RouteName;
	
	
	
	//线路查询
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/tv_buy")
	public WebElement RouteQuery_ChooseSeatButton;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/line_sure")
	public WebElement RouteQuery_ConfirmStationButton;
	
	
	//座位预定
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@text=\"9\"]")
	public WebElement BookSeat_SelectSeat;
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@resource-id=\"com.gantang.gantang:id/btn_ok\"]")
	public WebElement BookSeat_ConfirmSeat;
	
	
	//支付页面
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@resource-id=\"com.gantang.gantang:id/pay_money\"]")
	public WebElement Pay_ConfirmPayButton;
	
	//确认交易
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@resource-id=\"com.tencent.mm:id/dyg\"]")
	public WebElement Pay_NowPayButton;
	@FindBy(how = How.XPATH, using = "//android.widget.RelativeLayout[@resource-id=\"com.tencent.mm:id/ckn\"]/android.widget.RelativeLayout[1]")
	public WebElement Pay_InputPayPassword;
	
	
	
	
	
	
	
	
	

}
