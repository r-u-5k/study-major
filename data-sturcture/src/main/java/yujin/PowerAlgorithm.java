package yujin;

public class PowerAlgorithm {
    public static long power(long x, int n) {
        if (n == 0) {
            return 1;
        } else if (n % 2 != 0) {
            long y = power(x, (n - 1) / 2);
            return x * y * y;
        } else {
            long y = power(x, n / 2);
            return y * y;
        }
    }

}
