package ru.p4t.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.ContactData;
import ru.p4t.addressbook.model.Contacts;
import ru.p4t.addressbook.model.GroupData;
import ru.p4t.addressbook.model.Groups;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactInGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions () {
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();

    if (groups.size() == 0) {
      GroupData group = app.group().createGroup();
      logger.info("Create a group " + group);
      groups = app.db().groups();
      if (before.size() != 0) {
        return;
      }
    }

    if (before.size() == 0) {
      app.contact().createContactWithoutGroup();
      return;
    }

    Groups contactGroups;

    for (ContactData contact : before) {
      contactGroups = contact.getGroups();
      if (contactGroups.size() != groups.size()) {
       return;
      }
    }

    GroupData group = app.group().createGroup();
    logger.info("Create a group " + group);
  }

  @Test()
  public void testAddContactInGroup() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();

    Groups contactGroups;

    for (ContactData contact : contacts) {
      contactGroups = contact.getGroups();
      if (contactGroups.size() != groups.size()) {
        for (GroupData group : groups) {
          if (contactGroups.stream().filter(it -> it.getId() == group.getId()).findFirst().orElse(null) == null) {
            app.contact().addContactInAGroup(contact, group);

            Contacts after = app.db().contacts();
            Groups contactGroupsAfter = Objects.requireNonNull(after.stream().filter(it -> it.getId() == contact.getId()).findFirst().orElse(null)).getGroups();

            assertThat(contactGroupsAfter.size(), equalTo(contactGroups.size() + 1));

            assertThat(contactGroupsAfter, equalTo(contactGroups.withAdded(group)));

            logger.info("Added group " + group + " to a contact " + contact);
            break;
          }
        }
      }
      break;
    }

  }

  public ContactData getRandomContact(Contacts contacts) {
    return contacts.iterator().next();
  }
}
