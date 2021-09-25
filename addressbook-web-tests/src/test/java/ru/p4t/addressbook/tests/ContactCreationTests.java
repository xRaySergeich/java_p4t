package ru.p4t.addressbook.tests;

import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;
import ru.p4t.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    ContactData cd = new ContactData()
            .withFirstname("Zorian")
            .withMiddlename("Viktorovich")
            .withLastname("Kazinsky")
            .withNickname("Nutcracker")
            .withAvatarFileName("anonymous.jpg")
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
            .withGroup("test1")
            .withAddress2("Some secondary address")
            .withPhone2("888899999")
            .withNotes("Extremely important notes for test");

    app.goTo().homePage();

    Contacts before = app.contact().all();

    app.contact().createContact(cd);
    log.info("added contact " + cd);
    app.goTo().homePage();
    assertThat(app.contact().getContactCount(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();

    log.info("before size " + before.size());
    log.info("after size " + after.size());

    assertThat(after, equalTo(before.withAdded(cd.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    log.info("before value " + before);
    log.info("after value " + after);
  }

}
