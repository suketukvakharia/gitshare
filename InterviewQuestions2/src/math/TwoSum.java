package math;

import java.util.HashMap;
import java.util.Map;

class TwoSum {
    /**
     * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
     *
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     *
     * You can return the answer in any order.
     *
     * https://leetcode.com/problems/two-sum/
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            Integer otherIndex = numToIndex.get(target - nums[i]);
            if (otherIndex != null) {
                return new int[] {i, otherIndex};

            }
            numToIndex.put(nums[i], i);
        }

        return new int[2];
    }
}