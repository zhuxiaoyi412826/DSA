package com.itheima.datastructure.hashtab.a;

import java.util.*;
import java.util.HashMap;

public class MyHashMap<K, V> {
    // 基础链表节点
    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    // 红黑树节点 继承 Node
    static final class TreeNode<K, V> extends Node<K, V> {
        final TreeNode<K, V> getTreeNode(int h, Object k, TreeNode<K, V> root) {
        TreeNode<K, V> p = root;
        while (p != null) {
            int ph = p.hash;
            K pk = p.key;
            if (ph > h)
                p = p.left;
            else if (ph < h)
                p = p.right;
            else if ((k == pk) || (k != null && k.equals(pk)))
                return p;
            else
                break;
        }
        return null;
    }
 final TreeNode<K, V> putTreeVal(MyHashMap<K, V> map, Node<K, V>[] tab,
                                    int h, K k, V v) {
        TreeNode<K, V> root = (TreeNode<K, V>) tab[(tab.length - 1) & h];
        TreeNode<K, V> p = root;
        // 简易树插入
        while (true) {
            if (p.hash == h && (p.key == k || (k != null && k.equals(p.key))))
                return p;
            if (p.hash > h) {
                if (p.left == null) {
                    TreeNode<K, V> newNode = new TreeNode<>(h, k, v, null);
                    newNode.parent = p;
                    p.left = newNode;
                    return null;
                }
                p = p.left;
            } else {
                if (p.right == null) {
                    TreeNode<K, V> newNode = new TreeNode<>(h, k, v, null);
                    newNode.parent = p;
                    p.right = newNode;
                    return null;
                }
                p = p.right;
            }
        }
    }

final void split(MyHashMap<K, V> map, Node<K, V>[] newTab, int index, int oldCap) {
        TreeNode<K, V> loHead = null, loTail = null;
        TreeNode<K, V> hiHead = null, hiTail = null;
        int lc = 0, hc = 0;
        TreeNode<K, V> e = (TreeNode<K, V>) table[index];
        TreeNode<K, V> next;
        do {
            next = (TreeNode<K, V>) e.next;
            if ((e.hash & oldCap) == 0) {
                if (loTail == null) loHead = e;
                else loTail.next = e;
                loTail = e;
                lc++;
            } else {
                if (hiTail == null) hiHead = e;
                else hiTail.next = e;
                hiTail = e;
                hc++;
            }
        } while ((e = next) != null);

        // 长度<=6 退化为链表
        if (loTail != null) {
            loTail.next = null;
            if (lc <= UNTREEIFY_THRESHOLD)
                newTab[index] = untreeify(loHead);
            else
                newTab[index] = loHead;
        }
        if (hiTail != null) {
            hiTail.next = null;
            if (hc <= UNTREEIFY_THRESHOLD)
                newTab[index + oldCap] = untreeify(hiHead);
            else
                newTab[index + oldCap] = hiHead;
        }
    }

   final void removeTreeNode(MyHashMap<K, V> map, Node<K, V>[] tab, boolean movable) {
        // 极简删除：直接断链退链表
        untreeify((TreeNode<K,V>)this);
    }






        TreeNode<K, V> parent;
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        boolean red;

        TreeNode(int hash, K key, V value, Node<K, V> next) {
            super(hash, key, value, next);
        }
    }

    // 常量
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    // 链表长度>=8 树化
    private static final int TREEIFY_THRESHOLD = 8;
    // 元素<=6 退化为链表
    private static final int UNTREEIFY_THRESHOLD = 6;
    // 数组容量>=64 才允许树化
    private static final int MIN_TREEIFY_CAPACITY = 64;

    private Node<K, V>[] table;
    private int size;
    private int threshold;
    private final float loadFactor;

