package ru.p4t.mantis.tests;

import org.testng.annotations.Test;

public class RegistrationTest extends TestBase{

  @Test
  public void testRegistration() {
    app.registration().start("u1", "email");
  }
}
