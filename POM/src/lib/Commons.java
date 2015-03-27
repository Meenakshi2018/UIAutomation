package lib;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import atu.testng.reports.ATUReports;
import atu.testng.reports.utils.Utils;

public final class Commons 
{	
	public static Properties getprop(String propFileName) throws IOException
	{
		Properties defaultProps = new Properties();
		FileInputStream in = new FileInputStream(propFileName);
		defaultProps.load(in);
		in.close();
		return defaultProps;
	}
	
	public static void pauseTime(int milisec) throws InterruptedException
	{
		Thread.sleep(milisec);
	}
	
}
