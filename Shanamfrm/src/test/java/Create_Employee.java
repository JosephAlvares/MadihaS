

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericUtility.ExcelUtility;
import genericUtility.PropertyUtility;
import genericUtility.WebDriverUtility;

public class Create_Employee {
	
	public static void main(String[] args) throws Exception{
		WebDriver driver;
		// 1.login
		// 1.1 read data from property file
		PropertyUtility prop = new PropertyUtility();

		String browser = prop.readDataFromPropertyFile("browser");
		String url = prop.readDataFromPropertyFile("url");
		String un = prop.readDataFromPropertyFile("userName");
		String pwd = prop.readDataFromPropertyFile("password");

		// 1.2 launch browser
		WebDriverUtility wu = new WebDriverUtility();
		driver = wu.launchBrowser(browser);

		wu.maximizeBrowser(driver);
		wu.implicitWaitMethod(driver);

		// 1.3 Navigating to app
		driver.get(url);

		// 1.4 login
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(un);
		Thread.sleep(3000);

		driver.findElement(By.id("inputPassword")).clear();
		driver.findElement(By.id("inputPassword")).sendKeys(pwd);
		Thread.sleep(3000);

		driver.findElement(By.xpath("//button[text()='Sign in']")).click();

		Thread.sleep(5000);
		
		// 2.create Employee
		// 2.1 go to Employee feature
		driver.findElement(By.linkText("Employees")).click();
		
		// 2.2 click on create Employee
		driver.findElement(By.xpath("//button[contains(.,'Add New Employee')]")).click();
		
		Random r= new Random();
		int num= r.nextInt();
		
		ExcelUtility eu= new ExcelUtility();
		String name=eu.readDataFromExcel("Employee", 2, 0)+num;
		String email=eu.readDataFromExcel("Employee", 2, 1);
		String phno=eu.readDataFromExcel("Employee", 2, 2);
		
		String user=eu.readDataFromExcel("Employee", 2, 0)+num;
		String desig=eu.readDataFromExcel("Employee", 2, 4);
		String exp=eu.readDataFromExcel("Employee", 2, 5);
		String project=eu.readDataFromExcel("Employee", 2, 6);
		
		driver.findElement(By.xpath("//label[text()='Name*']/following-sibling::input[@type='text']")).clear();
		driver.findElement(By.xpath("//label[text()='Name*']/following-sibling::input[@type='text']")).sendKeys(name);
		
		driver.findElement(By.xpath("//label[text()='Email*']/following-sibling::input[@type='email']")).clear();
		driver.findElement(By.xpath("//label[text()='Email*']/following-sibling::input[@type='email']")).sendKeys(email);
		
		driver.findElement(By.xpath("//label[text()='Phone*']/following-sibling::input[@type='text']")).clear();
		driver.findElement(By.xpath("//label[text()='Phone*']/following-sibling::input[@type='text']")).sendKeys(phno);
		
		driver.findElement(By.xpath("//label[text()='Username*']/following-sibling::input[@type='text']")).clear();
		driver.findElement(By.xpath("//label[text()='Username*']/following-sibling::input[@type='text']")).sendKeys(user);
		
		driver.findElement(By.xpath("//label[text()='Designation*']/following-sibling::input[@type='text']")).clear();
		driver.findElement(By.xpath("//label[text()='Designation*']/following-sibling::input[@type='text']")).sendKeys(desig);
		
		driver.findElement(By.xpath("//label[text()='Experience*']/following-sibling::input[@type='text']")).clear();
		driver.findElement(By.xpath("//label[text()='Experience*']/following-sibling::input[@type='text']")).sendKeys(exp);
		
		WebElement projectDD=driver.findElement(By.name("project"));
		wu.selectFromDD(projectDD,project);
		
		driver.findElement(By.xpath("//input[@value='Add']")).click();
		
		Thread.sleep(5000);
		driver.quit();
	}

}
