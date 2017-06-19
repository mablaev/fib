package com.mycompany;


import java.math.BigInteger;

public class FibCalcImpl implements FibCalc {

    /**
     * Use fibBigInt instead, this function is deprecated
     *
     */
    @Override
    @Deprecated
    public long fib(int n) {
        long first = 0L;
        long second = 1L;
        long next;

        for (int i = 0; i < Math.abs(n) - 1; i++) {
            next = Math.addExact(first, second);
            first = second;
            second = next;
        }
        if (n == 0) {
            return 0L;
        } else if (n > 0) {
            return second;
        } else {
            long sign = (long) Math.pow(-1, n + 1);
            return second *= sign;
        }
    }

    @Override
    public BigInteger fibBigInt(int n) {
        BigInteger first = BigInteger.ZERO;
        BigInteger second = BigInteger.ONE;
        BigInteger next;

        for (int i = 0; i < Math.abs(n) - 1; i++) {
            next = first.add(second);
            first = second;
            second = next;
        }
        if (n == 0) {
            return BigInteger.ZERO;
        } else if (n > 0) {
            return second;
        } else {
            long sign = (long) Math.pow(-1, n + 1);
            return second.multiply(BigInteger.valueOf(sign));
        }
    }


}
