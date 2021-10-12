package ru.p4t.mantis.tests;

import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.p4t.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTest extends TestBase {

  //@BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  //@AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

  @Test
  public void testRegistration() throws MessagingException, IOException {
    long now = System.currentTimeMillis();
    String email = "user" + now + "@localhost";
    String user = "user" + now;
    String password = "password";

    app.james().createUser(user, password);

    app.registration().start(user, email);
    //List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000, 0);

    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter(m -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
}
