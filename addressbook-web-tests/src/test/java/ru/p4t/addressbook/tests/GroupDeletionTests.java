package ru.p4t.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends  TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    GroupData creationGroup = new GroupData("test1",null, null);
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(creationGroup);
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
    List<GroupData> before = app.group().list();

    int index = before.size() - 1;
    app.group().delete(index);
    log.info("Удалена группа " + before.get(index));
    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index);
    Assert.assertEquals(before,after);
  }


}
