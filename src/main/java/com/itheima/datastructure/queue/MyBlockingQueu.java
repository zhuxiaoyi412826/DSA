package com.itheima.datastructure.queue;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
public class MyBlockingQueu<E> {
    private final Object[] items;  // 数组存储元素
    private int takeIndex;         // 队头（取元素）
    private int putIndex;          // 队尾（放元素）
    private int count;             // 元素个数

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();  // 非空条件（消费者等）
    private final Condition notFull = lock.newCondition();   // 非满条件（生产者等）

    // 构造：指定容量
    public MyBlockingQueu(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();
        items = new Object[capacity];
    }

    // ====================== 核心阻塞方法 ======================

    /**
     * 阻塞入队
     * 队列满 → 线程阻塞等待
     */
    public void put(E e) throws InterruptedException {
        lock.lockInterruptibly();  // 可中断锁
        try {
            // 经典写法：必须用 while，防止虚假唤醒
            while (count == items.length) {
                notFull.await();   // 满了，生产者等待
            }
            enqueue(e);            // 入队
        } finally {
            lock.unlock();
        }
    }

    /**
     * 阻塞出队
     * 队列空 → 线程阻塞等待
     */
    public E take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                notEmpty.await();  // 空了，消费者等待
            }
            return dequeue();       // 出队
        } finally {
            lock.unlock();
        }
    }

    // ====================== 非阻塞方法（常用） ======================

    /**
     * 非阻塞入队
     * 满了返回 false，不等待
     */
    public boolean offer(E e) {
        lock.lock();
        try {
            if (count == items.length) return false;
            enqueue(e);
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 非阻塞出队
     * 空了返回 null
     */
    public E poll() {
        lock.lock();
        try {
            return count == 0 ? null : dequeue();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 查看队头，不删除
     */
    public E peek() {
        lock.lock();
        try {
            return (E) items[takeIndex];
        } finally {
            lock.unlock();
        }
    }

    // ====================== 工具方法 ======================

    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        lock.lock();
        try {
            Arrays.fill(items, null);
            takeIndex = putIndex = count = 0;
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // ====================== 内部入队出队 ======================

    /**
     * 真正入队（已加锁）
     */
    private void enqueue(E x) {
        items[putIndex] = x;
        // 环形指针
        if (++putIndex == items.length) putIndex = 0;
        count++;
        notEmpty.signal();  // 唤醒一个等待的消费者
    }

    /**
     * 真正出队（已加锁）
     */
    private E dequeue() {
        Object x = items[takeIndex];
        items[takeIndex] = null;  // 帮助GC
        // 环形指针
        if (++takeIndex == items.length) takeIndex = 0;
        count--;
        notFull.signal();   // 唤醒一个等待的生产者
        return (E) x;
    }

    public static void main(String[] args) {
        MyBlockingQueu<String> queue = new MyBlockingQueu<>(3);

        // 生产者线程
        new Thread(() -> {
            try {
                queue.put("任务1");
                System.out.println("放入：任务1");
                queue.put("任务2");
                System.out.println("放入：任务2");
                queue.put("任务3");
                System.out.println("放入：任务3");
                queue.put("任务4");  // 队列满，会阻塞
                System.out.println("放入：任务4");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 消费者线程
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("取出：" + queue.take());
                System.out.println("取出：" + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

    

