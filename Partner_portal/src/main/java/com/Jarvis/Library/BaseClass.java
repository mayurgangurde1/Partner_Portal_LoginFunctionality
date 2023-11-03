package com.Jarvis.Library;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.Module.SetupContext;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	public Properties prop;
	public static WebDriver driver;
	public static ExtentSparkReporter spark;
	public static ExtentReports extent;
	public static ExtentTest test;
	WebDriverWait wait;
	public static ChromeOptions option;
	public static FirefoxOptions firefoxOption;
	public static EdgeOptions Edgeoption;

	// ** to run the script on different browsers and
	// reading the browser value from testng.xml file

	public void newDriver(@Optional String browser) throws Exception {

		try {
//			String browser = System.getProperty("browser") != null ? System.getProperty("browser")
//					: System.getProperty(UtilityClass.getDatafromPropertyFile("broswer"));
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				option = new ChromeOptions();
			

				option.addArguments("start-maximized"); // open Browser in maximized mode
				option.addArguments("disable-infobars"); // disabling infobars
				option.addArguments("--disable-notification");
				option.addArguments("--disable-extensions"); // disabling extensions
				option.addArguments("--disable-gpu"); // applicable to windows os only
				option.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
				option.addArguments("--no-sandbox"); // Bypass OS security model
				option.addArguments("--remote-debugging-port=9222");
				option.addArguments("--remote-allow-origins=*");

				driver = new ChromeDriver(option);
				driver.manage().window().maximize();
//				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				driver.get(UtilityClass.getDatafromPropertyFile("url"));
				driver.getTitle();
				System.out.println(driver.getCurrentUrl());

				System.out.println("chrome driver started");

			} else if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();

				firefoxOption = new FirefoxOptions();
				firefoxOption.addArguments("--disable-notification");
				firefoxOption.addArguments("--disable-extensions"); // disabling extensions
				firefoxOption.addArguments("--disable-gpu"); // applicable to windows os only
				firefoxOption.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
				firefoxOption.addArguments("--no-sandbox"); // Bypass OS security model

				driver = new FirefoxDriver(firefoxOption);

				driver.manage().window().maximize();
//				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				driver.get(UtilityClass.getDatafromPropertyFile("url"));
				driver.getTitle();
				System.out.println(driver.getCurrentUrl());

				Thread.sleep(1000);

				System.out.println("Firefox driver started");

			} else if (browser.equalsIgnoreCase("edge")) {

				System.out.println("edge");
				WebDriverManager.edgedriver().setup();
				Edgeoption = new EdgeOptions();
				driver = new EdgeDriver(Edgeoption);
				driver.manage().window().maximize();
//				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				driver.get(UtilityClass.getDatafromPropertyFile("url"));
				driver.getTitle();
				System.out.println(driver.getCurrentUrl());

				System.out.println("Edge driver started");

			} else if (browser.equalsIgnoreCase("safari")) {
				System.out.println("safari");

				driver = new SafariDriver();
				System.out.println("safari");

			}

		} catch (IllegalStateException e) {
			System.out.println(e);
//			if (driver != null) {
//				driver.get("https://customer-qa1.jarvisinvest.com");
//
//				System.out.println("hi");
//			}

		}

	}

	public static void setExtentReport() {
		spark = new ExtentSparkReporter("Extent Report.html");
		spark.config().setTheme(Theme.STANDARD);
		spark.config().setReportName("JARVIS Test Automation Results Report");
		spark.config().setDocumentTitle("TN Automation Report");
		spark.config().setTimeStampFormat("dd/MM/yyyy hh:mm:ss");
		extent = new ExtentReports();
		extent.attachReporter(spark);
	}

	public WebDriver InitialiseBrowser(String browsername) throws IOException {
		if (browsername.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browsername.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browsername.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (browsername.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}
		driver.manage().window().maximize();
		driver.get(UtilityClass.getDatafromPropertyFile("url"));
		return driver;
	}

	public void SendAttachmentInEmailPass() {

		// Recipient's email ID needs to be mentioned.
		String to = "sakshi.bodhare@techstalwarts.com";
		String cc = "mayur.gangurde@techstalwarts.com,sakshi.bodhare@techstalwarts.com";
//		String cc = "sakshi.bodhare@techstalwarts.com";

		// Sender's email ID needs to be mentioned
		String from = "sakshi.bodhare@techstalwarts.com";

		final String username = "sakshi.bodhare@techstalwarts.com";
		final String password = "zkztrufkmjqfkeqe";

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";//

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));

			// Set Subject: header field
			message.setSubject(" Partner portal Automation Testing Execution Report "
					+ new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date()));

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// set the actual message
			messageBodyPart.setText("Hello Team, \r\n" + "\r\n"
					+ "Automation testing is done on JARVIS Partner portal which was carried out within the Live Environment.\r\n"
					+ "\r\n  "
					+ "\r\n" + "\r\nBest Regards," + "\r\nJarvis Team");

			// Create a multiple message
			MimeMultipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment1 -- Emailable report
			messageBodyPart = new MimeBodyPart();
			String filename = (System.getProperty("user.dir") + "/test-output/emailable-report.html");

			FileDataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

