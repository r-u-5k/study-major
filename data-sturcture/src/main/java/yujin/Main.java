package yujin;

public class Main {
    public static void main(String[] args) {
        long base = 5;
        int exponent = 16;
        long result1 = PowerAlgorithm.power(base, exponent);
        System.out.println(base + "^" + exponent + " = " + result1);

        int[] numbers = new int[100];
        for (int i = 0; i < 100; i++) {
            numbers[i] = i + 1;
        }
        int result2 = BinarySum.binarySum(numbers, 0, numbers.length);
        System.out.println("1부터 100까지의 합: " + result2);

        long[] result3 = LinearFib.linearFib(50);
        System.out.println("50번째 수열: " + result3[0]);

    }
}
