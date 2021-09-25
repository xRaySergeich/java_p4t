package ru.p4t.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.p4t.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends HelperBase {


  public GroupHelper(WebDriver wd) {
    super(wd);
  }
  public NavigationHelper navi = new NavigationHelper(wd);

  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getName());
    type(By.name("group_footer"), groupData.getName());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void selectGroup(int index) {
    getList().get(index).click();
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    navi.groupPage();
  }


  public void modify(int index, GroupData modGroup) {
    selectGroup(index);
    initGroupModification();
    fillGroupForm(modGroup);
    submitGroupModification();
    navi.groupPage();
  }

  public void delete(int index) {
    selectGroup(index);
    deleteSelectedGroups();
    navi.groupPage();
  }


  public boolean isThereAGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  public List<WebElement> getList() {
    List<WebElement> list = wd.findElements(By.name("selected[]"));
    return list;
  }

  public List<GroupData> list() {
    List<GroupData> groups = new ArrayList<GroupData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
    for (WebElement element : elements) {
      String name = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      groups.add(new GroupData()
              .withId(id)
              .withName(name));
    }
    return groups;
  }
}
