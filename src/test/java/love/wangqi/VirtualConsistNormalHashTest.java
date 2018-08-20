package love.wangqi;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018/8/17 下午3:04
 */
public class VirtualConsistNormalHashTest {
    Logger logger = LoggerFactory.getLogger(VirtualConsistNormalHashTest.class);

    @Test
    public void testDist() {
        int nodesSize = 100;
        final int SIZE = 100000;

        int[] nums = RandomNum.getRandomNum(SIZE * nodesSize);
        logger.info("start");
        Hash virtualConsistHash = new VirtualConsistHash(nodesSize, 100).initNodes();
        for (int num : nums) {
            virtualConsistHash.add(String.valueOf(num));
        }
        logger.info("VirtualConsistHash Statistics");
        virtualConsistHash.statistics();

        Hash consistHash = new ConsistHash(nodesSize).initNodes();
        for (int num : nums) {
            consistHash.add(String.valueOf(num));
        }
        logger.info("ConsistHash Statistics");
        consistHash.statistics();
    }

    @Test
    public void testMigrate() {
        int nodesSize = 100;
        int nodesSizeInc = nodesSize + 1;
        int nodesSizeDec = nodesSize - 1;
        final int SIZE = 100000;

        Hash hash = new VirtualConsistHash(nodesSize, 100).initNodes();
        Hash hashInc = new VirtualConsistHash(nodesSizeInc, 100).initNodes();
        Hash hashDec = new VirtualConsistHash(nodesSizeDec, 100).initNodes();

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
