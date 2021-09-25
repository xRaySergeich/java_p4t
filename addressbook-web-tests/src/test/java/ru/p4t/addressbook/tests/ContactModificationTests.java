package ru.p4t.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModificationTests extends TestBase {

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
    if (app.contact().all().size() == 0) {
      app.contact().createContact(cdCreation);
    }
  }

  @Test
  public void testContactModification() throws Exception {


    /*ContactData cdModForCompare = new ContactData()
            .withFirstname("Deimen")
            .withLastname("Kazinsky");*/

    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();

    ContactData cdMod = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstname("Deimen")
            .withMiddlename("Petrovich")
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
            .withNotes("Extremely important notes for modification test");

    app.contact().modify(cdMod);

    log.info("изменен контакт, было " + modifiedContact);
    Set<ContactData> after = app.contact().all();
    log.info("изменен контакт, стало " + cdMod);
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(cdMod);
    Assert.assertEquals(before, after);

  }


}