package love.wangqi;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018/8/20 上午8:51
 */
public interface Hash {
    /**
     * 初始化节点
     * @return
     */
    Hash initNodes();

    /**
     * 增加数据
     * @param data
     * @return
     */
    int add(String data);

    /**
     * 统计信息
     */
    void statistics();
}
