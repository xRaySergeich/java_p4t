package ru.p4t.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    ContactData cd = new ContactData("Zorian", "Viktorovich", "Kazinsky", "Nutcracker", "anonymous.jpg", "Wizards, inc", "Some title", "Some address", "99922211", "77718882", "937557728", "993949587", "address1@ex.com", "address2@ex.com", "address3@ex.com", "http://localhost", "10", "August", "1955", "6", "April", "1999", "test1", "Some secondary address", "888899999", "Extremely important notes for test");

    app.getNavigationHelper().gotoHomePage();

    int before = app.getContactHelper().getGroupCount();

    app.getContactHelper().createContact(cd);
    app.getNavigationHelper().gotoHomePage();

    int after = app.getContactHelper().getGroupCount();
    Assert.assertEquals(after, before + 1);
  }

}
