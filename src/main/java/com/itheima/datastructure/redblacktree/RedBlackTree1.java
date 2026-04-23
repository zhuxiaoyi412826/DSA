package com.itheima.datastructure.redblacktree;

import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree1 {
    // 定义颜色常量 红色 黑色
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    class Node {
        int key;//储存值
        boolean color;//颜色
        Node left, right, parent;//存储节点 左右

        Node(int key) {
            this.key = key;
            this.color = RED; // 新节点默认红色
        }
    }
// 这一行代码定义了一个私有的 Node 类型的成员变量 root，用于存储红黑树的根节点
    private Node root;

    public RedBlackTree1() {
        root = null;
    }

    // ------------------------------
    // 旋转：左旋
    // ------------------------------
    private void leftRotate(Node x) {
        System.out.println("[日志] 执行左旋，节点: " + x.key);
        Node y = x.right;
        x.right = y.left;

        if (y.left != null)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    // ------------------------------
    // 旋转：右旋
    // ------------------------------
    private void rightRotate(Node y) {
        System.out.println("[日志] 执行右旋，节点: " + y.key);
        Node x = y.left;
        y.left = x.right;

        if (x.right != null)
            x.right.parent = y;

        x.parent = y.parent;

        if (y.parent == null)
            root = x;
        else if (y == y.parent.right)
            y.parent.right = x;
        else
            y.parent.left = x;

        x.right = y;
        y.parent = x;
    }

    // ------------------------------
    // 插入
    // ------------------------------
    public void insert(int key) {
        System.out.println("\n===== 插入节点：" + key + " =====");
        Node z = new Node(key);
        Node y = null;
        Node x = root;

        while (x != null) {
            y = x;
            if (z.key < x.key)
                x = x.left;
            else
                x = x.right;
        }

        z.parent = y;
        if (y == null)
            root = z;
        else if (z.key < y.key)
            y.left = z;
        else
            y.right = z;

        fixInsert(z);
        System.out.println("插入完成，当前树结构：");
        printTree();
    }

    // 插入修复
    private void fixInsert(Node z) {
        while (z.parent != null && z.parent.color == RED) {
            if (z.parent == z.parent.parent.left) {
                Node uncle = z.parent.parent.right;

                // 情况1：叔叔红色
                if (uncle != null && uncle.color == RED) {
                    System.out.println("[日志] 情况1：叔叔红色，变色");
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    // 情况2
                    if (z == z.parent.right) {
                        System.out.println("[日志] 情况2：转成情况3");
                        z = z.parent;
                        leftRotate(z);
                    }
                    // 情况3
                    System.out.println("[日志] 情况3：右旋+变色");
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                // 对称
                Node uncle = z.parent.parent.left;
                if (uncle != null && uncle.color == RED) {
                    System.out.println("[日志] 情况1（对称）：叔叔红色，变色");
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        System.out.println("[日志] 情况2（对称）：转成情况3");
                        z = z.parent;
                        rightRotate(z);
                    }
                    System.out.println("[日志] 情况3（对称）：左旋+变色");
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    leftRotate(z.parent.parent);
                }
            }
            if (z == root) break;
        }
        root.color = BLACK;
    }

    // ------------------------------
    // 查找
    // ------------------------------
    public Node search(int key) {
        Node cur = root;
        while (cur != null) {
            if (key == cur.key) return cur;
            if (key < cur.key) cur = cur.left;
            else cur = cur.right;
        }
        return null;
    }

    // ------------------------------
    // 删除（完整实现）
    // ------------------------------
    public void delete(int key) {
        System.out.println("\n===== 删除节点：" + key + " =====");
        Node z = search(key);
        if (z == null) {
            System.out.println("节点不存在");
            return;
        }
        Node y = z;
        Node x;
        boolean yOriginalColor = y.color;

        if (z.left == null) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == null) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = min(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                if (x != null) x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (yOriginalColor == BLACK && x != null) {
            fixDelete(x);
        }
        printTree();
    }

    /**
     * 修复删除节点后可能导致的红黑树性质破坏
     * 这是红黑树删除操作后的修复步骤，确保删除后仍然保持红黑树的五个性质
     * @param x 需要修复的起始节点
     */
    private void fixDelete(Node x) {
        // 当x不是根节点且x的颜色为黑色时，需要继续修复
        while (x != root && x.color == BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if ((w.left == null || w.left.color == BLACK) &&
                        (w.right == null || w.right.color == BLACK)) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.right == null || w.right.color == BLACK) {
                        if (w.left != null) w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    if (w.right != null) w.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if ((w.right == null || w.right.color == BLACK) &&
                        (w.left == null || w.left.color == BLACK)) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.left == null || w.left.color == BLACK) {
                        if (w.right != null) w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    if (w.left != null) w.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        if (x != null) x.color = BLACK;
    }
// 红黑树中执行子树的替换操作，确保在删除节点时树的结构能够正确调整，同时更新节点的父节点关系以保持树的连通性。
    private void transplant(Node u, Node v) {
        if (u.parent == null) root = v;
        else if (u == u.parent.left) u.parent.left = v;
        else u.parent.right = v;
        if (v != null) v.parent = u.parent;
    }

    /**
     * 在二叉搜索树中查找最小节点
     * @param node 子树的根节点
     * @return 子树中的最小节点
     */
    private Node min(Node node) {
        // 循环向左子树查找，直到最左节点
        while (node.left != null) node = node.left;
        // 返回最小值节点
        return node;
    }

    // ------------------------------
    // 树结构打印（带颜色 + 层级）
    // ------------------------------
    public void printTree() {
        if (root == null) {
            System.out.println("树为空");
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                String color = cur.color == RED ? "红" : "黑";
                System.out.print(cur.key + "(" + color + ") ");
                if (cur.left != null) queue.add(cur.left);
                if (cur.right != null) queue.add(cur.right);
            }
            System.out.println();
        }
    }

    // ------------------------------
    // 测试 main
    // ------------------------------
    public static void main(String[] args) {
        RedBlackTree1 tree = new RedBlackTree1();

        // 插入测试
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(15);
        tree.insert(25);
        tree.insert(5);

        // 查找测试
        Node find = tree.search(20);
        System.out.println("\n查找 20：" + (find != null ? "存在" : "不存在"));

        // 删除测试
        tree.delete(20);
        tree.delete(15);
    }
}
