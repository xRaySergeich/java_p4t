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

public class DeleteContactFromGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();

    if (before.size() == 0) {
      app.contact().createContactWithoutGroup();
      before = app.db().contacts();
    }

    if (groups.size() == 0) {
      app.group().createGroup();
      groups = app.db().groups();
    }

    for (ContactData contact : before) {
      if (contact.getGroups().size() != 0) {
        return;
      }
    }

    app.contact().addContactInAGroup(getRandomContact(before), getRandomGroup(groups));

  }

  @Test
  public void testDeleteContactForGroup() {
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();

    Groups contactGroups;

    for (ContactData contact : before) {
      contactGroups = contact.getGroups();
      if (contactGroups.size() != 0) {
        for (GroupData group : groups) {
          if (contactGroups.stream().filter(it -> it.getId() == group.getId()).findFirst().orElse(null) != null) {
            app.contact().deleteContactFromAGroup(contact, group);

            Contacts after = app.db().contacts();
            Groups contactGroupsAfter = Objects.requireNonNull(after.stream().filter(it -> it.getId() == contact.getId()).findFirst().orElse(null)).getGroups();

            assertThat(contactGroupsAfter.size(), equalTo(contactGroups.size() - 1));

            assertThat(contactGroupsAfter, equalTo(contactGroups.without(group)));

            logger.info("Deleted group " + group + " from contact " + contact);
            break;
          }
        }
        break;
      }
    }
  }

    public ContactData getRandomContact(Contacts contacts){
      return contacts.iterator().next();
    }

    public GroupData getRandomGroup(Groups groups){
      return groups.iterator().next();
    }
  }
