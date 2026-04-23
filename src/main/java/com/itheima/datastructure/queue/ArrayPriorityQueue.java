package com.itheima.datastructure.queue;

public class ArrayPriorityQueue {
    
    private int[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayPriorityQueue() {
        elements = new int[DEFAULT_CAPACITY];
        size = 0;
    }

    // ==================== 入队 ====================
    public void offer(int val) {
        // 扩容
        if (size == elements.length) {
            int[] newArr = new int[elements.length * 2];
            System.arraycopy(elements, 0, newArr, 0, size);
            elements = newArr;
        }
        elements[size++] = val;
    }

    // ==================== 出队：取最小的元素（优先级最高） ====================
    public int poll() {
        if (isEmpty()) {
            System.out.println("优先级队列为空");
            return -1;
        }

        // 找到最小值的下标 遍历比较最小值
        int minIndex = 0;
        for (int i = 1; i < size; i++) {
            if (elements[i] < elements[minIndex]) {
                minIndex = i;
            }
        }

        int result = elements[minIndex];

        // 删除元素：后面元素向前移动
        for (int i = minIndex; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        return result;
    }

    // ==================== 查看队首（最小值） ====================
    public int peek() {
        if (isEmpty()) {
            System.out.println("优先级队列为空");
            return -1;
        }
        int minIndex = 0;
        for (int i = 1; i < size; i++) {
            if (elements[i] < elements[minIndex]) {
                minIndex = i;
            }
        }
        return elements[minIndex];
    }

    // ==================== 通用方法 ====================
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
    }

    // 打印队列内部顺序（不是优先级顺序）
    public void printQueue() {
        System.out.print("内部数组顺序：");
        for (int i = 0; i < size; i++) {
            System.out.print(elements[i] + " ");
        }
        System.out.println();
    }

    // ==================== 测试 ====================
    public static void main(String[] args) {
        ArrayPriorityQueue pq = new ArrayPriorityQueue();

        pq.offer(5);
        pq.offer(2);
        pq.offer(9);
        pq.offer(1);
        pq.offer(7);

        System.out.println("当前队首（最小）：" + pq.peek()); // 1
        System.out.println("队列大小：" + pq.size());       // 5
        pq.printQueue();

        System.out.print("按优先级出队顺序：");
        while (!pq.isEmpty()) {
            System.out.print(pq.poll() + " "); // 1 2 5 7 9
        }
        System.out.println();

        System.out.println("是否为空：" + pq.isEmpty()); // true
    }
}
