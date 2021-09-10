package ru.p4t.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.p4t.addressbook.model.ContactData;

import java.io.File;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("//input[@name='submit']"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());

    File avatarFile = new File("./src/files/" + contactData.getAvatarFileName());
    wd.findElement(By.name("photo")).sendKeys(avatarFile.getAbsolutePath());

    type(By.name("company"), contactData.getCompany());
    type(By.name("title"), contactData.getTitle());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("homepage"), contactData.getHomepage());
    type(By.name("fax"), contactData.getFax());
    type(By.name("fax"), contactData.getFax());
    type(By.name("fax"), contactData.getFax());

    select(By.name("bday"), contactData.getBday());
    select(By.name("bmonth"), contactData.getBmonth());
    type(By.name("byear"), contactData.getByear());
    select(By.name("aday"), contactData.getAday());
    select(By.name("amonth"), contactData.getAmonth());
    type(By.name("ayear"), contactData.getAyear());

    if (creation) {
      select(By.name("new_group"), contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }

    type(By.name("address2"), contactData.getAddress2());
    type(By.name("phone2"), contactData.getPhone2());
    type(By.name("notes"), contactData.getNotes());

  }

  /*public void fillContactFormGroup(ContactData contactData) {
    select(By.name("new_group"), contactData.getGroup());
  }*/

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void selectContact(int index) {
    list().get(index).click();
  }

  public void initContactModification(int index) {
    listEdit().get(index).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void initContactDeletion() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void submitContactDeletion() {
    wd.switchTo().alert().accept();
  }

  public void createContact(ContactData cd) {
    initContactCreation();
    fillContactForm(cd, true);
    submitContactCreation();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return list().size();
  }

  public List<WebElement> list () {
    List<WebElement> list = wd.findElements(By.name("selected[]"));
    return list;
  }
  public List<WebElement> listEdit () {
    List<WebElement> list = wd.findElements(By.xpath("//img[@alt='Edit']"));
    return list;
  }

}
