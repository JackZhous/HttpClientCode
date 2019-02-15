package com.jack.iot.conn.parse;

import com.jack.iot.conn.imple.InputBuffer;
import com.jack.iot.conn.imple.IotMsgReader;
import com.jack.iot.conn.imple.IotMsgWriter;
import com.jack.iot.conn.imple.MsgFactory;
import com.jack.iot.conn.imple.MsgParseFactory;
import com.jack.iot.conn.imple.OutputBuffer;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.parse
 * @filename DefaultIotMsgFactory
 * date on 2019/2/12 3:50 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class DefaultIotMsgFactory implements MsgFactory {

    public static DefaultIotMsgFactory INSTANCE = new DefaultIotMsgFactory();


    private MsgParseFactory parseFactory;

    public DefaultIotMsgFactory() {
        parseFactory = new IotMsgParseFactory();
    }

    @Override
    public IotMsgWriter<IotRequest> create(OutputBuffer buffer) {
        return new DefaultMsgWriter(buffer, parseFactory);
    }

    @Override
    public IotMsgReader<IotResponse> create(InputBuffer buffer) {
        return new DefaultMsgReader(buffer, parseFactory);
    }
}
