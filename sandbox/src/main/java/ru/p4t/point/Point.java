package ru.p4t.point;

public class Point {

  public double x;
  public double y;

  public Point (double x, double y) {

    this.x = x;
    this.y = y;
  }

  //4. Реализовать то же самое (вычисление расстояния между двумя точками) при помощи метода в классе Point
  public double distance(Point p2) {

    double diffX = p2.x - this.x;
    double diffY = p2.y - this.y;

    return  Math.sqrt(diffX * diffX + diffY * diffY);
  }
}
