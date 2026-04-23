package com.itheima.datastructure.linkedlist;

public class SinglyLinkedList1 {

    // 节点内部类
    private static class Node {
        int data;       // 数据域
        Node next;      // 指针域

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // 哨兵节点（哑节点，不存真实数据）
    private final Node sentinel;
    // 链表长度
    private int size;

    // 构造方法：初始化哨兵
    public SinglyLinkedList1() {
        sentinel = new Node(-1); // 数据随便给，没用
        size = 0;
    }

    // ==============================================
    // 1. 头部插入
    // ==============================================
    // 在单链表的头部添加一个新节点
    public void addFirst(int val) {
        Node newNode = new Node(val);
        newNode.next = sentinel.next;
        sentinel.next = newNode;
        size++;
    }

    // ==============================================
    // 2. 尾部插入
    // ==============================================
    public void addLast(int val) {
        Node newNode = new Node(val);

        Node cur = sentinel;
        while (cur.next != null) {
            cur = cur.next;
        }

        cur.next = newNode;
        size++;
    }

    // ==============================================
    // 3. 指定索引插入（从0开始）
    // ==============================================
    public void add(int index, int val) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("索引越界");
        }

        Node prev = sentinel;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        Node newNode = new Node(val);
        newNode.next = prev.next;
        prev.next = newNode;
        size++;
    }

    // ==============================================
    // 4. 删除头部节点
    // ==============================================
    public void removeFirst() {
        if (size == 0) {
            throw new RuntimeException("链表为空");
        }
        sentinel.next = sentinel.next.next;
        size--;
    }

    // ==============================================
    // 5. 删除尾部节点
    // ==============================================
    public void removeLast() {
        if (size == 0) {
            throw new RuntimeException("链表为空");
        }

        Node cur = sentinel;
        while (cur.next.next != null) {
            cur = cur.next;
        }

        cur.next = null;
        size--;
    }

    // ==============================================
    // 6. 删除指定索引节点
    // ==============================================
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("索引越界");
        }

        Node prev = sentinel;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        prev.next = prev.next.next;
        size--;
    }

    // ==============================================
    // 7. 根据值删除（删除第一个出现的）
    // ==============================================
    public boolean removeByValue(int val) {
        Node cur = sentinel;
        while (cur.next != null) {
            if (cur.next.data == val) {
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    // ==============================================
    // 8. 根据索引获取节点值
    // ==============================================
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("索引越界");
        }

        Node cur = sentinel.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    // ==============================================
    // 9. 修改指定索引节点的值
    // ==============================================
    public void set(int index, int newVal) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("索引越界");
        }

        Node cur = sentinel.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.data = newVal;
    }

    // ==============================================
    // 10. 查找是否包含某个值
    // ==============================================
    public boolean contains(int val) {
        Node cur = sentinel.next;
        while (cur != null) {
            if (cur.data == val) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    // ==============================================
    // 11. 遍历打印链表
    // ==============================================
    public void show() {
        Node cur = sentinel.next;
        while (cur != null) {
            System.out.print(cur.data + " -> ");
            cur = cur.next;
        }
        System.out.println("null");
    }

    // ==============================================
    // 12. 获取链表长度
    // ==============================================
    public int size() {
        return size;
    }

    // ==============================================
    // 13. 判断链表是否为空
    // ==============================================
    public boolean isEmpty() {
        return size == 0;
    }

    // ==============================================
    // 14. 清空链表
    // ==============================================
    public void clear() {
        sentinel.next = null;
        size = 0;
    }

    // ==============================================
    // 测试 main 方法
    // ==============================================
    public static void main(String[] args) {
        SinglyLinkedList1 list = new SinglyLinkedList1();

        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        list.addFirst(5);
        list.add(2, 15);

        // System.out.println("删除前");
        // System.out.println("链表内容：");
        // list.show(); // 5 -> 10 -> 15 -> 20 -> 30 -> null

        // System.out.println("索引2的值：" + list.get(2)); // 15
        // list.set(2, 100);
        // System.out.println("修改后索引2的值：" + list.get(2)); // 100

        // list.removeFirst();
        // list.removeLast();
        // list.remove(1);
        // System.out.println("删除后：");
        // list.show(); // 10 -> 20 -> null

        // System.out.println("是否包含20：" + list.contains(20)); // true

        list.clear();
        System.out.println("清空后是否为空：" + list.isEmpty()); // true
    }
}
