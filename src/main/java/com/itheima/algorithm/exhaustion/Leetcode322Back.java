package com.itheima.algorithm.exhaustion;

import java.util.*;

/**
 * <h3>零钱兑换</h3>
 */
public class Leetcode322Back {
    static int pc;
    static class Frame {
        int c;
        int exit;

        public Frame(int c, int exit) {
            this.c = c;
            this.exit = exit;
        }

        int c(){
            return c;
        }
    }
    public void coinChange(int[] coins, int amount) {

        LinkedList<Frame> stack = new LinkedList<>();
//        int coin
        stack.push(new Frame(coins[0],-1));
        while (!stack.isEmpty()) {
            int sum = stack.stream().map(Frame::c).mapToInt(Integer::intValue).sum();
            if(sum < amount) {
                pc = 1;
            } else if (sum == amount) {
                pc = 2;
            } else {
                pc = 3;
            }
            switch (pc) {
                case 1 -> {
                    stack.push(new Frame(coins[0], -1));
                }
                case 2 -> {
                    System.out.println("有解：" + stack);
                }
                case 3 -> {
                    pc = 100;
                }
            }
        }
    }

    public static void main(String[] args) {
        Leetcode322Back leetcode = new Leetcode322Back();
//        leetcode.coinChange(new int[]{1, 5, 10, 25}, 41);
        leetcode.coinChange(new int[]{1, 2, 3}, 5);
//        System.out.println(count);
    }
}
