package ru.p4t.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() throws Exception {
    ContactData cdCreation = new ContactData("Zorian", "Viktorovich", "Kazinsky", "Nutcracker", "anonymous.jpg", "Wizards, inc", "Some title", "Some address", "99922211", "77718882", "937557728", "993949587", "address1@ex.com", "address2@ex.com", "address3@ex.com", "http://localhost", "10", "August", "1955", "6", "April", "1999", "test1", "Some secondary address", "888899999", "Extremely important notes for test");
    ContactData cdMod = new ContactData("Deimen", "Petrovich", "Kazinsky", "Nutcracker", "anonymous.jpg", "Wizards, inc", "Some title", "Some address", "99922211", "77718882", "937557728", "993949587", "address1@ex.com", "address2@ex.com", "address3@ex.com", "http://localhost", "10", "August", "1955", "6", "April", "1999", "test1", "Some secondary address", "888899999", "Extremely important notes for modification test");

    app.getNavigationHelper().gotoHomePage();

    int before = app.getContactHelper().getContactCount();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(cdCreation);
    }
    app.getNavigationHelper().gotoHomePage();
    //app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification(before - 1);
    app.getContactHelper().fillContactForm(cdMod, false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().gotoHomePage();

    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before);

  }
}