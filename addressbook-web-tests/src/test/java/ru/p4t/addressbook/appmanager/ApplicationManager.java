package ru.p4t.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.time.Duration;

public class ApplicationManager {

  private WebDriver wd;
  private ContactHelper contactHelper;
  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private final String browser;

  public ApplicationManager(String browser) {

    this.browser = browser;
  }

  public void init() {
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
    wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    wd.get("http://localhost/addressbook");
    groupHelper = new GroupHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    contactHelper = new ContactHelper(wd);
    sessionHelper.login("admin", "secret");
  }


  public void stop() {
    wd.quit();
  }


  public GroupHelper getGroupHelper() {
    return groupHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }

  public ContactHelper getContactHelper() {
    return contactHelper;
  }

  public SessionHelper getSessionHelper() {
    return sessionHelper;
  }
}
