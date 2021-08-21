package ru.p4t.sandbox;

public class MyFirstProgram {
  public static void main(String[] args) {
    hello("world");
    hello("user");

    Square s = new Square(25);

    Rectangle r = new Rectangle(6, 8);

    System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

    System.out.println("Площадь прямоугольника со сторонами " + r.a + ", " + r.b + " = " + r.area());
  }

  public  static void hello(String somebody) {
    System.out.println("Hello, " + somebody + "!");
  }

}