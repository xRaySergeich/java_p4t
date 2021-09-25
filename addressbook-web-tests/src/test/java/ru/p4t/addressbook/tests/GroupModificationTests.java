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
    GroupData creationGroup = new GroupData().withName("test1");
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(creationGroup);
    }
  }

  @Test
  public void testGroupModification() {
    List<GroupData> before = app.group().list();
    int index = before.size() - 1;
    GroupData modGroup = new GroupData()
            .withId(before.get(index).getId())
            .withName("test4")
            .withHeader("test5")
            .withFooter("test6");

    app.group().modify(index, modGroup);
    log.info("изменена группа, было " + before.get(index));
    List<GroupData> after = app.group().list();
    log.info("изменена группа, стало " + after.get(index));
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(modGroup);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(),g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
