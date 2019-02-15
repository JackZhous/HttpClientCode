package com.jack.iot.conn.imple;

import com.jack.iot.txrx.impl.IotMessage;

import java.io.IOException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename IotMsgReader
 * date on 2019/2/12 3:47 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface IotMsgReader<T extends IotMessage> {

    T readAndParse() throws IOException;
}
