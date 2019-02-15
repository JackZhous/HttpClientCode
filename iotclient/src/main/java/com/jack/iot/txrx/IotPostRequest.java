package com.jack.iot.txrx;

import com.jack.iot.help.ConfigUtil;
import com.jack.iot.help.ShiftUtil;
import com.jack.iot.txrx.impl.Header;
import com.jack.iot.txrx.impl.IotRequest;

import java.util.Map;

/**
 * @author jackzhous
 * @package com.jack.iot.txrx
 * @filename IotPostRequest
 * date on 2019/2/11 11:36 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class IotPostRequest extends BasicIotReponse implements IotRequest {

    public IotPostRequest(Map<String, Header> map) {
        super(map);
    }

    public IotPostRequest() {
        super();
        Header header = initHeader();
        map.put(header.getName(), header);
    }

    private Header initHeader(){
        byte[] tag = new byte[2];
        tag[0] = (byte)0xA5;
        tag[1] = (byte)0xA5;

        return new BasicHeader(ConfigUtil.FRAME_TAG, tag);
    }


    @Override
    public void setConetent(byte[] data) {
        Header header = new BasicHeader(ConfigUtil.FRAME_CONTENT, data);
        map.put(header.getName(), header);
    }

    @Override
    public int getContentLenth() {
        Header contentLen = map.get(ConfigUtil.FRAME_CONTENT_LEN);

        return ShiftUtil.byteToInt(contentLen.getValue(), 0, 4);
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }



}
