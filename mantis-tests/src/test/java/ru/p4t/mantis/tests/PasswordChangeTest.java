package ru.p4t.mantis.tests;

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

  @Test
  public void testPasswordChange() throws IOException, MessagingException, InterruptedException {
    //Create user
    long now = System.currentTimeMillis();
    String email = "user" + now + "@localhost";
    String user = "user" + now;
    String password = "password";
    String newPassword = "newPwd";
    app.james().createUser(user, password);
    app.registration().start(user, email);
    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000, 0);
    String confirmationLink = findConfirmationLinkSignup(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
    logger.info(String.format("User %s created", user));

    //reset password by admin
    String adminName = "administrator";
    String adminPass = "root";
    app.loginPage().login(adminName, adminPass);
    app.managePage().ResetUserPassword(user);
    logger.info(String.format("Password for user %s is reset", user));


    //get mail and change pwd
    logger.info("Waiting for emails");
    mailMessages = app.james().waitForMail(user, password, 60000, 1);
    logger.info("Number of emails: " + mailMessages.size());
    logger.info("Going to set new password");
    confirmationLink = findConfirmationLinkReset(mailMessages, email);
    app.registration().finish(confirmationLink, newPassword);

    //check if user is able to login with new pwd
    assertTrue(app.newSession().login(user, newPassword));
    logger.info("User " + user + " is successfully logged with new password");

  }

  private void setPwd(String confirmationLink, String password) {
    app.registration().finish(confirmationLink, password);
    logger.info("Password is set");
  }

  private String findConfirmationLinkSignup(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter(m -> m.to.equals(email)).findFirst().get();
    logger.info("Signup message text " + mailMessage.text);
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  private String findConfirmationLinkReset(List<MailMessage> mailMessages, String email) {
    List<MailMessage> messages = mailMessages.stream().filter(m -> m.to.equals(email) && m.text.contains("Your password has been reset.")).collect(Collectors.toList());
    messages.sort(Collections.reverseOrder());
    MailMessage mailMessage = messages.get(0);
    logger.info("Message text " + mailMessage.text);
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
}
