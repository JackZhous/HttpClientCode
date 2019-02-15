package com.jack.iot.conn.imple;

import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename MsgFactory
 * date on 2019/2/12 3:50 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface MsgFactory {

    IotMsgWriter<IotRequest> create(OutputBuffer buffer);


    IotMsgReader<IotResponse> create(InputBuffer buffer);
}
