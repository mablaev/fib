package com.mycompany;

import java.math.BigInteger;

/**
 * A Fibonacci calculator.
 */
public interface FibCalc {

	/**
	 * Calculates the Fibonacci number with the given index.
	 * Examples:
	 * fib(1) = 1    <br>
	 * fib(2) = 1    <br>
	 * fib(3) = 2    <br>
	 * fib(4) = 3    <br>
	 * fib(5) = 5    <br>
	 */
	@Deprecated
	public long fib(int n);

	public BigInteger fibBigInt(int n);
}
