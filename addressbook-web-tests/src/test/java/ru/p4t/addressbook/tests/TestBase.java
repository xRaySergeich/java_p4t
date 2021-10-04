package ru.p4t.addressbook.tests;

//import org.apache.log4j.Logger;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.p4t.addressbook.appmanager.ApplicationManager;
import ru.p4t.addressbook.model.ContactData;
import ru.p4t.addressbook.model.Contacts;
import ru.p4t.addressbook.model.GroupData;
import ru.p4t.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestBase {
  //static Logger log = Logger.getLogger(ContactCreationTests.class.getName());
  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();

  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    app.getSessionHelper().logout();
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p) {
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m) {
    logger.info("Stop test " + m.getName());
  }


  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("isUIverified")) {
      Groups dbGroups = app.db().groups();
      Set<GroupData> dbGroupsCompare = dbGroups.stream().map(g -> new GroupData()
              .withId(g.getId())
              .withName(g.getName())
      ).collect(Collectors.toSet());
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroupsCompare));
    }
  }

  public void verifyContactListInUI() {
    if (Boolean.getBoolean("isUIverified")) {
      Contacts dbContacts = app.db().contacts();
      Set<ContactData> dbContactsCompare = dbContacts.stream().map(g -> new ContactData()
              .withId(g.getId())
              .withLastname(g.getLastname())
              .withFirstname(g.getFirstname())
              .withAddress(g.getAddress())
              .withAllEmails(g.getAllEmails())
              .withAllPhones(g.getAllPhones())
      ).collect(Collectors.toSet());
      Contacts uiContacts = app.contact().all();
      assertThat(uiContacts, equalTo(dbContactsCompare));
    }
  }
}
