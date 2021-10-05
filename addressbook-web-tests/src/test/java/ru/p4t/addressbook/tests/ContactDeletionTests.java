package ru.p4t.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;
import ru.p4t.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions () {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.contact().createContact();
    }
  }


  @Test
  public void testContactDeletion() {
    //app.goTo().homePage();
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
