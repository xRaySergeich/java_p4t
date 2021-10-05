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
  public void ensurePreconditions () {
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();

    if (groups.size() == 0) {
      GroupData group = app.group().createGroup();
      logger.info("Create a group " + group);
      groups = app.db().groups();
    }

    if (before.size() == 0) {
      app.contact().createContact(groups.iterator().next());
      before = app.db().contacts();
    } else {
      ContactData chosenContact = getRandomContact(before);
      if (chosenContact.getGroups().size() == 0) {
        boolean isThereAContactWithGroups = false;
        for (ContactData contact : before) {
          chosenContact = getRandomContact(before);
          if (chosenContact.getGroups().size() != 0) {
            isThereAContactWithGroups = true;
            break;
          }
        }
        if (!isThereAContactWithGroups) {
          //добавить контакт в группу
          AddContactInGroupTest addContactInGroupTest = new AddContactInGroupTest();
          addContactInGroupTest.testAddContactInGroup();
        }
      }

    }

  }

  @Test
  public void testDeleteContactForGroup() {
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();

    ContactData chosenContact = getRandomContact(before);
    Groups contactGroups = chosenContact.getGroups();

    for (ContactData contact : before) {
      if (contactGroups.size() == 0) {
        chosenContact = getRandomContact(before);
      } else {
        for (GroupData group : groups) {
          if (contactGroups.stream().filter(it -> it.getId() == group.getId()).findFirst().orElse(null) != null) {
            app.contact().deleteContactFromAGroup(chosenContact, group);

            Contacts after = app.db().contacts();
            ContactData finalChosenContact = chosenContact;
            Groups contactGroupsAfter = Objects.requireNonNull(after.stream().filter(it -> it.getId() == finalChosenContact.getId()).findFirst().orElse(null)).getGroups();

            assertThat(contactGroupsAfter.size(), equalTo(contactGroups.size() - 1));

            assertThat(after, equalTo(before.withDeletedGroupFromContact(chosenContact, group)));

            logger.info("Deleted group " + group + " from contact " + finalChosenContact);
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
