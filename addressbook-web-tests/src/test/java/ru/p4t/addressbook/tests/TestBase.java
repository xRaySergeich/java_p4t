package ru.p4t.addressbook.tests;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.p4t.addressbook.appmanager.ApplicationManager;

public class TestBase {
  static Logger log = Logger.getLogger(ContactCreationTests.class.getName());
  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();

  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.getSessionHelper().logout();
    app.stop();
  }

}
