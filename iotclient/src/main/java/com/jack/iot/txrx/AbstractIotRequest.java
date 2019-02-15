package com.jack.iot.txrx;

import com.jack.iot.txrx.impl.Header;
import com.jack.iot.txrx.impl.IotRequest;

/**
 * @author jackzhous
 * @package com.jack.iot.txrx
 * @filename AbstractIotRequest
 * date on 2019/2/15 2:48 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public abstract class AbstractIotRequest implements IotRequest {


    @Override
    public void setConetent(byte[] data) {

    }

    @Override
    public byte[] getContent() {
        return new byte[0];
    }

    @Override
    public int getContentLenth() {
        return 0;
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }



    @Override
    public Header[] getHeaders() {
        return new Header[0];
    }

    @Override
    public Header getHeader(String key) {
        return null;
    }

    @Override
    public void setHeaders(Header[] headers) {

    }

    @Override
    public void setHeader(Header header) {

    }

    @Override
    public void removeHeader(Header header) {

    }

    @Override
    public void upgrade(Header header) {

    }
}
