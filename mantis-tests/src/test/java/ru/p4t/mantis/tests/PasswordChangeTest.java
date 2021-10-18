package ru.p4t.mantis.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.p4t.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class PasswordChangeTest extends TestBase {


  @BeforeClass
  public void init() {
    app.loginPage().loginAsAdmin();
  }


  @Test
  public void testPasswordChange() throws IOException, MessagingException {

    //reset password by admin
    String userName = getRandomUserName();
    app.managePage().ResetUserPassword(userName);
    logger.info(String.format("Password for user %s is reset", userName));


    //get mail and change pwd
    String password = "password";
    String newPassword = "newPassword";
    logger.info("Waiting for emails");
    List<MailMessage> mailMessages = app.james().waitForMail(userName, password, 60000, 1);
    logger.info("Number of emails: " + mailMessages.size());
    logger.info("Going to set new password");
    String confirmationLink = findConfirmationLinkReset(mailMessages, String.format("%s@localhost", userName));
    app.registration().finish(confirmationLink, newPassword);

    //check if user is able to login with new pwd
    assertTrue(app.newSession().login(userName, newPassword));
    logger.info("User " + userName + " is successfully logged with new password");

  }

  public String getRandomUserName() {
    return app.managePage().getUserList().iterator().next();
  }

  private String findConfirmationLinkReset(List<MailMessage> mailMessages, String email) {
    List<MailMessage> messages = mailMessages.stream().filter(m -> m.to.equals(email) && m.text.contains("Your password has been reset.")).collect(Collectors.toList());
    messages.sort(Collections.reverseOrder());
    MailMessage mailMessage = messages.get(0);
    logger.info("Message text " + mailMessage.text);
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterClass
  public void tearDown() {
    app.loginPage().logOut();
  }
}
