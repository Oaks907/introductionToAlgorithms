package org.example.module5;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/13 21:07
 **/
public class DutchNotionalFlag {

    private static final Integer RED = 1;

    private static final Integer WHITE = 2;

    private static final Integer BLUE = 3;

    public void sortByColor(int[] arr) {


        int redIndex = 0;
        int blueIndex = arr.length - 1;
        int left = redIndex;
        int right = blueIndex;
        int leftWhiteCount = 0;
        int rightWeightCount = 0;

        while (left < right) {

            int xColor;
            while (left < right) {
                xColor = color(arr[left]);
                if (RED.equals(xColor)) {
                    if (leftWhiteCount > 0) {
                        leftWhiteCount--;
                        swap(arr, redIndex, left);
                    }
                    redIndex++;
                    left++;

                } else if (WHITE.equals(xColor)){
                    left++;
                    leftWhiteCount++;
                } else {
                    break;
                }
            }

            int yColor;
            while(left < right) {
                yColor = color(arr[right]);
                if (yColor == BLUE) {
                    if (rightWeightCount > 0) {
                        rightWeightCount--;
                        swap(arr, blueIndex, right);
                    }

                    blueIndex--;
                    right--;
                } else if (yColor == WHITE){
                    right--;
                    rightWeightCount++;
                } else {
                    break;
                }
            }

            swap(arr, left++, blueIndex--);
            swap(arr, right--, redIndex++);
        }

        for (int i : arr) {
            System.out.print(i);
        }
        System.out.println();
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private Integer color(int i) {
        return i;
    }

    public static void main(String[] args) {
        int[] arr = {2, 2, 2, 1, 1, 1};
        DutchNotionalFlag exe = new DutchNotionalFlag();
        exe.sortByColor(arr);

        int[] arr1 = {1, 2, 3, 1, 2, 3, 1, 2, 3};
        exe.sortByColor(arr1);

        int[] arr2 = {2, 2, 3, 2, 2, 3, 2, 2, 3};
        exe.sortByColor(arr2);
    }
}
