package ru.p4t.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.p4t.addressbook.model.ContactData;
import ru.p4t.addressbook.model.Contacts;
import ru.p4t.addressbook.model.GroupData;

import java.io.File;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public NavigationHelper navi = new NavigationHelper(wd);

  public void submitContactCreation() {
    click(By.xpath("//input[@name='submit']"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    attach(By.name("photo"), contactData.getPhoto());
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
      if (contactData.getGroups().size() > 0) {
        Assert.assertEquals(contactData.getGroups().size(), 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }

    type(By.name("address2"), contactData.getAddress2());
    type(By.name("phone2"), contactData.getPhone2());
    type(By.name("notes"), contactData.getNotes());

  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void initContactModificationById(int id) {
    wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
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
    navi.homePage();
    initContactCreation();
    fillContactForm(cd, true);
    submitContactCreation();
    contactCache = null;
  }

  public void modify(ContactData cdMod) {
    navi.homePage();
    selectContactById(cdMod.getId());
    initContactModificationById(cdMod.getId());
    fillContactForm(cdMod, false);
    submitContactModification();
    contactCache = null;
    navi.homePage();
  }

  public void delete(ContactData contact) {
    navi.homePage();
    selectContactById(contact.getId());
    initContactDeletion();
    submitContactDeletion();
    contactCache = null;
    navi.homePage();
  }

  public void addContactInAGroup(ContactData contact, GroupData group) {
    navi.homePage();
    selectContactById(contact.getId());
    selectById(By.name("to_group"), group.getId());
    click(By.cssSelector("input[value='Add to']"));
  }

  public void deleteContactFromAGroup(ContactData contact, GroupData group) {
    navi.homePage();
    selectById(By.name("group"), group.getId());
    selectContactById(contact.getId());
    click(By.name("remove"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return getList().size();
  }

  public List<WebElement> getList() {
    return wd.findElements(By.name("selected[]"));
  }

  public List<WebElement> listEdit() {
    return wd.findElements(By.xpath("//img[@alt='Edit']"));
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }

    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String allphones = cells.get(5).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData()
              .withId(id)
              .withFirstname(firstname)
              .withLastname(lastname)
              .withAllPhones(allphones)
              .withAddress(address)
              .withAllEmails(allEmails);
      contactCache.add(contact);
    }
    return new Contacts(contactCache);
  }


  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String phone2 = wd.findElement(By.name("phone2")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId())
            .withFirstname(firstname)
            .withLastname(lastname)
            .withHomePhone(home)
            .withMobilePhone(mobile)
            .withWorkPhone(work)
            .withPhone2(phone2)
            .withAddress(address)
            .withEmail(email)
            .withEmail2(email2)
            .withEmail3(email3);
  }

  public void createContact() {
    File photo = new File("src/test/resources/anonymous.jpg");
    ContactData cdCreation = new ContactData()
            .withFirstname("Zorian")
            .withMiddlename("Viktorovich")
            .withLastname("Kazinsky")
            .withNickname("Nutcracker")
            .withPhoto(photo)
            .withCompany("Wizards, inc")
            .withTitle("Some title")
            .withAddress("Some address")
            .withHomePhone("99922211")
            .withMobilePhone("77718882")
            .withWorkPhone("937557728")
            .withFax("993949587")
            .withEmail("address1@ex.com")
            .withEmail2("address2@ex.com")
            .withEmail3("address3@ex.com")
            .withHomepage("http://localhost")
            .withBday("10")
            .withBmonth("August")
            .withByear("1955")
            .withAday("6")
            .withAmonth("April")
            .withAyear("1999")
            .inGroup(new GroupData().withName("test1"))
            .withAddress2("Some secondary address")
            .withPhone2("888899999")
            .withNotes("Extremely important notes for test");
    createContact(cdCreation);
  }

  public void createContact(GroupData group) {
    File photo = new File("src/test/resources/anonymous.jpg");
    ContactData cdCreation = new ContactData()
            .withFirstname("Zorian")
            .withMiddlename("Viktorovich")
            .withLastname("Kazinsky")
            .withNickname("Nutcracker")
            .withPhoto(photo)
            .withCompany("Wizards, inc")
            .withTitle("Some title")
            .withAddress("Some address")
            .withHomePhone("99922211")
            .withMobilePhone("77718882")
            .withWorkPhone("937557728")
            .withFax("993949587")
            .withEmail("address1@ex.com")
            .withEmail2("address2@ex.com")
            .withEmail3("address3@ex.com")
            .withHomepage("http://localhost")
            .withBday("10")
            .withBmonth("August")
            .withByear("1955")
            .withAday("6")
            .withAmonth("April")
            .withAyear("1999")
            .inGroup(group)
            .withAddress2("Some secondary address")
            .withPhone2("888899999")
            .withNotes("Extremely important notes for test");
    createContact(cdCreation);
  }
}
