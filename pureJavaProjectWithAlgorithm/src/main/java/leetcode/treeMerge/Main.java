package leetcode.treeMerge;

public class Main {
    public static void main(String[] argu) {
        // https://leetcode.com/problems/merge-two-binary-trees/
    }

    public TreeNode merge(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        } else if (t2 == null) {
            return t1;
        } else {
            TreeNode mergeNode = new TreeNode(0);
            mergeNode.val = t1.val + t2.val;
            mergeNode.left = merge(t1.left, t2.left);
            mergeNode.right = merge(t1.right, t2.right);
            return mergeNode;
        }
    }
}
