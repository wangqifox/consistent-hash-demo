package love.wangqi;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018/8/17 上午11:10
 */
public class RandomNum {
    public static int[] getRandomNum(int size) {
        Random random = new Random();
        int[] nums = new int[size];
        for (int i = 0; i < size; i++) {
            nums[i] = Math.abs(random.nextInt());
        }
        return nums;
    }

    @Test
    public void testRandomNum() {
        int[] nums = getRandomNum(100);
        System.out.println(Arrays.toString(nums));
    }

}
