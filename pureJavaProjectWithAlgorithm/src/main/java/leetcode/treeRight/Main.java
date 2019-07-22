package leetcode.treeRight;

import java.util.ArrayList;

//https://leetcode.com/problems/increasing-order-search-tree/
public class Main {

    public static void main(String[] argu) {

    }

    public TreeNode increasingBST(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        inOrder(root, list);
        int rootVal = list.get(0);
        TreeNode rootAns = new TreeNode(rootVal);
        TreeNode current = rootAns;
        for (int i = 1; i < list.size(); i++) {
            TreeNode node = new TreeNode(list.get(i));
            current.right = node;
            current = node;
        }
        return rootAns;
    }

    public void inOrder(TreeNode node, ArrayList<Integer> list) {
        if (node == null) {
            return;
        }
        inOrder(node.left, list);
        list.add(node.val);
        inOrder(node.right, list);
    }
}
