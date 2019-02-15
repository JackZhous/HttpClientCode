package com.jack.iot.conn.parse;

import com.jack.iot.conn.imple.MsgParseFactory;
import com.jack.iot.help.ConfigUtil;
import com.jack.iot.help.ShiftUtil;
import com.jack.iot.txrx.BasicHeader;
import com.jack.iot.txrx.BasicIotReponse;
import com.jack.iot.txrx.impl.Header;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.parse
 * @filename IotMsgParseFactory
 * date on 2019/2/13 11:19 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class IotMsgParseFactory implements MsgParseFactory {

    ByteBuffer buffer;

    public IotMsgParseFactory() {
        buffer = ByteBuffer.allocate(100);
    }

    @Override
    public byte[] parseByte(IotRequest request) {
        buffer.put(request.getHeader(ConfigUtil.FRAME_TAG).getValue());  //调试帧头tag
        buffer.put(request.getOrderFiled());        //命令
        buffer.put(request.getOrderOperatorFiled());
        buffer.put(request.getFrameUniqueID().getValue());
        buffer.put(request.getHeader(ConfigUtil.FRAME_CONTENT_LEN).getValue());

        int msgLen = 16 + request.getContentLenth();
        if(request.getContentLenth() > 0){
            buffer.put(request.getContent());
        }

        buffer.clear();     //不影响底层数组内存，把position重置为0
        byte[] data = new byte[msgLen];

        buffer.get(data, 0, msgLen);

        return data;
    }

    @Override
    public IotResponse parseResponse(byte[] header, byte[] content) {
        Header tag = new BasicHeader(ConfigUtil.FRAME_TAG,
                ShiftUtil.splitByteArrays(header,0,2));
        Header order = new BasicHeader(ConfigUtil.FRAME_ORDER,
                ShiftUtil.splitByteArrays(header,2,1));
        Header orderOp = new BasicHeader(ConfigUtil.FRAME_ORDER_OPERATOR,
                ShiftUtil.splitByteArrays(header,3,1));
        Header id = new BasicHeader(ConfigUtil.FRAME_ID,
                ShiftUtil.splitByteArrays(header,4,8));
        Header contentLen = new BasicHeader(ConfigUtil.FRAME_CONTENT_LEN,
                ShiftUtil.splitByteArrays(header,12,4));

        Header con = new BasicHeader(ConfigUtil.FRAME_CONTENT,content);

        HashMap<String, Header> map = new HashMap<>();
        map.put(tag.getName(), tag);
        map.put(order.getName(), order);
        map.put(orderOp.getName(), orderOp);
        map.put(id.getName(), id);
        map.put(contentLen.getName(), contentLen);
        map.put(con.getName(), con);

        return new BasicIotReponse(map);
    }
}
