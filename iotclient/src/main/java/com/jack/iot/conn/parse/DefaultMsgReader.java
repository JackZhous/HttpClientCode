package com.jack.iot.conn.parse;

import com.jack.iot.conn.imple.InputBuffer;
import com.jack.iot.conn.imple.IotMsgReader;
import com.jack.iot.conn.imple.MsgParseFactory;
import com.jack.iot.help.ArgsUtils;
import com.jack.iot.help.ShiftUtil;
import com.jack.iot.txrx.impl.IotResponse;

import java.io.IOException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.parse
 * @filename DefaultMsgReader
 * date on 2019/2/12 3:48 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class DefaultMsgReader implements IotMsgReader<IotResponse> {

    private InputBuffer input;
    private MsgParseFactory factory;

    public DefaultMsgReader(InputBuffer input, MsgParseFactory factory) {
        ArgsUtils.isEmpty("reader input", input);
        ArgsUtils.isEmpty("parse factory", factory);
        this.input = input;
        this.factory = factory;
    }

    @Override
    public IotResponse readAndParse() throws IOException {
        byte[] header = new byte[16];           //header占20个字节
        input.read(header);

        int contentLen = ShiftUtil.byteToInt(header, 11, 4);

        byte[] content = new byte[contentLen];

        input.read(content, 0 , contentLen);


        return factory.parseResponse(header, content);
    }
}
