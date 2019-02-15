package com.jack.iot.txrx.impl;

/**
 * @author jackzhous
 * @package com.jack.iot.txrx.impl
 * @filename IotMessage
 * date on 2019/2/11 11:43 AM
 * @describe
 * 处理每一帧数据的相同特性，例如帧头命令字段等
 * solve the same feature per frame，like the header order  filed
 * @email jackzhouyu@foxmail.com
 **/
public interface IotMessage {

    Header  getFrameUniqueID();            //get the unique id by frame

    Header[] getHeaders();

    Header getHeader(String key);

    byte[] getContent();

    void setHeaders(Header[] headers);

    void setHeader(Header header);

    void removeHeader(Header header);

    void upgrade(Header header);


    void setOrderFiled(byte[] data);

    void setOrderOperatorField(byte[] data);

    void setUniqueId(long imei);

    void setContentLen(int len);

    byte[] getOrderFiled();

    byte[] getOrderOperatorFiled();

    long getUniqueId();

}
