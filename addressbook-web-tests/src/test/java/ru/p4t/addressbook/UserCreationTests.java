package ru.p4t.addressbook;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class UserCreationTests {
  private WebDriver wd;

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver_win32\\chromedriver.exe");
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    wd.get("http://localhost/addressbook");
    login("admin", "secret");
  }

  private void login(String username, String password) {
    wd.findElement(By.name("user")).click();
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys(username);
    wd.findElement(By.name("pass")).clear();
    wd.findElement(By.name("pass")).sendKeys(password);
    wd.findElement(By.xpath("//input[@value='Login']")).click();
  }

  @Test
  public void testUntitledTestCase() throws Exception {
    initUserCreation();
    fillUserForm(new UserData("Zorian", "Viktorovich", "Kazinsky", "Nutcracker", "anonymous.jpg", "Wizards, inc", "Some title", "Some address", "99922211", "77718882", "937557728", "993949587", "address1@ex.com", "address2@ex.com", "address3@ex.com", "http://localhost", "10", "August", "1955", "6", "April", "1999", "test1", "Some secondary address", "888899999", "Extremely important notes for test"));
    submitUserCreation();
    gotoHomePage();
    logout();

  }

  private void gotoHomePage() {
    wd.findElement(By.linkText("home page")).click();
  }

  private void submitUserCreation() {
    wd.findElement(By.xpath("//input[@name='submit']")).click();
  }

  private void fillUserForm(UserData userData) {
    wd.findElement(By.name("firstname")).clear();
    wd.findElement(By.name("firstname")).sendKeys(userData.getFirstname());
    wd.findElement(By.name("middlename")).clear();
    wd.findElement(By.name("middlename")).sendKeys(userData.getMiddlename());
    wd.findElement(By.name("lastname")).clear();
    wd.findElement(By.name("lastname")).sendKeys(userData.getLastname());
    wd.findElement(By.name("nickname")).clear();
    wd.findElement(By.name("nickname")).sendKeys(userData.getNickname());
    wd.findElement(By.name("photo")).clear();
    File avatarFile = new File("./src/files/" + userData.getAvatarFileName());
    wd.findElement(By.name("photo")).sendKeys(avatarFile.getAbsolutePath());
    wd.findElement(By.name("company")).clear();
    wd.findElement(By.name("company")).sendKeys(userData.getCompany());
    wd.findElement(By.name("title")).clear();
    wd.findElement(By.name("title")).sendKeys(userData.getTitle());
    wd.findElement(By.name("address")).clear();
    wd.findElement(By.name("address")).sendKeys(userData.getAddress());
    wd.findElement(By.name("home")).clear();
    wd.findElement(By.name("home")).sendKeys(userData.getHomePhone());
    wd.findElement(By.name("mobile")).clear();
    wd.findElement(By.name("mobile")).sendKeys(userData.getMobilePhone());
    wd.findElement(By.name("work")).clear();
    wd.findElement(By.name("work")).sendKeys(userData.getWorkPhone());
    wd.findElement(By.name("fax")).clear();
    wd.findElement(By.name("fax")).sendKeys(userData.getFax());
    wd.findElement(By.name("email")).clear();
    wd.findElement(By.name("email")).sendKeys(userData.getEmail());
    wd.findElement(By.name("email2")).clear();
    wd.findElement(By.name("email2")).sendKeys(userData.getEmail2());
    wd.findElement(By.name("email3")).clear();
    wd.findElement(By.name("email3")).sendKeys(userData.getEmail3());
    wd.findElement(By.name("homepage")).clear();
    wd.findElement(By.name("homepage")).sendKeys(userData.getHomepage());
    wd.findElement(By.name("bday")).click();
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(userData.getBday());
    wd.findElement(By.name("bmonth")).click();
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(userData.getBmonth());
    wd.findElement(By.name("byear")).clear();
    wd.findElement(By.name("byear")).sendKeys(userData.getByear());
    wd.findElement(By.name("aday")).click();
    new Select(wd.findElement(By.name("aday"))).selectByVisibleText(userData.getAday());
    wd.findElement(By.name("amonth")).click();
    new Select(wd.findElement(By.name("amonth"))).selectByVisibleText(userData.getAmonth());
    wd.findElement(By.name("ayear")).clear();
    wd.findElement(By.name("ayear")).sendKeys(userData.getAyear());
    wd.findElement(By.name("new_group")).click();
    new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(userData.getGroup());
    wd.findElement(By.name("address2")).clear();
    wd.findElement(By.name("address2")).sendKeys(userData.getAddress2());
    wd.findElement(By.name("phone2")).clear();
    wd.findElement(By.name("phone2")).sendKeys(userData.getPhone2());
    wd.findElement(By.name("notes")).clear();
    wd.findElement(By.name("notes")).sendKeys(userData.getNotes());
  }

  private void initUserCreation() {
    wd.get("http://localhost/addressbook/");
    wd.findElement(By.linkText("add new")).click();
  }

  private void logout() {
    wd.findElement(By.linkText("Logout")).click();

  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    wd.quit();
  }

  private boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }


}
