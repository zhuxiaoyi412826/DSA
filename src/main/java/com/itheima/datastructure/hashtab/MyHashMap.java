package com.itheima.datastructure.hashtab;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.checkerframework.checker.units.qual.K;

public class MyHashMap<K, V>{
    // 定义Node类，用于存储键值对，形成一个链表结构 
    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;
// 构造方法用于创建一个新的对象 int hash：表示键的哈希值，用于计算该键值对在哈希表中的索引位置。
// K key：表示键值对中的键。键是唯一的，用于标识和查找对应的值。
// V value：表示键值对中的值，即与键相关联的数据。
// Node<K, V> next：表示链表中的下一个节点
        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
// 默认初始容量为16
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    // 最大容量 1 * 2^30
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    // 默认加载因子 0.75
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
// 这个数组允许 MyHashMap 实现高效的插入、查找、删除和遍历操作，通过计算键的哈希值将键值对映射到数组的特定位置，并使用链表结构来处理哈希冲突。
    private Node<K, V>[] table;
    private int size;
    private int threshold;// 触发扩容的阈值
    private final float loadFactor;// 加载因子
// 初始化一个 MyHashMap 对象，并设置其加载因子为默认值 0.75。
    public MyHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }
// initialCapacity：表示初始容量，默认值为 1 << 4，即 16。
    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
// 这段构造方法的主要功能是初始化一个 MyHashMap 对象，确保其初始容量和加载因子的值都是合法的，并计算出一个合适的扩容阈值
    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        // 初始容量合法性检查         如果初始容量大于最大容量，就设置为最大容量。
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        // 如果负载因子 小于等于 0 或者 是一个非数字（NaN），就抛出非法参数异常。
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);

        this.loadFactor = loadFactor;
        // 计算扩容阈值
        this.threshold = tableSizeFor(initialCapacity);
    }

    // 计算扩容阈值方法实现
    private static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
// 获取哈希值
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
// 一个键值对插入到哈希表中
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
// 法的主要功能是将键值对插入到哈希表中，处理哈希冲突，并在需要时扩容哈希表。
// hash：表示键的哈希值，用于计算该键值对在哈希表中的索引位置。
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;

        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;

        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = new Node<>(hash, key, value, null);
        else {
            Node<K, V> e;
            K k;

            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else {
                for (int binCount = 0; ; binCount++) {
                    if ((e = p.next) == null) {
                        p.next = new Node<>(hash, key, value, null);
                        break;
                    }
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }

            if (e != null) {
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                return oldValue;
            }
        }

        size++;
        if (size > threshold)
            resize();
        return null;
    }
// MyHashMap中实现动态扩容机制。当哈希表中的元素数量超过扩容阈值时，
// 会调用resize()方法来创建一个新的、容量更大的哈希表，并将旧哈希表中的所有元素重新分配到新的哈希表中。
    final Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;

        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1;
        } else if (oldThr > 0) {
            newCap = oldThr;
        } else {
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }

        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY)
                    ? (int) ft
                    : Integer.MAX_VALUE;
        }

        threshold = newThr;
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        table = newTab;

        if (oldTab != null) {
            for (int j = 0; j < oldCap; j++) {
                Node<K, V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[(newCap - 1) & e.hash] = e;
                    else {
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;

                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null) loHead = e;
                                else loTail.next = e;
                                loTail = e;
                            } else {
                                if (hiTail == null) hiHead = e;
                                else hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);

                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    final Node<K, V> getNode(int hash, Object key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;

        if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k))))
                return first;

            if ((e = first.next) != null) {
                do {
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    public V remove(Object key) {
        Node<K, V> e;
        return (e = removeNode(hash(key), key, null, false, true)) == null ? null : e.value;
    }

    final Node<K, V> removeNode(int hash, Object key, Object value, boolean matchValue, boolean movable) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, index;

        if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) {
            Node<K, V> node = null, e;
            K k;
            V v;

            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
                node = p;
            else if ((e = p.next) != null) {
                node = e;
                do {
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                        node = e;
                        break;
                    }
                    p = e;
                } while ((e = e.next) != null);
            }

            if (node != null && (!matchValue || (v = node.value) == value || (value != null && value.equals(v)))) {
                if (node == p)
                    tab[index] = node.next;
                else
                    p.next = node.next;

                size--;
                return node;
            }
        }
        return null;
    }

    public boolean containsKey(Object key) {
        return getNode(hash(key), key) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Node<K, V>[] tab;
        if ((tab = table) != null && size > 0) {
            size = 0;
            Arrays.fill(tab, null);
        }
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        Node<K, V>[] tab = table;
        if (tab == null) return set;
        for (Node<K, V> e : tab) {
            for (; e != null; e = e.next)
                set.add(e.key);
        }
        return set;
    }

    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        Node<K, V>[] tab = table;
        if (tab == null) return list;
        for (Node<K, V> e : tab) {
            for (; e != null; e = e.next)
                list.add(e.value);
        }
        return list;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        Node<K, V>[] tab = table;
        if (tab == null) return set;
        for (Node<K, V> e : tab) {
            for (; e != null; e = e.next)
                set.add(new AbstractMap.SimpleEntry<>(e.key, e.value));
        }
        return set;
    }

    // ==================== MAIN 测试函数 ====================
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // 1. 插入
        map.put("A", 10);
        map.put("B", 20);
        map.put("C", 30);
        System.out.println("插入后大小：" + map.size());

        // 2. 查询
        System.out.println("get B：" + map.get("B"));

        // 3. 修改（覆盖）
        map.put("B", 999);
        System.out.println("修改后 B：" + map.get("B"));

        // 4. 判断存在
        System.out.println("包含 C？" + map.containsKey("C"));
        System.out.println("包含 D？" + map.containsKey("D"));

        // 5. 遍历 key
        System.out.print("\n所有 key：");
        for (String k : map.keySet()) System.out.print(k + " ");

        // 6. 遍历 value
        System.out.print("\n所有 value：");
        for (int v : map.values()) System.out.print(v + " ");

        // 7. 遍历 entry
        System.out.println("\n所有键值对：");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("  " + entry.getKey() + " = " + entry.getValue());
        }

        // 8. 删除
        map.remove("B");
        System.out.println("\n删除 B 后大小：" + map.size());

        // 9. 清空
        map.clear();
        System.out.println("清空后大小：" + map.size());
        System.out.println("是否为空：" + map.isEmpty());
    }
}
