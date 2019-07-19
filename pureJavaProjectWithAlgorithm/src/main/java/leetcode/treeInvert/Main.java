package leetcode.treeInvert;

//https://leetcode.com/problems/invert-binary-tree/submissions/
public class Main {
    public static void main(String[] argu) {

    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        } else {
            TreeNode tmp = root.right;
            root.right = invertTree(root.left);
            root.left = invertTree(tmp);
            return root;
        }
    }
}
