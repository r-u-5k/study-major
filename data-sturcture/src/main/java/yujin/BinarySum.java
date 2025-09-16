package yujin;

public class BinarySum {
    public static int binarySum(int[] a, int i, int n) {
        if (n == 1) {
            return a[i];
        } else {
            return binarySum(a, i, (int) Math.ceil((double) n / 2.0)) + binarySum(a, i + (int) Math.ceil((double) n / 2.0), n / 2);
        }
    }

}
