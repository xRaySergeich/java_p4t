package ru.p4t.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.GroupData;
import ru.p4t.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends  TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    GroupData creationGroup = new GroupData().withName("test1");
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(creationGroup);
    }
  }

  @Test
  public void testGroupDeletion() {
    Groups before = app.group().all();
    GroupData deletedGroup = before.iterator().next();

    app.group().delete(deletedGroup);
    assertThat(app.group().count(), equalTo(before.size() - 1));
    logger.info("deleted group " + deletedGroup);
    Groups after = app.group().all();
    assertThat(after, equalTo(before.without(deletedGroup)));
  }

}
