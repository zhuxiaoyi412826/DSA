package com.itheima.datastructure.queue;

public class LinkedDeque {
    // 双向链表节点
    private static class Node {
        int val;
        Node prev;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    private Node head;  // 队头
    private Node tail;  // 队尾
    private int size;   // 元素数量

    // ==================== 头部操作 ====================
    // 头部添加
    public void offerFirst(int val) {
        Node newNode = new Node(val);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // 头部删除
    public int pollFirst() {
        if (isEmpty()) {
            System.out.println("双端队列为空，无法从头部出队");
            return -1;
        }
        int val = head.val;
        Node next = head.next;
        // 断开连接
        head.next = null;
        if (next != null) {
            next.prev = null;
            head = next;
        } else {
            head = tail = null;
        }
        size--;
        return val;
    }

    // 查看头部
    public int peekFirst() {
        if (isEmpty()) {
            System.out.println("双端队列为空");
            return -1;
        }
        return head.val;
    }

    // ==================== 尾部操作 ====================
    // 尾部添加
    public void offerLast(int val) {
        Node newNode = new Node(val);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // 尾部删除
    public int pollLast() {
        if (isEmpty()) {
            System.out.println("双端队列为空，无法从尾部出队");
            return -1;
        }
        int val = tail.val;
        Node prev = tail.prev;
        // 断开连接
        tail.prev = null;
        if (prev != null) {
            prev.next = null;
            tail = prev;
        } else {
            head = tail = null;
        }
        size--;
        return val;
    }

    // 查看尾部
    public int peekLast() {
        if (isEmpty()) {
            System.out.println("双端队列为空");
            return -1;
        }
        return tail.val;
    }

    // ==================== 通用方法 ====================
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // 清空
    public void clear() {
        head = tail = null;
        size = 0;
    }

    // 从队头到队尾打印
    public void printFromHead() {
        System.out.print("队头 → 队尾：");
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    // 从队尾到队头打印
    public void printFromTail() {
        System.out.print("队尾 → 队头：");
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.prev;
        }
        System.out.println();
    }

    // ==================== 测试 ====================
    public static void main(String[] args) {
        LinkedDeque deque = new LinkedDeque();

        // 尾部添加
        deque.offerLast(10);
        deque.offerLast(20);
        // 头部添加
        deque.offerFirst(5);
        deque.offerFirst(1);

        deque.printFromHead();   // 1 5 10 20
        deque.printFromTail();   // 20 10 5 1

        System.out.println("队头：" + deque.peekFirst());  // 1
        System.out.println("队尾：" + deque.peekLast());   // 20
        System.out.println("大小：" + deque.size());        // 4

        // 尾部出队
        System.out.println("尾部出队：" + deque.pollLast());// 20
        // 头部出队
        System.out.println("头部出队：" + deque.pollFirst());// 1

        deque.printFromHead();   // 5 10

        // 清空
        deque.clear();
        System.out.println("清空后是否为空：" + deque.isEmpty()); // true
    }
}
