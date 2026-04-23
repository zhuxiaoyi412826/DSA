package com.itheima.datastructure.queue;

public class CircularQueue {
    private int[] elements;   // 数组存储
    private int front;        // 队头指针
    private int rear;         // 队尾指针
    private int capacity;     // 队列容量

    // 构造方法，指定容量
    public CircularQueue(int capacity) {
        this.capacity = capacity + 1; // 空出一个位置区分空与满
        this.elements = new int[this.capacity];
        this.front = 0;
        this.rear = 0;
    }

    // 1. 入队
    public void enqueue(int val) {
        if (isFull()) {
            System.out.println("队列已满，无法入队：" + val);
            return;
        }
        elements[rear] = val;
        // 指针移动公式
        rear = (rear + 1) % capacity;
    }

    // 2. 出队
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("队列为空，无法出队");
            return -1;
        }
        int val = elements[front];
        front = (front + 1) % capacity;
        return val;
    }

    // 3. 获取队头
    public int peek() {
        if (isEmpty()) {
            System.out.println("队列为空");
            return -1;
        }
        return elements[front];
    }

    // 4. 判断为空
    public boolean isEmpty() {
        return front == rear;
    }

    // 5. 判断满
    public boolean isFull() {
        return (rear + 1) % capacity == front;
    }

    // 6. 获取大小
    public int size() {
        return (rear - front + capacity) % capacity;
    }

    // 7. 打印队列
    public void printQueue() {
        System.out.print("队列内容 [队头 → 队尾]：");
        int i = front;
        while (i != rear) {
            System.out.print(elements[i] + " ");
            i = (i + 1) % capacity;
        }
        System.out.println();
    }

    // 8. 清空队列
    public void clear() {
        front = rear = 0;
    }

    // ================= 测试 =================
    public static void main(String[] args) {
        // 创建容量为 3 的队列
        CircularQueue queue = new CircularQueue(3);

        // 入队
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.printQueue();

        // 队列已满测试
        queue.enqueue(40);

        System.out.println("队头：" + queue.peek());
        System.out.println("大小：" + queue.size());

        // 出队
        System.out.println("出队：" + queue.dequeue());
        queue.printQueue();

        // 再次入队，测试循环效果
        queue.enqueue(40);
        queue.printQueue();

        // 全部出队
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        System.out.println("是否为空：" + queue.isEmpty());

        // 清空测试
        queue.enqueue(100);
        queue.enqueue(200);
        queue.clear();
        System.out.println("清空后大小：" + queue.size());
    } 
}
