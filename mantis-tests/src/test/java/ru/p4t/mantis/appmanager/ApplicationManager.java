package ru.p4t.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {
  private final Properties properties;

  private WebDriver wd;
  private final String browser;
  private RegistrationHelper registrationHelper;
  private FtpHelper ftp;
  private MailHelper mailHelper;
  private JamesHelper jamesHelper;
  private LoginHelper loginHelper;
  private ManageHelper manageHelper;

  public ApplicationManager(String browser) {

    this.browser = browser;
    properties = new Properties();

  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");

    properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));


  }


  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }

  public HttpSession newSession() {
    return new HttpSession(this);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public RegistrationHelper registration() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }

  public LoginHelper loginPage() {
    if (loginHelper == null) {
      loginHelper = new LoginHelper(this);
    }
    return loginHelper;
  }

  public ManageHelper managePage() {
    if (manageHelper == null) {
      manageHelper = new ManageHelper(this);
    }
    return manageHelper;
  }


  public FtpHelper ftp() {
    if (ftp == null) {
      ftp = new FtpHelper(this);
    }
    return ftp;
  }

  public WebDriver getDriver() {
    if (wd == null) {
      switch (browser) {
        case BrowserType.FIREFOX:
          wd = new FirefoxDriver();
          break;
        case BrowserType.CHROME:
          System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver_win32\\chromedriver.exe");
          wd = new ChromeDriver();
          break;
        case BrowserType.IE:
          System.setProperty("webdriver.ie.driver", "C:\\tools\\IEDriverServer_Win32_3.150.2\\IEDriverServer.exe");
          wd = new InternetExplorerDriver();
          wd.manage().window().maximize();

          break;
      }
      wd.manage().deleteAllCookies();
      wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
      wd.get(properties.getProperty("web.baseUrl"));

    }
    return wd;
  }

  public MailHelper mail() {
    if (mailHelper == null) {
      mailHelper = new MailHelper(this);
    }
    return mailHelper;
  }

  public JamesHelper james() {
    if (jamesHelper == null) {
      jamesHelper = new JamesHelper(this);
    }
    return jamesHelper;
  }


}
