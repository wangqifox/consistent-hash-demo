package love.wangqi;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018/8/17 上午8:31
 */
public class NormalHashTest {
    Logger logger = LoggerFactory.getLogger(NormalHashTest.class);

    @Test
    public void testDist() {
        int nodesSize = 100;
        final int SIZE = 100000;
        int[] nums = RandomNum.getRandomNum(SIZE * nodesSize);
        Hash normalHash = new NormalHash(nodesSize).initNodes();
        for (int num : nums) {
            normalHash.add(String.valueOf(num));
        }
        normalHash.statistics();
    }

    @Test
    public void testMigrate() {
        int nodesSize = 100;
        int nodesSizeInc = nodesSize + 1;
        int nodesSizeDec = nodesSize - 1;

        final int SIZE = 100000;

        Hash normalHash = new NormalHash(nodesSize).initNodes();
        Hash normalHashInc = new NormalHash(nodesSizeInc).initNodes();
        Hash normalHashDec = new NormalHash(nodesSizeDec).initNodes();

        int changedInc = 0, changedDec = 0;
        int[] nums = RandomNum.getRandomNum(SIZE * nodesSize);

        for (int num : nums) {
            int hashIndex = normalHash.add(String.valueOf(num));
            int hashIncIndex = normalHashInc.add(String.valueOf(num));
            int hashDecIndex = normalHashDec.add(String.valueOf(num));

            if (hashIndex != hashIncIndex) {
                changedInc++;
            }
            if (hashIndex != hashDecIndex) {
                changedDec++;
            }
        }

        logger.info("Inc Changed: {} ({})", changedInc, String.format("%.2f%%", changedInc / (1.0 * SIZE * nodesSize) * 100));
        logger.info("Dec Changed: {} ({})", changedDec, String.format("%.2f%%", changedDec / (1.0 * SIZE * nodesSize) * 100));
    }
}
