package ru.p4t.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.GroupData;
import ru.p4t.addressbook.model.Groups;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    GroupData creationGroup = new GroupData().withName("test1");
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(creationGroup);
    }
  }

  @Test
  public void testGroupModification() {
    Groups before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId())
            .withName("test4")
            .withHeader("test5")
            .withFooter("test6");

    app.group().modify(group);
    log.info("изменена группа, было " + modifiedGroup);
    Groups after = app.group().all();
    log.info("изменена группа, стало " + group);
    assertEquals(after.size(), before.size());

    before.remove(modifiedGroup);
    before.add(group);
    assertEquals(before, after);
    assertThat(after, equalTo(before.withModified(group, modifiedGroup)));
  }

}
