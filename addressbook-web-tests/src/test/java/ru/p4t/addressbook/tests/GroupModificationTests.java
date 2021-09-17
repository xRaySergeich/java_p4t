package ru.p4t.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    GroupData creationGroup = new GroupData("test1",null, null);
    app.getNavigationHelper().gotoGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(creationGroup);
    }
  }

  @Test
  public void testGroupModification() {
    List<GroupData> before = app.getGroupHelper().getGroupList();
    int index = before.size() - 1;
    GroupData modGroup = new GroupData(before.get(index).getId(), "test4", "test5", "test6");

    app.getGroupHelper().modifyGroup(index, modGroup);
    log.info("Изменена группа, было " + before.get(index));
    List<GroupData> after = app.getGroupHelper().getGroupList();
    log.info("Изменена группа, стало " + after.get(index));
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(modGroup);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(),g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
