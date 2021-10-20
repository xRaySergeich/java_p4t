package ru.p4t.rest;

import org.testng.annotations.Test;

public class SomeTest extends SuperTest {

    @Test
    public void fixed() {
        skipIfNotFixed(1536);
        System.out.println("тест пройден");
    }

    @Test
    public void notFixed() {
        skipIfNotFixed(1535);
    }
}
