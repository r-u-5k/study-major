package yujin;

public class LinearFib {
    public static long[] linearFib(int n) {
        if (n <= 1) {
            return new long[]{n, 0};
        } else {
            long[] temp = linearFib(n - 1);
            long i = temp[0]; // i = F_(n-1)
            long j = temp[1]; // j = F_(n-2)
            return new long[]{i + j, i};
        }
    }
}
