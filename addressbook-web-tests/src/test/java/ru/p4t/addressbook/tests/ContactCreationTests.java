package ru.p4t.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    ContactData cd = new ContactData("Zorian", "Viktorovich", "Kazinsky", "Nutcracker", "anonymous.jpg", "Wizards, inc", "Some title", "Some address", "99922211", "77718882", "937557728", "993949587", "address1@ex.com", "address2@ex.com", "address3@ex.com", "http://localhost", "10", "August", "1955", "6", "April", "1999", "test1", "Some secondary address", "888899999", "Extremely important notes for test");
    ContactData cdAdd = new ContactData("Zorian", null,"Kazinsky",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null );

    app.getNavigationHelper().gotoHomePage();

    List<ContactData> before = app.getContactHelper().getContactList();

    app.getContactHelper().createContact(cd);
    log.info("Добавлен контакт " + cd);
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    log.info("before size " + before.size());
    log.info("after size " + after.size());

    before.add(cdAdd);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(),c2.getId());
    before.sort(byId);
    before.sort(byId);
    after.sort(byId);
    log.info("before value " + before);
    log.info("after value " + after);
    Assert.assertEquals(before,after);
  }

}
