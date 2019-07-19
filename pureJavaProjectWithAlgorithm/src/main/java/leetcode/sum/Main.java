package leetcode.sum;

import java.util.HashMap;
// https://leetcode.com/problems/two-sum/
public class Main {
    public static void main(String[] argu) {
        int[] A = new int[]{2, 5, 4, 8, 9};
        int[] ans = getIndex(A, 7);
        for (int i = 0; i < ans.length; i++) {
            System.out.println(ans[i]);
        }
    }

    public static int[] getIndex(int[] A, int target) {
        HashMap<Integer, Integer> mapping = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            if (mapping.containsKey(A[i])) {
                return new int[]{mapping.get(A[i]), i};
            }
            mapping.put(target - A[i], i);
        }
        return null;
    }
}
