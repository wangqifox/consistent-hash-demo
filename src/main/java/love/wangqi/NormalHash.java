package love.wangqi;

import love.wangqi.util.KetamaHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangqi
 * @description: 普通hash
 * @date: Created in 2018/8/17 上午11:12
 */
public class NormalHash implements Hash {
    Logger logger = LoggerFactory.getLogger(NormalHash.class);

    class Node {
        List<String> list;

        public Node() {
            list = new ArrayList<>();
        }

        void add(String data) {
            list.add(data);
        }

        int size() {
            return list.size();
        }
    }

    private Node[] nodes;
    private int nodeSize;
    private int total;

    @Override
    public NormalHash initNodes() {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
        }
        return this;
    }

    public NormalHash(int nodeSize) {
        this.nodeSize = nodeSize;
        nodes = new Node[nodeSize];
    }

    @Override
    public int add(String data) {
        long h = KetamaHash.calculate(data);
        int index = (int) (h % nodeSize);
        nodes[index].add(data);
        total++;
        return index;
    }

    @Override
    public void statistics() {
        double ave = total / (nodeSize * 1.0);
        logger.info("Ave: {}", ave);
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (Node node : nodes) {
            int count = node.size();
            if (count > max) { max = count; }
            if (count < min) { min = count; }
        }
        logger.info("Max: {} ({})", max, String.format("%.2f%%", Math.abs(max - ave) / ave * 100));
        logger.info("Min: {} ({})", min, String.format("%.2f%%", Math.abs(min - ave) / ave * 100));
    }
}
