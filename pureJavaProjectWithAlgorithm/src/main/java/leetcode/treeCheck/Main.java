package leetcode.treeCheck;

import java.util.ArrayList;

//https://leetcode.com/problems/univalued-binary-tree/
public class Main {
    public static void main(String[] argu) {
        TreeNode root = new TreeNode(10);
        ArrayList<Integer> list = new ArrayList<Integer>();
        traversal(list, root);
        boolean ans = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(0) != list.get(i)) {
                ans = false;
                break;
            }
        }
    }

    public static void traversal(ArrayList<Integer> list, TreeNode node) {
        if (node == null) {
            return;
        }
        list.add(node.val);
        traversal(list, node.left);
        traversal(list, node.right);
    }
}
