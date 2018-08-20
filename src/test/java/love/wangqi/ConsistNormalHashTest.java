package love.wangqi;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018/8/17 上午10:08
 */
public class ConsistNormalHashTest {
    Logger logger = LoggerFactory.getLogger(ConsistNormalHashTest.class);

    @Test
    public void testDist() {
        int nodesSize = 100;
        final int SIZE = 100000;
        int[] nums = RandomNum.getRandomNum(SIZE * nodesSize);
        Hash consistHash = new ConsistHash(nodesSize).initNodes();
        for (int num : nums) {
            consistHash.add(String.valueOf(num));
        }
        consistHash.statistics();
    }

    @Test
    public void testMigrate() {
        int nodesSize = 100;
        int nodesSizeInc = nodesSize + 1;
        int nodesSizeDec = nodesSize - 1;
        final int SIZE = 100000;

        Hash hash = new ConsistHash(nodesSize).initNodes();
        Hash hashInc = new ConsistHash(nodesSizeInc).initNodes();
        Hash hashDec = new ConsistHash(nodesSizeDec).initNodes();

        int changedInc = 0, changedDec = 0;
        int[] nums = RandomNum.getRandomNum(SIZE * nodesSize);

        for (int num : nums) {
            int hashIndex = hash.add(String.valueOf(num));
            int hashIncIndex = hashInc.add(String.valueOf(num));
            int hashDecIndex = hashDec.add(String.valueOf(num));

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
