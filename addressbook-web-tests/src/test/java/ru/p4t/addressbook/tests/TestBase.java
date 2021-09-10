package ru.p4t.addressbook.tests;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.p4t.addressbook.appmanager.ApplicationManager;

public class TestBase {
  static Logger log = Logger.getLogger(ContactCreationTests.class.getName());
  protected final ApplicationManager app = new ApplicationManager(BrowserType.CHROME);

  @BeforeMethod(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();

  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception {
    app.getSessionHelper().logout();
    app.stop();
  }

}
