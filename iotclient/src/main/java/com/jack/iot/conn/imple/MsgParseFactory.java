package com.jack.iot.conn.imple;

import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename MsgParseFactory
 * date on 2019/2/13 11:17 AM
 * @describe
 * 对象 <---> 字节数组转换接口
 * @email jackzhouyu@foxmail.com
 **/
public interface MsgParseFactory {


    byte[] parseByte(IotRequest request);


    IotResponse parseResponse(byte[] header, byte[] content);

}
