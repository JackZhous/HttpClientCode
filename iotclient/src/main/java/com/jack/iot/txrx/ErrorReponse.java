package com.jack.iot.txrx;

import com.jack.iot.txrx.impl.Header;
import com.jack.iot.txrx.impl.IotResponse;

/**
 * @author jackzhous
 * @package com.jack.iot.txrx
 * @filename ErrorReponse
 * date on 2019/2/14 4:27 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class ErrorReponse implements IotResponse {
    private Throwable ex;
    private String errorMsg;

    public ErrorReponse(Throwable ex, String errorMsg) {
        this.ex = ex;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Throwable getEx() {
        return ex;
    }

    @Override
    public Header getFrameUniqueID() {
        return null;
    }

    @Override
    public void setOrderFiled(byte[] data) {

    }

    @Override
    public void setOrderOperatorField(byte[] data) {

    }

    @Override
    public void setUniqueId(long imei) {

    }

    @Override
    public void setContentLen(int len) {

    }

    @Override
    public byte[] getOrderFiled() {
        return new byte[0];
    }

    @Override
    public byte[] getOrderOperatorFiled() {
        return new byte[0];
    }

    @Override
    public long getUniqueId() {
        return 0;
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
    public byte[] getContent() {
        return new byte[0];
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
