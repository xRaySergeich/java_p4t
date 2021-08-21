package ru.p4t.sandbox;

public class MyFirstProgram {
  public static void main(String[] args) {
    hello("world");
    hello("user");

    double len = 55;
    double wid = 10;
    System.out.println("Площадь квадрата со стороной " + len + " = " + area(len));
    System.out.println("Площадь прямоугольника со сторонами " + len + ", " + wid + " = " + area(len, wid));
  }

  public  static void hello(String somebody) {
    System.out.println("Hello, " + somebody + "!");
  }

  public static double area(double a, double b) {
    return a * b;
  }

  public static double area(double l) {
    return l * l;
  }
}