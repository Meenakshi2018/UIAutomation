package Pages;
import java.io.IOException;
import java.util.Properties;
import lib.Commons;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


public class SignIn 
{
	public final WebDriver driver;
	Properties signinprops = Commons.getprop("/Users/rashmi/Desktop/POM/src/resources/signIn.properties");
	
	public SignIn(WebDriver driver) throws IOException
	{	
		this.driver=driver;
		driver.manage().window().maximize();	
	}
	
	public void getPage()
	{
		driver.get(signinprops.getProperty("URL"));
	}
	
	public void getCredentials( String emailId, String passwd)
	{
		driver.findElement(By.xpath(signinprops.getProperty("email"))).sendKeys(emailId);
		driver.findElement(By.xpath(signinprops.getProperty("pwd"))).sendKeys(passwd);
	}
	
	public void assertLoggedInPage()
	{
		Assert.assertTrue(driver.getTitle().toString().contains(signinprops.getProperty("loginTitle")));
	}
	
	public void submitForm()
	{
		driver.findElement(By.id(signinprops.getProperty("login"))).click();	
	}
	
}
