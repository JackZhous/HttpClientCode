package com.jack.iot.conn.imple;

import java.io.IOException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename InputBuffer
 * date on 2019/2/12 11:28 AM
 * @describe 底层标准输入流接口
 * @email jackzhouyu@foxmail.com
 **/
public interface InputBuffer {


    int read(byte[] buf, int off, int len) throws IOException;


    int read(byte[] buf) throws IOException;
}
