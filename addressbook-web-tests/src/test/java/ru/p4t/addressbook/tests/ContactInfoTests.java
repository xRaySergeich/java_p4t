package ru.p4t.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;
import ru.p4t.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions () {
    ContactData cdCreation = new ContactData()
            .withFirstname("Zorian")
            .withMiddlename("Viktorovich")
            .withLastname("Kazinsky")
            .withNickname("Nutcracker")
            .withAvatarFileName("anonymous.jpg")
            .withCompany("Wizards, inc")
            .withTitle("Some title")
            .withAddress("Some address")
            .withHomePhone("+7 999 22 21 1")
            .withMobilePhone("(777)18882")
            .withWorkPhone("937 557 728")
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
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().createContact(cdCreation);
    }
  }


  public ContactData contact() {
    return app.contact().all().iterator().next();
  }

  public ContactData contactInfoFromEditForm(ContactData contact) {
    return app.contact().infoFromEditForm(contact);
  }

  @Test
  public void testContactPhones() {
    app.goTo().homePage();

    assertThat(contact().getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm(contact()))));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(), contact.getPhone2())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactInfoTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("[-()\\s]", "");
  }

  @Test
  public void testPostAddress() {
    app.goTo().homePage();

    assertThat(contact().getAddress(), equalTo(makeInfoAddress(contactInfoFromEditForm(contact()))));
  }

  private String makeInfoAddress(ContactData contact) {
    return contact.getAddress().replaceAll("\\s+\\n", "\n");
  }

  @Test
  public void testEmails() {
    app.goTo().homePage();

    assertThat(contact().getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm(contact()))));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactInfoTests::removeSpaces)
            .collect(Collectors.joining("\n"));
  }

  public static String removeSpaces(String emails) {
    return emails.replaceAll("\\s", "");
  }

}
