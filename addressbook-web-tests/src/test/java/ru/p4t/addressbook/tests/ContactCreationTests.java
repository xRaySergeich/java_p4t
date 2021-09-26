package ru.p4t.addressbook.tests;

import com.google.gson.Gson;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;
import ru.p4t.addressbook.model.Contacts;
import ru.p4t.addressbook.model.GroupData;
import ru.p4t.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType()); //List<ContactData>.class
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  private Groups groups = new Groups();

  @BeforeTest
  public void getGroupList() {
    app.goTo().groupPage();
    groups = app.group().all();
  }

  public void groupPrecondition (String groupName) {
    app.goTo().groupPage();
      GroupData creationGroup = new GroupData().withName(groupName);
      app.group().create(creationGroup);
    groups = app.group().all();
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData cd) throws Exception {
    ContactData finalCd = cd;
    if (groups.stream().filter(group -> group.getName().equals(finalCd.getGroup())).findFirst().orElse(null) == null) {
      groupPrecondition(cd.getGroup());
    }

    File photo = new File("src/test/resources/anonymous.jpg");
    cd = cd.withPhoto(photo);
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
