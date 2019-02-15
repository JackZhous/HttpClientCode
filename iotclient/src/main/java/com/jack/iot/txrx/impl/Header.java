package com.jack.iot.txrx.impl;

/**
 * @author jackzhous
 * @package com.jack.iot.txrx.impl
 * @filename Header
 * date on 2019/2/12 3:26 PM
 * @describe
 * 通信帧消息头，
 * @email jackzhouyu@foxmail.com
 **/
public interface Header {

    String getName();

    byte[] getValue();

    void setValue(byte[] value);
}
