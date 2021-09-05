package ru.p4t.sandbox;

public class Equality {

  public static void main(String[] args) {
    //сравнение одного объекта
    String s1 = "some_str";
    String s2 = s1;

    compare(s1, s2);

    //сравнение разных объектов
    String s3 = "some_str";
    String s4 = new String(s3);

    compare(s3, s4);

    //объект по факту один и тот же
    String s5 = "some_str";
    String s6 = "some_str";

    compare(s5, s6);


  }

  private static void compare(String s1, String s2) {
    System.out.println(s1 == s2);
    System.out.println(s1.equals(s2));
  }
}
