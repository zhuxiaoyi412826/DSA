package com.itheima.datastructure.queue;

public class HeapPriorityQueue {
    private int[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public HeapPriorityQueue() {
        heap = new int[DEFAULT_CAPACITY];
        size = 0;
    }

    // 入队
    public void offer(int value) {
        // 扩容
        if (size == heap.length) {
            int[] newHeap = new int[heap.length * 2];
            System.arraycopy(heap, 0, newHeap, 0, size);
            heap = newHeap;
        }
        // 放到最后，向上调整
        heap[size] = value;
        siftUp(size);
        size++;
    }

    // 出队（优先级最高 → 最小元素）
    public int poll() {
        if (isEmpty()) {
            System.out.println("队列空");
            return -1;
        }
        int top = heap[0];
        // 最后一个元素移到堆顶
        heap[0] = heap[size - 1];
        size--;
        // 向下调整
        siftDown(0);
        return top;
    }

    // 查看堆顶
    public int peek() {
        return isEmpty() ? -1 : heap[0];
    }

    // 是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 元素个数
    public int size() {
        return size;
    }

    // 清空
    public void clear() {
        size = 0;
    }

    // 上浮调整（小顶堆）
    /**
     * 上浮操作：将指定位置的元素在堆中向上移动，以维持堆的性质
     * @param index 需要上移的元素的初始位置
     */
    private void siftUp(int index) {
        int temp = heap[index];  // 保存当前需要上移的元素的值
        while (index > 0) {     // 循环直到到达堆顶或找到合适位置
            int parentIndex = (index - 1) / 2;  // 计算父节点的位置
            if (temp < heap[parentIndex]) {      // 如果当前元素小于父节点，则交换
                heap[index] = heap[parentIndex];  // 将父节点的值下移到当前位置
                index = parentIndex;              // 更新当前位置为父节点的位置
            } else {
                break;                            // 如果当前元素大于等于父节点，则停止上移
            }
        }
        heap[index] = temp;  // 将保存的元素值放入最终位置
    }

    // 下沉调整
    private void siftDown(int index) {
        int temp = heap[index];
        int childIndex;
        while ((childIndex = 2 * index + 1) < size) {
            // 找左右孩子中更小的
            if (childIndex + 1 < size && heap[childIndex + 1] < heap[childIndex]) {
                childIndex++;
            }
            if (heap[childIndex] < temp) {
                heap[index] = heap[childIndex];
                index = childIndex;
            } else {
                break;
            }
        }
        heap[index] = temp;
    }

    // 测试
    public static void main(String[] args) {
        HeapPriorityQueue pq = new HeapPriorityQueue();

        pq.offer(5);
        pq.offer(2);
        pq.offer(9);
        pq.offer(1);
        pq.offer(7);

        System.out.println("堆顶：" + pq.peek());  // 1
        System.out.println("大小：" + pq.size());  // 5

        System.out.print("出队顺序：");
        while (!pq.isEmpty()) {
            System.out.print(pq.poll() + " "); // 1 2 5 7 9
        }
    }
}
