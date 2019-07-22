package leetcode.sum;

//https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
public class SortDataMain {
    public static void main(String[] argu) {
        int[] nums = new int[]{2, 7, 11, 15};
        int[] ans = getIndex(nums, 7);
        for (int i = 0; i < ans.length; i++) {
            System.out.print(ans[i] + " ");
        }
    }

    public static int[] getIndex(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (target == sum) {
                return new int[]{++left, ++right};
            } else if (target > sum) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{};
    }

}
