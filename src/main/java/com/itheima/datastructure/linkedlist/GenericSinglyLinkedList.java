package com.itheima.datastructure.linkedlist;

public class GenericSinglyLinkedList<E> {

    // 泛型节点
    private static class Node<E> {
        E data;       // 数据：泛型，可以存任意类型
        Node<E> next; // 指针

        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    // 哨兵节点（永远存在）
    private final Node<E> sentinel;
    // 链表长度
    private int size;

    // 构造方法：初始化哨兵
    public GenericSinglyLinkedList() {
        sentinel = new Node<>(null); // 哨兵不存数据
        size = 0;
    }

    // ==============================================
    // 1. 头部插入
    // ==============================================
    public void addFirst(E val) {
        Node<E> newNode = new Node<>(val);
        newNode.next = sentinel.next;
        sentinel.next = newNode;
        size++;
    }

    // ==============================================
    // 2. 尾部插入
    // ==============================================
    public void addLast(E val) {
        Node<E> newNode = new Node<>(val);
        Node<E> cur = sentinel;

        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = newNode;
        size++;
    }

    // ==============================================
    // 3. 指定索引插入
    // ==============================================
    public void add(int index, E val) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("索引越界");
        }

        Node<E> prev = sentinel;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        Node<E> newNode = new Node<>(val);
        newNode.next = prev.next;
        prev.next = newNode;
        size++;
    }

    // ==============================================
    // 4. 删除头部
    // ==============================================
    public void removeFirst() {
        if (isEmpty()) {
            throw new RuntimeException("链表为空，无法删除");
        }
        sentinel.next = sentinel.next.next;
        size--;
    }

    // ==============================================
    // 5. 删除尾部
    // ==============================================
    public void removeLast() {
        if (isEmpty()) {
            throw new RuntimeException("链表为空，无法删除");
        }

        Node<E> cur = sentinel;
        while (cur.next.next != null) {
            cur = cur.next;
        }
        cur.next = null;
        size--;
    }

    // ==============================================
    // 6. 删除指定索引
    // ==============================================
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("索引越界");
        }

        Node<E> prev = sentinel;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        prev.next = prev.next.next;
        size--;
    }

    // ==============================================
    // 7. 根据值删除（删除第一个匹配项）
    // ==============================================
    public boolean removeByValue(E val) {
        Node<E> cur = sentinel;
        while (cur.next != null) {
            if (val.equals(cur.next.data)) { // 泛型必须用 equals
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    // ==============================================
    // 8. 根据索引获取
    // ==============================================
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("索引越界");
        }

        Node<E> cur = sentinel.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    // ==============================================
    // 9. 修改指定索引的值
    // ==============================================
    public void set(int index, E newVal) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("索引越界");
        }

        Node<E> cur = sentinel.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.data = newVal;
    }

    // ==============================================
    // 10. 是否包含某个值
    // ==============================================
    public boolean contains(E val) {
        Node<E> cur = sentinel.next;
        while (cur != null) {
            if (val.equals(cur.data)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    // ==============================================
    // 11. 遍历打印
    // ==============================================
    public void show() {
        Node<E> cur = sentinel.next;
        while (cur != null) {
            System.out.print(cur.data + " → ");
            cur = cur.next;
        }
        System.out.println("null");
    }

    // ==============================================
    // 12. 获取长度
    // ==============================================
    public int size() {
        return size;
    }

    // ==============================================
    // 13. 是否为空
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

 public static void main(String[] args) {

        System.out.println("===== 测试1：泛型链表存整数 =====");
        GenericSinglyLinkedList<Integer> list1 = new GenericSinglyLinkedList<>();

        list1.addLast(10);
        list1.addLast(20);
        list1.addLast(30);
        list1.addFirst(5);
        list1.add(2, 15);

        System.out.print("添加后：");
        list1.show(); // 5 →10 →15 →20 →30 →null

        System.out.println("索引2的值：" + list1.get(2)); // 15
        list1.set(2, 100);
        System.out.println("修改后索引2的值：" + list1.get(2)); // 100

        list1.removeFirst();
        list1.removeLast();
        list1.remove(1);
        System.out.print("删除后：");
        list1.show(); // 10 → 20 → null

        System.out.println("是否包含20：" + list1.contains(20)); // true

        list1.removeByValue(10);
        System.out.print("根据值删除后：");
        list1.show(); // 20 → null

        list1.clear();
        System.out.println("清空后是否为空：" + list1.isEmpty()); // true

        System.out.println("\n===== 测试2：泛型链表存字符串 =====");
        GenericSinglyLinkedList<String> list2 = new GenericSinglyLinkedList<>();
        list2.addLast("Java");
        list2.addLast("Python");
        list2.addFirst("C++");
        list2.show(); // C++ → Java → Python → null

        System.out.println("是否包含Java：" + list2.contains("Java")); // true
        list2.removeByValue("Java");
        list2.show(); // C++ → Python → null
    }

}