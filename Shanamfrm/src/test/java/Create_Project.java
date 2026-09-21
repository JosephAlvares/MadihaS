

import java.sql.Connection;
import java.sql.DriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mysql.cj.jdbc.Driver;

import genericUtility.DatabaseUtility;
import genericUtility.ExcelUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyUtility;
import genericUtility.WebDriverUtility;
import objectRepository.Login_Page;

public class Create_Project {

	public static void main(String[] args) throws Exception {
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

		Login_Page lp = new Login_Page(driver);
		lp.userLogin(un, pwd);
//		driver.findElement(By.id("username")).clear();
//		driver.findElement(By.id("username")).sendKeys(un);
//		Thread.sleep(3000);
//
//		driver.findElement(By.id("inputPassword")).clear();
//		driver.findElement(By.id("inputPassword")).sendKeys(pwd);
//		Thread.sleep(3000);
//
//		driver.findElement(By.xpath("//button[text()='Sign in']")).click();
//
//		Thread.sleep(5000);

		// 2.create project
		// 2.1 go to project feature
		driver.findElement(By.linkText("Projects")).click();

		// 2.2 click on create project
		driver.findElement(By.xpath("//button[contains(.,'Create Project')]")).click();

		// 2.3 Reading required data to create project from excel
		ExcelUtility eu = new ExcelUtility();

		// 2.3.1 random number generating for new project name
		JavaUtility ju = new JavaUtility();

		String pName = eu.readDataFromExcel("Project", 2, 0) + ju.genRandomNumber();
		String pManager = eu.readDataFromExcel("Project", 2, 1);
		String pStatus = eu.readDataFromExcel("Project", 2, 2);

		// 2.4 creating project
		driver.findElement(By.name("projectName")).clear();
		driver.findElement(By.name("projectName")).sendKeys(pName);

		driver.findElement(By.name("createdBy")).clear();
		driver.findElement(By.name("createdBy")).sendKeys(pManager);

		wu.selectFromDD(
				driver.findElement(
						By.xpath("//label[text()='Project Status* ']/following-sibling::select[@name='status']")),
				pStatus);

//		Select sel = new Select(driver
//				.findElement(By.xpath("//label[text()='Project Status* ']/following-sibling::select[@name='status']")));
//		sel.selectByVisibleText(pStatus);

		driver.findElement(By.xpath("//input[@value='Add Project']")).click();

		// 2.5 getting projectId
		String projectId = driver.findElement(By.xpath("//td[contains(text(),'" + pName + "')]/preceding-sibling::td"))
				.getText();
		eu.writeDataInNewCell("Project", 1, 3, projectId);
		eu.writeDataInExistingCell("Project", 1, 0, pName);
//		wb.getSheet("Project").getRow(2).createCell(3).setCellValue(projectId);
//		wb.getSheet("Project").getRow(2).getCell(0).setCellValue(pName);

		DatabaseUtility du = new DatabaseUtility();

		Driver d = new Driver();
		DriverManager.registerDriver(d);

		Connection con = DriverManager.getConnection("jdbc:mysql://49.249.29.4:3307/ninza_hrm", "root@%", "root");

		con.createStatement();

		boolean b = du.validateDataEntry("project", "project_name", pName, "jdbc:mysql://49.249.29.4:3307/ninza_hrm",
				"root@%", "root");
		if (b == true)
			System.out.println("Project with" + pName + "got created in frontend and is available in backend");
		else
			System.out.println("Project with" + pName + "is not created in frontend and is not available in backend");

		du.closeConnection(con);

		Thread.sleep(5000);
		driver.quit();

	}

}
