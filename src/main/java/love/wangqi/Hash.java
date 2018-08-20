package love.wangqi;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018/8/20 上午8:51
 */
public interface Hash {
    Hash initNodes();

    int add(String data);

    void statistics();
}
