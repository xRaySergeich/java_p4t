package ru.p4t.mantis.model;

import java.time.LocalDateTime;

public class MailMessage implements Comparable<MailMessage> {

  public String to;
  public String text;
  public LocalDateTime date;

  public MailMessage(String to, String text, LocalDateTime date) {
    this.to = to;
    this.text = text;
    this.date = date;
  }

  public LocalDateTime getDate() {
    return date;
  }

  @Override
  public int compareTo(MailMessage o) {
    return getDate().compareTo(o.getDate());
  }
}
