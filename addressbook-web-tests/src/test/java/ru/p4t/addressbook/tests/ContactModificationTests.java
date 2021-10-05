package ru.p4t.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;
import ru.p4t.addressbook.model.Contacts;
import ru.p4t.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions () {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.contact().createContact();
    }
  }

  @Test
  public void testContactModification() {
    //app.goTo().homePage();
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    File photo = new File("src/test/resources/anonymous.jpg");
    ContactData cdMod = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstname("Deimen")
            .withMiddlename("Petrovich")
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
            .withNotes("Extremely important notes for modification test");

    app.contact().modify(cdMod);

    logger.info("contact modified, before " + modifiedContact);
    assertThat(app.contact().getContactCount(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    logger.info("contact modified, after " + cdMod);

    before.remove(modifiedContact);
    before.add(cdMod);
    assertThat(after, equalTo(before.withModified(cdMod, modifiedContact)));

    verifyContactListInUI();
  }


}