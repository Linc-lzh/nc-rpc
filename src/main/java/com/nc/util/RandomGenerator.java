package com.nc.util;

import java.util.Arrays;
import java.util.Random;

public class RandomGenerator {
    public static void main(String[] args) {
        byte[] array = generate(20, 10);
        System.out.println(Arrays.toString(array));
    }

    public static byte[] generate(int limit, int num) {

        byte[] tempArray = new byte[limit];

        if (num <= 0) {
            for (int i = 0; i < limit; i++) {
                tempArray[i] = 0;
            }
            return tempArray;
        }
        if (num >= limit) {
            for (int i = 0; i < limit; i++) {
                tempArray[i] = 1;
            }
            return tempArray;
        }

        //在数组中随机填充num个1
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            int temp = Math.abs(random.nextInt()) % limit;
            while (tempArray[temp] == 1) {
                temp = Math.abs(random.nextInt()) % limit;
            }
            tempArray[temp] = 1;
        }
        return tempArray;
    }
}
