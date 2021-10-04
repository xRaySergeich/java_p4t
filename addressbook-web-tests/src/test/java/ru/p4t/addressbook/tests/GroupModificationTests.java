package ru.p4t.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.GroupData;
import ru.p4t.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.db().groups().size() == 0) {
      GroupData creationGroup = new GroupData().withName("test1");
      app.group().create(creationGroup);
    }
  }

  @Test
  public void testGroupModification() {
    Groups before = app.db().groups();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId())
            .withName("test4")
            .withHeader("test5")
            .withFooter("test6");

    app.group().modify(group);
    assertThat(app.group().count(), equalTo(before.size()));
    logger.info("modified group, before " + modifiedGroup);
    Groups after = app.db().groups();
    logger.info("modified group, after " + group);

    before.remove(modifiedGroup);
    before.add(group);
    assertThat(after, equalTo(before.withModified(group, modifiedGroup)));

    verifyGroupListInUI();
  }

}
