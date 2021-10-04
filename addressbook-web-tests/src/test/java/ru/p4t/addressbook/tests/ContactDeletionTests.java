package ru.p4t.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;
import ru.p4t.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions () {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
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
              .withGroup("test1")
              .withAddress2("Some secondary address")
              .withPhone2("888899999")
              .withNotes("Extremely important notes for test");
      app.contact().createContact(cdCreation);
    }
  }


  @Test
  public void testContactDeletion() {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();

    app.contact().delete(deletedContact);

    logger.info("deleted contact " + deletedContact);
    assertThat(app.contact().getContactCount(), equalTo(before.size() - 1));
    Contacts after = app.db().contacts();

    before.remove(deletedContact);
    assertThat(after, equalTo(before.without(deletedContact)));

    verifyContactListInUI();
  }


}
