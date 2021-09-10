package ru.p4t.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimeTests {

    @Test
    public void testPrime() {
        Assert.assertTrue(Primes.isPrime(Integer.MAX_VALUE));
    }

    @Test
    public void testPrimeFast() {
        Assert.assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE));
    }

    @Test
    public void testPrimeMath() {
        Assert.assertTrue(Primes.isPrimeMath(Integer.MAX_VALUE));
    }

    @Test
    public void testNonPrime() {
        Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE - 2));
    }

    @Test
    public void testPrimeWhile() {
        Assert.assertTrue(Primes.isPrimeWhile(Integer.MAX_VALUE));
    }

    @Test
    public void testNonPrimeWhile() {
        Assert.assertFalse(Primes.isPrimeWhile(Integer.MAX_VALUE - 2));
    }

    @Test(enabled = false)
    public void testPrimeLong() {
        long n = Integer.MAX_VALUE;
        Assert.assertTrue(Primes.isPrime(n));
    }
}
