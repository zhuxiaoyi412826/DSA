package com.itheima.datastructure.stack;

public class MyStack {
    // 栈底层用数组存储
    private int[] elements;
    // 栈顶指针，指向当前栈顶元素下标
    private int top;
    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;

    // 构造方法：初始化栈
    public MyStack() {
        // 初始化并创建数组，容量为默认值
        elements = new int[DEFAULT_CAPACITY];
        top = -1; // 空栈时 top = -1
    }

    // 入栈 push
    public void push(int val) {
        // 栈满时进行扩容
        if (top == elements.length - 1) {
            // 扩容为原来的2倍
            int newCapacity = elements.length * 2;
            int[] newElements = new int[newCapacity];
            // 复制原数组元素
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;
            System.out.println("栈已满，已扩容至" + newCapacity + "容量");
        }
        elements[++top] = val;
    }

    // 出栈 pop
    public int pop() {
        if (isEmpty()) {
            System.out.println("栈为空，不能出栈");
            // 异常处理简化，实际可用 throw new EmptyStackException()
            return -1;
        }
        return elements[top--];
    }

    // 获取栈顶元素（不删除）
    public int peek() {
        if (isEmpty()) {
            System.out.println("栈为空");
            return -1;
        }
        return elements[top];
    }

    // 判断栈是否为空
    public boolean isEmpty() {
        return top == -1;
    }

    // 获取栈大小
    public int size() {
        return top + 1;
    }
    
    // 全部出栈
    public int[] popAll() {
        int[] result = new int[size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = pop();
        }
        return result;
    }

    // 打印栈内容（从栈底到栈顶）
    public void printStack() {
        System.out.print("栈内容 [栈底 → 栈顶]：");
        for (int i = 0; i <= top; i++) {
            System.out.print(elements[i] + " ");
        }
        System.out.println();
    }

    // 验证测试方法
    public static void main(String[] args) {
        MyStack stack = new MyStack();

        // 测试入栈
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.printStack(); // 10 20 30

    //     // 测试栈顶
        System.out.println("栈顶元素：" + stack.peek()); // 30
        System.out.println("栈大小：" + stack.size());   // 3

    //     // 测试出栈
        System.out.println("出栈元素：" + stack.pop());  // 30
        stack.printStack(); // 10 20

        // 连续出栈
        stack.pop();
        stack.pop();
        System.out.println("是否为空：" + stack.isEmpty()); // true

    //     // 测试空栈出栈
        stack.pop();
        
        // 测试扩容功能
        System.out.println("\n===== 测试扩容功能 =====");
        MyStack stack2 = new MyStack();
        // 压入超过默认容量的元素
        for (int i = 1; i <= 15; i++) {
            stack2.push(i * 10);
        }
        stack2.printStack();
        System.out.println("栈大小：" + stack2.size());
        
        // 测试全部出栈
        System.out.println("\n===== 测试全部出栈 =====");
        int[] poppedElements = stack2.popAll();
        System.out.print("全部出栈元素：");
        for (int element : poppedElements) {
            System.out.print(element + " ");
        }
        System.out.println();
        System.out.println("出栈后栈是否为空：" + stack2.isEmpty());
        System.out.println("出栈后栈大小：" + stack2.size());
     }
    
}
