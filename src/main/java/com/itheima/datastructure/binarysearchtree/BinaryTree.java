package com.itheima.datastructure.binarysearchtree;

import java.util.LinkedList;
import java.util.Queue;

// 二叉树节点类
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class BinaryTree {

// ------------------- 1. 前序遍历：根 -> 左 -> 右 -------------------
    public void preOrder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    // ------------------- 2. 中序遍历：左 -> 根 -> 右 -------------------
    public void inOrder(TreeNode root) {
        if (root == null) return;
        inOrder(root.left);
        System.out.print(root.val + " ");
        inOrder(root.right);
    }

    // ------------------- 3. 后序遍历：左 -> 右 -> 根 -------------------
    public void postOrder(TreeNode root) {
        if (root == null) return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.val + " ");
    }

    // ------------------- 4. 层序遍历（递归版） -------------------
    public void levelOrder(TreeNode root) {
        if (root == null) return;
        int depth = maxDepth(root);
        for (int i = 1; i <= depth; i++) {
            printLevel(root, i);
        }
    }

    // 打印第 level 层
    private void printLevel(TreeNode root, int level) {
        if (root == null) return;
        if (level == 1) {
            System.out.print(root.val + " ");
        } else {
            printLevel(root.left, level - 1);
            printLevel(root.right, level - 1);
        }
    }

    // ------------------- 5. 求树的最大深度 -------------------
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    // ------------------- 6. 总结点个数 -------------------
    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        return countNodes(root.left) + countNodes(root.right) + 1;
    }

    // ------------------- 7. 叶子节点个数 -------------------
    public int countLeafNodes(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return countLeafNodes(root.left) + countLeafNodes(root.right);
    }

    // ------------------- 8. 查找值为 target 的节点 -------------------
    public TreeNode searchNode(TreeNode root, int target) {
        if (root == null) return null;
        if (root.val == target) return root;
        TreeNode leftFind = searchNode(root.left, target);
        if (leftFind != null) return leftFind;
        return searchNode(root.right, target);
    }
// ==================== 以下是新增功能 ====================

    // ------------------- 9. 插入节点（BST规则） -------------------
    public TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insert(root.left, val);
        else if (val > root.val) root.right = insert(root.right, val);
        return root;
    }

    // ------------------- 10. 删除节点（BST规则） -------------------
    public TreeNode delete(TreeNode root, int val) {
        if (root == null) return null;

        if (val < root.val) root.left = delete(root.left, val);
        else if (val > root.val) root.right = delete(root.right, val);
        else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            TreeNode min = root.right;
            while (min.left != null) min = min.left;
            root.val = min.val;
            root.right = delete(root.right, min.val);
        }
        return root;
    }

    // ------------------- 11. 翻转二叉树 -------------------
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    // ------------------- 12. 序列化（树 → 字符串） -------------------
    public String serialize(TreeNode root) {
        if (root == null) return "null,";
        String res = root.val + ",";
        res += serialize(root.left);
        res += serialize(root.right);
        return res;
    }

    // ------------------- 13. 反序列化（字符串 → 树） -------------------
    public TreeNode deserialize(String data) {
        String[] arr = data.split(",");
        Queue<String> q = new LinkedList<>();
        for (String s : arr) q.offer(s);
        return buildTree(q);
    }

    private TreeNode buildTree(Queue<String> q) {
        String val = q.poll();
        if (val.equals("null")) return null;
        TreeNode root = new TreeNode(Integer.parseInt(val));
        root.left = buildTree(q);
        root.right = buildTree(q);
        return root;
    }

    // ------------------- 14. 判断是否平衡二叉树 -------------------
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int l = maxDepth(root.left);
        int r = maxDepth(root.right);
        if (Math.abs(l - r) > 1) return false;
        return isBalanced(root.left) && isBalanced(root.right);
    }

    // ------------------- 15. 判断是否完全二叉树 -------------------
    public boolean isComplete(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean end = false;

        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (cur == null) end = true;
            else {
                if (end) return false;
                q.offer(cur.left);
                q.offer(cur.right);
            }
        }
        return true;
    }

    // ------------------- 16. 判断是否满二叉树 -------------------
    public boolean isFull(TreeNode root) {
        if (root == null) return true;
        if (root.left == null && root.right == null) return true;
        if (root.left == null || root.right == null) return false;
        return isFull(root.left) && isFull(root.right);
    }




















      public static void main(String[] args) {
        // 构建一棵二叉树
        //        1
        //       / \
        //      2   3
        //     / \
        //    4   5
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        BinaryTree bt = new BinaryTree();

        System.out.print("前序遍历：");
        bt.preOrder(root);
        System.out.println();

        System.out.print("中序遍历：");
        bt.inOrder(root);
        System.out.println();

        System.out.print("后序遍历：");
        bt.postOrder(root);
        System.out.println();

        System.out.print("层序遍历：");
        bt.levelOrder(root);
        System.out.println();

        System.out.println("树的深度：" + bt.maxDepth(root));
        System.out.println("总结点个数：" + bt.countNodes(root));
        System.out.println("叶子节点个数：" + bt.countLeafNodes(root));

        int target = 5;
        TreeNode res = bt.searchNode(root, target);
        if (res != null) {
            System.out.println("找到节点：" + res.val);
        } else {
            System.out.println("未找到节点：" + target);
        }
    
      // ==================== 新增功能测试 ====================
        System.out.println("\n===== 增强功能测试 =====");

        // 插入
        root = bt.insert(root, 6);
        System.out.print("插入6后层序：");
        bt.levelOrder(root);
        System.out.println();

        // 删除
        root = bt.delete(root, 3);
        System.out.print("删除3后层序：");
        bt.levelOrder(root);
        System.out.println();

        // 翻转
        root = bt.invertTree(root);
        System.out.print("翻转后层序：");
        bt.levelOrder(root);
        root = bt.invertTree(root); // 翻回来
        System.out.println();

        // 序列化
        String ser = bt.serialize(root);
        System.out.println("序列化结果：" + ser);

        // 反序列化
        TreeNode newRoot = bt.deserialize(ser);
        System.out.print("反序列化后层序：");
        bt.levelOrder(newRoot);
        System.out.println();

        // 判断
        System.out.println("是否平衡：" + bt.isBalanced(root));
        System.out.println("是否完全二叉树：" + bt.isComplete(root));
        System.out.println("是否满二叉树：" + bt.isFull(root));
    


    }
}
