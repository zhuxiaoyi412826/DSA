package com.itheima.datastructure.queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class DualLockBlockingQueue<E> {
    
    private final E[] items;
    private final int capacity;

    // 队头、队尾指针
    private int takeIndex = 0;
    private int putIndex  = 0;

    // 元素数量（原子类，保证多线程安全） 定义一个线程安全的整数对象count
    private final AtomicInteger count = new AtomicInteger(0);

    // ==================== 两把锁 ====================
    // 读锁（出队、消费者）
    private final ReentrantLock takeLock = new ReentrantLock();
    private final java.util.concurrent.locks.Condition notEmpty = takeLock.newCondition();

    // 写锁（入队、生产者）
    private final ReentrantLock putLock = new ReentrantLock();
    private final java.util.concurrent.locks.Condition notFull = putLock.newCondition();

    // 构造方法
    public DualLockBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.items = (E[]) new Object[capacity];
    }

    // ==================== 阻塞入队 put ====================
    public void put(E e) throws InterruptedException {
        if (e == null) throw new NullPointerException();

        int c = -1; // 记录入队前的元素个数
        putLock.lockInterruptibly();
        try {
            // 队列满 → 等待
            while (count.get() == capacity) {
                notFull.await();
            }
            // 入队
            items[putIndex] = e;
            // 环形指针
            if (++putIndex == capacity) {
                putIndex = 0;
            }
            // 数量+1，返回**增加前**的值
            c = count.getAndIncrement();

            // 如果还有空位 → 唤醒下一个生产者
            if (c + 1 < capacity) {
                notFull.signal();
            }
        } finally {
            putLock.unlock();
        }

        // 如果之前队列为空 → 唤醒消费者
        if (c == 0) {
            takeLock.lock();
            try {
                notEmpty.signal();
            } finally {
                takeLock.unlock();
            }
        }
    }

    // ==================== 阻塞出队 take ====================
    public E take() throws InterruptedException {
        E e;
        int c = -1; // 记录出队前的元素个数

        takeLock.lockInterruptibly();
        try {
            // 队列空 → 等待
            while (count.get() == 0) {
                notEmpty.await();
            }
            // 出队
            e = items[takeIndex];
            items[takeIndex] = null; // help GC

            // 环形指针
            if (++takeIndex == capacity) {
                takeIndex = 0;
            }
            // 数量-1，返回**减少前**的值
            c = count.getAndDecrement();

            // 队列还有元素 → 唤醒下一个消费者
            if (c > 1) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }

        // 如果之前队列是满的 → 唤醒生产者
        if (c == capacity) {
            putLock.lock();
            try {
                notFull.signal();
            } finally {
                putLock.unlock();
            }
        }
        return e;
    }

    // ==================== 常用工具方法 ====================
    public int size() {
        return count.get();
    }

    public boolean isEmpty() {
        return count.get() == 0;
    }
 public static void main(String[] args) {
        DualLockBlockingQueue<String> queue = new DualLockBlockingQueue<>(3);

        // 生产者
        new Thread(() -> {
            try {
                queue.put("A");
                queue.put("B");
                queue.put("C");
                System.out.println("放入3个，队列已满");
                queue.put("D"); // 阻塞
                System.out.println("放入D");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 消费者
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
