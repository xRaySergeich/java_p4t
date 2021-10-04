package ru.p4t.addressbook.tests;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.p4t.addressbook.model.GroupData;
import ru.p4t.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroupsFromCsv() throws IOException {
    List<Object[]> list = new ArrayList<>();
    try(BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.csv"))) {
      String line = reader.readLine();
      while (line != null) {
        String[] split = line.split(";");
        list.add(new Object[]
                {new GroupData()
                        .withName(split[0])
                        .withHeader(split[1])
                        .withFooter(split[2])});
        line = reader.readLine();
      }
      return list.iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromXml() throws IOException {
    try(BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.xml"))) {
      StringBuilder xml = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        xml.append(line);
        line = reader.readLine();
      }
      XStream xStream = new XStream();
      xStream.addPermission(AnyTypePermission.ANY);
      xStream.processAnnotations(GroupData.class);
      List<GroupData> groups = (List<GroupData>) xStream.fromXML(xml.toString());
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.json"))) {
      StringBuilder json = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        json.append(line);
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GroupData> groups = gson.fromJson(json.toString(), new TypeToken<List<GroupData>>() {
      }.getType()); //List<GroupData>.class
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validGroupsFromJson")
  public void testGroupCreation(GroupData group) {

    app.goTo().groupPage();
    Groups before = app.db().groups();
    app.group().create(group);
    logger.info("added group " + group);
    app.goTo().groupPage();
    assertThat(app.group().count(), equalTo(before.size() + 1));
    Groups after = app.db().groups();
    assertThat(after, equalTo(
            //before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
            before.withAdded(group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt()))));

    verifyGroupListInUI();
  }

  @Test
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.db().groups();
    GroupData group = new GroupData().withName("test1'");
    app.group().create(group);
    app.goTo().groupPage();
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups();
    assertThat(after, equalTo(
            before));
  }

}
