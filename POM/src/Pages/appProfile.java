package Pages;

import java.io.IOException;
import java.util.Properties;

import lib.Commons;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class appProfile 
{
	private  WebDriver driver;
	Properties apps = Commons.getprop("/Users/rashmi/Desktop/POM/src/resources/AppConfig.properties");
	
	public appProfile(WebDriver driver) throws IOException
	{
		this.driver=driver;
	}
	
	public void appPage() throws IOException
	{
		driver.findElement(By.cssSelector(apps.getProperty("app"))).click();
	}
	
	public void selectApp()
	{
		driver.findElement(By.xpath(apps.getProperty("Jenkin_run"))).click();;
	}
	
	public void appProfile()
	{
		driver.findElement(By.cssSelector(apps.getProperty("app_profile"))).click();
	}
}