//			// attachment 2-- extend report
//			messageBodyPart = new MimeBodyPart();
//			String filename1 = (System.getProperty("user.dir") + "/Extent Report.html");
//			FileDataSource source2 = new FileDataSource(filename1);
//			messageBodyPart.setDataHandler(new DataHandler(source2));
//			messageBodyPart.setFileName(filename1);
//			multipart.addBodyPart(messageBodyPart);

			// ---JARVIS LOGO----
//			messageBodyPart = new MimeBodyPart();
//
//			String filename3 = (System.getProperty("user.dir") + "/jarvis_logo/1.png");
//			messageBodyPart.setContent(filename3, "text/html");
//			FileDataSource source3 = new FileDataSource(filename3);
//			messageBodyPart.setDataHandler(new DataHandler(source3));
////			messageBodyPart.setFileName(filename3);
//			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			// Send message
			Transport.send(message);

			System.out.println("Sent Email successfully....");

		} catch (

		MessagingException e) {
			System.out.println("Email not sent");
			System.out.println(e);
			throw new RuntimeException(e);

		}
	}
	public void SendAttachmentInEmailFail() {

		// Recipient's email ID needs to be mentioned.
		String to = "sakshi.bodhare@techstalwarts.com";
		String cc = "mayur.gangurde@techstalwarts.com,sakshi.bodhare@techstalwarts.com";
//		String cc = "sakshi.bodhare@techstalwarts.com";

		// Sender's email ID needs to be mentioned
		String from = "sakshi.bodhare@techstalwarts.com";

		final String username = "sakshi.bodhare@techstalwarts.com";
		final String password = "zkztrufkmjqfkeqe";

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";//

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));

			// Set Subject: header field
			message.setSubject("Partner portal Automation Error status on "
					+ new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date()));

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// set the actual message
			messageBodyPart.setText("Hello Team, \r\n" + "\r\n"
					+ "Automation testing is done on JARVIS Partner portal and the error occures while running the Test suite.\r\n"
					+ "\r\n error are as follows : "
					+ "\r\n" + "\r\nBest Regards," + "\r\nJarvis Team");
//

			// Create a multiple message
			MimeMultipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment1 -- Emailable report
			messageBodyPart = new MimeBodyPart();
			String filename = (System.getProperty("user.dir") + "/test-output/emailable-report.html");

			FileDataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

//			// attachment 2-- extend report
//			messageBodyPart = new MimeBodyPart();
//			String filename1 = (System.getProperty("user.dir") + "/Extent Report.html");
//			FileDataSource source2 = new FileDataSource(filename1);
//			messageBodyPart.setDataHandler(new DataHandler(source2));
//			messageBodyPart.setFileName(filename1);
//			multipart.addBodyPart(messageBodyPart);

			// ---JARVIS LOGO----
//			messageBodyPart = new MimeBodyPart();
//
//			String filename3 = (System.getProperty("user.dir") + "/jarvis_logo/1.png");
//			messageBodyPart.setContent(filename3, "text/html");
//			FileDataSource source3 = new FileDataSource(filename3);
//			messageBodyPart.setDataHandler(new DataHandler(source3));
////			messageBodyPart.setFileName(filename3);
//			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			// Send message
			Transport.send(message);

			System.out.println("Sent error Email successfully....");

		} catch (

		MessagingException e) {
			System.out.println("Email not sent");
			System.out.println(e);
			throw new RuntimeException(e);

		}
	}

}