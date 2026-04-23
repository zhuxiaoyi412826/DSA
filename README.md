# 数据结构与算法实现

这是一个包含各种数据结构和算法实现的Java项目，旨在帮助学习者理解和掌握数据结构与算法的核心概念和实现方法。

## 项目结构

项目按照功能模块进行组织，主要分为以下几个部分：

### 数据结构（datastructure）

- **array**：数组相关实现
  - DynamicArray：动态数组
  - 各种LeetCode题目解决方案

- **avltree**：AVL树实现

- **binarysearchtree**：二叉搜索树实现
  - BSTTree1、BSTTree2：不同实现方式的二叉搜索树
  - 各种LeetCode题目解决方案

- **binarytree**：二叉树相关实现
  - 树的遍历算法（前序、中序、后序）
  - 各种LeetCode题目解决方案

- **blockingqueue**：阻塞队列实现

- **btree**：B树和B+树实现
  - BTree：B树实现
  - BPTree：B+树实现
  - bplustree：另一种B+树实现

- **circular**：循环链表实现

- **deque**：双端队列实现
  - 基于数组和链表的双端队列实现
  - 相关LeetCode题目解决方案

- **graph**：图算法实现
  - BFS、DFS：广度和深度优先搜索
  - 最短路径算法：Dijkstra、Bellman-Ford、Floyd-Warshall
  - 最小生成树算法：Kruskal、Prim
  - 拓扑排序

- **hashtable**：哈希表实现
  - 各种LeetCode题目解决方案

- **heap**：堆实现
  - 最大堆、最小堆
  - 堆排序
  - 相关LeetCode题目解决方案

- **linkedlist**：链表实现
  - 双向链表
  - 各种LeetCode题目解决方案

- **queue**：队列实现

- **stack**：栈实现

### 算法（algorithm）

- **binarysearch**：二分查找算法
  - 基础二分查找
  - 各种LeetCode题目解决方案

- **exhaustion**：穷举算法
  - 背包问题
  - 零钱兑换问题

- **greedy**：贪心算法
  - 活动选择问题
  - 分数背包问题
  - 霍夫曼编码

- **recursion_multi**：多分支递归
  - 斐波那契数列
  - 汉诺塔问题
  - 帕斯卡三角形

- **recursion_single**：单分支递归
  - 阶乘
  - 字符串反转
  - 递归实现的二分查找、冒泡排序等

- **sort**：排序算法
  - 冒泡排序、选择排序、插入排序
  - 归并排序、快速排序、堆排序
  - 桶排序、计数排序、基数排序
  - 希尔排序
  - 各种LeetCode题目解决方案

## 技术特点

1. **全面的实现**：涵盖了常见的数据结构和算法
2. **详细的注释**：代码中包含详细的注释，便于理解
3. **LeetCode题目**：包含了许多LeetCode题目的解决方案
4. **多种实现方式**：对于同一数据结构或算法，提供了多种实现方式
5. **模块化设计**：代码结构清晰，易于理解和扩展

## 环境要求

- JDK 8或更高版本
- Maven（用于构建项目）

## 如何使用

### 构建项目

```bash
mvn clean package
```

### 运行示例

每个类都包含main方法，可以直接运行查看效果。例如：

```bash
java -cp target/classes com.itheima.algorithm.sort.BubbleSort
```

### 测试B+树

B+树实现包含了文件输入输出功能，可以通过以下方式测试：

1. 创建一个输入文件，例如 `input.txt`，包含以下内容：
   ```
   Initialize(3)
   Insert(1, 1.0)
   Insert(2, 2.0)
   Insert(3, 3.0)
   Search(2)
   Delete(2)
   Search(2)
   ```

2. 运行B+树测试：
   ```bash
   java -cp target/classes com.itheima.datastructure.btree.bplustree input.txt
   ```

3. 查看输出文件 `output_file.txt` 中的结果

## 学习建议

1. **从基础开始**：先学习基本的数据结构，如数组、链表、栈、队列
2. **循序渐进**：逐步学习更复杂的数据结构，如树、图等
3. **理解原理**：不仅要知道如何实现，还要理解其工作原理和时间复杂度
4. **多做练习**：通过LeetCode题目巩固所学知识
5. **比较实现**：比较不同实现方式的优缺点

## 贡献

欢迎提交Issue和Pull Request，帮助改进这个项目。

## 许可证

本项目采用MIT许可证，详见LICENSE文件。

## 联系方式

如有问题或建议，请联系项目维护者。
