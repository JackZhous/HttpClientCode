package com.jack.iot.txrx;

import android.graphics.Bitmap;
import android.graphics.Shader;

import com.jack.iot.help.ConfigUtil;
import com.jack.iot.help.ShiftUtil;
import com.jack.iot.txrx.impl.Header;
import com.jack.iot.txrx.impl.IotResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.parse
 * @filename BasicIotReponse
 * date on 2019/2/13 10:55 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class BasicIotReponse implements IotResponse {

    Map<String, Header> map;

    public BasicIotReponse(Map<String, Header> map) {
        this.map = map;

    }

    public BasicIotReponse() {
        map = new HashMap<>();
    }

    @Override
    public Header getFrameUniqueID() {
        return map.get(ConfigUtil.FRAME_ID);
    }

    @Override
    public Header[] getHeaders() {
        Header[] headers = new Header[map.size()];
        int index = 0;
        for(String key : map.keySet()){
            headers[index++] = map.get(key);
        }
        return headers;
    }

    @Override
    public Header getHeader(String key) {
        return map.get(key);
    }

    @Override
    public byte[] getContent() {
        Header h = map.get(ConfigUtil.FRAME_CONTENT);
        return h == null ? null : h.getValue();
    }

    @Override
    public void setHeaders(Header[] headers) {
        for(Header header : headers){
            map.put(header.getName(), header);
        }
    }

    @Override
    public void setHeader(Header header) {
        map.put(header.getName(), header);
    }

    @Override
    public void removeHeader(Header header) {
        map.remove(header.getName());
    }

    @Override
    public void upgrade(Header header) {
        map.put(header.getName(), header);
    }


    @Override
    public void setOrderFiled(byte[] data) {
        Header header = map.get(ConfigUtil.FRAME_ORDER);
        if(header != null){
            header.setValue(data);
        }else {
            header = new BasicHeader(ConfigUtil.FRAME_ORDER, data);
            map.put(header.getName(), header);
        }
    }

    @Override
    public void setOrderOperatorField(byte[] data) {
        Header header = map.get(ConfigUtil.FRAME_ORDER_OPERATOR);
        if(header != null){
            header.setValue(data);
        }else {
            header = new BasicHeader(ConfigUtil.FRAME_ORDER_OPERATOR, data);
            map.put(header.getName(), header);
        }
    }

    @Override
    public void setUniqueId(long imei) {
        Header header = map.get(ConfigUtil.FRAME_ID);
        if(header != null){
            header.setValue(ShiftUtil.longToByte(imei));
        }else {
            header = new BasicHeader(ConfigUtil.FRAME_ID, ShiftUtil.longToByte(imei));
            map.put(header.getName(), header);
        }
    }

    @Override
    public void setContentLen(int len) {
        Header header = map.get(ConfigUtil.FRAME_CONTENT_LEN);
        if(header != null){
            header.setValue(ShiftUtil.intToByte(len));
        }else {
            header = new BasicHeader(ConfigUtil.FRAME_CONTENT_LEN, ShiftUtil.intToByte(len));
            map.put(header.getName(), header);
        }
    }

    @Override
    public byte[] getOrderFiled() {
        Header header = map.get(ConfigUtil.FRAME_ORDER);
        return header == null ? null : header.getValue();
    }

    @Override
    public byte[] getOrderOperatorFiled() {
        Header header = map.get(ConfigUtil.FRAME_ORDER_OPERATOR);
        return header == null ? null : header.getValue();
    }

    @Override
    public long getUniqueId() {
        Header header = map.get(ConfigUtil.FRAME_ID);
        if(header == null || header.getValue() == null){
            return 0;
        }
        return ShiftUtil.byteToLonng(header.getValue(), 0, 8);
    }
}
