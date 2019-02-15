package com.jack.iot.conn.imple;

import com.jack.iot.txrx.impl.IotMessage;

import java.io.IOException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename IotMsgWriter
 * date on 2019/2/12 3:43 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface IotMsgWriter<T extends IotMessage> {


    void write(T msg) throws IOException;

}
