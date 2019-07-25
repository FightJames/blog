package leetcode.climb;

public class Climb {
    public static void main(String[] argu) {
        System.out.print(climbForF(44));
    }

    public static int climb(int start, int target, int[] memo) {
        if (start > target) {
            return 0;
        }
        if (start == target) {
            return 1;
        }
        if (memo[start] > 0) {
            return memo[start];
        }
        return climb(start + 1, target, memo) + climb(start + 2, target, memo);
    }

    public static int climbFF(int input) {
        if (input <= 0) {
            return 0;
        }
        if (input == 1) {
            return 1;
        }
        if (input == 2) {
            return 2;
        }
        return climbFF(input - 1) + climbFF(input - 2);
    }

    public static int climbForF(int input) {
        if (input == 1) {
            return 1;
        }
        int[] dp = new int[input + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= input; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[input];
    }
}