    // 构造
    public MyHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);

        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    private static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    // 扰动哈希
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    // ============ 树化、退树核心方法 ============
    final void treeifyBin(Node<K, V>[] tab, int index) {
        int n;
        Node<K, V> e;
        // 容量不足64 不树化，直接扩容
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
        else if ((e = tab[index]) != null) {
            TreeNode<K, V> hd = null, tl = null;
            // 链表转树节点双向链表
            do {
                TreeNode<K, V> p = new TreeNode<>(e.hash, e.key, e.value, null);
                if (tl == null)
                    hd = p;
                else {
                    p.parent = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            // 双向树链表转为红黑树
            tab[index] = hd;
            if (hd != null)
                treeify(hd);
        }
    }

    // 简单红黑树构建（极简核心规则）
    final void treeify(TreeNode<K, V> root) {
        TreeNode<K, V> cur = root;
        cur.red = false; // 根节点黑色
        while (cur.next != null) {
            TreeNode<K, V> next = (TreeNode<K, V>) cur.next;
            next.red = true;
            cur = next;
        }
    }

    // 树中查找节点
    final TreeNode<K, V> getTreeNode(int h, Object k, TreeNode<K, V> root) {
        TreeNode<K, V> p = root;
        while (p != null) {
            int ph = p.hash;
            K pk = p.key;
            if (ph > h)
                p = p.left;
            else if (ph < h)
                p = p.right;
            else if ((k == pk) || (k != null && k.equals(pk)))
                return p;
            else
                break;
        }
        return null;
    }

    // ============ put 核心（加入树化判断） ============
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;

        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;

        if ((p = tab[i = (n - 1) & hash]) == null) {
            tab[i] = new Node<>(hash, key, value, null);
        } else {
            Node<K, V> e;
            K k;

            // 第一个节点命中
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            // 如果是树节点，走树插入
            else if (p instanceof TreeNode)
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            
            // 链表遍历
            else {
                int binCount = 0;
                for (; ; binCount++) {
                    if ((e = p.next) == null) {
                        p.next = new Node<>(hash, key, value, null);
                        // 链表长度达到8 触发树化
                        if (binCount >= TREEIFY_THRESHOLD - 1)
                            treeifyBin(tab, i);
                        break;
                    }
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }

            // key存在，覆盖value（修改功能）
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

    // 树节点插入方法
    final TreeNode<K, V> putTreeVal(MyHashMap<K, V> map, Node<K, V>[] tab,
                                    int h, K k, V v) {
        TreeNode<K, V> root = (TreeNode<K, V>) tab[(tab.length - 1) & h];
        TreeNode<K, V> p = root;
        // 简易树插入
        while (true) {
            if (p.hash == h && (p.key == k || (k != null && k.equals(p.key))))
                return p;
            if (p.hash > h) {
                if (p.left == null) {
                    TreeNode<K, V> newNode = new TreeNode<>(h, k, v, null);
                    newNode.parent = p;
                    p.left = newNode;
                    return null;
                }
                p = p.left;
            } else {
                if (p.right == null) {
                    TreeNode<K, V> newNode = new TreeNode<>(h, k, v, null);
                    newNode.parent = p;
                    p.right = newNode;
                    return null;
                }
                p = p.right;
            }
        }
    }

    // ============ 扩容 resize ============
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
                    if (e.next == null) {
                        newTab[(newCap - 1) & e.hash] = e;
                    } else if (e instanceof TreeNode) {
                        // 树节点扩容迁移
                        ((TreeNode<K, V>)e).split(this, newTab, j, oldCap);
                    } else {
                        // 链表高低位拆分
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

    // 树节点扩容拆分、退树
    final void split(MyHashMap<K, V> map, Node<K, V>[] newTab, int index, int oldCap) {
        TreeNode<K, V> loHead = null, loTail = null;
        TreeNode<K, V> hiHead = null, hiTail = null;
        int lc = 0, hc = 0;
        TreeNode<K, V> e = (TreeNode<K, V>) table[index];
        TreeNode<K, V> next;
        do {
            next = (TreeNode<K, V>) e.next;
            if ((e.hash & oldCap) == 0) {
                if (loTail == null) loHead = e;
                else loTail.next = e;
                loTail = e;
                lc++;
            } else {
                if (hiTail == null) hiHead = e;
                else hiTail.next = e;
                hiTail = e;
                hc++;
            }
        } while ((e = next) != null);

        // 长度<=6 退化为链表
        if (loTail != null) {
            loTail.next = null;
            if (lc <= UNTREEIFY_THRESHOLD)
                newTab[index] = untreeify(loHead);
            else
                newTab[index] = loHead;
        }
        if (hiTail != null) {
            hiTail.next = null;
            if (hc <= UNTREEIFY_THRESHOLD)
                newTab[index + oldCap] = untreeify(hiHead);
            else
                newTab[index + oldCap] = hiHead;
        }
    }

    // 树转链表
    final Node<K, V> untreeify(TreeNode<K, V> root) {
        Node<K, V> head = null, tail = null;
        TreeNode<K, V> p = root;
        while (p != null) {
            Node<K, V> next = p.next;
            if (tail == null)
                head = p;
            else
                tail.next = p;
            tail = p;
            p = (TreeNode<K, V>) next;
        }
        tail.next = null;
        return head;
    }

    // ============ get 查找（支持树查找） ============
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
                // 树节点查找
                if (first instanceof TreeNode)
                    return getTreeNode(hash, key, (TreeNode<K, V>) first);
                // 链表遍历
                do {
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    // ============ remove 删除（支持树节点） ============
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
                if (p instanceof TreeNode)
                    node = getTreeNode(hash, key, (TreeNode<K, V>) p);
                else {
                    do {
                        if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                            node = e;
                            break;
                        }
                        p = e;
                    } while ((e = e.next) != null);
                }
            }

            if (node != null && (!matchValue || (v = node.value) == value || (value != null && value.equals(v)))) {
                if (node instanceof TreeNode)
                    ((TreeNode<K, V>)node).removeTreeNode(this, tab, movable);
                else if (node == p)
                    tab[index] = node.next;
                else
                    p.next = node.next;
                size--;
                return node;
            }
        }
        return null;
    }

    // 简易树节点删除
    final void removeTreeNode(MyHashMap<K, V> map, Node<K, V>[] tab, boolean movable) {
        // 极简删除：直接断链退链表
        untreeify((TreeNode<K,V>)this);
    }

    // ============ 通用方法不变 ============
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

    // ==================== 测试 Main 函数 ====================
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // 1.插入
        map.put("语文", 88);
        map.put("数学", 95);
        map.put("英语", 90);
        System.out.println("【插入后元素个数】：" + map.size());

        // 2.查找
        System.out.println("【查询数学】：" + map.get("数学"));

        // 3.修改(覆盖)
        map.put("数学", 100);
        System.out.println("【修改后数学】：" + map.get("数学"));

        // 4.判断key是否存在
        System.out.println("【是否包含英语】：" + map.containsKey("英语"));
        System.out.println("【是否包含体育】：" + map.containsKey("体育"));

        // 5.遍历key
        System.out.print("\n【遍历所有Key】：");
        for (String key : map.keySet()) {
            System.out.print(key + "  ");
        }

        // 6.遍历value
        System.out.print("\n【遍历所有Value】：");
        for (Integer val : map.values()) {
            System.out.print(val + "  ");
        }

        // 7.遍历键值对
        System.out.println("\n\n【遍历所有键值对】：");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        // 8.删除
        map.remove("英语");
        System.out.println("\n【删除英语后元素个数】：" + map.size());

        // 9.清空
        map.clear();
        System.out.println("【清空后元素个数】：" + map.size());
        System.out.println("【是否为空】：" + map.isEmpty());
    }
}
