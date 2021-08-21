package ru.p4t.point;

public class Main {
  public static void main(String[] args) {

    //п.3. Сделать запускаемый класс и при помощи него убедиться, что функция вычисления расстояния между точками действительно работает.
    Point p1 = new Point(5, 3);
    Point p2 = new Point(-1, -2);
    System.out.println("Расстояние между точками p1(" + p1.x + "," + p1.y + ") и p2(" + p2.x + "," + p2.y + ") = " + distance(p1, p2));

    //4. Реализовать то же самое (вычисление расстояния между двумя точками) при помощи метода в классе Point
    System.out.println("Расстояние между точками p1(" + p1.x + "," + p1.y + ") и p2(" + p2.x + "," + p2.y + ") = " + p1.distance(p2));
  }

  //2. Создать функцию
  //public static double distance(Point p1, Point p2)
  //которая вычисляет расстояние между двумя точками. Для вычисления квадратного корня можно использовать функцию Math.sqrt
  public static double distance(Point p1, Point p2) {
    double diffX = p2.x - p1.x;
    double diffY = p2.y - p1.y;

    return  Math.sqrt(diffX * diffX + diffY * diffY);
  }

}
