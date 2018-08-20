package love.wangqi;

import love.wangqi.util.KetamaHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018/8/17 上午11:31
 */
public class VirtualConsistHash extends ConsistHash {
    Logger logger = LoggerFactory.getLogger(VirtualConsistHash.class);

    private int virtualNodeSize;

    public VirtualConsistHash(int nodeSize, int virtualNodeSize) {
        super(nodeSize);
        this.virtualNodeSize = virtualNodeSize;
    }

    @Override
    public VirtualConsistHash initNodes() {
        for (int i = 0; i < nodeSize; i++) {
            Long nodeHash = KetamaHash.calculate(i);
            Node node = new Node(i);
            hashNodeMap.put(nodeHash, node);
            for (int j = 0; j < virtualNodeSize; j++) {
                Long virtualNodeHash = KetamaHash.calculate(i + "#" + j);
                hashNodeMap.put(virtualNodeHash, node);
            }
        }
        hashArray = new Long[hashNodeMap.size()];
        hashArray = hashNodeMap.keySet().toArray(hashArray);
        return this;
    }
}
