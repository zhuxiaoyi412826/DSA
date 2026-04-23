package com.itheima.datastructure.binarysearchtree;
import java.util.*;

// 二叉树节点
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
public class BinaryTreeIterative {
    // ==================== 1. 前序遍历（迭代） ====================
    public void preOrder(TreeNode root) {
        if (root == null) return;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
    }

    // ==================== 2. 中序遍历（迭代） ====================
    public void inOrder(TreeNode root) {
        if (root == null) return;
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
    }

    // ==================== 3. 后序遍历（迭代） ====================
    public void postOrder(TreeNode root) {
        if (root == null) return;
        Deque<TreeNode> stack = new ArrayDeque<>();
        Stack<Integer> res = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.push(node.val);
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        while (!res.isEmpty()) {
            System.out.print(res.pop() + " ");
        }
    }

    // ==================== 4. 层序遍历（迭代） ====================
    public void levelOrder(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            System.out.print(node.val + " ");
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
    }

    // ==================== 5. 最大深度（迭代） ====================
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int depth = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
        }
        return depth;
    }

    // ==================== 6. 总结点个数（迭代） ====================
    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int count = 0;
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            count++;
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        return count;
    }

    // ==================== 7. 叶子节点个数（迭代） ====================
    public int countLeafNodes(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int count = 0;
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node.left == null && node.right == null) count++;
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        return count;
    }

    // ==================== 8. 查找节点（迭代） ====================
    public TreeNode searchNode(TreeNode root, int target) {
        if (root == null) return null;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node.val == target) return node;
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        return null;
    }

    // ==================== 9. BST 插入（迭代） ====================
    public TreeNode insertBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        TreeNode cur = root;
        TreeNode parent = null;
        while (cur != null) {
            parent = cur;
            if (val < cur.val) cur = cur.left;
            else if (val > cur.val) cur = cur.right;
            else return root; // 已存在
        }
        if (val < parent.val) parent.left = new TreeNode(val);
        else parent.right = new TreeNode(val);
        return root;
    }

    // ==================== 10. BST 删除（迭代） ====================
    public TreeNode deleteBST(TreeNode root, int key) {
        TreeNode cur = root;
        TreeNode parent = null;

        // 找节点
        while (cur != null && cur.val != key) {
            parent = cur;
            cur = key < cur.val ? cur.left : cur.right;
        }
        if (cur == null) return root; // 没找到

        // 情况1：叶子 or 单孩子
        if (cur.left == null || cur.right == null) {
            TreeNode child = cur.left != null ? cur.left : cur.right;
            if (parent == null) return child;
            if (parent.left == cur) parent.left = child;
            else parent.right = child;
        } else {
            // 情况2：两个孩子，找后继（右子树最小）
            TreeNode minParent = cur;
            TreeNode minNode = cur.right;
            while (minNode.left != null) {
                minParent = minNode;
                minNode = minNode.left;
            }
            cur.val = minNode.val;
            if (minParent.left == minNode) minParent.left = minNode.right;
            else minParent.right = minNode.right;
        }
        return root;
    }

    // ==================== 11. 翻转二叉树（迭代） ====================
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            // 交换左右
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        return root;
    }

    // ==================== 12. 序列化（层序迭代） ====================
    public String serialize(TreeNode root) {
        if (root == null) return "null";
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                sb.append("null,");
                continue;
            }
            sb.append(node.val).append(",");
            q.offer(node.left);
            q.offer(node.right);
        }
        return sb.toString();
    }

    // ==================== 13. 反序列化（迭代） ====================
    public TreeNode deserialize(String data) {
        if (data.equals("null")) return null;
        String[] nodes = data.split(",");
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
        q.offer(root);
        int i = 1;
        while (!q.isEmpty() && i < nodes.length) {
            TreeNode parent = q.poll();
            // 左
            if (!nodes[i].equals("null")) {
                parent.left = new TreeNode(Integer.parseInt(nodes[i]));
                q.offer(parent.left);
            }
            i++;
            // 右
            if (i < nodes.length && !nodes[i].equals("null")) {
                parent.right = new TreeNode(Integer.parseInt(nodes[i]));
                q.offer(parent.right);
            }
            i++;
        }
        return root;
    }

    // ==================== 14. 判断平衡二叉树（迭代） ====================
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            int l = maxDepth(node.left);
            int r = maxDepth(node.right);
            if (Math.abs(l - r) > 1) return false;
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        return true;
    }

    // ==================== 15. 判断完全二叉树（迭代） ====================
    public boolean isComplete(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean flag = false;
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) flag = true;
            else {
                if (flag) return false;
                q.offer(node.left);
                q.offer(node.right);
            }
        }
        return true;
    }

    // ==================== 16. 判断满二叉树（迭代） ====================
    public boolean isFull(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            // 要么两个孩子，要么叶子
            if ((node.left == null && node.right != null) ||
                (node.left != null && node.right == null))
                return false;
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        return true;
    }





    public static void main(String[] args) {
        // 构建树
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

        BinaryTreeIterative bt = new BinaryTreeIterative();

        System.out.print("前序："); bt.preOrder(root); System.out.println();
        System.out.print("中序："); bt.inOrder(root); System.out.println();
        System.out.print("后序："); bt.postOrder(root); System.out.println();
        System.out.print("层序："); bt.levelOrder(root); System.out.println();

        System.out.println("深度：" + bt.maxDepth(root));
        System.out.println("总节点：" + bt.countNodes(root));
        System.out.println("叶子数：" + bt.countLeafNodes(root));

        int target = 5;
        TreeNode find = bt.searchNode(root, target);
        System.out.println("查找" + target + "：" + (find != null ? "找到" : "未找到"));

        // 插入
        root = bt.insertBST(root, 6);
        System.out.print("\n插入6层序："); bt.levelOrder(root); System.out.println();

        // 删除
        root = bt.deleteBST(root, 3);
        System.out.print("删除3层序："); bt.levelOrder(root); System.out.println();

        // 翻转
        root = bt.invertTree(root);
        System.out.print("翻转层序："); bt.levelOrder(root);
        root = bt.invertTree(root); // 复原
        System.out.println();

        // 序列化 & 反序列化
        String ser = bt.serialize(root);
        System.out.println("序列化：" + ser);
        TreeNode deRoot = bt.deserialize(ser);
        System.out.print("反序列化层序："); bt.levelOrder(deRoot); System.out.println();

        // 判断
        System.out.println("\n是否平衡：" + bt.isBalanced(root));
        System.out.println("是否完全：" + bt.isComplete(root));
        System.out.println("是否满二叉树：" + bt.isFull(root));
    }
}
