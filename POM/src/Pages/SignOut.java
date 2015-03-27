package Pages;

import java.io.IOException;
import java.util.Properties;

import lib.Commons;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignOut 
{
	private  WebDriver driver;
	public SignOut(WebDriver driver) 
	{
		this.driver=driver;
	}
	
	public void signOut() throws IOException
	{
		Properties signoutProps = Commons.getprop("/Users/rashmi/Desktop/POM/src/resources/signIn.properties");
		driver.findElement(By.xpath(signoutProps.getProperty("logout"))).click();
	}
}
