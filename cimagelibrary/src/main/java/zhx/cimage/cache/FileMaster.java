package zhx.cimage.cache;

/**
 * Created by ${zhouxue} on 17/10/4 15: 19.
 * QQ:515278502
 */

public interface FileMaster {
    /**
     * 文件是否存在
     * @param path 文件路径
     * @return
     */
    public boolean isexist(String path);

    /**
     * 文件大小
     * @param path 文件路径
     * @return
     */

    public long fileSize(String path);

    /**
     *
     * @param url
     * @return
     */

    public String encodefileName(String url);

}
