package com.itheima.algorithm.binarysearch;

public class binarySearchBasic1 {
    public static void main(String[] args) {

        int[] a = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        int target = 9;
        
        // 调用方法，接收数组（里面装了 索引 + 次数）
        int[] result = binarySearch(a, target);
        
        int index = result[0]; // 索引

        if (index != -1) {
            System.out.println("目标值在数组中");
            System.out.println("找到的索引位置是: " + index);
        } else {
            System.out.println("目标值不在数组中");
        }
    }

    // 把返回值改成 int[]，可以同时返回 索引 和 次数
    public static int[] binarySearch(int[] a, int target) {
        int i = 0, j = a.length ;
        
        //  while (i <= j) {
        //     count++; // 每次循环 +1
        //     int m = (i + j) >>> 1;
            
        //     if (target < a[m]) {
        //         j = m - 1;
        //     } else if (a[m] < target) {
        //         i = m + 1;
        //     } else {
        //         // 找到：返回 {索引, 次数}
        //         return new int[]{m, count};
        //     }
        // }
        while (1 < j - i) {
        int m = (i + j) >>> 1;
        if (target < a[m]) {
            j = m;
        } else {
            i = m;
        }

    }
        
    // return (a[i] == target) ? i : -1;

     if (a[i] == target) {
            return new int[]{i, };
        } else {
            return new int[]{-1, };
        }
}
}