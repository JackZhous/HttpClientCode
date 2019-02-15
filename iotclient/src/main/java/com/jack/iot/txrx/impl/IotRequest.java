package com.jack.iot.txrx.impl;

/**
 * @author jackzhous
 * @package com.jack.iot.txrx.impl
 * @filename IotPostRequest
 * date on 2019/2/11 11:41 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface IotRequest extends IotMessage{


    void setConetent(byte[] data);

    byte[] getContent();

    int getContentLenth();

    boolean isRepeatable();
}
