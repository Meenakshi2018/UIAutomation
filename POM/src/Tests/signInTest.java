
/***
 * Author:-Rashmi Menon                                                           Date:- 03/17/2015
 * WorkFlow:- 1. Admin logs in with valid credentials
 * 			  2. Launches Jenkins App
 * 
 */
package Tests;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import lib.Commons;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Pages.SignIn;
import Pages.SignOut;
import Pages.appProfile;
import Pages.deployApp;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class,
MethodListener.class })
public class signInTest
{
	protected static WebDriver driver;
	static String email,pwd,dep_name,dep_desc,dep_version,dep_tags,dep_env,dep_cloud,dep_aging,dep_metadata;
	Properties config = Commons.getprop("/Users/rashmi/Desktop/POM/src/resources/config.properties");
	
	{
		System.setProperty("atu.reporter.config",config.getProperty("atuRep"));
	}
	
	public signInTest() throws IOException
	{
		try 
		{
			FileReader loginReader = new FileReader(config.getProperty("LoginData"));
			JSONParser loginParser=new JSONParser();
			JSONObject loginObject = (JSONObject) loginParser.parse(loginReader);
			
			email=(String) loginObject.get("email");
			pwd= (String) loginObject.get("password");
			
			FileReader depReader = new FileReader(config.getProperty("DepData"));
			JSONParser depParser=new JSONParser();
			JSONObject depObject = (JSONObject) depParser.parse(depReader);
			
			dep_name=(String) depObject.get("depName");
			dep_desc= (String) depObject.get("depDesc");
			dep_version=(String) depObject.get("appVersion");
			dep_tags= (String) depObject.get("tags");
			dep_env=(String) depObject.get("depEnv");
			dep_cloud= (String) depObject.get("depCloud");
			dep_aging=(String) depObject.get("depAging");
			dep_metadata= (String) depObject.get("metaData");
			
		} 
		catch (ParseException | IOException e) {
			
			e.printStackTrace();
		}
	}
	
	@BeforeClass
	public static void setUp()
	{
				driver = new FirefoxDriver();
				// ATU Reports
				ATUReports.setWebDriver(driver);
				ATUReports.indexPageDescription = "App Launch Test Project";
				ATUReports.setAuthorInfo("Rashmi", Utils.getCurrentTime(),"1.0");
	}
	
	@AfterClass
	public static void tearDown()
	{
		//driver.quit();
	}
	
	@Test
	public static void logIn() throws InterruptedException, IOException 
	{
		SignIn logsin = new SignIn(driver);
		ATUReports.setTestCaseReqCoverage("This test is mapped to Login Requirement Testcase TC_001");
		logsin.getPage();
		
		ATUReports.indexPageDescription = "App Launcher Sanity Testcases";
		ATUReports.setAuthorInfo("Rashmi", Utils.getCurrentTime(),"1.0");
		logsin.getCredentials(email,pwd);
		ATUReports.add("Log in as admin",LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		logsin.submitForm();
		Commons.pauseTime(5000);
		ATUReports.add("Logged in as admin",LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		logsin.assertLoggedInPage();
	    
	}
	@Test(dependsOnMethods={"logIn"} )
	public static void gotoApp() throws IOException, InterruptedException
	{
		appProfile appPro = new appProfile(driver);
		
		appPro.appPage();
		Commons.pauseTime(1000);
		ATUReports.add("Go to App Page",LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	   
		
	    appPro.selectApp();
	    Commons.pauseTime(2000);
	   
	    ATUReports.add("Select App to launch",LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	   
	}
	@Test(dependsOnMethods={"gotoApp"} )
	public static void launchApp() throws IOException, InterruptedException
	{
		deployApp appDep = new deployApp(driver);
		
		Commons.pauseTime(2000);
		ATUReports.add("Enter deployment info",LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		
		appDep.launchAppData(dep_name, dep_desc, dep_tags, dep_env, dep_cloud);
		ATUReports.add("Entered deployment info",LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		
		appDep.launchApp();
	   
	   
	    Commons.pauseTime(2000); 
	    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	    appDep.assertJobStarted();
	    ATUReports.add("App Launched",LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
	
	@Test(dependsOnMethods={"launchApp"})
	public static void logOut() throws IOException
	{
		SignOut logsout = new SignOut(driver); 
		logsout.signOut();
	}
	
}
