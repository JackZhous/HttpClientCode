package com.jack.iot.conn.imple;

import java.io.IOException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename OutputBuffer
 * date on 2019/2/12 11:30 AM
 * @describe 底层标准输出流接口
 * @email jackzhouyu@foxmail.com
 **/
public interface OutputBuffer {

    void write(byte[] buf, int off, int len) throws IOException;

    void write(byte[] buf) throws IOException;

    void write(byte data) throws IOException;
}
