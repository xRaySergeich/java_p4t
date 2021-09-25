package ru.p4t.addressbook.tests;

import org.testng.annotations.*;
import ru.p4t.addressbook.model.GroupData;
import ru.p4t.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("test1");
    app.group().create(group);
    log.info("Добавлена группа " + group);
    app.goTo().groupPage();
    Groups after = app.group().all();

    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }


}
