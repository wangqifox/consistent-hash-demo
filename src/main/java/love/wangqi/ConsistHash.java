package love.wangqi;

import love.wangqi.util.KetamaHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018/8/17 上午11:31
 */
public class ConsistHash implements Hash {
    Logger logger = LoggerFactory.getLogger(ConsistHash.class);

    class Node {
        private int index;
        private List<String> list;

        public Node(int index) {
            list = new ArrayList<>();
            this.index = index;
        }

        void add(String data) {
            list.add(data);
        }

        int size() {
            return list.size();
        }

        public int getIndex() {
            return index;
        }
    }

    TreeMap<Long, Node> hashNodeMap = new TreeMap<>();
    Long[] hashArray;
    int nodeSize;
    int total;

    public ConsistHash(int nodeSize) {
        this.nodeSize = nodeSize;
    }

    @Override
    public ConsistHash initNodes() {
        for (int i = 0; i < nodeSize; i++) {
            Long nodeHash = KetamaHash.calculate(i);
            Node node = new Node(i);
            hashNodeMap.put(nodeHash, node);
        }
        hashArray = new Long[hashNodeMap.size()];
        hashArray = hashNodeMap.keySet().toArray(hashArray);
        return this;
    }

    private Long findHash(Long dataHash, int left, int right) {
        int mid = (left + right) / 2;
        if (dataHash.equals(hashArray[mid])) {
            return hashArray[mid];
        }
        if (right - left == 1) {
            if (dataHash < hashArray[left]) {
                return hashArray[left];
            }
            if (dataHash > hashArray[left] && dataHash <= hashArray[right]) {
                return hashArray[right];
            }
            return null;
        }
        if (dataHash < hashArray[mid]) {
            return findHash(dataHash, left, mid);
        }
        if (dataHash > hashArray[mid]) {
            return findHash(dataHash, mid, right);
        }
        return null;
    }

//    private Node getNode(Long dataHash) {
//        for (Map.Entry<Long, Node> entry : hashNodeMap.entrySet()) {
//            if (dataHash < entry.getKey()) {
//                return entry.getValue();
//            }
//        }
//        return hashNodeMap.firstEntry().getValue();
//    }

    private Node getNode(Long dataHash) {
        Long hash = findHash(dataHash, 0, hashNodeMap.size() - 1);

        if (hash == null) {
            return hashNodeMap.firstEntry().getValue();
        }
        return hashNodeMap.get(hash);
    }

    @Override
    public int add(String data) {
        Long m = KetamaHash.calculate(data);
        Node node = getNode(m);
        node.add(data);
        total++;
        return node.getIndex();
    }

    @Override
    public void statistics() {
        double ave = total / (nodeSize * 1.0);
        logger.info("Ave: {}", ave);
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (Map.Entry<Long, Node> entry : hashNodeMap.entrySet()) {
            Node node = entry.getValue();
            int count = node.size();
            if (count > max) { max = count; }
            if (count < min) { min = count; }
        }
        logger.info("Max: {} ({})", max, String.format("%.2f%%", Math.abs(max - ave) / ave * 100));
        logger.info("Min: {} ({})", min, String.format("%.2f%%", Math.abs(min - ave) / ave * 100));
    }

}
