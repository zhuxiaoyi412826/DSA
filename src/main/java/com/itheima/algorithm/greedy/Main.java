package com.itheima.algorithm.greedy;// Java implementation to print all
// the possible solutions of the
// 0/1 Knapsack problem

import java.util.*;

public class Main {

    // Utility function to find the maximum of the two
    // elements
    static int max(int a, int b) { return (a > b) ? a : b; }

    // Function to find the all the possible solutions of
    // the 0/1 knapSack problem
    static int knapSack(int W, List<Integer> wt,
                        List<Integer> val, int n) {

        // Mapping weights with Profits
        Map<Integer, Integer> umap = new HashMap<>();

        Set<List<Map.Entry<Integer, Integer> > > setSol
                = new HashSet<>();

        // Making Pairs and inserting into the map
        for (int i = 0; i < n; i++) {
            umap.put(wt.get(i), val.get(i));
        }

        int result = Integer.MIN_VALUE;
        int remaining_weight;
        int sum = 0;

        // Loop to iterate over all the possible
        // permutations of array
        do {
            sum = 0;

            // Initially bag will be empty
            remaining_weight = W;
            List<Map.Entry<Integer, Integer> > possible
                    = new ArrayList<>();

            // Loop to fill up the bag until there is no
            // weight such which is less than remaining
            // weight of the 0-1 knapSack
            for (int i = 0; i < n; i++) {
                if (wt.get(i) <= remaining_weight) {

                    remaining_weight -= wt.get(i);
                    Integer valAtWtI = umap.get(wt.get(i));
                    sum += valAtWtI;
                    possible.add(
                            new AbstractMap.SimpleEntry<>(
                                    wt.get(i), valAtWtI));
                }
            }
            Collections.sort(
                    possible,
                    Comparator.comparingInt(Map.Entry::getKey));
            if (sum > result) {
                result = sum;
            }
            if (!setSol.contains(possible)) {
                for (Map.Entry<Integer, Integer> sol :
                        possible) {
                    System.out.print(sol.getKey() + ": "
                            + sol.getValue()
                            + ", ");
                }
                System.out.println();
                setSol.add(possible);
            }

        } while (nextPermutation(wt));

        return result;
    }

    // Utility function to generate the next permutation
    static boolean nextPermutation(List<Integer> arr) {
        int i = arr.size() - 2;
        while (i >= 0 && arr.get(i) >= arr.get(i + 1)) {
            i--;
        }
        if (i < 0) {
            return false;
        }
        int j = arr.size() - 1;
        while (arr.get(j) <= arr.get(i)) {
            j--;
        }
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);

        Collections.reverse(arr.subList(i + 1, arr.size()));
        return true;
    }

    // Driver code
    public static void main(String[] args) {
        List<Integer> val
                = new ArrayList<>(Arrays.asList(3, 4, 5));
        List<Integer> wt
                = new ArrayList<>(Arrays.asList(2, 3, 4));
        int W = 8;
        int n = val.size();
        int maximum = knapSack(W, wt, val, n);
        System.out.println("Maximum Profit = " + maximum);
    }
}
// This code was contributed by rutikbhosale
