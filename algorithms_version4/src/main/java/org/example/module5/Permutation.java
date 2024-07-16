package org.example.module5;

import java.util.*;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/13 23:26
 **/
public class Permutation {

    public void isChild(int[] arr1, int[] arr2) {

    }

    public void moveZeroes(int[] nums) {

        int lastNotZeroIndex = nums.length;
        for (int i = nums.length - 1; i >= 0; i--) {
            int cur = nums[i];

            if (cur == 0) {
                if (lastNotZeroIndex != nums.length) {
                    for(int m = i; m < lastNotZeroIndex; m++) {
                        swap(nums, m, m + 1);
                    }
                    nums[lastNotZeroIndex] = 0;
                    lastNotZeroIndex--;
                }
            } else {
                if (lastNotZeroIndex == nums.length) {
                    lastNotZeroIndex = i;
                }
            }
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {

        int[] arr = {0,1,0,3,12};
        Permutation ext = new Permutation();
        ext.moveZeroes(arr);
    }
}
