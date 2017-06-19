import com.mycompany.FibCalc;
import com.mycompany.FibCalcImpl;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class FibCalcTest {

    @Test
    public void calcLongPositive() {
        FibCalc fibCalc = new FibCalcImpl();
        assertThat(fibCalc.fib(0), is(0L));
        assertThat(fibCalc.fib(1), is(1L));
        assertThat(fibCalc.fib(10), is(55L));
        assertThat(fibCalc.fib(92), is(7540113804746346429L));
    }

    @Test
    public void calcLongNegative() {
        FibCalc fibCalc = new FibCalcImpl();
        assertThat(fibCalc.fib(0), is(0L));
        assertThat(fibCalc.fib(-1), is(1L));
        assertThat(fibCalc.fib(-10), is(-55L));
        assertThat(fibCalc.fib(-92), is(-7540113804746346429L));
    }

    @Test(expected = ArithmeticException.class)
    public void expectLongOverflow() {
        FibCalc fibCalc = new FibCalcImpl();
        fibCalc.fib(93);
    }

    @Test
    public void calcBigIntPositive() {
        FibCalc fibCalc = new FibCalcImpl();
        assertThat(fibCalc.fibBigInt(0), is(BigInteger.ZERO));
        assertThat(fibCalc.fibBigInt(1), is(BigInteger.ONE));
        assertThat(fibCalc.fibBigInt(10), is(BigInteger.valueOf(55L)));
        assertThat(fibCalc.fibBigInt(92), is(BigInteger.valueOf(7540113804746346429L)));
    }

    @Test
    public void calcBigIntNegative() {
        FibCalc fibCalc = new FibCalcImpl();
        assertThat(fibCalc.fibBigInt(0), is(BigInteger.ZERO));
        assertThat(fibCalc.fibBigInt(-1), is(BigInteger.ONE));
        assertThat(fibCalc.fibBigInt(-10), is(BigInteger.valueOf(-55L)));
        assertThat(fibCalc.fibBigInt(-92), is(BigInteger.valueOf(-7540113804746346429L)));
    }
}
