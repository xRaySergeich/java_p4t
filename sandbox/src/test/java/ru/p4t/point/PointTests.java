package ru.p4t.point;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testDistanceIntDouble() {
    Point p3 = new Point(5, 3);
    Point p4 = new Point(-1, -2);

    Assert.assertEquals(p3.distance(p4),7.810249675906654);
  }

  @Test
  public void testDistanceIntInt() {
    Point p1 = new Point(3, 4);
    Point p2 = new Point(0, 0);

    Assert.assertEquals(p1.distance(p2), 5);
  }

  @Test
  public void testDistanceDoubleDouble() {
    Point p1 = new Point(2.064, 3.072);
    Point p2 = new Point(8.5732, -23.44);

    Assert.assertEquals(p1.distance(p2), 27.29937414374183);
  }

  @Test
  public void testDistanceNil() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 0);

    Assert.assertEquals(p1.distance(p2), 0);
  }

  @Test
  public void testDistanceOverFlow() {
    Point p1 = new Point(99999944444449999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999993333333333333333333333333333333333333333333333333333333344444444444444444444444444444444444222222222222222222222222222222221111111111111111111111111111111111111111111111111118999999999999999999999.0, 99999944444449999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999993333333333333333333333333333333333333333333333333333333344444444444444444444444444444444444222222222222222222222222222222221111111111111111111111111111111111111111111111111118999999999999999999999.0);
    Point p2 = new Point(0, 0);

    Assert.assertEquals(p1.distance(p2),Math.pow(999,999));
  }
}
