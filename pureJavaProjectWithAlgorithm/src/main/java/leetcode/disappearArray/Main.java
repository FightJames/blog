package leetcode.disappearArray;

import java.util.ArrayList;
import java.util.List;

//https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
public class Main {
    public static void main(String[] argu) {
        List<Integer> ans = findDisappearedNumbers(new int[]{4, 3, 2, 7, 8, 2, 3, 1});
        for (int i = 0; i < ans.size(); i++) {
            System.out.print(ans.get(i) + " ");
        }
    }

    public static List<Integer> findDisappearedNumbers(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            index = nums[i];
            if (index < 0) index *= -1;
            index--;
            if (nums[index] > 0) {
                nums[index] *= -1;
            }
        }

        ArrayList<Integer> ans = new ArrayList<>();
        for (int k = 0; k < nums.length; k++) {
            if (nums[k] > 0) {
                ans.add(k + 1);
            }
        }
        return ans;
    }
}
