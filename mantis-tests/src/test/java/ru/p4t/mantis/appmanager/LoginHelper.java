package ru.p4t.mantis.appmanager;

import org.openqa.selenium.By;

import static org.testng.Assert.assertTrue;

public class LoginHelper extends HelperBase{

  public LoginHelper(ApplicationManager app){
    super(app);
  }

  public void login(String username, String password) {

    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.id("username"), username);
    click(By.cssSelector("input[type='submit']"));
    assertTrue(wd.findElement(By.cssSelector("fieldset")).getText().contains(String.format("Введите пароль для '%s'", username)));
    type(By.id("password"), password);
    click(By.cssSelector("input[type='submit']"));
    assertTrue(wd.findElement(By.xpath("//ul[@class='breadcrumb']//a")).getText().equals(username));
  }
}
