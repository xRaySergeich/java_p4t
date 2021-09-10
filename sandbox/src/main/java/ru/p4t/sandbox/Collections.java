package ru.p4t.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

  public static void main(String[] args) {
    String[] langs = {"Java", "C#", "Python", "PHP"};

    List<String> languages1 = new ArrayList<String>();
    languages1.add("Java");
    languages1.add("C#");

    List<String> languages = Arrays.asList(langs);

    List languagesObj = Arrays.asList(langs);

    for (int i = 0; i < langs.length; i++) {
      System.out.println("Я хочу выучить " + langs[i]);
    }

    breakLine();

    for (String l : langs) {
      System.out.println("Я хочу выучить " + l);
    }

    breakLine();

    for (int i = 0; i < languages.size(); i++) {
      System.out.println("Я хочу выучить " + languages.get(i));
    }

    breakLine();

    for (String l : languages) {
      System.out.println("Я хочу выучить " + l);
    }

    breakLine();

    for (Object l : languagesObj) {
      System.out.println("Я хочу выучить " + l);
    }

  }

  private static void breakLine() {
    System.out.println("------------");
  }
}
