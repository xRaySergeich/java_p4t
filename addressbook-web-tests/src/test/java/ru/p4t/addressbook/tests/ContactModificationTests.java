package ru.p4t.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions () {
    ContactData cdCreation = new ContactData("Zorian", "Viktorovich", "Kazinsky", "Nutcracker", "anonymous.jpg", "Wizards, inc", "Some title", "Some address", "99922211", "77718882", "937557728", "993949587", "address1@ex.com", "address2@ex.com", "address3@ex.com", "http://localhost", "10", "August", "1955", "6", "April", "1999", "test1", "Some secondary address", "888899999", "Extremely important notes for test");
    app.goTo().homePage();
    if (app.contact().list().size() == 0) {
      app.contact().createContact(cdCreation);
    }
  }

  @Test
  public void testContactModification() throws Exception {
    ContactData cdMod = new ContactData("Deimen", "Petrovich", "Kazinsky", "Nutcracker", "anonymous.jpg", "Wizards, inc", "Some title", "Some address", "99922211", "77718882", "937557728", "993949587", "address1@ex.com", "address2@ex.com", "address3@ex.com", "http://localhost", "10", "August", "1955", "6", "April", "1999", "test1", "Some secondary address", "888899999", "Extremely important notes for modification test");

    ContactData cdModForCompare = new ContactData("Deimen", null, "Kazinsky", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

    List<ContactData> before = app.contact().list();

    int index = before.size() - 1;
    app.contact().modify(cdMod, before, index);

    log.info("изменен контакт, было " + before.get(index));
    List<ContactData> after = app.contact().list();
    log.info("изменен контакт, стало " + after.get(index));
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(cdModForCompare);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(),g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }


}