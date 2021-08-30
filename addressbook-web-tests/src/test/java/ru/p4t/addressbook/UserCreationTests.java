package ru.p4t.addressbook;

import org.testng.annotations.Test;

public class UserCreationTests extends TestBase {

  @Test
  public void testUserCreation() throws Exception {
    initUserCreation();
    fillUserForm(new UserData("Zorian", "Viktorovich", "Kazinsky", "Nutcracker", "anonymous.jpg", "Wizards, inc", "Some title", "Some address", "99922211", "77718882", "937557728", "993949587", "address1@ex.com", "address2@ex.com", "address3@ex.com", "http://localhost", "10", "August", "1955", "6", "April", "1999", "test1", "Some secondary address", "888899999", "Extremely important notes for test"));
    submitUserCreation();
    gotoHomePage();
    logout();

  }

}
