package ru.p4t.mantis.appmanager;

import org.openqa.selenium.By;

public class ManageHelper extends HelperBase {
  public ManageHelper(ApplicationManager app) {
    super(app);
  }

  public void ResetUserPassword(String userName) {
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
    click(By.xpath(String.format("//tr/td[1]/a[.='%s']", userName)));
    click(By.xpath("//input[@value='Сбросить пароль']"));
    /*assertEquals(wd.findElement(By.cssSelector(".bigger-110"))
            .getText(), "По адресу электронной почты указанного пользователя отправлен запрос подтверждения. С его помощью пользователь сможет изменить пароль.");*/
  }
}
