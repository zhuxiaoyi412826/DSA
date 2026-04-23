package com.itheima.algorithm.exhaustion;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <h3>0-1 背包问题</h3>
 */
public class KnapsackProblem2 {
    static class Item {
        int index;
        int value;
        int weight;

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        public Item(int index, int weight, int value) {
            this.index = index;
            this.value = value;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Item(" + index + ")";
        }

        public int getValue() {
            return value;
        }
    }

    public static void allSolutions(Item[] items, int total) {
        int[][] dp = new int[items.length][total + 1];
        for (int i = 1; i <= total; i++) {
            Item item = items[0];
            for (int j = 0; j <= total; j++) {
                int y = 0;
                if (j >= item.weight) {
                    y = item.value;
                }
                dp[0][j] = y;
            }
        }
        for (int i = 1; i < items.length; i++) {
            Item item = items[i];
            for (int j = 0; j <= total; j++) {
                int x = dp[i - 1][j];
                int y = 0;
                if (j >= item.weight) {
                    y = dp[i - 1][j - item.weight] + item.value;
                }
                dp[i][j] = Math.max(x, y);
            }
        }
        System.out.print("重量：      ");
        for (int i = 1; i <= total; i++) {
            System.out.print(i + "        ");
        }
        System.out.println();
        for (int[] ints : dp) {
            System.out.println(Arrays.stream(ints).boxed().map(i -> String.format("%-8d", i)).collect(Collectors.joining(" ")));
        }
    }

    /*
        dp[i][j] 代表放入了前 i 个物品后，容量为 j 时的最大价值
        比如 dp[1][9] 代表放入了银子、金子后，容量为9 时的最大价值
        当计算 dp[2][10] 的时候其中 y = dp[1][9]+当前钻石(1) 的价值
重量：      1        2        3        4        5        6        7        8        9        10
0        0        0        0        0        30       30       30       30       30       30      银子(5)
0        0        0        0        1600     1600     1600     1600     1600     1630     1630    金子(4)
0        1000000  1000000  1000000  1000000  1001600  1001600  1001600  1001600  1001600  1001630 钻石(1)
0        1000000  1000000  1000000  1000000  1001600  1001600  1001600  1001600  1002400  1002400 红宝石(8)
     */

    public static void main(String[] args) {
//        Item[] items = {
//                new Item(0, 60, 10),
//                new Item(1, 100, 20),
//                new Item(2, 120, 30),
//                new Item(3, 30, 10),
//                new Item(4, 40, 20)
//        };
//        int totalWeight = 50;
        Item[] items = new Item[]{
                new Item(3, 5, 30),
                new Item(1, 4, 1600),
                new Item(0, 1, 1_000_000),
                new Item(2, 8, 2400),
        };
        int totalWeight = 10;
        allSolutions(items, totalWeight);

    }

}
