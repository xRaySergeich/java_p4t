package ru.p4t.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
    Set<GroupData> before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();
    GroupData modGroup = new GroupData()
            .withId(modifiedGroup.getId())
            .withName("test4")
            .withHeader("test5")
            .withFooter("test6");

    app.group().modify(modGroup);
    log.info("изменена группа, было " + modifiedGroup);
    Set<GroupData> after = app.group().all();
    log.info("изменена группа, стало " + modGroup);
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedGroup);
    before.add(modGroup);
    Assert.assertEquals(before, after);
  }

}
