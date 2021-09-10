package ru.p4t.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {
  @Test
  public void testGroupModification() {
    GroupData creationGroup = new GroupData("test1",null, null);

    app.getNavigationHelper().gotoGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(creationGroup);
    }

    List<GroupData> before = app.getGroupHelper().getGroupList();
    int index = before.size() - 1;
    app.getGroupHelper().selectGroup(index);

    app.getGroupHelper().initGroupModification();

    GroupData modGroup = new GroupData(before.get(index).getId(), "test4", "test5", "test6");

    app.getGroupHelper().fillGroupForm(modGroup);
    app.getGroupHelper().submitGroupModification();
    log.info("Изменена группа, было " + before.get(index));
    app.getNavigationHelper().gotoGroupPage();
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
