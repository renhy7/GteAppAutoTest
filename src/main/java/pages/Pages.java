package pages;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Pages {

	public AndroidDriver<?> driver;

	public Pages(AndroidDriver<?> driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
          
	// 启动页面的 “开启体验”按钮元素
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/button")
	public WebElement StartUsingAPP;

	// Login page
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/iv_left")
	public WebElement Home_PersonInfo;

	@FindBy(how = How.ID, using = "com.gantang.gantang:id/iv_head")
	public WebElement LeftNavigation_PersonImage;

	@FindBy(how = How.ID, using = "com.gantang.gantang:id/edt_account")
	public WebElement Login_InputMobile;

	@FindBy(how = How.CLASS_NAME, using = "android.widget.Button")
	public WebElement StartupPage_OpenAccess;

	@FindBy(how = How.ID, using = "com.gantang.gantang:id/edt_char")
	public WebElement Login_InputValidateCode;

	@FindBy(how = How.ID, using = "com.gantang.gantang:id/get_code")
	public WebElement Login_GetMessageCodeButton;

	@FindBy(how = How.ID, using = "com.gantang.gantang:id/edt_password")
	public WebElement Login_InputPassword;

	@FindBy(how = How.ID, using = "com.gantang.gantang:id/login")
	public WebElement Login_LoginButton;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/agree")
	public WebElement Login_UserAgreementButton;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/tv_set")
	public WebElement LeftNavigation_More;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/btn_exit")
	public WebElement LeftNavigation_More_SignOut;

	// 新手引导页面的元素
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/start")
	public WebElement NoviceGuide_NextClick;

	// 左侧导航栏昵称的元素
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/tv_phone")
	public WebElement LeftNavigation_PhoneNumber;

	// 首页元素
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/title_name")
	public WebElement Home_Title;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/btn_area_query")
	public WebElement Home_AreaQuery;
	@FindBy(how = How.XPATH, using = "//android.widget.RelativeLayout[@resource-id=\"com.gantang.gantang:id/rl_start_clean\"]/android.widget.ImageView[1]")
	public WebElement Home_StartStationDelete;
	@FindBy(how = How.XPATH, using = "//android.widget.EditText[@resource-id=\"com.gantang.gantang:id/start_add\"]")
	public WebElement Home_StartStationInput;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@text=\"中关村(地铁站)\"]")
	public WebElement Home_StartStationName;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@text=\"十八里店\"]")
	public WebElement Home_StartStationNameNo;

	@FindBy(how = How.XPATH, using = "//android.widget.RelativeLayout[@resource-id=\"com.gantang.gantang:id/rl_end_clean\"]/android.widget.ImageView[1]")
	public WebElement Home_EndStationDelete;
	@FindBy(how = How.XPATH, using = "//android.widget.EditText[@resource-id=\"com.gantang.gantang:id/end_add\"]")
	public WebElement Home_EndStationInput;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@text=\"黄村(公交站)\"]")
	public WebElement Home_EndStationName;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@text=\"丰台科技园(地铁站)\"]")
	public WebElement Home_EndStationNameNo;
	@FindBy(how = How.XPATH, using = "//android.widget.RelativeLayout[@resource-id=\"com.gantang.gantang:id/btn_classcar_query\"]/android.widget.ImageView[1]")
	public WebElement Home_StationQueryButton;

	// 全部区域查询页面
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/edt_station_name")
	public WebElement AreaQuery_QueryInput;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/iv_search")
	public WebElement AreaQuery_QueryButton;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/tv_area_name")
	public WebElement AreaQuery_RouteName;

	// 线路查询
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/tv_buy")
	public WebElement RouteQuery_ChooseSeatButton;
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/line_sure")
	public WebElement RouteQuery_ConfirmStationButton;

	// 座位预定
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@text=\"9\"]")
	public WebElement BookSeat_SelectSeat;
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@resource-id=\"com.gantang.gantang:id/btn_ok\"]")
	public WebElement BookSeat_ConfirmSeat;

	// 支付页面
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@resource-id=\"com.gantang.gantang:id/pay_money\"]")
	public WebElement Pay_ConfirmPayButton;

	// 确认交易
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@resource-id=\"com.tencent.mm:id/dyg\"]")
	public WebElement Pay_NowPayButton;

	// 支付成功页面，返回商家
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@resource-id=\"com.tencent.mm:id/drs\"]")
	public WebElement Pay_GoBackAppButton;

	// 支付成功后，点击返回商家，成功返回APP，且跳转的页面正常
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@resource-id=\"com.gantang.gantang:id/tv_order_state\"]")
	public WebElement Pay_JudeGoBackAPP;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@resource-id=\"com.gantang.gantang:id/tv_continue_ticket\"]")
	public WebElement PaySuccess_ContinueBus;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@resource-id=\"com.gantang.gantang:id/tv_look_order\"]")
	public WebElement PaySuccess_ViewOrders;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@resource-id=\"com.gantang.gantang:id/tv_join_chat\"]")
	public WebElement PaySuccess_JoinChat;
	@FindBy(how = How.XPATH, using = "//android.widget.LinearLayout[@resource-id=\"com.gantang.gantang:id/tv_share\"]")
	public WebElement PaySuccess_ShareRedPacket;

	// 我的订单页面
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/tv_pay")
	public WebElement MyOrder_OrderStatus;

	// 登录微信
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@resource-id=\"android:id/text1\"]")
	public WebElement WeChat_TitleLoginWeChat;
	@FindBy(how = How.XPATH, using = "//android.widget.EditText[@resource-id=\"com.tencent.mm:id/ht\" and @text=\"请填写微信号/QQ号/邮箱\"]")
	public WebElement WeChat_Username;
	@FindBy(how = How.XPATH, using = "//android.widget.LinearLayout[@resource-id=\"com.tencent.mm:id/byb\"]/android.widget.EditText[1]")
	public WebElement WeChat_Password;
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@resource-id=\"com.tencent.mm:id/byd\"]")
	public WebElement WeChat_Login;

	// 查询结果页面
	// @FindBy(how = How.XPATH, using =
	// "//android.widget.TextView[@resource-id=\"com.gantang.gantang:id/title_name\"]")
	@FindBy(how = How.ID, using = "com.gantang.gantang:id/title_name")
	public WebElement QueryResult_Title;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@resource-id=\"com.gantang.gantang:id/tv_line_name\"]")
	public WebElement QueryResult_ResultData;
	@FindBy(how = How.XPATH, using = "//android.widget.ImageView[@resource-id=\"com.gantang.gantang:id/iv_left\"]")
	public WebElement QueryResult_Return;

	// 线路定制
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@resource-id=\"com.gantang.gantang:id/custom_open\"]")
	public WebElement RoutesCustom_ApplyForOpen;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@resource-id=\"com.gantang.gantang:id/line_search\"]")
	public WebElement RoutesCustom_AreaQuery;

	// 允许安装赶趟巴士APP
	@FindBy(how = How.XPATH, using = "//android.widget.Button[@resource-id=\"android:id/button1\"]")
	public WebElement PermitInstall;

	// 热门搜索
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@text=\"中关村 \"]")
	public WebElement HotArea_ZGCButton;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@text=\"呼家楼\"]")
	public WebElement HotArea_HJLButton;
	@FindBy(how = How.XPATH, using = "//android.widget.TextView[@text=\"022中关村-西红门-黄村\"]")
	public WebElement HotArea_ZGCResult;
	@FindBy(how = How.XPATH, using = "//android.widget.ImageView[@resource-id=\"com.gantang.gantang:id/log_zhuan\"]")
	public WebElement HotArea_HJLResult;
	@FindBy(how = How.XPATH, using = "//android.widget.ImageView[@resource-id=\"com.gantang.gantang:id/area_query_back\"]")
	public WebElement HotArea_QueryBack;

}
