package com.jack.iot.conn.parse;

import com.jack.iot.conn.imple.IotMsgWriter;
import com.jack.iot.conn.imple.MsgParseFactory;
import com.jack.iot.conn.imple.OutputBuffer;
import com.jack.iot.help.ArgsUtils;
import com.jack.iot.help.ShiftUtil;
import com.jack.iot.txrx.impl.Header;
import com.jack.iot.txrx.impl.IotRequest;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.parse
 * @filename DefaultMsgWriter
 * date on 2019/2/12 3:45 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class DefaultMsgWriter implements IotMsgWriter<IotRequest> {

    private OutputBuffer out;

    private MsgParseFactory factory;


    public DefaultMsgWriter(OutputBuffer out, MsgParseFactory factory) {
        ArgsUtils.isEmpty("reader out", out);
        ArgsUtils.isEmpty("parse factory", factory);
        this.out = out;
        this.factory = factory;

    }

    @Override
    public void write(IotRequest msg) throws IOException {


        byte[] data = factory.parseByte(msg);

        out.write(data);
    }


}
