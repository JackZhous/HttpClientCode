package com.jack.iot.help;

/**
 * @author jackzhous
 * @package com.jack.iot.help
 * @filename ConfigUtil
 * date on 2019/2/13 11:04 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface ConfigUtil {

    /**
     * 帧数据的基本格式
     */
    String FRAME_TAG = "tag";                       //占用字节数 2
    String FRAME_ORDER = "order";                   //          1
    String FRAME_ORDER_OPERATOR = "operator";       //          1
    String FRAME_ID = "id";                         //          8
    String FRAME_CONTENT_LEN = "contentLen";        //          4
    String FRAME_CONTENT = "content";               //          待定


    /**
     * TCP连接步骤
     */
    int CONN_REQUEST = 4;       //请求连接

    int CONN_START = 1;         //启动连接

    int CONN_LEASE = 2;         //占有连接

    int CONN_RELEASE = 3;       //释放连接


    String TAG = "iot==>";
}
