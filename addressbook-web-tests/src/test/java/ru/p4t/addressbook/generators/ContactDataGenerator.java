package ru.p4t.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.p4t.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try{
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }

    generator.run();

  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData()
              .withFirstname(String.format("Zorian %s", i))
              .withMiddlename(String.format("Viktorovich %s", i))
              .withLastname(String.format("Kazinsky %s", i))
              .withNickname(String.format("Nutcracker %s", i))
              .withCompany(String.format("Wizards, inc %s", i))
              .withTitle(String.format("Some title %s", i))
              .withAddress(String.format("Some address %s", i))
              .withHomePhone(String.format("99922211 %s", i))
              .withMobilePhone(String.format("77718882 %s", i))
              .withWorkPhone(String.format("937557728 %s", i))
              .withFax(String.format("993949587 %s", i))
              .withEmail(String.format("address1%s@ex.com", i))
              .withEmail2(String.format("address2%s@ex.com", i))
              .withEmail3(String.format("address3%s@ex.com", i))
              .withHomepage(String.format("http://localhost%s", i))
              .withBday("10")
              .withBmonth("August")
              .withByear("1955")
              .withAday("6")
              .withAmonth("April")
              .withAyear("1999")
              .withGroup("test1")
              .withAddress2(String.format("Some secondary address %s", i))
              .withPhone2(String.format("888899999%s", i))
              .withNotes(String.format("Extremely important notes for test %s", i)));
    }
    return contacts;
  }
}