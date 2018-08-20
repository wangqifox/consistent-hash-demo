package love.wangqi.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018/8/18 15:22
 */
public class KetamaHash {
    private static final int NUM0 = 0;
    private static final int NUM1 = 1;
    private static final int NUM2 = 2;
    private static final int NUM3 = 3;
    private static final int NUM4 = 4;
    private static final int NUM8 = 8;
    private static final int NUM16 = 16;
    private static final int NUM24 = 24;
    private static final int NUM_0XFF = 0xFF;

    private static MessageDigest md5 = null;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static byte[] computeMd5(String k) {
        md5.reset();
        md5.update(k.getBytes(StandardCharsets.UTF_8));
        return md5.digest();
    }

    public static long calculate(Object paramObj) {
        String paramValue = paramObj == null ? "null" : paramObj.toString();
        return HashAlgorithm.KETAMA_HASH.hash(computeMd5(paramValue), 0);
    }

    private enum HashAlgorithm {
        /**
         * Ketama Hash算法
         */
        KETAMA_HASH;

        public long hash(byte[] digest, int nTime) {
            long rv = ((long)(digest[NUM3 + nTime * NUM4] & NUM_0XFF) << NUM24)
                    | ((long)(digest[NUM2 + nTime * NUM4] & NUM_0XFF) << NUM16)
                    | ((long)(digest[NUM1 + nTime * NUM4] & NUM_0XFF) << NUM8)
                    | (digest[NUM0 + nTime * NUM4] & NUM_0XFF);
            return rv & 0xffffffffL;
        }
    }

}
