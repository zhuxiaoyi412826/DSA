package com.itheima.datastructure.queue;

public class LinkedQueue {
    
    // 节点类
    private static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    private Node front;  // 队头
    private Node rear;   // 队尾
    private int size;    // 元素个数

    // 1. 入队：加到队尾
    public void enqueue(int val) {
        Node newNode = new Node(val);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // 2. 出队：从队头删除并返回
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("队列为空，无法出队");
            return -1;
        }
        int val = front.val;
        front = front.next;
        size--;
        // 最后一个元素出队后，把 rear 也置空
        if (front == null) {
            rear = null;
        }
        return val;
    }

    // 3. 获取队头元素（不删除）
    public int peek() {
        if (isEmpty()) {
            System.out.println("队列为空");
            return -1;
        }
        return front.val;
    }

    // 4. 判断队列是否为空
    public boolean isEmpty() {
        return front == null;
    }

    // 5. 获取队列大小
    public int size() {
        return size;
    }

    // 6. 判断队列是否已满（链表无容量限制，永远不满）
    public boolean isFull() {
        return false;
    }

    // 7. 打印队列（从队头到队尾）
    public void printQueue() {
        System.out.print("队列内容 [队头 → 队尾]：");
        Node cur = front;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    // 8. 清空队列
    public void clear() {
        front = rear = null;
        size = 0;
    }

    // ===================== 测试方法 =====================
    public static void main(String[] args) {
        LinkedQueue queue = new LinkedQueue();

        // 测试1：入队
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.printQueue();       // 10 20 30

        // 测试2：查看队头、大小
        System.out.println("队头元素：" + queue.peek());   // 10
        System.out.println("队列大小：" + queue.size());   // 3

        // 测试3：出队
        System.out.println("出队元素：" + queue.dequeue());// 10
        queue.printQueue();       // 20 30

        // 测试4：连续出队
        queue.dequeue();
        queue.dequeue();
        System.out.println("是否为空：" + queue.isEmpty());// true

        // 测试5：空队列出队
        queue.dequeue();

        // 测试6：判断是否满
        System.out.println("队列是否满：" + queue.isFull());// false

        // 测试7：再次入队
        queue.enqueue(100);
        queue.enqueue(200);
        queue.printQueue();

        // 测试8：清空队列
        queue.clear();
        System.out.println("清空后是否为空：" + queue.isEmpty());// true
        queue.printQueue();
    }
}
