package Pages;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import lib.Commons;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class deployApp
{
	private  WebDriver driver;
	Properties depapp = Commons.getprop("/Users/rashmi/Desktop/POM/src/resources/DepConfig.properties");
	
	public deployApp(WebDriver driver) throws IOException
	{
		this.driver=driver;
	}
	public void launchAppData(String d_name,String d_desc,String d_tags,String d_env,String d_cloud)
	{	
			driver.findElement(By.xpath(depapp.getProperty("dep_name"))).sendKeys(d_name);
			driver.findElement(By.xpath(depapp.getProperty("dep_desc"))).sendKeys(d_desc);
			//driver.findElement(By.xpath(depapp.getProperty("dep_tags"))).sendKeys(d_tags);
			//job_systemTagsTypeAhead_chosen
			driver.findElement(By.id("job_systemTagsTypeAhead_chosen")).sendKeys(d_tags);
			
			//select environment
			WebElement environ = driver.findElement(By.xpath(depapp.getProperty("dep_env")));
			new Select(environ).selectByVisibleText(d_env);
			
			//select cloud
			WebElement cloud = driver.findElement(By.xpath(depapp.getProperty("dep_cloud")));
			new Select(cloud).selectByVisibleText(d_cloud);	
	}
	
	public void launchApp()
	{
		driver.findElement(By.xpath(depapp.getProperty("dep_submit"))).click();
	}
	
	public void assertJobStarted()
	{
		 List<WebElement> tdlist = driver.findElements(By.id("jobStatus"));
		 for(WebElement el: tdlist) 
		    {
			 Assert.assertTrue(el.getText().contains("JobStarting"));
		    }
	}

}
