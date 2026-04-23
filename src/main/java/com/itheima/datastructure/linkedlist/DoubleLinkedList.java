package com.itheima.datastructure.linkedlist;
public class DoubleLinkedList<E> {

    // 双向链表节点
    private static class Node<E> {
        E item;
        Node<E> prev;
        Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    // 头尾哨兵（永远存在）
    private final Node<E> head;
    private final Node<E> tail;
    private int size;

    // 构造：初始化哨兵
    public DoubleLinkedList() {
        // 初始化头尾哨兵
        head = new Node<>(null, null, null);
        tail = new Node<>(head, null, null);
        // 头尾相连
        head.next = tail;
        size = 0;
    }

    // ======================== 基础工具 ========================
    // 返回当前节点的数量
    public int size() {
        return size;
    }
// 判断双向链表是否为空
    public boolean isEmpty() {
        return size == 0;
    }
// 清空双向链表头哨兵的net指向尾哨兵
    public void clear() {
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    // 根据 index 找节点
    private Node<E> node(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<E> p;
        // 前半段从前往找，后半段从后往前找（双向链表优势）
        if (index < size / 2) {
            p = head.next;
            for (int i = 0; i < index; i++)
                p = p.next;
        } else {
            p = tail.prev;
            for (int i = size - 1; i > index; i--)
                p = p.prev;
        }
        return p;
    }

    // ======================== 增 ========================
    public void addFirst(E e) {
        Node<E> next = head.next;
        Node<E> newNode = new Node<>(head, e, next);
        head.next = newNode;
        next.prev = newNode;
        size++;
    }

    public void addLast(E e) {
        Node<E> prev = tail.prev;
        Node<E> newNode = new Node<>(prev, e, tail);
        prev.next = newNode;
        tail.prev = newNode;
        size++;
    }

    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        Node<E> succ = (index == size) ? tail : node(index);
        Node<E> pred = succ.prev;
        Node<E> newNode = new Node<>(pred, e, succ);
        pred.next = newNode;
        succ.prev = newNode;
        size++;
    }

    // ======================== 删 ========================
    public E removeFirst() {
        if (isEmpty())
            throw new RuntimeException("链表为空");
        Node<E> x = head.next;
        E item = x.item;
        Node<E> next = x.next;
        head.next = next;
        next.prev = head;
        size--;
        return item;
    }

    public E removeLast() {
        if (isEmpty())
            throw new RuntimeException("链表为空");
        Node<E> x = tail.prev;
        E item = x.item;
        Node<E> prev = x.prev;
        tail.prev = prev;
        prev.next = tail;
        size--;
        return item;
    }

    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node<E> x = node(index);
        E item = x.item;
        Node<E> pred = x.prev;
        Node<E> succ = x.next;
        pred.next = succ;
        succ.prev = pred;
        size--;
        return item;
    }

    public boolean removeByValue(E val) {
        for (Node<E> p = head.next; p != tail; p = p.next) {
            if (val.equals(p.item)) {
                Node<E> pred = p.prev;
                Node<E> succ = p.next;
                pred.next = succ;
                succ.prev = pred;
                size--;
                return true;
            }
        }
        return false;
    }

    // ======================== 查 & 改 ========================
    public E get(int index) {
        return node(index).item;
    }

    public E getFirst() {
        return head.next.item;
    }

    public E getLast() {
        return tail.prev.item;
    }

    public E set(int index, E val) {
        Node<E> p = node(index);
        E old = p.item;
        p.item = val;
        return old;
    }

    public boolean contains(E val) {
        for (Node<E> p = head.next; p != tail; p = p.next) {
            if (val.equals(p.item))
                return true;
        }
        return false;
    }

    // ======================== 打印 ========================
    public void show() {
        System.out.print("链表：");
        for (Node<E> p = head.next; p != tail; p = p.next) {
            System.out.print(p.item + " <-> ");
        }
        System.out.println("null");
    }

    // ======================== 额外功能1：翻转链表 ========================
    public void reverse() {
        Node<E> p = head.next;
        while (p != tail) {
            Node<E> temp = p.next;
            // 交换前后指针
            Node<E> t = p.prev;
            p.prev = p.next;
            p.next = t;
            p = temp;
        }
        // 交换头尾哨兵指向
        Node<E> temp = head.next;
        head.next = tail.prev;
        tail.prev = temp;
        // 修正哨兵边界
        head.next.prev = head;
        tail.prev.next = tail;
    }

    // ======================== 额外功能2：扩容1.5倍（复制一遍再接后半段） ========================
    public void expand() {
        int oldSize = size;
        if (oldSize == 0) return;

        // 从尾往前遍历原链表，依次加到尾部
        Node<E> p = node(oldSize - 1);
        for (int i = 0; i < oldSize; i++) {
            addLast(p.item);
            p = p.prev;
        }
        // 最终长度 = 原长度 * 1.5
        // 上面逻辑：原n个 → 追加n个 → 总长2n
        // 要严格 1.5 倍：保留前 3n/2 个，删掉后面多余
        int targetSize = (oldSize * 3) / 2;
        while (size > targetSize) {
            removeLast();
        }
    }

     public static void main(String[] args) {

        System.out.println("===== 初始化双向链表 =====");
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        list.addFirst(5);
        list.add(2, 15);
        list.show();                // 5 <-> 10 <-> 15 <-> 20 <-> 30 <-> null
        System.out.println("size = " + list.size()); // 5

        // System.out.println("\n===== get & set =====");
        // System.out.println("index2: " + list.get(2)); //15
        // list.set(2, 100);
        // list.show();                //5 <->10 <->100 <->20 <->30

        // System.out.println("\n===== 删除 =====");
        // list.removeFirst();
        // list.removeLast();
        // list.remove(1);
        // list.show();                //10 <->20 <-> null

        // System.out.println("\n===== 包含判断 =====");
        // System.out.println("包含20？" + list.contains(20)); //true

        // list.removeByValue(10);
        // list.show();                //20 <-> null

        // System.out.println("\n===== 清空测试 =====");
        // list.clear();
        // System.out.println("isEmpty? " + list.isEmpty()); //true

        // // 重新构造用于测试翻转和扩容
        // list.addLast(1);
        // list.addLast(2);
        // list.addLast(3);
        // list.addLast(4);
        // System.out.println("\n===== 原链表 =====");
        // list.show();    //1<->2<->3<->4

        // System.out.println("\n===== 翻转后 =====");
        // list.reverse();
        // list.show();    //4<->3<->2<->1

        // System.out.println("\n===== 扩容1.5倍后 =====");
        // list.expand();
        // list.show();
        // System.out.println("扩容后size：" + list.size()); //6
    }
}